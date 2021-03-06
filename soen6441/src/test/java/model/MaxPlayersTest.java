package model;
import model.GameManager;
import org.junit.Test;
import util.expetion.InvalidNumOfPlayersException;

/**
 * Tests number of players with MaxPlayers + 1
 */
public class MaxPlayersTest {

    /**
     * Creates a game with 7 players
     * @throws InvalidNumOfPlayersException be careful
     */
    @Test(expected = InvalidNumOfPlayersException.class)
    public void testMaxPlayers() throws InvalidNumOfPlayersException
    {
        GameManager gm = new GameManager(7,"r,r,r,r,r,r,r", 500);
        gm.start();
    }

}
