package org.fourinarow.entity;

import org.fourinarow.enums.GridPiece;

import java.util.List;

public class Player {
    private final String playerName;
    private final GridPiece gridPiece;
    private List<Integer> moves;
    private int roundsWon;

    public Player(String playerName, GridPiece gridPiece, List moves) {
        this.playerName = playerName;
        this.gridPiece = gridPiece;
        this.moves = moves;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<Integer> getMoves() {
        return moves;
    }

    public void addMoves(Integer move) {
        getMoves().add(move);
    }

    public int getRoundsWon() {
        return roundsWon;
    }

    public void setRoundsWon(int roundsWon) {
        this.roundsWon = roundsWon;
    }

    public GridPiece getGridPiece() {
        return gridPiece;
    }

}
