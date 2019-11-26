package core;

import java.util.Random;
import ui.Connect4TextConsole;

/** Runs turns of CPU player in Connect 4 game
 *
 * @author Brandon
 * @version 1.0
 */
public class Connect4ComputerPlayer {
    
Connect4TextConsole console;    
    
    /** Default Constructor */
    public Connect4ComputerPlayer(Connect4TextConsole cons) {
        console = cons;
    }
    
    /** Checks for and plays a valid move on the game board.
     * @return column value of the CPU's next move. 
     */
    public int CPUTurn() {
        int placement = 0;
        Random r = new Random();
        boolean valid = false;
        
        while(!valid) {
            placement = r.nextInt(7) + 1;
            
            valid = isValid(placement);
        }
        return placement;
        
    }
    
     /** Checks if the board column selected is in the board range and
     *  if the column has an empty slot.
     * 
     * @param move column that the cpu would like to place a piece in
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
}
