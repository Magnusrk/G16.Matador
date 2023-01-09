package G16.Controllers;

import G16.Dev.DevConsole;
import G16.Fields.*;
import G16.Fields.BuyableFields.Brewery;
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
import G16.PlayerUtils.FakeDie;
import G16.PlayerUtils.Player;

import java.util.ArrayList;
import java.util.Objects;

public class GameController {

    private final int MAX_PLAYERS = 6;
    private final int MIN_PLAYERS = 3;
    private final int PASS_START_REWARD = 4000;
    private Die die = new Die();
    private final int NUMBER_OFF_FIELDS = 40;

    private ArrayList<Player> players = new ArrayList<>();

    private MatadorGUI mgui;

    private int currentPlayerID = 0;

    private Field[] fields;

    private boolean TEST_MODE;

    private boolean winnerfound = false;
    private boolean diceRigged = false;
    private int nextDiceValue = 0;
    private int extraCounter = 0;
    private boolean gameStarted = false;

    public GameController(boolean TEST_MODE) {
        this.TEST_MODE = TEST_MODE;
        fields = Initializer.InitFields();

        if (TEST_MODE) {
            mgui = new TestingGUI(this, fields);
        } else {
            mgui = new MatadorGUI(this, fields);
        }
    }

    public void playGame() {
        DevConsole dc = new DevConsole(this);
        setupPlayers();
        gameStarted = true;
        playTurn();

    }

    //Create players, limit amount of player to 3-6 players then add player's names.
    public void setupPlayers() {
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

    public void playTurn() {
        extraCounter = 0;
        Player currentPlayer = players.get(currentPlayerID);
        if (!currentPlayer.getBankrupt()) {
            if (currentPlayer.getJailed()) {
                inJail(currentPlayer);
            } else {
                throwAndMove(currentPlayer);
            }

            checkPlayerBankrupt(currentPlayer);
        }


        mgui.updatePlayerBalance(currentPlayer);
        setWinnerfound();
        currentPlayerID += 1;
        if (currentPlayerID >= players.size()) {
            currentPlayerID = 0;

        }
        updatePlayerinfo();
        if (!TEST_MODE) {
            if (!winnerfound) {
                playTurn();
            }
        }
    }

    /** If the player is bankrupt it removes their car from the board
     * and makes it so they don't own any properties
     * @param currentPlayer is the player whose turn it is
     *@return void
     */
    private void checkPlayerBankrupt(Player currentPlayer) {
        if (currentPlayer.getBankrupt()) {
            mgui.showMessage(currentPlayer.getName() + " er gået bankerot. Du er nu ude af spillet. ");
            removeowner(currentPlayer);
            mgui.removecar(currentPlayer);
        }
    }
    /** Checks how many players are left in the game, if there is only one they will be the winner
     *@return void
     */
    private void setWinnerfound() {
        int deadplayers = 0;
        Player potwinner = null;
        for (Player player : players) {
            if (player.getBankrupt()) {
                deadplayers++;
            } else {
                potwinner = player;
            }
        }
        if (deadplayers == players.size() - 1) {
            winnerfound = true;
            mgui.showMessage(potwinner + " " + Language.getString("winner"));
        }
    }

    public boolean getWinnerfound() {
        return winnerfound;
    }

    public Player getCurrentplayer() {
        return players.get(currentPlayerID);
    }
    /** Used in the dev console to rig the dice.
     * @param value is how many spaces you want to move
     *@return void
     */
    public void rigDice(int value) {
        diceRigged = true;
        nextDiceValue = value;
    }
    /** Used to do controlled test for method that uses the dice.
     * @param loaded determines wheter the dice should be predetermined or random.
     * @param value1 the face value of the first die
     * @param value2 the face value of the second die
     *@return java.awt.Color
     */
    public void fakeDie(boolean loaded, int value1, int value2) {
        if (loaded) {
            die = new FakeDie();
            FakeDie fakeDie = (FakeDie) die;
            fakeDie.setFaces(value1, value2);
        } else {
            die = new Die();
        }
    }
    /** Used to throw the die and move the player. also calls the landOnField method.
     * @param currentPlayer is the player whose turn it is.
     *@return void
     */
    private void throwAndMove(Player currentPlayer) {
        //Throw Dice
        mgui.showMessage(currentPlayer.getName() + " kast med terningen!");
        boolean extra = true;
        /*This code is used to stay on the same players turn in case they land a dice roll of 2 of a kind.
         *It will stay in the while as long as 2 of a kind is rolled and stop if 2 of a kind is not rolled.
         *In the event of getting 2 of a kind 3 times in a row, the while is broken,
         *and the player is sent directly to jail.*/
        while (extra) {
            int[] diceThrow = die.throwDice();

            mgui.drawDice(diceThrow[0], diceThrow[1]);

            if (diceThrow[0] == diceThrow[1]) {
                extraCounter++;
                if (extraCounter == 3) {
                    currentPlayer.setPlayerPosition(10);
                    currentPlayer.setJailed(true);
                    mgui.drawPlayerPosition(currentPlayer);
                    mgui.showMessage(Language.getString("snyd???"));
                    break;
                }
            } else {
                extra = false;
            }
            int diceSum = diceThrow[0] + diceThrow[1];


            if (diceRigged) {
                diceSum = nextDiceValue;
                diceRigged = false;
            }

            mgui.drawDice(diceThrow[0], diceThrow[1]);


            //Move player
            movePlayer(currentPlayer, diceSum);
            mgui.drawPlayerPosition(currentPlayer);
            landOnField(currentPlayer, diceSum);
            if (diceThrow[0] == diceThrow[1]) {
                mgui.showMessage(Language.getString("ekstra"));
                mgui.showMessage(currentPlayer.getName() + " kast med terningen!");
            }
        }
    }

    public void balance(Player player, int add) {
        player.addBalance(add);
        mgui.updatePlayerBalance(player);
    }

    /** Used to move a player. Also checks if the player passed start.
     * @param player is a player. Usually the current player
     *@return void.
     */
        public void movePlayer (Player player,int moves){
            int currentPosition = player.getPlayerPosition();
            int newPosition = currentPosition + moves;
            if( newPosition >= NUMBER_OFF_FIELDS){
                newPosition -= NUMBER_OFF_FIELDS;
                giveStartMoney(player);
            }
            player.setPlayerPosition(newPosition);

    }
    /** Used when a player lands on a field and determines what should happen depending on the field
     * @param player is the one who lands on the field
     * @param diceSum is the sum of the dice, used if the field is a brewery
     *@return void
     */
    public void landOnField (Player player, int diceSum){
        Field currentfield = fields[player.getPlayerPosition()];
        if(currentfield instanceof GoToJail){
            goToJail(player);
        } else if (currentfield instanceof Property prop) {
            //if property has no owner, then player can purchase it.
            if (prop.getOwner()==null) {
                buyField(player,prop);
            } else {
                //if property has an owner, then player have to pay the rent.
                payRent(player,prop);
            }
            //The player land on the Shipping Company's field.
        } else if (currentfield instanceof ShippingCompany ship) {
            if (ship.getOwner()==null){
                buyField(player,ship);
            } else {
                payShipRent(player, ship);
                }
            } else if (currentfield instanceof Tax tax){
                mgui.showMessage("Du betaler skat");
                player.addBalance(-tax.getTax());
            } else if (currentfield instanceof Brewery brew){
                if (brew.getOwner()==null){
                    buyField(player,brew);
                } else {
                    payBrewRent(player,brew, diceSum);
                }
            }

    }
    /** Used to give money to a player who passes start
     * @param player is the player who get the money
     *@return void
     */
    //Added 4000 kr to the player's money balance
    public void giveStartMoney (Player player){
        player.addBalance(PASS_START_REWARD);
    }

    /** Used to update all the players balance and their position on the UI.
     *@return void
     */
    public void updatePlayerinfo(){
        for (Player player: players){
            mgui.drawPlayerPosition(player);
            mgui.updatePlayerBalance(player);
        }
    }

    /** Used to send a player to jail
     * @param player is the player who gets send to jail
     *@return void
     */
    public void goToJail(Player player) {
        player.setPlayerPosition(10);
        player.setJailed(true);
        mgui.drawPlayerPosition(player);
    }

    /** Used to send player to jail by their id. Used in the dev console.
     * @param id is the players id, player 1's id is 0.
     *@return void
     */
    public void goToJailByID(int id) {
        Player player = players.get(id);
        player.setPlayerPosition(10);
        player.setJailed(true);
        mgui.showMessage(Language.getString("gotojailprompt"));
        mgui.drawPlayerPosition(player);
    }

    /** Used when a player is in jail. Gives the player the option to choose what they want to do the get out of jail.
     * @param player is the player who is in jail
     *@return void
     */
    public void inJail(Player player) {
        String response;
        if (player.getOutOfJailCards() > 0) {
            response = mgui.requestUserButton((Language.getString("injail")), Language.getString("injailcard"), Language.getString("injailikkecard"));
            if (response.equals(Language.getString("injailikkecard"))) {
                mgui.requestUserButton((Language.getString("injail")), Language.getString("injailpay"), Language.getString("injaildie"));
            } else {
                player.setJailed(false);
                player.addOutOfJailCard(-1);
                mgui.showMessage(Language.getString("brugtkort"));
                throwAndMove(player);
                return;
            }
        } else {
            response = mgui.requestUserButton((Language.getString("injail")), Language.getString("injailpay"), Language.getString("injaildie"));
        }

        if (response.equals(Language.getString("injailpay"))) {
            player.addBalance(-1000);
            player.setJailed(false);
            mgui.showMessage(Language.getString("betalt"));
            throwAndMove(player);
        } else {

            int[] dievalue = die.throwDice();
            mgui.drawDice(dievalue[0], dievalue[1]);

            if (dievalue[0] == dievalue[1]) {
                player.setJailed(false);
                movePlayer(player, dievalue[0] + dievalue[1]);
                landOnField(player, dievalue[0] + dievalue[1]);
                mgui.showMessage(Language.getString("2ens"));
                mgui.drawPlayerPosition(player);
                extraCounter++;
                throwAndMove(player);
            } else {
                player.increaseTurnsinjail();
                mgui.showMessage(Language.getString("ikke2ens"));
                if (player.getTurnsinjail() > 2) {
                    player.addBalance(-1000);
                    player.setJailed(false);
                    movePlayer(player, dievalue[0] + dievalue[1]);
                    landOnField(player, dievalue[0] + dievalue[1]);
                    mgui.showMessage(Language.getString("3ture"));
                    mgui.drawPlayerPosition(player);
                    movePlayer(player, dievalue[0] + dievalue[1]);
                    landOnField(player, dievalue[0] + dievalue[1]);

                    //mgui.showMessage(Language.getString("ekstra"));
                    //throwAndMove(player);

                }
            }
        }
    }
    /** Used to get an arraylist of all the players
     *@return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }
    /** Used when a player lands on an unowned buyable field. Gives the player the option to buy the field or do nothing.
     * @param currentplayer is the player who landed on the field
     * @param currentfield is the field the player landed on.
     *@return java.awt.Color
     */
        public void buyField(Player currentplayer, BuyableField currentfield) {
            if (currentfield.getPrice()< currentplayer.getPlayerBalance()) {
                String results = null;
                if (currentfield instanceof Property){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("prop"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                }
                if (currentfield instanceof ShippingCompany){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("ship"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                    if (results.equals(Language.getString("yesTxt"))) {
                        currentplayer.setShipsOwned(currentplayer.getShipsOwned()+1);
                    }
                }
                if (currentfield instanceof Brewery){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("brew"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                    if (results.equals(Language.getString("yesTxt"))) {
                        currentplayer.setBrewsOwned(currentplayer.getBrewsOwned()+1);
                    }
                }
                if (Objects.equals(results, Language.getString("yesTxt"))) {
                    currentplayer.addBalance(-currentfield.getPrice());
                    currentfield.setOwner(currentplayer);
                    mgui.setOwner(currentfield, currentplayer.getPlayerPosition());
                }

                }else {
                mgui.showMessage("propikkeråd");
            }
        }

    /** Used to give a player money by their id. Used in the dev console
     * @param id is the players id, player 1's id is 0.
     *@return void
     */
    public void addPlayerMoney(int id, int amount) {
        players.get(id).addBalance(amount);
        mgui.updatePlayerBalance(players.get(id));
    }
    /** Used to set whose turn it is
     * @param id is the players id, player 1's id is 0.
     *@return void
     */
    public void setPlayerTurn(int id) {
        currentPlayerID = id;
    }
    /** Used to pay rent when a player lands on a property field
     * @param currentplayer is the player who lands on the field
     * @param currentfield is the field the player lands on.
     *@return void
     */
    public void payRent(Player currentplayer, BuyableField currentfield) {
        if (currentfield.getOwner() != currentplayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentfield.getOwner());
            if (currentfield instanceof Property property) {
                if (allinColorOwned(property)) {
                    if (currentfield.getRent(0) < currentplayer.getPlayerBalance()) {
                        currentplayer.addBalance(2 * -currentfield.getRent(0));
                        currentfield.getOwner().addBalance(2 * currentfield.getRent(0));
                    } else {
                        currentfield.getOwner().addBalance(currentplayer.getPlayerBalance() + 1);
                        currentplayer.addBalance(-currentplayer.getPlayerBalance() - 1);
                    }
                } else if (currentfield.getRent(0) < currentplayer.getPlayerBalance()) {
                    currentplayer.addBalance(-currentfield.getRent(0));
                    currentfield.getOwner().addBalance(currentfield.getRent(0));
                } else {
                    currentfield.getOwner().addBalance(currentplayer.getPlayerBalance() + 1);
                    currentplayer.addBalance(-currentplayer.getPlayerBalance() - 1);
                }
            }
        }
        else
        {
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to pay rent when a player lands on a ferry field
     * @param currentplayer is the player who lands on the field
     * @param currentfield is the field the player lands on.
     *@return void
     */
    public void payShipRent(Player currentplayer, BuyableField currentfield){
        if (currentfield.getOwner() != currentplayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentfield.getOwner());
            if (currentfield.getRent(currentfield.getOwner().getShipsOwned() - 1) < currentplayer.getPlayerBalance()) {
                currentplayer.addBalance(-currentfield.getRent(currentfield.getOwner().getShipsOwned() - 1));
                currentfield.getOwner().addBalance(currentfield.getRent(currentfield.getOwner().getShipsOwned() - 1));
                mgui.updatePlayerBalance(currentfield.getOwner());
            } else {
                currentfield.getOwner().addBalance(currentplayer.getPlayerBalance() + 1);
                currentplayer.addBalance(-currentplayer.getPlayerBalance() - 1);
            }
        } else {
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to pay rent when a player lands on a brewery field
     * @param currentplayer is the player who lands on the field
     * @param currentfield is the field the player lands on.
     * @param diceSum is the sum of the dice.
     *@return void
     */
    public void payBrewRent(Player currentplayer, BuyableField currentfield, int diceSum){
        if (currentfield.getOwner() != currentplayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentfield.getOwner());
            int toPay = currentfield.getRent(currentfield.getOwner().getBrewsOwned() - 1) * diceSum;
            if (toPay < currentplayer.getPlayerBalance()) {
                currentplayer.addBalance(-toPay);
                currentfield.getOwner().addBalance(toPay);
                mgui.updatePlayerBalance(currentfield.getOwner());
            } else {
                currentfield.getOwner().addBalance(currentplayer.getPlayerBalance() + 1);
                currentplayer.addBalance(-currentplayer.getPlayerBalance() - 1);
            }
        } else{
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to remove the owner of buyable fields if they're owned by a bankrupt player.
     * @param bankruptplayer is a player who is bankrupt
     *@return void
     */
    public void removeowner (Player bankruptplayer){
        Field field[]= fields;
        for (int i=0; i<field.length;i++){
            if (field[i] instanceof BuyableField prop){
                if (prop.getOwner()==bankruptplayer){
                    prop.setOwner(null);
                    mgui.resetOwner(prop,i);
                }
            }
        }
    }
    /** Used to show whose turn it is on the UI
     *@return String
     */
    public String getTurnMessage (){
        if(players.size() > 0 && gameStarted){
            return "["+players.get(currentPlayerID).getName() +"'s tur] ";
        }
        else {
            return "";
        }
    }

    /** Used to check if a player all the properties of the same color.
     * @param currentproperty is the property that a player has landed on
     *@return a boolean
     */
    public boolean allinColorOwned (Property currentproperty){
        Player propertyowner = currentproperty.getOwner();
        for (Field field : fields) {
            if (field instanceof Property property) {
                if (currentproperty.getColor() == property.getColor()) {
                    if (property.getOwner() == null) {
                        return false;
                    }
                    else if (property.getOwner().getID() != propertyowner.getID()) {
                        return false;
                    }
                }
            }

        }
        return true;
    }
}
