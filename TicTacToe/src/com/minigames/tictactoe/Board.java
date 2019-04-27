package com.minigames.tictactoe;

/**
 * The Board class models the game-board.
 */
public class Board {
    // Named-constants for the dimensions
    public static int rows;
    public static int cols;
    public static int sizes;

    // package access
    Cell[][] cells;  // a board composes of rows-by-cols Cell instances
    int currentRow, currentCol;  // the current seed's row and column

    /**
     * Constructor to initialize the game board
     */
    public Board(int size) {
        rows = size;
        cols = size;
        sizes = size;

        cells = new Cell[sizes][sizes];  // allocate the array
        for (int row = 0; row < sizes; ++row) {
            for (int col = 0; col < sizes; ++col) {
                cells[row][col] = new Cell(row, col); // allocate element of the array
            }
        }
    }

    /**
     * Initialize (or re-initialize) the contents of the game board
     */
    public void init() {
        for (int row = 0; row < sizes; ++row) {
            for (int col = 0; col < sizes; ++col) {
                cells[row][col].clear();  // clear the cell content
            }
        }
    }

    /**
     * Return true if it is a draw (i.e., no more EMPTY cell)
     */
    public boolean isDraw() {
        for (int row = 0; row < sizes; ++row) {
            for (int col = 0; col < sizes; ++col) {
                if (cells[row][col].content == Seed.EMPTY) {
                    return false; // an empty seed found, not a draw, exit
                }
            }
        }
        return true; // no empty cell, it's a draw
    }

    /**
     * Return true if the player with "theSeed" has won after placing at
     * (currentRow, currentCol)
     */
    public boolean hasWon(Seed theSeed, int currentRow, int currentCol, int size) {
        return (checkRowWon(theSeed, currentRow, size)
                || checkColWon(theSeed, currentCol, size)
                || checkDiagWon(theSeed, size)
                || checkOppDiagWon(theSeed, size));
    }

    private boolean checkRowWon(Seed theSeed, int currentRow, int size) {
        int trueCount = 0;
        for (int i = 0; i < size; i++) {
            if (theSeed == cells[this.currentRow][i].content) {
                trueCount++;
            }
        }
        return trueCount == size;
    }

    private boolean checkColWon(Seed theSeed, int currentCol, int size) {
        int trueCount = 0;
        for (int i = 0; i < size; i++) {
            if (theSeed == cells[i][this.currentCol].content) {
                trueCount++;
            }
        }
        return trueCount == size;
    }

    private boolean checkDiagWon(Seed theSeed, int size) {
        int trueCount = 0;
        for (int i = 0; i < size; i++) {
            if (theSeed == cells[i][i].content) {
                trueCount++;
            }
        }
        return trueCount == size;
    }

    private boolean checkOppDiagWon(Seed theSeed, int size) {
        int trueCount = 0;
        for (int i = 0; i < size; i++) {
            if (theSeed == cells[i][size - (i + 1)].content) {
                trueCount++;
            }
        }
        return trueCount == size;
    }

    /**
     * Paint itself
     */
    public void paint() {
        System.out.println();
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col].paint();   // each cell paints itself
                if (col < cols - 1) System.out.print("|");
            }
            System.out.println();
            if (row < rows - 1) {
                for (int i = 0; i < cols; i++) {
                    if (i < (cols - 1)) {
                        System.out.print("----"); // print horizontal partition
                    } else {
                        System.out.println("----"); // print horizontal partition
                    }
                }
            }
        }
        System.out.println();
    }
}
