package ui;

import core.Connect4;
import core.Player;
/** Displays and updates Connect 4 game board
 *
 * @author Brandon
 * @version 1.0
 */
public class Connect4TextConsole {
    char[][] board;
    
    /** Constructor for Connect4TextConsole object
     *  Initializes every position with a ' ' value.
     */
    public Connect4TextConsole() {
        board = new char[6][7];
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = ' ';
            }
        }
    }
    
    /** Display formatted board in text console */
    public void print() {
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print("| " + board[i][j] + " ");
            }
            System.out.print("| \n");
        }
    }
    /** Exports the board to be checked for player positions
     *  Creates a temporary array to protect the integrity of the board
     * @return the two dimensional array of the board
     */
    public char[][] getBoard() {
        char[][] boardCopy = new char[6][7];
        for(int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                boardCopy[i][j] = board[i][j];
            }
        }
        
        return boardCopy;
    }
    
    /** Updates the given position on the board with the player's game piece
     * 
     * @param row Selected row on game board to receive game piece
     * @param col Selected column on game board to receive game piece
     * @param isX Is true if the player is Player X, otherwise it it Player O
     */
    public void updateBoard(int row, int col, boolean isX) {
        if(isX)
            board[row][col] = 'X';
        else
            board[row][col] = 'O';
    }
}
