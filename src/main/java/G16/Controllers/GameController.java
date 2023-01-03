package G16.Controllers;

import G16.Graphics.MatadorGUI;
import G16.PlayerUtils.Die;
import G16.PlayerUtils.Player;

import java.util.ArrayList;

public class GameController {

    private final int MAX_PLAYERS = 6;
    private final int MIN_PLAYERS = 3;

    private final int startingBalance = 30000;

    private final int passStartReward = 4000;

    private final int numberOfFields = 40;

    private ArrayList<Player> players = new ArrayList<>();

    private MatadorGUI mgui;

    private int currentPlayerID = 0;


    public void playGame(){
        mgui = new MatadorGUI();
        setupPlayers();
        playTurn();

    }

    private void setupPlayers(){
        int playerCount = mgui.requestInteger("Hvor mange spillere vil i spille? (3-6) ", MIN_PLAYERS, MAX_PLAYERS);
        while (players.size() < playerCount){
            String newPlayerName = mgui.requestString("Skriv spiller navn: ");
            Player newPlayer = new Player();
            newPlayer.setName(newPlayerName);
            newPlayer.setID(players.size());
            newPlayer.AddBalance(startingBalance);
            players.add(newPlayer);
            mgui.addPlayer(newPlayerName, startingBalance);
            mgui.drawPlayerPosition(newPlayer);
        }
        mgui.showMessage("Tryk OK for at starte!");
    }

    private void playTurn(){

        Player currentPlayer = players.get(currentPlayerID);

        //Throw Dice
        mgui.showMessage(currentPlayer.getName() + " kast med terningen!");
        int[] diceThrow = Die.throwDice();
        int diceSum = diceThrow[0] + diceThrow[1];

        mgui.drawDice(diceThrow[0], diceThrow[1]);


        //Move player
        movePlayer(currentPlayer, diceSum);


        //Update balance and position on GUI
        mgui.drawPlayerPosition(currentPlayer);
        mgui.updatePlayerBalance(currentPlayer);

        currentPlayerID += 1;
        if(currentPlayerID >= players.size()){
            currentPlayerID = 0;

        }

        playTurn();
    }

    private void movePlayer(Player player, int moves){
        int currentPosition = player.getPlayerPosition();
        int newPosition = currentPosition + moves;
        if( newPosition >= numberOfFields){
            newPosition -= numberOfFields;
            giveStartMoney(player);
        }
        player.setPlayerPosition(newPosition);

    }

    private void giveStartMoney(Player player){
        player.AddBalance(passStartReward);

    }

}
