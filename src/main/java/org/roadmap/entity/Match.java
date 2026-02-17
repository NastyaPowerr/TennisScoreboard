package org.roadmap.entity;

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
public class Match {
    @Id
    // GenerationType.IDENTITY - для моих auto_increment
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

    public void setId(Integer id) {
        this.id = id;
    }

    public Player getFirstPlayer() {
        return firstPlayer;
    }

    public void setFirstPlayer(Player firstPlayer) {
        this.firstPlayer = firstPlayer;
    }

    public Player getSecondPlayer() {
        return secondPlayer;
    }

    public void setSecondPlayer(Player secondPlayer) {
        this.secondPlayer = secondPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
