package org.roadmap.tennisscoreboard.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "player1")
    private Player firstPlayer;

    @ManyToOne
    @JoinColumn(name = "player2")
    private Player secondPlayer;

    @ManyToOne
    @JoinColumn(name = "winner")
    private Player winner;

    public Match() {
    }

    public Match(Integer id, Player firstPlayer, Player secondPlayer, Player winner) {
        this.id = id;
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    public Match(Player firstPlayer, Player secondPlayer, Player winner) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winner = winner;
    }

    public Integer getId() {
        return id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public Player getWinner() {
        return winner;
    }
}
