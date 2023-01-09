package G16.Controllers;

import G16.Fields.Field;
import G16.PlayerUtils.Player;
import junit.framework.TestCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

import java.util.ArrayList;

public class GameControllerTest extends TestCase {

    public void testMovePlayer(){
        GameController controller = new GameController(true);
        Player player = new Player();
        controller.movePlayer(player,1);
        assertEquals(1,player.getPlayerPosition());
        controller.movePlayer(player,10);
        assertEquals(11,player.getPlayerPosition());
        controller.movePlayer(player,35);
        assertEquals(6,player.getPlayerPosition());
    }
    public void testGiveStartMoney(){
        GameController controller = new GameController(true);
        Player player = new Player();
        controller.movePlayer(player,20);
        controller.movePlayer(player,20);
        assertEquals(34000,player.getPlayerBalance());
    }

    public void testPlayTurn(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        for(int i = 0; i < 1000; i++){
            controller.playTurn();
        }
    }

    public void testExtraTurn(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        Player jailedPlayer = controller.getPlayers().get(0);
        controller.fakeDie(true,2,2);
        for(int i = 0; i < players.size() * 3; i++){
            controller.playTurn();
        }
        assertTrue(jailedPlayer.getJailed());
    }

    public void testPayRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,1,2);
        controller.playTurn();
        assertEquals(28800,players.get(0).getPlayerBalance());
        controller.playTurn();
        assertEquals(28850,players.get(0).getPlayerBalance());
        assertEquals(29950,players.get(1).getPlayerBalance());
    }

    public void testPayDoubleRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,2,4);
        controller.playTurn();
        assertEquals(28000,players.get(0).getPlayerBalance());
        controller.setPlayerTurn(0);
        controller.fakeDie(true,2,0);
        controller.playTurn();
        assertEquals(26000,players.get(0).getPlayerBalance());
        controller.setPlayerTurn(0);
        controller.fakeDie(true,1,0);
        controller.playTurn();
        assertEquals(23600,players.get(0).getPlayerBalance());
        controller.fakeDie(true,2,4);
        controller.playTurn();
        assertEquals(29800,players.get(1).getPlayerBalance());
        assertEquals(23800,players.get(0).getPlayerBalance());
    }

    public void testPayShipRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,2,3);
        controller.playTurn();
        assertEquals(26000,players.get(0).getPlayerBalance());
        assertEquals(1,players.get(0).getShipsOwned());
        controller.playTurn();
        assertEquals(29500,players.get(1).getPlayerBalance());
        assertEquals(26500,players.get(0).getPlayerBalance());
    }
    public void testPayDoubleShipRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,2,3);
        controller.playTurn();
        players.get(0).setShipsOwned(players.get(0).getShipsOwned()+1);
        assertEquals(26000,players.get(0).getPlayerBalance());
        controller.playTurn();
        assertEquals(29000,players.get(1).getPlayerBalance());
        assertEquals(27000,players.get(0).getPlayerBalance());
    }

        public void testPayBrewRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,7,5);
        controller.playTurn();
        assertEquals(27000,players.get(0).getPlayerBalance());
        assertEquals(1,players.get(0).getBrewsOwned());
        controller.playTurn();
        assertEquals(28800,players.get(1).getPlayerBalance());
        assertEquals(28200,players.get(0).getPlayerBalance());
    }
    public void testPayDoubleBrewRent(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        controller.fakeDie(true,7,5);
        controller.playTurn();
        players.get(0).setBrewsOwned(players.get(0).getBrewsOwned()+1);
        assertEquals(27000,players.get(0).getPlayerBalance());
        assertEquals(2,players.get(0).getBrewsOwned());
        controller.playTurn();
        assertEquals(27600,players.get(1).getPlayerBalance());
        assertEquals(29400,players.get(0).getPlayerBalance());
    }

    public void testJailedAfterThreeTurns(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        Player jailedPlayer = controller.getPlayers().get(0);
        jailedPlayer.setJailed(true);
        for(int i = 0; i < players.size() * 3; i++){
            controller.playTurn();
        }
        assertFalse(jailedPlayer.getJailed());

    }
    public void testBankruptcy(){
        GameController controller = new GameController(true);
        controller.setupPlayers();
        ArrayList<Player> players = controller.getPlayers();
        for(Player p : players){
            p.addBalance(-29001);
            System.out.println(p.getPlayerBalance());
        }
        for(int i = 0; i < 1000; i++){
            controller.getCurrentplayer().addBalance(-500);
            for(Player p : players){

                System.out.println(p.getPlayerBalance());
            }
            if(!controller.getWinnerfound()) {
                controller.playTurn();
            }
            else {
                break;
            }
        }
        for(Player p : players){
            System.out.println(p.getPlayerBalance());
            System.out.println(p.getBankrupt());
        }

    }


}