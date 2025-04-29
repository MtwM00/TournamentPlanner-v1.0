package com.mtwm00.tournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Match {

    @Id
    @GeneratedValue
    private Long id;
    private int round;

    @ManyToOne
    private TournamentPlayer player1;

    @ManyToOne
    private TournamentPlayer player2;

    @ManyToOne
    private TournamentPlayer winner;

    @ManyToOne
    private Tournament tournament;

    protected Match() {
    }

    public Match(int round, TournamentPlayer player1, TournamentPlayer player2, Tournament tournament) {
        this.round = round;
        this.player1 = player1;
        this.player2 = player2;
        this.tournament = tournament;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", round=" + round +
                ", player1Id=" + player1 +
                ", player2Id=" + player2 +
                ", winner=" + winner +
                '}';
    }

    public Tournament getTournament() {
        return tournament;
    }

    public Long getId() {
        return id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public TournamentPlayer getPlayer1() {
        return player1;
    }

    public void setPlayer1(TournamentPlayer player1) {
        this.player1 = player1;
    }

    public TournamentPlayer getPlayer2() {
        return player2;
    }

    public void setPlayer2(TournamentPlayer player2) {
        this.player2 = player2;
    }

    public TournamentPlayer getWinner() {
        return winner;
    }

    public void setWinners(TournamentPlayer winner) {
        this.winner = winner;
    }
}
