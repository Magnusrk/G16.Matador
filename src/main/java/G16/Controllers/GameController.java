package G16.Controllers;

import G16.Fields.*;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.UnbuyableFields.GoToJail;
import G16.Fields.Initializer;
import G16.Fields.Field;
import G16.Fields.UnbuyableFields.Tax;
import G16.Graphics.MatadorGUI;
import G16.Graphics.TestingGUI;
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

    private boolean TEST_MODE;

    private boolean diceRigged = false;
    private int nextDiceValue = 0;

    private boolean gameStarted = false;

    public GameController(boolean TEST_MODE){
        this.TEST_MODE = TEST_MODE;
        fields=Initializer.InitFields();

        if(TEST_MODE){
            mgui = new TestingGUI(this, fields);
        }else {
            mgui = new MatadorGUI(this, fields);
        }
    }

    public void playGame(){
        DevConsole dc = new DevConsole(this);
        setupPlayers();
        gameStarted = true;
        playTurn();

    }

    public void setupPlayers(){
        int playerCount = mgui.requestInteger(Language.getString("howManyPlayers"), MIN_PLAYERS, MAX_PLAYERS);
        while (players.size() < playerCount) {
            int playerNum = 0;
            while (players.size() < playerCount) {
                String newPlayerName = mgui.requestString(Language.getString("playerName"));
                Player newPlayer = new Player();
                newPlayer.setName(newPlayerName);
                newPlayer.setID(players.size());
                players.add(newPlayer);
                mgui.addPlayer(newPlayerName, newPlayer.getPlayerBalance(), playerNum);
                mgui.drawPlayerPosition(newPlayer);
                playerNum++;
            }
            mgui.showMessage("Tryk OK for at starte!");
        }
    }

        public void playTurn () {

            Player currentPlayer = players.get(currentPlayerID);
            if (!currentPlayer.getBankrupt()) {


        if (currentPlayer.getJailed()){
            inJail(currentPlayer);
        }else  {
            throwAndMove(currentPlayer);
        }


        //Update balance and position on GUI

        mgui.updatePlayerBalance(currentPlayer);

            currentPlayerID += 1;
        if(currentPlayerID >= players.size()){
            currentPlayerID = 0;

            }

        if(currentPlayer.getBankrupt()){
            removeowner(currentPlayer);
            mgui.removecar(currentPlayer);
        }


            if(!TEST_MODE){
            playTurn();
        }

        }

    public void rigDice(int value){
        diceRigged = true;
        nextDiceValue = value;
    }

    private void throwAndMove(Player currentPlayer) {
        //Throw Dice
        mgui.showMessage(currentPlayer.getName() + " kast med terningen!");
        boolean extra = true;
        int extraCounter = 0;
        while (extra) {
            int[] diceThrow = Die.throwDice();

            mgui.drawDice(diceThrow[0], diceThrow[1]);

            if (diceThrow[0] == diceThrow[1]) {
                extraCounter ++;
                if (extraCounter == 3) {
                    currentPlayer.setPlayerPosition(10);
                    currentPlayer.setJailed(true);
                    mgui.drawPlayerPosition(currentPlayer);
                    mgui.showMessage(Language.getString("snyd???"));
                    break;
                }
                mgui.showMessage(Language.getString("ekstra"));
            } else {
                extra = false;
            }
            int diceSum = diceThrow[0] + diceThrow[1];


            //Move player
            movePlayer(currentPlayer, diceSum);
            mgui.drawPlayerPosition(currentPlayer);
            landOnField(currentPlayer);
        }
    }

        public void balance (Player player,int add){
            player.addBalance(add);
            mgui.updatePlayerBalance(player);
        }

        public void movePlayer (Player player,int moves){
            int currentPosition = player.getPlayerPosition();
            int newPosition = currentPosition + moves;
            if( newPosition >= numberOfFields){
                newPosition -= numberOfFields;
                giveStartMoney(player);
            }
            player.setPlayerPosition(newPosition);

        }

        public void landOnField (Player player){
            Field currentfield = fields[player.getPlayerPosition()];
            if(currentfield instanceof GoToJail){
                goToJail(player);
            } else if (currentfield instanceof Property prop) {
                if (prop.getOwner()==null) {
                    buyPropfield(player,prop);
                } else {
                    payRent(player,prop);
            }
        } else if (currentfield instanceof ShippingCompany ship) {
            if (ship.getOwner()==null){
                buyShipField(player,ship);
            } else {
                payShipRent(player, ship);
                }
            } else if (currentfield instanceof Tax tax){
                mgui.showMessage("Du betaler skat");
                player.addBalance(-tax.getTax());
            }

        }

        public void giveStartMoney (Player player){
            player.addBalance(passStartReward);

        }

        public void goToJail (Player player){
            player.setPlayerPosition(10);
            player.setJailed(true);
            mgui.drawPlayerPosition(player);
        }

    public void goToJailByID(int id){
        Player player = players.get(id);
        player.setPlayerPosition(10);
        player.setJailed(true);
        mgui.drawPlayerPosition(player);
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
                mgui.showMessage(Language.getString("brugtkort"));
                throwAndMove(player);
                return;
            }
        } else {
            response =mgui.requestUserButton((Language.getString("injail")), Language.getString("injailpay"),Language.getString("injaildie"));
        }

            if (response.equals(Language.getString("injailpay"))){
                player.addBalance(-1000);
                player.setJailed(false);
                mgui.showMessage(Language.getString("betalt"));
                throwAndMove(player);
            }
        else {

                int[] die=Die.throwDice();
                mgui.drawDice(die[0],die[1]);

                if (die[0]==die[1]){
                    player.setJailed(false);
                    movePlayer(player,die[0]+die[1]);
                    landOnField(player);
                    mgui.showMessage(Language.getString("2ens"));
                }
            else {
                    player.increaseTurnsinjail();
                    mgui.showMessage(Language.getString("ikke2ens"));
                    if (player.getTurnsinjail()>2){
                        player.addBalance(-1000);
                        player.setJailed(false);
                        movePlayer(player,die[0]+die[1]);
                        landOnField(player);
                        mgui.showMessage(Language.getString("3ture"));
                    }
                }
            }
        }

        public ArrayList<Player> getPlayers (){
            return players;
        }

    public void buyPropfield(Player currentplayer, BuyableField currentfield){
        if (currentfield.getPrice()< currentplayer.getPlayerBalance()) {
            String resuslt = mgui.requestUserButton(Language.getString("prop"), Language.getString("yesTxt"), Language.getString("noTxt"));
            if (resuslt == Language.getString("yesTxt")) {
                currentplayer.addBalance(-currentfield.getPrice());
                currentfield.setOwner(currentplayer);
                mgui.setOwner(currentfield, currentplayer.getPlayerPosition());
            }
        }
        else {
            mgui.showMessage("propikkeråd");
        }
    }
    public void buyShipField(Player currentplayer, BuyableField currentfield){
        if (currentfield.getPrice()< currentplayer.getPlayerBalance()) {
            String resuslt = mgui.requestUserButton(Language.getString("ship"), Language.getString("yesTxt"), Language.getString("noTxt"));
            if (resuslt == Language.getString("yesTxt")) {
                currentplayer.addBalance(-currentfield.getPrice());
                currentfield.setOwner(currentplayer);
                currentplayer.setShipsOwned(currentplayer.getShipsOwned()+1);
                mgui.setOwner(currentfield, currentplayer.getPlayerPosition());
            }
        }
        else {
            mgui.showMessage(Language.getString("propikkeråd"));
        }
    }

    public void addPlayerMoney(int id, int amount){
        players.get(id).addBalance(amount);
        mgui.updatePlayerBalance(players.get(id));
    }

    public void setPlayerTurn(int id){
        currentPlayerID = id;
    }

    public void payRent(Player currentplayer, BuyableField currentfield){
        mgui.showMessage(Language.getString("payrent" )+" "+ currentfield.getOwner());
if (currentfield instanceof Property property){
                if (allinColorOwned(property)){
                    if (currentfield.getRent(0) < currentplayer.getPlayerBalance()) {
                        currentplayer.addBalance(2*-currentfield.getRent(0));
                        currentfield.getOwner().addBalance(2*currentfield.getRent(0));
                    } else {
                        currentplayer.addBalance(-currentplayer.getPlayerBalance());
                        currentfield.getOwner().addBalance(currentplayer.getPlayerBalance());
                    }
                }
            }        if (currentfield.getRent(0)<currentplayer.getPlayerBalance()) {
            currentplayer.addBalance(-currentfield.getRent(0));
            currentfield.getOwner().addBalance(currentfield.getRent(0));
        }
        else {
            currentplayer.addBalance(-currentplayer.getPlayerBalance());
            currentfield.getOwner().addBalance(currentplayer.getPlayerBalance());
        }
    }
    public void payShipRent(Player currentplayer, BuyableField currentfield){
        mgui.showMessage(Language.getString("payrent" )+" "+ currentfield.getOwner());
        if (currentfield.getRent(currentfield.getOwner().getShipsOwned()-1)<currentplayer.getPlayerBalance()) {
            currentplayer.addBalance(-currentfield.getRent(currentfield.getOwner().getShipsOwned()-1));
            currentfield.getOwner().addBalance(currentfield.getRent(currentfield.getOwner().getShipsOwned()-1));
        }
            else {
            currentplayer.addBalance(-currentplayer.getPlayerBalance());
            currentfield.getOwner().addBalance(currentplayer.getPlayerBalance());
        }
    }

        public void removeowner (Player bankruptplayer){
            Field field[]= fields;
            for (int i=0; i<field.length;i++){
                if (field[i] instanceof BuyableField prop){
                    if (prop.getOwner()==bankruptplayer){
                        prop.setOwner(null);
                    }
                }
            }
        }

        public String getTurnMessage (){
            if(players.size() > 0 && gameStarted){
                return "["+players.get(currentPlayerID).getName() +"'s tur] ";
            }
        else {
                return "";
            }

        }

}
        public boolean allinColorOwned (Property currentpropery){
            Player properyowner = currentpropery.getOwner();
            for (Field field : fields) {
                if (field instanceof Property property) {
                    if (currentpropery.getColor() == property.getColor()) {
                        if (property.getOwner() == null) {
                            return false;
                        }
                        if (property.getOwner().getID() != properyowner.getID()) {
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
    }
