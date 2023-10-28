package org.fourinarow.entity;

import org.fourinarow.enums.GridPiece;
import org.fourinarow.enums.MatchStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Game {

    private final Grid grid;
    private final int noOfRounds;
    private List<Player> players;
    private final int connectN;


    public Game(int noOfRounds, int noOfRows, int noOfColumns, int connectN) {
        this.noOfRounds = noOfRounds;
        this.connectN = connectN;
        this.grid = new Grid(noOfRows, noOfColumns);
    }

    private void initPlayers(List<Player> players) {
        this.players = players;
    }

    public void initGame(List<Player> players) {
        initPlayers(players);
        grid.fillBoard();
    }

    public void startGame() {
        int numRounds = noOfRounds;
        while (numRounds > 0) {
            Player winner = playRound();
            if (winner != null) {
                System.out.println("Player: " + winner.getPlayerName() + " wins the round....");
                winner.setRoundsWon(winner.getRoundsWon() + 1);
            } else {
                System.out.println("Round is drawn .......");
            }
            numRounds--;
            grid.fillBoard();
            displayPlayerStatus();
        }
    }

    private Player playRound() {
        Player winner = null;
        boolean roundResultAvailable = false;
        Scanner scanner = new Scanner(System.in);
        while (!roundResultAvailable) {
            for (Player player : this.players) {
                System.out.println("Please enter move for player: " + player.getPlayerName());
                int columnPosition = scanner.nextInt();
                int rowPosition = grid.playMove(columnPosition, player.getGridPiece());
                if (rowPosition >= 0)  // -1 was returned which is invalid
                {
                    player.addMoves(rowPosition);
                    System.out.println("Element was placed at rowPosition: " + rowPosition + " and columnPosition: " + columnPosition);
                    MatchStatus matchStatus = checkWin(rowPosition, columnPosition, grid, player.getGridPiece());
                    if (matchStatus.name().equalsIgnoreCase("WIN")) {
                        roundResultAvailable = true;
                        winner = player;
                        break;
                    } else if (matchStatus.name().equalsIgnoreCase("DRAW")) {
                        roundResultAvailable = true;
                        break;
                    }
                }
                displayBoardStatus();
            }

        }

        return winner;
    }

    MatchStatus checkWin(int rowPosition, int columnPosition, Grid grid, GridPiece gridPiece) {
        boolean drawCondition = true;

        int countOfFour = 0;
        //Check horizontally
        for (int col = 0; col < grid.getNoOfColumns(); col++) {
            if (grid.getBoard()[rowPosition][col] == gridPiece) {
                countOfFour += 1;
                if (countOfFour == this.connectN) {
                    return MatchStatus.WIN;
                }
            } else {
                countOfFour = 0;
            }
        }

        //Check vertically
        countOfFour = 0;
        for (int row = 0; row < grid.getNoOfRows(); row++) {
            if (grid.getBoard()[row][columnPosition] == gridPiece) {
                countOfFour += 1;
                if (countOfFour == this.connectN) {
                    return MatchStatus.WIN;
                }
            } else {
                countOfFour = 0;
            }
        }


        //Check anti - diagonally
        countOfFour = 0;
        int[] startCoordinate = GameSvcUtils.getAntiDiagonalCoordinates(grid.getBoard(), rowPosition, columnPosition);
        int rowCoordinate = startCoordinate[0];
        int colCoordinate = startCoordinate[1];
        System.out.println("The start coordinates for the check anti diagonally are: " + Arrays.toString(startCoordinate));
        while (rowCoordinate > 0 && colCoordinate < grid.getNoOfColumns()) {
            if (grid.getBoard()[rowCoordinate][colCoordinate] == gridPiece) {
                countOfFour += 1;
                if (countOfFour == this.connectN) {
                    return MatchStatus.WIN;
                }
            } else {
                countOfFour = 0;
            }
            rowCoordinate--;
            colCoordinate++;
        }

        //Check diagonally
        countOfFour = 0;
        startCoordinate = GameSvcUtils.getDiagonalCoordinates(grid.getBoard(), rowPosition, columnPosition);
        rowCoordinate = startCoordinate[0];
        colCoordinate = startCoordinate[1];
        System.out.println("The start coordinates for the check diagonally are: " + Arrays.toString(startCoordinate));
        while (rowCoordinate < grid.getNoOfRows() && colCoordinate < grid.getNoOfColumns()) {
            if (grid.getBoard()[rowCoordinate][colCoordinate] == gridPiece) {
                countOfFour += 1;
                if (countOfFour == this.connectN) {
                    return MatchStatus.WIN;
                }
            } else {
                countOfFour = 0;
            }
            rowCoordinate++;
            colCoordinate++;

        }

        for (int i = 0; i < grid.getNoOfRows(); i++) {
            for (int j = 0; j < grid.getNoOfColumns(); j++) {
                if (grid.getBoard()[i][j] == GridPiece.EMPTY) {
                    drawCondition = false;

                }
            }
        }
        if (drawCondition) {
            return MatchStatus.DRAW;
        }

        return MatchStatus.CONTINUE;
    }

    public void displayBoardStatus() {
        System.out.println("************** BOARD STATUS *****************");
        for (int r = 0; r < this.grid.getNoOfRows(); r++) {       //for loop for row iteration.
            System.out.print("              ");
            for (int c = 0; c < this.grid.getNoOfColumns(); c++) {   //for loop for column iteration.
                System.out.print(this.grid.getBoard()[r][c].ordinal() + " ");
            }
            System.out.println(); //using this for new line to print array in matrix format.
        }

    }

    public void displayPlayerStatus() {
        System.out.println("************** PLAYER STATUS ****************");
        System.out.println();
        for (Player player : this.players) {
            System.out.println("Player :" + player.getPlayerName() + ", Moves: " + player.getMoves().toString());
        }

    }

    static class GameSvcUtils {

        public static int[] getDiagonalCoordinates(GridPiece[][] arr, int pointX, int pointY) {

            int[] resultArr = new int[2];
            int i = pointX;
            int j = pointY;
            if (pointX == pointY) {
                return resultArr;
            } else if (pointX < pointY) {
                do {
                    i--;
                    j--;
                } while (i >= 0);
            } else {
                do {
                    i--;
                    j--;
                } while (j >= 0);
            }
            resultArr[0] = i + 1;
            resultArr[1] = j + 1;
            return resultArr;
        }

        public static int[] getAntiDiagonalCoordinates(GridPiece[][] arr, int pointX, int pointY) {
            int i = pointX;
            int j = pointY;
            int[] resultArr = new int[2];
            do {
                i++;
                j--;
            } while (j >= 0 && i < arr.length);
            resultArr[0] = (i - 1);
            resultArr[1] = (j + 1);
            return resultArr;
        }

    }

}
