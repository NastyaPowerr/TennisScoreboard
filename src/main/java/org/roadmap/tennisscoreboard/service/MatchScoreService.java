package org.roadmap.tennisscoreboard.service;

import org.hibernate.ObjectNotFoundException;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.PlayerScore;
import org.roadmap.tennisscoreboard.domain.SetScoreInfo;
import org.roadmap.tennisscoreboard.domain.strategy.DefaultScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TennisScoringStrategy;
import org.roadmap.tennisscoreboard.domain.strategy.TiebreakScoringStrategy;
import org.roadmap.tennisscoreboard.entity.Player;

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
                                "Match with id %s not found.",
                                matchId
                        )));
        PlayerScore scoringPlayer = getPlayerScore(playerId, match);
        PlayerScore opponent = match.getScoreModel().getOpponentPlayerScore(scoringPlayer);

        TennisScoringStrategy scoringStrategy = getScoringStrategy(match);

        int firstPlayerSetBefore = scoringPlayer.getPlayerSet();
        int secondPlayerSetBefore = opponent.getPlayerSet();
        int firstPlayerGameBefore = scoringPlayer.getPlayerGame();
        int secondPlayerGameBefore = opponent.getPlayerGame();

        scoringStrategy.pointWon(scoringPlayer, opponent);

        int firstPlayerSetAfter = scoringPlayer.getPlayerSet();
        int secondPlayerSetAfter = opponent.getPlayerSet();

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

        if (scoringStrategy.isMatchWon(scoringPlayer, opponent)) {
            Optional<Player> winner = match.getScoreModel().getWinner(match.getFirstPlayer(), match.getSecondPlayer());
            if (winner.isEmpty()) {
                throw new IllegalStateException("Cannot have a won match without a winner.");
            }
            match.setFinished(true);
            finishedMatchesService.save(match, winner.get());
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
        PlayerScore playerScore = null;
        if (match.getFirstPlayer().getId().equals(playerId)) {
            playerScore = match.getScoreModel().getFirstPlayerScore();
        }
        if (match.getSecondPlayer().getId().equals(playerId)) {
            playerScore = match.getScoreModel().getSecondPlayerScore();
        }
        if (playerScore == null) {
            throw new NoSuchElementException("Player with that id is not in that match.");
        }
        return playerScore;
    }
}
