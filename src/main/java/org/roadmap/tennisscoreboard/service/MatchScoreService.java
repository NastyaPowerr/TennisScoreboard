package org.roadmap.tennisscoreboard.service;

import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Point;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.entity.Player;

public class MatchScoreService {
    private static final Integer SETS_TO_WIN = 2;
    private static final Integer TIE_BREAK = 6;
    private static final Integer GAMES_TO_WIN_SET = 6;
    private final MatchService matchService;

    public MatchScoreService(MatchService matchService) {
        this.matchService = matchService;
    }

    public void givePoint(Integer playerId, OngoingMatch match) {
        System.out.println("Player's id is: " + playerId);

        Score currenctScore = match.getScore();

        Point firstPlayerPoint = currenctScore.getFirstPlayerPoint();
        Point secondPlayerPoint = currenctScore.getSecondPlayerPoint();

        if (currenctScore.getFirstPlayerGame() == GAMES_TO_WIN_SET) {
            currenctScore.setFirstPlayerSet(currenctScore.getFirstPlayerSet() + 1);
            clearPoints(currenctScore);
            clearGames(currenctScore);
            return;
        }

        if (currenctScore.getSecondPlayerGame() == GAMES_TO_WIN_SET) {
            currenctScore.setSecondPlayerSet(currenctScore.getSecondPlayerSet() + 1);
            clearPoints(currenctScore);
            clearGames(currenctScore);
            return;
        }

        if (firstPlayerPoint.equalTo(secondPlayerPoint) && firstPlayerPoint != Point.AD) {
            if (firstPlayerScored(playerId, match)) {
                currenctScore.setFirstPlayerPoint(nextPoint(firstPlayerPoint));
            } else {
                currenctScore.setSecondPlayerPoint(nextPoint(secondPlayerPoint));
            }
            return;
        }

        // получает преимущество при 40-40 (AD)
        if (firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.FORTY) {
            if (firstPlayerScored(playerId, match)) {
                currenctScore.setFirstPlayerPoint(Point.AD);
            } else {
                currenctScore.setSecondPlayerPoint(Point.AD);
            }
            return;
        }

        // у первого игрока преимущество (AD), выигрывает если забивает
        // если второй игрок забивает, то у первого сбрасывается преимущество
        if (firstPlayerPoint == Point.AD && secondPlayerPoint == Point.FORTY) {
            if (firstPlayerScored(playerId, match)) {
                firstPlayerWinGame(currenctScore);
            } else {
                currenctScore.setFirstPlayerPoint(Point.FORTY);
            }
            return;
        }

        // у второго игрока преимущество (AD), выигрывает если забивает
        // если первый игрок забивает, то у второго сбрасывается преимущество
        if (firstPlayerPoint == Point.FORTY && secondPlayerPoint == Point.AD) {
            if (firstPlayerScored(playerId, match)) {
                currenctScore.setSecondPlayerPoint(Point.FORTY);
            } else {
                secondPlayerWinGame(currenctScore);
            }
            return;
        }

        // простые случаи
        if (firstPlayerScored(playerId, match)) {
            if (currenctScore.getFirstPlayerPoint() == Point.FORTY) {
                firstPlayerWinGame(currenctScore);
                return;
            }
            currenctScore.setFirstPlayerPoint(nextPoint(firstPlayerPoint));
        } else {
            if (currenctScore.getSecondPlayerPoint() == Point.FORTY) {
                secondPlayerWinGame(currenctScore);
                return;
            }
            currenctScore.setSecondPlayerPoint(nextPoint(secondPlayerPoint));
        }

        // проверяем тайбрейк (если два гейма = 6, то играется тай-брейк)
        if (isTieBrake(currenctScore)) {
            resolveTieBrake(currenctScore);
        }

        // проверяем, закончена ли игра (у одного из игроков сет = 2)
        if (isMatchFinished(currenctScore)) {
            Player winner = getWinner(match, currenctScore);
            matchService.save(match, winner);
        }
    }

    private static boolean firstPlayerScored(Integer playerId, OngoingMatch match) {
        return playerId.equals(match.getFirstPlayer().getId());
    }

    private void firstPlayerWinGame(Score score) {
        score.setFirstPlayerGame(score.getFirstPlayerGame() + 1);
        clearPoints(score);
    }


    private void secondPlayerWinGame(Score score) {
        score.setSecondPlayerGame(score.getSecondPlayerGame() + 1);
        clearPoints(score);
    }

    private void clearPoints(Score score) {
        score.setFirstPlayerPoint(Point.ZERO);
        score.setSecondPlayerPoint(Point.ZERO);
    }

    private void clearGames(Score score) {
        score.setFirstPlayerGame(0);
        score.setSecondPlayerGame(0);
    }

    private boolean isMatchFinished(Score score) {
        return score.getFirstPlayerSet() == SETS_TO_WIN || score.getSecondPlayerSet() == SETS_TO_WIN;
    }

    private boolean isTieBrake(Score score) {
        return score.getFirstPlayerGame() == TIE_BREAK && score.getSecondPlayerGame() == TIE_BREAK;
    }

    private Player getWinner(OngoingMatch match, Score score) {
        if (score.getFirstPlayerSet() == SETS_TO_WIN) {
            return match.getFirstPlayer();
        }
        return match.getSecondPlayer();
    }

    private void resolveTieBrake(Score score) {
        System.out.println("Tiebreak! Will do later.");
    }

    private Point nextPoint(Point point) {
        return switch (point) {
            case ZERO -> Point.FIFTEEN;
            case FIFTEEN -> Point.THIRTY;
            case THIRTY -> Point.FORTY;
            case FORTY -> Point.AD;
            default -> point;
        };
    }
}
