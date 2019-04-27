package com.minigames.tictactoe;

import java.util.Scanner;

/**
 * Tic-Tac-Toe: Two-player console, non-graphics, OO version.
 * in the OO version.
 */
public class DynamicTicTacToe {
    private Board board;            // the game board
    private GameState currentState; // the current state of the game (of enum GameState)
    private Seed currentPlayer;     // the current player (of enum Seed)

    private static final Scanner in = new Scanner(System.in);  // input Scanner

    /**
     * Constructor to setup the game
     */
    public DynamicTicTacToe() {
        try {
            // Loop game (Play Again?) until exit
            do {
                setBoardSize(); // Set the board's rows and cols size
                initGame(); // Initialize the game-board and current status

                System.out.println("GAME STARTED"); // Game Started!
                System.out.println();
                do {
                    playerMove(currentPlayer, board.sizes); // update the content, currentRow and currentCol
                    updateGame(currentPlayer, board.currentRow, board.currentCol, board.sizes); // update currentState
                    switchPlayer(currentPlayer); // Switch player
                    gameState(currentState); // Print message if game-over
                } while (currentState == GameState.PLAYING);  // repeat until game-over

                playAgain(); // Prompt the user whether to play again
            } while (true);
        } catch (Exception e) {
            System.out.println();
            System.out.println("Wrong Input >>>> Game Closed");
            System.out.println();
        }
    }

    private void switchPlayer(Seed player) {
        currentPlayer = (player == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS; // Switch player
    }

    private void gameState(GameState currentState) {
        switch (currentState) {
            case CROSS_WON:
                System.out.println("Congratulation, Player 'X' won!");
                System.out.println();
                System.out.println("GAME ENDED");
                break;
            case NOUGHT_WON:
                System.out.println("Congratulation, Player 'O' won!");
                System.out.println();
                System.out.println("GAME ENDED");
                break;
            case DRAW:
                System.out.println("It's Draw!");
                System.out.println();
                System.out.println("GAME ENDED");
                break;
            default:
                break;
        }
    }

    private void playAgain() {
        System.out.println();
        System.out.print("Play again (y/n)? ");
        char ans = in.next().charAt(0);
        if (ans != 'y' && ans != 'Y') {
            System.out.println("Bye!");
            System.exit(0);  // terminate the program
        }
    }

    private void setBoardSize() {
        System.out.println();
        System.out.print("Set the board's size [n] : ");
        int size = in.nextInt();
        board = new Board(size);  // allocate game-board
        System.out.println();
    }

    /**
     * Initialize the game-board contents and the current states
     */
    public void initGame() {
        board.init();  // clear the board contents
        currentPlayer = Seed.CROSS; // CROSS plays first
        currentState = GameState.PLAYING; // ready to play
    }

    /**
     * The player with "theSeed" makes one move, with input validation.
     * Update Cell's content, Board's currentRow and currentCol.
     */
    public void playerMove(Seed theSeed, int size) {
        boolean validInput = false;  // for validating input
        do {
            if (theSeed == Seed.CROSS) {
                System.out.print("Player 'X', enter your move (row[1-" + size + "] column[1-" + size + "]): ");
            } else {
                System.out.print("Player 'O', enter your move (row[1-" + size + "] column[1-" + size + "]): ");
            }
            int row = in.nextInt() - 1;
            int col = in.nextInt() - 1;
            if (row >= 0 && row < board.rows && col >= 0 && col < board.cols && board.cells[row][col].content == Seed.EMPTY) {
                board.cells[row][col].content = theSeed;
                board.currentRow = row;
                board.currentCol = col;
                board.paint(); // ask the board to paint itself

                validInput = true; // input okay, exit loop
            } else {
                System.out.println("This move at (" + (row + 1) + "," + (col + 1)
                        + ") is not valid. Try again...");
            }
        } while (!validInput);   // repeat until input is valid
    }

    /**
     * Update the currentState after the player with "theSeed" has moved
     */
    public void updateGame(Seed theSeed, int currentRow, int currentCol, int size) {
        if (board.hasWon(theSeed, currentRow, currentCol, size)) {  // check for win
            currentState = (theSeed == Seed.CROSS) ? GameState.CROSS_WON : GameState.NOUGHT_WON;
        } else if (board.isDraw()) {  // check for draw
            currentState = GameState.DRAW;
        }
        // Otherwise, no change to current state (still GameState.PLAYING).
    }

    /**
     * The entry main() method
     */
    public static void main(String[] args) {
        new DynamicTicTacToe();  // Let the constructor do the job
    }
}