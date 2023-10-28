package org.fourinarow.entity;

import org.fourinarow.enums.GridPiece;

public class Grid {

    private final int noOfRows;
    private final int noOfColumns;
    private final GridPiece[][] board;

    public Grid(int noOfRows, int noOfColumns) {
        this.noOfRows = noOfRows;
        this.noOfColumns = noOfColumns;
        this.board = new GridPiece[noOfRows][noOfColumns];
    }

    public int getNoOfRows() {
        return noOfRows;
    }

    public int getNoOfColumns() {
        return noOfColumns;
    }

    public GridPiece[][] getBoard() {
        return board;
    }

    void fillBoard() {
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfColumns; j++) {
                this.board[i][j] = GridPiece.EMPTY;
            }
        }
    }

    int playMove(int column, GridPiece piece) {
        for (int rowPosition = this.noOfRows - 1; rowPosition >= 0; rowPosition--) {
            if (board[rowPosition][column] == GridPiece.EMPTY) {
                board[rowPosition][column] = piece;
                return rowPosition;
            }
        }
        return -1;
    }

}
