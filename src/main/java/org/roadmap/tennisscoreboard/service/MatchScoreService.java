package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.PlayerScore;
import org.roadmap.tennisscoreboard.domain.SetScoreInfo;
import org.roadmap.tennisscoreboard.domain.strategy.DefaultScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TennisScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TiebreakScoringStrategy;
import org.roadmap.tennisscoreboard.dto.FinishedMatchDto;
import org.roadmap.tennisscoreboard.dto.PlayerDto;
import org.roadmap.tennisscoreboard.exception.ExceptionMessages;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

public class MatchScoreService {
    private static final int GAMES_TO_START_TIEBREAK = 6;
    private final OngoingMatchService ongoingMatchService;
    private final FinishedMatchesPersistenceService finishedMatchesService;

    public MatchScoreService(
            OngoingMatchService ongoingMatchService,
            FinishedMatchesPersistenceService finishedMatchesService
    ) {
        this.ongoingMatchService = ongoingMatchService;
        this.finishedMatchesService = finishedMatchesService;
    }

    public void givePoint(Integer playerId, UUID matchId) {
        OngoingMatch match = ongoingMatchService.getById(matchId)
                .orElseThrow(() -> new NoSuchElementException(
                        String.format(
                                ExceptionMessages.MATCH_NOT_FOUND,
                                matchId
                        )));
        PlayerScore firstPlayerScore = match.getScoreModel().getFirstPlayerScore();
        PlayerScore secondPlayerScore = match.getScoreModel().getSecondPlayerScore();

        PlayerScore scoringPlayer = getPlayerScore(playerId, match);
        PlayerScore opponent = match.getScoreModel().getOpponentPlayerScore(scoringPlayer);

        int firstPlayerSetBefore = firstPlayerScore.getPlayerSet();
        int secondPlayerSetBefore = secondPlayerScore.getPlayerSet();
        int firstPlayerGameBefore = firstPlayerScore.getPlayerGame();
        int secondPlayerGameBefore = secondPlayerScore.getPlayerGame();

        TennisScoringStrategy scoringStrategy = getScoringStrategy(match);
        scoringStrategy.pointWon(scoringPlayer, opponent);

        int firstPlayerSetAfter = firstPlayerScore.getPlayerSet();
        int secondPlayerSetAfter = secondPlayerScore.getPlayerSet();

        if (firstPlayerSetBefore < firstPlayerSetAfter) {
            match.setTiebreak(false);
            int setNumber = match.getSetsHistory().size();
            SetScoreInfo scoreInfo = new SetScoreInfo(
                    firstPlayerGameBefore + 1,
                    secondPlayerGameBefore
            );
            match.getSetsHistory().put(setNumber, scoreInfo);
        } else {
            if (secondPlayerSetBefore < secondPlayerSetAfter) {
                match.setTiebreak(false);
                int setNumber = match.getSetsHistory().size();
                SetScoreInfo scoreInfo = new SetScoreInfo(
                        firstPlayerGameBefore,
                        secondPlayerGameBefore + 1
                );
                match.getSetsHistory().put(setNumber, scoreInfo);
            }
        }

        if (scoringPlayer.getPlayerGame() == GAMES_TO_START_TIEBREAK && opponent.getPlayerGame() == GAMES_TO_START_TIEBREAK) {
            match.setTiebreak(true);
        }

        if (scoringStrategy.isMatchWon(scoringPlayer)) {
            Optional<PlayerDto> winner = match.getScoreModel().getWinner(match.getFirstPlayer(), match.getSecondPlayer());
            if (winner.isEmpty()) {
                throw new IllegalStateException(ExceptionMessages.MISSING_WINNER);
            }
            match.setFinished(true);
            FinishedMatchDto finishedMatch = new FinishedMatchDto(
                    null,
                    match.getFirstPlayer(),
                    match.getSecondPlayer(),
                    winner.get()
            );
            finishedMatchesService.save(finishedMatch);
        }
    }

    private static TennisScoringStrategy getScoringStrategy(OngoingMatch match) {
        TennisScoringStrategy scoringStrategy;
        if (match.isTiebreak()) {
            scoringStrategy = new TiebreakScoringStrategy();
        } else {
            scoringStrategy = new DefaultScoringStrategy();
        }
        return scoringStrategy;
    }

    private PlayerScore getPlayerScore(Integer playerId, OngoingMatch match) {
        if (match.getFirstPlayer().id().equals(playerId)) {
            return match.getScoreModel().getFirstPlayerScore();
        }
        if (match.getSecondPlayer().id().equals(playerId)) {
            return match.getScoreModel().getSecondPlayerScore();
        }
        throw new NoSuchElementException(ExceptionMessages.PLAYER_NOT_IN_MATCH);
    }
}
