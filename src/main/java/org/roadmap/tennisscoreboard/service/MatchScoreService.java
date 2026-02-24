package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.entity.Player;

public class MatchScoreService {
    private static final Integer FIRST_AND_SECOND_SCORE_ADD = 15;
    private static final Integer THIRD_SCORE_ADD = 10;
    private static final Integer AD = 100;
    private final MatchService matchService;

    public MatchScoreService(MatchService matchService) {
        this.matchService = matchService;
    }

    public void givePoint(Integer playerId, OngoingMatch match) {
        Integer firstPlayerId = match.getFirstPlayer().getId();
        Integer secondPlayerId = match.getSecondPlayer().getId();

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
            Player winner = match.getFirstPlayer();
            matchService.save(match, winner);
            // rendering
            return;
        }
        if (secondPlayerSet == 2) {
            Player winner = match.getSecondPlayer();
            matchService.save(match, winner);
            // rendering
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
}
