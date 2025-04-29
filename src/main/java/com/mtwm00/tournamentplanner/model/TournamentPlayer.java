package com.mtwm00.tournamentplanner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class TournamentPlayer {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Player player;

    @ManyToOne
    private Tournament tournament;

    private int score;

    public TournamentPlayer() {

    }

    public TournamentPlayer(Tournament tournament, Player player) {
        this.tournament = tournament;
        this.player = player;
        this.score = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
