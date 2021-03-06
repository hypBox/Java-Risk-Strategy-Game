package model;

import model.GameManager;
import model.contract.IPlayer;
import org.junit.Assert;
import org.junit.Test;
import util.expetion.InvalidNumOfPlayersException;

/**
 * Start game without a map
 */
public class WithoutMapTest {

    @Test(expected = Exception.class)
    public void test() throws InvalidNumOfPlayersException
    {
        GameManager gm = new GameManager(3, "r,r,r", 500);
        gm.start(false);
    }

}
