package core;

/** Player object for Connect 4 game
 *
 * @author Brandon
 * @version 1.0
 */
public class Player {
    
    boolean isPlayerX;
    
    /** Constructor
     * @param x If true, then this object is player x, otherwise player O
     */
    public Player(boolean x) {
        if (x)
            isPlayerX = true;
        else
            isPlayerX = false;
    }
}
