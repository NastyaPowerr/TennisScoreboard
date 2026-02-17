package org.roadmap.dto;

public class MatchDto {
    private Integer firstPlayerId;
    private Integer secondPlayerId;
    private Score score;

    public MatchDto(Integer firstPlayerId, Integer secondPlayerId, Score score) {
        this.firstPlayerId = firstPlayerId;
        this.secondPlayerId = secondPlayerId;
        this.score = score;
    }

    public Integer getFirstPlayerId() {
        return firstPlayerId;
    }

    public void setFirstPlayerId(Integer firstPlayerId) {
        this.firstPlayerId = firstPlayerId;
    }

    public Integer getSecondPlayerId() {
        return secondPlayerId;
    }

    public void setSecondPlayerId(Integer secondPlayerId) {
        this.secondPlayerId = secondPlayerId;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    // TEMP: for testing only
    @Override
    public String toString() {
        return "MatchDto{" +
                "firstPlayerId=" + firstPlayerId +
                ", secondPlayerId=" + secondPlayerId +
                ", score=" + score +
                '}';
    }
}
