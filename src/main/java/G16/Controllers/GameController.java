package G16.Controllers;

import G16.Graphics.MatadorGUI;
import G16.PlayerUtils.Player;

import java.util.ArrayList;

public class GameController {

    private final int MAX_PLAYERS = 6;
    private final int MIN_PLAYERS = 3;

    private final int startingBalance = 30000;

    private ArrayList<Player> players = new ArrayList<>();

    private MatadorGUI mgui;


    public void playGame(){
        mgui = new MatadorGUI();
        setupPlayers();

    }

    private void setupPlayers(){
        int playerCount = mgui.requestInteger("Hvor mange spillere vil i spille? (3-6) ", MIN_PLAYERS, MAX_PLAYERS);
        while (players.size() < playerCount){
            String newPlayerName = mgui.requestString("Skriv spiller navn: ");
            players.add(new Player());
            mgui.addPlayer(newPlayerName, startingBalance);
        }
        mgui.showMessage("Tryk OK for at starte!");
    }

}
