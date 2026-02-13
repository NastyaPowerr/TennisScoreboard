package org.roadmap.model.entity;

public class MatchEntity {
    private Integer id;
    private PlayerEntity firstPlayerEntity;
    private PlayerEntity secondPlayerEntity;
    private PlayerEntity winner;

    public MatchEntity(Integer id, PlayerEntity firstPlayerEntity, PlayerEntity secondPlayerEntity, PlayerEntity winner) {
        this.id = id;
        this.firstPlayerEntity = firstPlayerEntity;
        this.secondPlayerEntity = secondPlayerEntity;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PlayerEntity getFirstPlayer() {
        return firstPlayerEntity;
    }

    public void setFirstPlayer(PlayerEntity firstPlayerEntity) {
        this.firstPlayerEntity = firstPlayerEntity;
    }

    public PlayerEntity getSecondPlayer() {
        return secondPlayerEntity;
    }

    public void setSecondPlayer(PlayerEntity secondPlayerEntity) {
        this.secondPlayerEntity = secondPlayerEntity;
    }

    public PlayerEntity getWinner() {
        return winner;
    }

    public void setWinner(PlayerEntity winner) {
        this.winner = winner;
    }
}
