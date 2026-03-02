package org.roadmap.tennisscoreboard.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.roadmap.tennisscoreboard.domain.OngoingMatch;
import org.roadmap.tennisscoreboard.domain.Point;
import org.roadmap.tennisscoreboard.domain.Score;
import org.roadmap.tennisscoreboard.domain.SetScoreInfo;
import org.roadmap.tennisscoreboard.entity.Player;

import java.util.List;
import java.util.Map;

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


        // проверяем тайбрейк (если два гейма = 6, то играется тай-брейк)
        if (isTieBrake(currenctScore)) {
            resolveTieBrake(playerId, match, currenctScore);
            return;
        }

        Point firstPlayerPoint = currenctScore.getFirstPlayerPoint();
        Point secondPlayerPoint = currenctScore.getSecondPlayerPoint();

        if (currenctScore.getFirstPlayerGame() >= GAMES_TO_WIN_SET &&
                currenctScore.getFirstPlayerGame() - currenctScore.getSecondPlayerGame() >= 2) {
            int currentSetNumber = currenctScore.getFirstPlayerSet();
            currenctScore.setFirstPlayerSet(currentSetNumber + 1);

            Map<Integer, SetScoreInfo> setHistory = match.getSetsHistory();
            SetScoreInfo setScore = new SetScoreInfo(
                    currenctScore.getFirstPlayerGame(),
                    currenctScore.getSecondPlayerGame()
            );
            setHistory.put(currentSetNumber, setScore);

            clearPoints(currenctScore);
            clearGames(currenctScore);
            return;
        }

        if (currenctScore.getSecondPlayerGame() >= GAMES_TO_WIN_SET &&
                currenctScore.getSecondPlayerGame() - currenctScore.getFirstPlayerGame() >= 2) {
            int currentSetNumber = currenctScore.getSecondPlayerSet();
            currenctScore.setSecondPlayerSet(currentSetNumber + 1);

            Map<Integer, SetScoreInfo> setHistory = match.getSetsHistory();
            SetScoreInfo setScore = new SetScoreInfo(
                    currenctScore.getFirstPlayerGame(),
                    currenctScore.getSecondPlayerGame()
            );
            setHistory.put(currentSetNumber, setScore);

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

    // BO3, поэтому ровно == 2
    public boolean isMatchFinished(Score score) {
        return score.getFirstPlayerSet() == SETS_TO_WIN || score.getSecondPlayerSet() == SETS_TO_WIN;
    }

    private boolean isTieBrake(Score score) {
        return score.getFirstPlayerGame() == TIE_BREAK && score.getSecondPlayerGame() == TIE_BREAK;
    }

    public Player getWinner(OngoingMatch match, Score score) {
        if (score.getFirstPlayerSet() == SETS_TO_WIN) {
            return match.getFirstPlayer();
        }
        return match.getSecondPlayer();
    }

    private void resolveTieBrake(Integer playerId, OngoingMatch match, Score score) {
        score.setTieBreak(true);

        if (firstPlayerScored(playerId, match)) {
            score.setFirstPlayerTiebreakPoint(score.getFirstPlayerTiebreakPoint() + 1);
        } else {
            score.setSecondPlayerTiebreakPoint(score.getSecondPlayerTiebreakPoint() + 1);
        }

        if (score.getFirstPlayerTiebreakPoint() >= 7 && score.getFirstPlayerTiebreakPoint() - score.getSecondPlayerTiebreakPoint() >= 2) {
            score.setFirstPlayerGame(score.getFirstPlayerGame() + 1);
            score.setFirstPlayerSet(score.getFirstPlayerSet() + 1);
            clearPoints(score);
            clearTieBrakePoints(score);
            clearGames(score);
            score.setTieBreak(false);
            return;
        }
        if (score.getSecondPlayerTiebreakPoint() >= 7 && score.getSecondPlayerTiebreakPoint() - score.getFirstPlayerTiebreakPoint() >= 2) {
            score.setSecondPlayerGame(score.getSecondPlayerGame() + 1);
            score.setSecondPlayerSet(score.getSecondPlayerSet() + 1);
            clearPoints(score);
            clearTieBrakePoints(score);
            clearGames(score);
            score.setTieBreak(false);
        }
    }

    private void clearTieBrakePoints(Score score) {
        score.setFirstPlayerTiebreakPoint(0);
        score.setSecondPlayerTiebreakPoint(0);
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
