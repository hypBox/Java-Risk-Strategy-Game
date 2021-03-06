package model;

import model.GameManager;
import model.Map;
import model.contract.IPlayer;
import org.junit.Assert;
import org.junit.Test;
import util.expetion.InvalidNumOfPlayersException;

/**
 * Tests minimum reinforcement armies
 */
public class MinReinforceTest {

    @Test()
    public void test() throws InvalidNumOfPlayersException
    {
        Map m = new Map();
        m.clearData();
        m.fakeData();

        GameManager gm = new GameManager(m, 3,"r,r,r", 500);
        gm.start(false);

        IPlayer p = gm.nextPlayer();
        Assert.assertEquals(3, gm.calculateReinforcementArmies(p));
    }

}
