package G16.Controllers;

import G16.Fields.GoToJail;
import G16.Fields.Initializer;
import G16.Fields.Field;
import G16.Graphics.MatadorGUI;
import G16.Language;
import G16.PlayerUtils.Die;
import G16.PlayerUtils.Player;

import java.util.ArrayList;

public class GameController {

    private final int MAX_PLAYERS = 6;
    private final int MIN_PLAYERS = 3;
    private final int passStartReward = 4000;

    private final int numberOfFields = 40;

    private ArrayList<Player> players = new ArrayList<>();

    private MatadorGUI mgui;

    private int currentPlayerID = 0;

    private Field[] fields;


    public void playGame(){
        fields=Initializer.InitFields();
        mgui = new MatadorGUI(fields);
        setupPlayers();
        playTurn();

    }

    private void setupPlayers(){
        int playerCount = mgui.requestInteger(Language.getString("howManyPlayers"), MIN_PLAYERS, MAX_PLAYERS);
        while (players.size() < playerCount){
            String newPlayerName = mgui.requestString(Language.getString("playerName"));
            Player newPlayer = new Player();
            newPlayer.setName(newPlayerName);
            newPlayer.setID(players.size());
            players.add(newPlayer);
            mgui.addPlayer(newPlayerName, newPlayer.getPlayerBalance());
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

    public void movePlayer(Player player, int moves){
        int currentPosition = player.getPlayerPosition();
        int newPosition = currentPosition + moves;
        if( newPosition >= numberOfFields){
            newPosition -= numberOfFields;
            giveStartMoney(player);
        }
        player.setPlayerPosition(newPosition);

    }

    public void landOnField(Player player){
        Field currentfield=fields[player.getPlayerPosition()];
        if(currentfield instanceof GoToJail){
            goToJail(player);
        }
    }

    public void giveStartMoney(Player player){
        player.addBalance(passStartReward);

    }

    public void goToJail(Player player){
        player.setPlayerPosition(30);
        player.setJailed(true);
    }

    public void inJail(Player player) {
        String response;
        if(player.getOutOfJailCards() > 0){
            response =mgui.requestUserButton((Language.getString("injail")),Language.getString("injailcard"),Language.getString("injailikkecard"));
            if(response.equals(Language.getString("injailikkecard"))){
                mgui.requestUserButton((Language.getString("injail")), Language.getString("injailpay"),Language.getString("injaildie"));
            }else {
                player.setJailed(false);
                player.addOutOfJailCard(-1);

                return;
            }
        } else {
            response =mgui.requestUserButton((Language.getString("injail")), Language.getString("injailpay"),Language.getString("injaildie"));
        }

        if (response.equals(Language.getString("injailpay"))){
            player.addBalance(-1000);
            player.setJailed(false);
        }
        else {

            int[] die=Die.throwDice();
            mgui.drawDice(die[0],die[1]);

            if (die[0]==die[1]){
                player.setJailed(false);
            }
            else {
                player.increaseTurnsinjail();
            }
        }
    }

}