package org.roadmap.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
// In most cases, the name of the table in the database and the name of the entity won’t be the same.
// In these cases, we can specify the table name using the @Table annotation
// in my case it's the same but for remembering I will keep these temp
@Table(name = "matches")
public class MatchEntity {
    @Id
    // GenerationType.IDENTITY - для моих auto_increment
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player1")
    private PlayerEntity firstPlayerEntity;

    @ManyToOne
    @JoinColumn(name = "player2")
    private PlayerEntity secondPlayerEntity;

    @ManyToOne
    @JoinColumn(name = "winner")
    private PlayerEntity winner;

    public MatchEntity() {
    }

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
