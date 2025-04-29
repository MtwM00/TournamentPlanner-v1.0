package com.mtwm00.tournamentplanner.dto;

public class MatchDto {

    private long id;
    private int round;
    private String player1;
    private long player1Id;
    private String player2;
    private long player2Id;
    private long winnersId;

    public MatchDto(long id, int round, String player1, long player1Id, String player2, long player2Id, long winnersId) {
        this.id = id;
        this.round = round;
        this.player1 = player1;
        this.player1Id = player1Id;
        this.player2 = player2;
        this.player2Id = player2Id;
        this.winnersId = winnersId;
    }

    public MatchDto() {
    }

    public long getId() {
        return id;
    }

    public long getPlayer1Id() {
        return player1Id;
    }

    public long getPlayer2Id() {
        return player2Id;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public long getWinnersId() {
        return winnersId;
    }

    public void setWinnersId(long winnersId) {
        this.winnersId = winnersId;
    }
}
