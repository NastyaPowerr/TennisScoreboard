package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.dto.MatchDto;
import org.roadmap.tennisscoreboard.dto.Score;
import org.roadmap.tennisscoreboard.dto.response.MatchDtoResponse;
import org.roadmap.tennisscoreboard.dto.response.PlayerDtoResponse;
import org.roadmap.tennisscoreboard.entity.Match;
import org.roadmap.tennisscoreboard.entity.Player;
import org.roadmap.tennisscoreboard.repository.MatchRepository;
import org.roadmap.tennisscoreboard.repository.PlayerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MatchService {
    private static final Integer FIRST_AND_SECOND_SCORE_ADD = 15;
    private static final Integer THIRD_SCORE_ADD = 10;
    private static final Integer AD = 100;
    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final Map<UUID, MatchDto> matches;

    public MatchService(MatchRepository matchRepository, PlayerRepository playerRepository) {
        this.matches = new ConcurrentHashMap<>();
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
    }

    public UUID create(MatchDto matchDto) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, matchDto);
        return uuid;
    }

    public MatchDto getById(UUID id) {
        return matches.get(id);
    }

    public List<MatchDtoResponse> getAll() {
        List<Match> matches = matchRepository.findAll();
        List<MatchDtoResponse> responseMatches = new ArrayList<>();
        for (Match match : matches) {
            PlayerDtoResponse firstPlayer = new PlayerDtoResponse(match.getFirstPlayer().getName());
            PlayerDtoResponse secondPlayer = new PlayerDtoResponse(match.getSecondPlayer().getName());
            PlayerDtoResponse winner = new PlayerDtoResponse(match.getWinner().getName());

            MatchDtoResponse matchDto = new MatchDtoResponse(firstPlayer, secondPlayer, winner);
            responseMatches.add(matchDto);
        }
        return responseMatches;
    }

    public void givePoint(Integer playerId, MatchDto match) {
        Integer firstPlayerId = match.getFirstPlayerId();
        Integer secondPlayerId = match.getSecondPlayerId();

        Score currenctScore = match.getScore();

        int firstPlayerScore = currenctScore.getFirstPlayerScore();
        int secondPlayerScore = currenctScore.getSecondPlayerScore();

        int firstPlayerGame = currenctScore.getFirstPlayerGame();
        int secondPlayerGame = currenctScore.getSecondPlayerGame();

        int firstPlayerSet = currenctScore.getFirstPlayerSet();
        int secondPlayerSet = currenctScore.getSecondPlayerSet();
        if (firstPlayerGame == 6) {
            currenctScore.setFirstPlayerSet(firstPlayerSet + 1);
            currenctScore.setFirstPlayerScore(0);
            currenctScore.setFirstPlayerGame(0);
            currenctScore.setSecondPlayerGame(0);
            currenctScore.setSecondPlayerScore(0);
            match.setScore(currenctScore);
            return;
        }
        if (secondPlayerGame == 6) {
            currenctScore.setSecondPlayerSet(secondPlayerSet + 1);
            currenctScore.setFirstPlayerScore(0);
            currenctScore.setFirstPlayerGame(0);
            currenctScore.setSecondPlayerGame(0);
            currenctScore.setSecondPlayerScore(0);
            match.setScore(currenctScore);
            return;
        }

        if (firstPlayerSet == 2) {
            save(match, playerRepository.findById(match.getFirstPlayerId()));
            return;
        }
        if (secondPlayerSet == 2) {
            save(match, playerRepository.findById(match.getSecondPlayerId()));
            return;
        }

        // FOR TEST ONLY:
        currenctScore.setFirstPlayerSet(1);
        currenctScore.setFirstPlayerGame(5);
        match.setScore(currenctScore);

        if (playerId.equals(firstPlayerId)) {
            System.out.println("FIRST PLAYER SCORED");
            if (firstPlayerScore == 0 || firstPlayerScore == 15) {
                currenctScore.setFirstPlayerScore(firstPlayerScore + FIRST_AND_SECOND_SCORE_ADD);
                match.setScore(currenctScore);
                return;
            }
            if (firstPlayerScore == 30) {
                currenctScore.setFirstPlayerScore(firstPlayerScore + THIRD_SCORE_ADD);
                match.setScore(currenctScore);
                return;
            }
            if (firstPlayerScore == 40) {
                if (secondPlayerScore == AD) {
                    currenctScore.setSecondPlayerScore(40);
                    match.setScore(currenctScore);
                    return;
                }
                currenctScore.setFirstPlayerScore(AD);
                match.setScore(currenctScore);
                return;
            }
            if (firstPlayerScore == AD) {
                currenctScore.setFirstPlayerScore(0);
                currenctScore.setFirstPlayerGame(firstPlayerGame + 1);
                match.setScore(currenctScore);
                return;
            }
        }
        if (playerId.equals(secondPlayerId)) {
            System.out.println("SECOND PLAYER SCORED");
            if (secondPlayerScore == 0 || secondPlayerScore == 15) {
                currenctScore.setSecondPlayerScore(secondPlayerScore + FIRST_AND_SECOND_SCORE_ADD);
                match.setScore(currenctScore);
                return;
            }
            if (secondPlayerScore == 30) {
                currenctScore.setSecondPlayerScore(secondPlayerScore + THIRD_SCORE_ADD);
                match.setScore(currenctScore);
                return;
            }
            if (secondPlayerScore == 40) {
                if (firstPlayerScore == AD) {
                    currenctScore.setFirstPlayerScore(40);
                    match.setScore(currenctScore);
                    return;
                }
                currenctScore.setSecondPlayerScore(AD);
                match.setScore(currenctScore);
                return;
            }
            if (secondPlayerScore == AD) {
                currenctScore.setSecondPlayerScore(0);
                currenctScore.setSecondPlayerGame(secondPlayerGame + 1);
                match.setScore(currenctScore);
                return;
            }
        }
        System.out.println(match);
    }

    public MatchDtoResponse save(MatchDto match, Player winner) {
        Player firstPlayer = playerRepository.findById(match.getFirstPlayerId());
        Player secondPlayer = playerRepository.findById(match.getSecondPlayerId());
        Match entity = new Match(firstPlayer, secondPlayer, winner);

        Match savedEntity = matchRepository.save(entity);

        PlayerDtoResponse firstPlayerDto = new PlayerDtoResponse(firstPlayer.getName());
        PlayerDtoResponse secondPlayerDto = new PlayerDtoResponse(secondPlayer.getName());
        return new MatchDtoResponse(
                firstPlayerDto,
                secondPlayerDto,
                new PlayerDtoResponse(winner.getName())
        );
    }
}
