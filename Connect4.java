package core;

import java.util.InputMismatchException;
import ui.Connect4TextConsole;
import java.util.Scanner;

/** Runs instance of Connect 4 game
 *
 * @author Brandon
 * @version 1.2
 */
public class Connect4 {

    Player playerX, playerO;
    Connect4TextConsole console;
    Connect4ComputerPlayer cpu;
    int turnCounter;
    char playMode;
    
    /** 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Connect4 game = new Connect4();
    }
    
    /** Constructor for Connect 4 game. Initializes players and board.
     *  Prints board and then begins the game with PLayer X having
     *  the first turn.
     */
    public Connect4() {
        playerX = new Player(true);
        playerO = new Player(false);
        console = new Connect4TextConsole();
        cpu = new Connect4ComputerPlayer(console);
        turnCounter = 0;
        
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        playMode = 'O';
        
        console.print();
        
        // Validates that the player selected proper input for game mode
        while(!valid){
            System.out.println("Begin Game. Enter ‘P’ if you want to play against another player; "
                    + "enter ‘C’ to play against computer.");
            try {
                playMode = in.next().charAt(0);
            }
            catch (Exception e){
                    System.out.println("Invalid input! Try again.");
            }
            if(playMode == 'P' || playMode == 'C')
                valid = true;
            else
                System.out.println("Invalid input! Try again.");
        }
        
        // Play mode had been selected. Begin game with X going first.
        playerTurn(true);
        
    }
    
    /** Prompts the correct player for input, receives the input,
     *  checks to see if it was a valid move, updates the board,
     *  displays the board, and checks if the game is over before
     *  moving on to the next player's turn.
     *  @param isX Denotes if it is Player X's turn or not
     */
    public void playerTurn(boolean isX) {
        boolean valid = false;
        Scanner in = new Scanner(System.in);
        int move = 0;
        
        while(!valid){
            if(isX) {
                System.out.println("Player X, enter your move (1-7):");
                try {
                    move = in.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid input! Try again.");
                    playerTurn(isX);
                }
                valid = isValid(move);
                if(!valid)
                   System.out.println("Invalid move! Try again.");
                else
                    movePiece(move, isX);
            }
            else if(!isX && playMode == 'C') {
                System.out.println("Player O, enter your move (1-7):");
                move = cpu.CPUTurn();
                System.out.println(move);
                valid = true;
                movePiece(move, false);
            }
            else {
                System.out.println("Player O, enter your move (1-7):");

                try {
                    move = in.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Invalid input! Try again.");
                    playerTurn(isX);
                }
                valid = isValid(move);
                if(!valid)
                   System.out.println("Invalid move! Try again.");
                else
                    movePiece(move, isX);
            }
        }

        // Display current game board
        console.print();
        turnCounter++;
        
        if(turnCounter == 42){
            System.out.println("Tie Game!");
            System.exit(0);
        }
            

        // Current turn is over. It is now the next player's turn.
        playerTurn(!isX);
        
    }
    /** Checks if the board column selected is in the board range and
     *  if the column has an empty slot.
     * 
     * @param move column that the player would like to place a piece in
     * @return true if the selected column has an open space 
     */
    public boolean isValid(int move) {
        if(move > 0 && move < 8)
        {
            for(int i = 0; i < 6; i++) {
                if(console.getBoard()[i][move-1] == ' ')
                    return true;
            }
        }
        
        return false;
    }
    
    /** Locates the bottom-most empty position in the selected column
     * 
     * @param col Selected column for a piece to be moved to
     * @param isX If this is true then it is Player X's turn
     */
    public void movePiece(int col, boolean isX) {
        boolean moveLeft = true;
        for(int i = 5; i >= 0; i--) {
                if(console.getBoard()[i][col-1] == ' ' && moveLeft) {
                    console.updateBoard(i, col-1, isX);
                    moveLeft = false;
                    if(isWin(i, col-1, isX)){
                        console.print();
                        //Announce winner
                        if(isX)
                            System.out.println("Player X wins!");
                        else
                            System.out.println("Player O wins!");
                    System.exit(0);
                    }
                }
            }
    }

    public boolean isWin(int row, int col, boolean isX) {
        int connectedCount = 0;
        boolean win = false;
        char piecePlayed;
        
        if(isX)
            piecePlayed = 'X';
        else
            piecePlayed = 'O';

        // Check horizontal for win
        for (int i = 0; i < 7; i++) {
            // If there are 4 pieces connected in a row then the game has been won
            if(connectedCount != 4) {
                if (console.getBoard()[row][i] == piecePlayed)
                    connectedCount++;
                else
                    connectedCount = 0;
            }
            else
                win = true;
        }

        // Check vertical for win
        // Reset counter for number of pieces that are connected
        connectedCount = 0;

        for (int i = 0; i < 6; i++) {
            // If there are 4 pieces connected in a row then the game has been won
            if (connectedCount != 4) {
                if (console.getBoard()[i][col] == piecePlayed)
                    connectedCount++;
                else
                    connectedCount = 0;
            }
            if (connectedCount == 4)
                win = true;
        }

        // Check diagonal top left to bottom right for win
        // Reset counter for number of pieces that are connected

        connectedCount = 1;

        // Start at placed piece and count diagonal down and right
        int i = row;
        int j = col;
        boolean cont = true;

        do {
            i++;
            j++;
            if(i < 6) {
                if (j < 7) {
                    if(console.getBoard()[i][j] == piecePlayed)
                        connectedCount++;
                    else
                        cont = false;
                }
                else
                    cont = false;
            }
            else
                cont = false;
        }while (cont);

        // Check diagonal in other direction
        i = row;
        j = col;
        cont = true;

        do {
            i--;
            j--;
            if(i >= 0) {
                if (j >= 0) {
                    if(console.getBoard()[i][j] == piecePlayed)
                        connectedCount++;
                    else
                        cont = false;
                }
                else
                    cont = false;
            }
            else
                cont = false;
        }while (cont);

        // If there are 4 pieces connected in a row then the game has been won
        if (connectedCount == 4) {
            win = true;
        }

        // Check diagonal bottom left to top right for win
        // Reset counter for number of pieces that are connected

        connectedCount = 1;

        // Start at placed piece and count diagonal up and right
        i = row;
        j = col;
        cont = true;

        do {
            i--;
            j++;
            if(i >= 0) {
                if (j < 7) {
                    if(console.getBoard()[i][j] == piecePlayed)
                        connectedCount++;
                    else
                        cont = false;
                }
                else
                    cont = false;
            }
            else
                cont = false;
        }while (cont);

        // Check diagonal in other direction
        i = row;
        j = col;
        cont = true;

        do {
            i++;
            j--;
            if(i < 6) {
                if (j >= 0) {
                    if(console.getBoard()[i][j] == piecePlayed)
                        connectedCount++;
                    else
                        cont = false;
                }
                else
                    cont = false;
            }
            else
                cont = false;
        }while (cont);

        // If there are 4 pieces connected in a row then the game has been won
        if (connectedCount == 4) {
            win = true;
        }
        
        // Return if the game has been won
        return win;
    }    
}
