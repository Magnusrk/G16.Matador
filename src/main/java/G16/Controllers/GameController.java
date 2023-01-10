package G16.Controllers;

import G16.Dev.DevConsole;
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
import gui_fields.GUI_Field;
import gui_fields.GUI_Street;

import java.util.ArrayList;
import java.util.Objects;

public class GameController {

    private final int MAX_PLAYERS = 6;
    private final int MIN_PLAYERS = 3;
    private Die die = new Die();
    private final int NUMBER_OF_FIELDS = 40;

    private final ArrayList<Player> players = new ArrayList<>();

    private final MatadorGUI mgui;

    private int currentPlayerID = 0;

    private final Field[] fields;

    private final boolean TEST_MODE;

    private boolean winnerFound = false;
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
        new DevConsole(this);
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
        setWinnerFound();
        currentPlayerID += 1;
        if (currentPlayerID >= players.size()) {
            currentPlayerID = 0;

        }
        updatePlayerInfo();
        if (!TEST_MODE) {
            if (!winnerFound) {
                playTurn();
            }
        }
    }

    /** If the player is bankrupt it removes their car from the board
     * and makes it so they don't own any properties
     * @param currentPlayer is the player whose turn it is
     */
    private void checkPlayerBankrupt(Player currentPlayer) {
        if (currentPlayer.getBankrupt()) {
            mgui.showMessage(currentPlayer.getName() + " er gået bankerot. Du er nu ude af spillet. ");
            removeOwner(currentPlayer);
            mgui.removeCar(currentPlayer);
        }
    }
    /** Checks how many players are left in the game, if there is only one they will be the winner
     */
    private void setWinnerFound() {
        int deadPlayers = 0;
        Player potentialWinner = null;
        for (Player player : players) {
            if (player.getBankrupt()) {
                deadPlayers++;
            } else {
                potentialWinner = player;
            }
        }
        if (deadPlayers == players.size() - 1) {
            winnerFound = true;
            mgui.showMessage(potentialWinner + " " + Language.getString("winner"));
        }
    }

    public boolean getWinnerFound() {
        return winnerFound;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerID);
    }
    /** Used in the dev console to rig the dice.
     * @param value is how many spaces you want to move
     */
    public void rigDice(int value) {
        diceRigged = true;
        nextDiceValue = value;
    }
    /** Used to do controlled test for method that uses the dice.
     * @param loaded determines wheter the dice should be predetermined or random.
     * @param value1 the face value of the first die
     * @param value2 the face value of the second die
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

    /** Used to move a player. Also checks if the player passed start.
     * @param player is a player. Usually the current player
     */
        public void movePlayer (Player player,int moves){
            int currentPosition = player.getPlayerPosition();
            int newPosition = currentPosition + moves;
            if( newPosition >= NUMBER_OF_FIELDS){
                newPosition -= NUMBER_OF_FIELDS;
                giveStartMoney(player);
            }
            player.setPlayerPosition(newPosition);

    }
    /** Used when a player lands on a field and determines what should happen depending on the field
     * @param player is the one who lands on the field
     * @param diceSum is the sum of the dice, used if the field is a brewery
     */
    public void landOnField (Player player, int diceSum){
        Field currentField = fields[player.getPlayerPosition()];
        if(currentField instanceof GoToJail){
            goToJail(player);
        } else if (currentField instanceof Property prop) {
            //if property has no owner, then player can purchase it.
            if (prop.getOwner()==null) {
                buyField(player,prop);
            } else {
                //if property has an owner, then player have to pay the rent.
                payRent(player,prop);
            }
            //The player land on the Shipping Company's field.
        } else if (currentField instanceof ShippingCompany ship) {
            if (ship.getOwner()==null){
                buyField(player,ship);
            } else {
                payShipRent(player, ship);
                }
            } else if (currentField instanceof Tax tax){
                mgui.showMessage("Du betaler skat");
                player.addBalance(-tax.getTax());
            } else if (currentField instanceof Brewery brew){
                if (brew.getOwner()==null){
                    buyField(player,brew);
                } else {
                    payBrewRent(player,brew, diceSum);
                }
            }

    }
    /** Used to give money to a player who passes start
     * @param player is the player who get the money
     */
    //Added 4000 kr to the player's money balance
    public void giveStartMoney (Player player){
        int PASS_START_REWARD = 4000;
        player.addBalance(PASS_START_REWARD);
    }

    /** Used to update all the players balance and their position on the UI.
     */
    public void updatePlayerInfo(){
        for (Player player: players){
            mgui.drawPlayerPosition(player);
            mgui.updatePlayerBalance(player);
        }
    }

    /** Used to send a player to jail
     * @param player is the player who gets send to jail
     */
    public void goToJail(Player player) {
        player.setPlayerPosition(10);
        player.setJailed(true);
        mgui.drawPlayerPosition(player);
    }

    /** Used to send player to jail by their id. Used in the dev console.
     * @param id is the players id, player 1's id is 0.
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

            int[] dieValue = die.throwDice();
            mgui.drawDice(dieValue[0], dieValue[1]);

            if (dieValue[0] == dieValue[1]) {
                player.setJailed(false);
                movePlayer(player, dieValue[0] + dieValue[1]);
                landOnField(player, dieValue[0] + dieValue[1]);
                mgui.showMessage(Language.getString("2ens"));
                mgui.drawPlayerPosition(player);
                extraCounter++;
                throwAndMove(player);
            } else {
                player.increaseTurnsInJail();
                mgui.showMessage(Language.getString("ikke2ens"));
                if (player.getTurnsInJail() > 2) {
                    player.addBalance(-1000);
                    player.setJailed(false);
                    movePlayer(player, dieValue[0] + dieValue[1]);
                    landOnField(player, dieValue[0] + dieValue[1]);
                    mgui.showMessage(Language.getString("3ture"));
                    mgui.drawPlayerPosition(player);
                    movePlayer(player, dieValue[0] + dieValue[1]);
                    landOnField(player, dieValue[0] + dieValue[1]);

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
     * @param currentPlayer is the player who landed on the field
     * @param currentField is the field the player landed on.
     */
        public void buyField(Player currentPlayer, BuyableField currentField) {
            if (currentField.getPrice()< currentPlayer.getPlayerBalance()) {
                String results = null;
                if (currentField instanceof Property prop){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("prop"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                }
                if (currentField instanceof ShippingCompany){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("ship"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                    if (results.equals(Language.getString("yesTxt"))) {
                        currentPlayer.setShipsOwned(currentPlayer.getShipsOwned()+1);
                    }
                }
                if (currentField instanceof Brewery){
                    if (TEST_MODE){
                        results = Language.getString("yesTxt");
                    } else {
                        results = mgui.requestUserButton(Language.getString("brew"), Language.getString("yesTxt"), Language.getString("noTxt"));
                    }
                    if (results.equals(Language.getString("yesTxt"))) {
                        currentPlayer.setBrewsOwned(currentPlayer.getBrewsOwned()+1);
                    }
                }
                if (Objects.equals(results, Language.getString("yesTxt"))) {
                    currentPlayer.addBalance(-currentField.getPrice());
                    currentField.setOwner(currentPlayer);
                    mgui.setOwner(currentField, currentPlayer.getPlayerPosition());
                }

                }else {
                mgui.showMessage(Language.getString("propikkeråd"));
            }
        }

    /** Used to give a player money by their id. Used in the dev console
     * @param id is the players id, player 1's id is 0.
     */
    public void addPlayerMoney(int id, int amount) {
        players.get(id).addBalance(amount);
        mgui.updatePlayerBalance(players.get(id));
    }
    /** Used to set whose turn it is
     * @param id is the players id, player 1's id is 0.
     */
    public void setPlayerTurn(int id) {
        currentPlayerID = id;
    }
    /** Used to pay rent when a player lands on a property field
     * @param currentPlayer is the player who lands on the field
     * @param currentField is the field the player lands on.
     */
    public void payRent(Player currentPlayer, BuyableField currentField) {
        if (currentField.getOwner() != currentPlayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            if (currentField instanceof Property property) {
                if (allinColorOwned(property)) {
                    if (currentField.getRent(0) < currentPlayer.getPlayerBalance()) {
                        currentPlayer.addBalance(2 * -currentField.getRent(0));
                        currentField.getOwner().addBalance(2 * currentField.getRent(0));
                    } else {
                        currentField.getOwner().addBalance(currentPlayer.getPlayerBalance() + 1);
                        currentPlayer.addBalance(-currentPlayer.getPlayerBalance() - 1);
                    }
                } else if (currentField.getRent(0) < currentPlayer.getPlayerBalance()) {
                    currentPlayer.addBalance(-currentField.getRent(0));
                    currentField.getOwner().addBalance(currentField.getRent(0));
                } else {
                    currentField.getOwner().addBalance(currentPlayer.getPlayerBalance() + 1);
                    currentPlayer.addBalance(-currentPlayer.getPlayerBalance() - 1);
                }
            }
        }
        else
        {
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to pay rent when a player lands on a ferry field
     * @param currentPlayer is the player who lands on the field
     * @param currentField is the field the player lands on.
     */
    public void payShipRent(Player currentPlayer, BuyableField currentField){
        if (currentField.getOwner() != currentPlayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            if (currentField.getRent(currentField.getOwner().getShipsOwned() - 1) < currentPlayer.getPlayerBalance()) {
                currentPlayer.addBalance(-currentField.getRent(currentField.getOwner().getShipsOwned() - 1));
                currentField.getOwner().addBalance(currentField.getRent(currentField.getOwner().getShipsOwned() - 1));
                mgui.updatePlayerBalance(currentField.getOwner());
            } else {
                currentField.getOwner().addBalance(currentPlayer.getPlayerBalance() + 1);
                currentPlayer.addBalance(-currentPlayer.getPlayerBalance() - 1);
            }
        } else {
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to pay rent when a player lands on a brewery field
     * @param currentPlayer is the player who lands on the field
     * @param currentField is the field the player lands on.
     * @param diceSum is the sum of the dice.
     */
    public void payBrewRent(Player currentPlayer, BuyableField currentField, int diceSum){
        if (currentField.getOwner() != currentPlayer) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            int toPay = currentField.getRent(currentField.getOwner().getBrewsOwned() - 1) * diceSum;
            if (toPay < currentPlayer.getPlayerBalance()) {
                currentPlayer.addBalance(-toPay);
                currentField.getOwner().addBalance(toPay);
                mgui.updatePlayerBalance(currentField.getOwner());
            } else {
                currentField.getOwner().addBalance(currentPlayer.getPlayerBalance() + 1);
                currentPlayer.addBalance(-currentPlayer.getPlayerBalance() - 1);
            }
        } else{
            mgui.showMessage(Language.getString("selfown"));
        }
    }
    /** Used to remove the owner of buyable fields if they're owned by a bankrupt player.
     * @param bankruptPlayer is a player who is bankrupt
     */
    public void removeOwner(Player bankruptPlayer){
        Field[] field = fields;
        for (int i=0; i<field.length;i++){
            if (field[i] instanceof BuyableField prop){
                if (prop.getOwner()==bankruptPlayer){
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
     * @param currentProperty is the property that a player has landed on
     *@return a boolean
     */
    public boolean allinColorOwned (Property currentProperty){
        Player propertyOwner = currentProperty.getOwner();
        for (Field field : fields) {
            if (field instanceof Property property) {
                if (currentProperty.getColor() == property.getColor()) {
                    if (property.getOwner() == null) {
                        return false;
                    }
                    else if (property.getOwner().getID() != propertyOwner.getID()) {
                        return false;
                    }
                }
            }

        }
        return true;
    }


}
