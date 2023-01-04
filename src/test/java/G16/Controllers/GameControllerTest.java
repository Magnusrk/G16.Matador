package G16.Controllers;

import G16.PlayerUtils.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class GameControllerTest extends TestCase {
    GameController controller = new GameController();

    @Test
    public void testMovePlayer(){
        Player player = new Player();
        controller.movePlayer(player,1);
        assertEquals(1,player.getPlayerPosition());
        controller.movePlayer(player,10);
        assertEquals(11,player.getPlayerPosition());
        controller.movePlayer(player,35);
        assertEquals(6,player.getPlayerPosition());
    }
    @Test
    public void testGiveStartMoney(){
        Player player = new Player();
        controller.movePlayer(player,20);
        controller.movePlayer(player,20);
        assertEquals(34000,player.getPlayerBalance());
    }


}