package G16.Controllers;

import G16.Dev.DevConsole;
import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.UnbuyableFields.Chance;
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
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
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
    private boolean auctionMode =false;

    private boolean bankruptcySavingState =false;
    ChanceCardController ccController;
    TradeController tradeController;

    public GameController(boolean TEST_MODE) {
        this.TEST_MODE = TEST_MODE;
        fields = Initializer.InitFields();

        if (TEST_MODE) {
            mgui = new TestingGUI(this, fields);
        } else {
            mgui = new MatadorGUI(this, fields);
        }
        ccController = new ChanceCardController(mgui);
        tradeController = new TradeController(mgui, this);
    }

    public void playGame() {
        new DevConsole(this);

        setGameLanguage();

        setupPlayers();
        gameStarted = true;
        playTurn();
    }

    private void setGameLanguage(){
        String language = mgui.requestUserDropDown("Select game language", "Dansk", "English");
        if(language.equals("English")){
            Language.setLanguage("en");
            mgui.updateGUI(fields);
        }
    }

    //Create players, limit amount of player to 3-6 players then add player's names.
    public void setupPlayers() {
        int playerCount = mgui.requestInteger(Language.getString("howManyPlayers"), MIN_PLAYERS, MAX_PLAYERS);
        while (players.size() < playerCount) {
            int playerNum = 0;
            while (players.size() < playerCount) {
                //Check that the player's name must not be the same
                String newPlayerName;
                boolean nameIsTaken;
                do {//The warning message shows if Player's name is taken and shows the new name filling field.
                    newPlayerName =  mgui.requestString(Language.getString("playerName"));
                    nameIsTaken = false;
                    for (Player player: players){
                        if(player.getName().equals(newPlayerName)){
                            nameIsTaken = true;
                            mgui.showMessage(Language.getString("playerNameTaken"));
                        }
                    }
                }while (nameIsTaken);

                Player newPlayer = new Player();
                newPlayer.setName(newPlayerName);
                newPlayer.setID(players.size());
                players.add(newPlayer);
                mgui.addPlayer(newPlayerName, newPlayer.getPlayerBalance(), playerNum);
                mgui.drawPlayerPosition(newPlayer);
                playerNum++;
            }
            mgui.showMessage(Language.getString("pressStart"));
        }
    }

    public void playTurn() {
        extraCounter = 0;
        Player currentPlayer = players.get(currentPlayerID);
        if (!currentPlayer.getBankrupt()) {
            if (currentPlayer.getJailed()) {
                inJail(currentPlayer);
                if (!currentPlayer.getBankrupt()) {askPlayerActions(currentPlayer);}
            } else {
                throwAndMove(currentPlayer);
                if (!currentPlayer.getBankrupt()) {askPlayerActions(currentPlayer);}
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

    private void askPlayerActions(Player currentPlayer) {
        int houses;
        ArrayList<String> options = new ArrayList<>();
        //Trade mulighed
        options.add(Language.getString("tradeAction"));

        //Byg hus/hotel
        ArrayList<Property> ownedProperties = getOwnedProperties(currentPlayer);
        ownedProperties.removeIf(prop -> !allinColorOwned(prop));
        if(ownedProperties.size() > 0 ){
            options.add(Language.getString("buildHouseAction"));
        }
        options.add(Language.getString("mort"));
        options.add(Language.getString("payMort"));
        for (Property property: ownedProperties) {
           houses=property.getHouseCount();
            if (houses>0) {
                options.add(Language.getString("sellHouse"));
                break;
            }
        }
        ArrayList<Property>[] HouseInfo = getHouseMethod(ownedProperties);
        ownedProperties = HouseInfo[0];
        ArrayList<Property> ownedHouses = HouseInfo[1];

        //Slut tur
        options.add(Language.getString("endTurnAction"));

        String action = mgui.requestUserButton(Language.getString("getPlayerAction"), options.toArray((new String[0])));

        if(action.equals(Language.getString("tradeAction"))){
            tradeController.startTrade(currentPlayer);
        } else if(action.equals(Language.getString("buildHouseAction"))){
            buyHousePrompt(currentPlayer, ownedProperties);
        } else if (action.equals(Language.getString("mort"))) {
            mortgage(currentPlayer);
        } else if (action.equals(Language.getString("payMort"))) {
            payMortgage(currentPlayer);
        } else if (action.equals(Language.getString("sellHouse"))) {
            sellHousePrompt(currentPlayer, ownedHouses);
        }
        if(!action.equals(Language.getString("endTurnAction"))){
            askPlayerActions(currentPlayer);
        }
    }

    @NotNull
    private static ArrayList<Property>[] getHouseMethod(ArrayList<Property> ownedProperties) {
        ArrayList<Property> ownedHouses= (ArrayList<Property>) ownedProperties.clone();

        HashMap<Color, Integer> streetMinHouseMap = new HashMap<>();
        HashMap<Color, Integer> streetMaxHouseMap = new HashMap<>();
        for(Property prop : ownedProperties){
            Color currentColor = prop.getColor();
            if(streetMinHouseMap.containsKey(currentColor)){
                if(prop.getHouseCount() < streetMinHouseMap.get(currentColor)){
                    streetMinHouseMap.replace(currentColor, prop.getHouseCount());
                }
                if(prop.getHouseCount() > streetMaxHouseMap.get(currentColor)){
                    streetMaxHouseMap.replace(currentColor, prop.getHouseCount());
                }
            }else {
                streetMinHouseMap.put(currentColor, prop.getHouseCount());
                streetMaxHouseMap.put(currentColor, prop.getHouseCount());
            }
        }
        ownedProperties.removeIf(prop -> prop.getHouseCount() > streetMinHouseMap.get(prop.getColor()));
        ownedHouses.removeIf(prop -> prop.getHouseCount() < streetMaxHouseMap.get(prop.getColor()));
        return new ArrayList[]{ownedProperties, ownedHouses};
    }

    public int getPlayerAmount(){
        return players.size();
    }
    private void buyHousePrompt(Player currentPlayer, ArrayList<Property> houseableProps){
        ArrayList<String> options = new ArrayList<>();
        for(Property prop : houseableProps){
            if(prop.getHouseCount() < 5){
                options.add(prop.getName() + " - " + prop.getHousePrice() + ",-");
            }
        }
        options.add(Language.getString("cancelBuyHouseText"));
        String action = mgui.requestUserDropDown(Language.getString("buyHouseMessage"), options.toArray((new String[0])));
        if(!action.equals(Language.getString("cancelBuyHouseText"))){
            Property property;
            for(Property prop : houseableProps){
                if((prop.getName() + " - " + prop.getHousePrice() + ",-").equals(action)){
                    property = prop;
                    buildHouse(currentPlayer, property, prop.getHouseCount()+1);
                    break;
                }
            }
        }
    }

    public ArrayList<BuyableField> getOwnedBuyableFields(Player currentPlayer) {
        ArrayList<BuyableField> ownedBuyableFields = new ArrayList<>();
        for(Field field : fields){
            if(field instanceof BuyableField bf){
                if(bf.getOwner() == currentPlayer){
                    ownedBuyableFields.add(bf);
                }
            }
        }
        return ownedBuyableFields;
    }

    public ArrayList<Property> getOwnedProperties(Player currentPlayer) {
        ArrayList<Property> ownedProperties = new ArrayList<>();
        for(Field field : fields){
            if(field instanceof Property prop){
                if(prop.getOwner() == currentPlayer){
                    ownedProperties.add(prop);
                }
            }
        }
        return ownedProperties;
    }

    /** If the player is bankrupt it removes their car from the board
     * and makes it so they don't own any properties
     * @param currentPlayer is the player whose turn it is
     */
    private void checkPlayerBankrupt(Player currentPlayer) {
        if (currentPlayer.getBankrupt()) {
            mgui.showMessage(currentPlayer.getName() + " er g??et bankerot. Du er nu ude af spillet. ");
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
     * @param loaded determines whether the dice should be predetermined or random.
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
        mgui.showMessage(currentPlayer.getName() + " "+ Language.getString("throwDice"));
        boolean extra = true;
        /*This code is used to stay on the same players turn in case they land a dice roll of 2 of a kind.
         *It will stay in the while as long as 2 of a kind is rolled and stop if 2 of a kind is not rolled.
         *In the event of getting 2 of a kind 3 times in a row, the while is broken,
         *and the player is sent directly to jail.*/
        while (extra) {
            int[] diceThrow = die.throwDice();

            mgui.drawDice(diceThrow[0], diceThrow[1]);

            if (currentPlayer.getJailed()) {
                break;
            } else if (diceThrow[0] == diceThrow[1]) {
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
            if (currentPlayer.getBankrupt()){
                break;
            }
            else if (currentPlayer.getJailed()) {
                mgui.showMessage(Language.getString("gotojailprompt"));
            } else if (diceThrow[0] == diceThrow[1]) {
                mgui.showMessage(Language.getString("ekstra"));
                mgui.showMessage(currentPlayer.getName() + " "+ Language.getString("throwDice"));
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
                mgui.showMessage(Language.getString("tax"));
                addBalanceToPlayer(player, -tax.getTax());
            } else if (currentField instanceof Brewery brew){
                if (brew.getOwner()==null){
                    buyField(player,brew);
                } else {
                    payBrewRent(player,brew, diceSum);
                }
            } else if (currentField instanceof Chance){
            ccController.DoChanceCard(player,this);
        }
    }
    /** Used to give money to a player who passes start
     * @param player is the player who get the money
     */
    //Added 4000 kr to the player's money balance
    public void giveStartMoney (Player player){
        int PASS_START_REWARD = 4000;
        addBalanceToPlayer(player, PASS_START_REWARD);
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
            if (player.getPlayerBalance()< 1000){
                mgui.showMessage(Language.getString("injailcantpay"));
                inJail(player);
            } else {
                addBalanceToPlayer(player, -1000);
                player.setJailed(false);
                mgui.updatePlayerBalance(player);
                mgui.showMessage(Language.getString("betalt"));
                throwAndMove(player);
            }
        } else {
            mgui.showMessage(Language.getString("3kast"));
            for(int i=0; i<3; i++){
                int[] dieValue = die.throwDice();
                mgui.drawDice(dieValue[0], dieValue[1]);

                if (dieValue[0] == dieValue[1]) {
                    player.setJailed(false);
                    mgui.showMessage(Language.getString("2ens"));
                    movePlayer(player, dieValue[0] + dieValue[1]);
                    mgui.drawPlayerPosition(player);
                    landOnField(player, dieValue[0] + dieValue[1]);
                    extraCounter++;
                    throwAndMove(player);
                    break;
                } else {
                    player.increaseTurnsInJail();
                    mgui.showMessage(Language.getString("ikke2ens"));
                    if (player.getTurnsInJail() > 8) {
                        if (player.getPlayerBalance()< 1000) {
                            mgui.showMessage(Language.getString("3tureBankrupt"));
                            addBalanceToPlayer(player, -1000);
                            checkPlayerBankrupt(player);
                            if (!player.getBankrupt()) {
                                player.setJailed(false);
                                movePlayer(player, dieValue[0] + dieValue[1]);
                                mgui.showMessage(Language.getString("noLongBankrupt"));
                                mgui.drawPlayerPosition(player);
                                landOnField(player, dieValue[0] + dieValue[1]);
                                break;
                            }
                        } else {
                            player.setJailed(false);
                            movePlayer(player, dieValue[0] + dieValue[1]);
                            mgui.showMessage(Language.getString("3ture"));
                            addBalanceToPlayer(player, -1000);
                            mgui.drawPlayerPosition(player);
                            landOnField(player, dieValue[0] + dieValue[1]);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void addBalanceToPlayer(Player player, int amount){

        player.addBalance(amount);
        mgui.updatePlayerBalance(player);
        if (player.getPlayerBalance()< 0 && getOwnedBuyableFields(player).isEmpty()){
            player.setBankrupt(true);
        } else if (!getOwnedBuyableFields(player).isEmpty() && player.getPlayerBalance()<0 ) {
            playerBankruptcySaveChance(player);
        }
    }

    private void playerBankruptcySaveChance(Player player) {
        if(bankruptcySavingState){
            return;
        }
        bankruptcySavingState = true;
        String result;
        boolean con = true;
        while (con) {
            result = mgui.requestUserButton(Language.getString("bankerot"), Language.getString("mort"), Language.getString("sellHouse"), Language.getString("con"));
            if (result.equals(Language.getString("mort"))) {
                mortgage(player);
            } else if (result.equals(Language.getString("sellHouse"))) {
                sellHousePrompt(player, getHouseMethod(getOwnedProperties(player))[1]);
            } else if(result.equals(Language.getString("con"))){
                ArrayList<BuyableField> ownedFields = getOwnedBuyableFields(player);
                ownedFields.removeIf(BuyableField::getMortgaged);
                if(!(ownedFields.size() > 0 ) && player.getPlayerBalance() < 0){
                    con = false;
                    player.setBankrupt(true);

                } else if (player.getPlayerBalance()>=0) {
                    con = false;
                } else {
                    mgui.showMessage(Language.getString("stillBankerot"));
                }

            }

        }
        bankruptcySavingState = false;
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
            if (currentField instanceof Property){
                if (TEST_MODE){
                    results = Language.getString("yesTxt");
                } else {
                    results = mgui.requestUserButton(Language.getString("prop"), Language.getString("yesTxt"), Language.getString("noTxt"));
                }
            }
            if (currentField instanceof ShippingCompany ShippingCompanyName){
                if (TEST_MODE){
                    results = Language.getString("yesTxt");
                } else {
                    results = mgui.requestUserButton(Language.getString("ship")+" "+  ShippingCompanyName.getTitle() + Language.getString("ship2"), Language.getString("yesTxt"), Language.getString("noTxt"));
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
                addBalanceToPlayer(currentPlayer, -currentField.getPrice());
                currentField.setOwner(currentPlayer);
                mgui.setOwner(currentField, currentPlayer.getPlayerPosition());
            } else if (Objects.equals(results,Language.getString("noTxt"))) {
                auction(currentField,currentPlayer);
            }
        }else {
            mgui.showMessage(Language.getString("propikker??d"));
        }
    }

    /** Used to give a player money by their id. Used in the dev console
     * @param id is the players id, player 1's id is 0.
     */
    public void addPlayerBalanceByID(int id, int amount) {
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
        if (currentField.getOwner() != currentPlayer && !currentField.getMortgaged() && !currentField.getOwner().getJailed()) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            if (currentField instanceof Property property) {
                if (allinColorOwned(property) && property.getHouseCount()==0) {
                    if (currentField.getRent(0) < currentPlayer.getPlayerBalance() || !getOwnedBuyableFields(currentPlayer).isEmpty()) {
                        addBalanceToPlayer(currentPlayer,2 * -currentField.getRent(0) );
                        addBalanceToPlayer(currentField.getOwner(),2 * currentField.getRent(0));
                    } else {
                        addBalanceToPlayer(currentField.getOwner(),currentPlayer.getPlayerBalance() + 1);
                        addBalanceToPlayer(currentPlayer,-currentPlayer.getPlayerBalance() - 1);
                    }
                } else if (currentField.getRent(property.getHouseCount()) < currentPlayer.getPlayerBalance() || !getOwnedBuyableFields(currentPlayer).isEmpty()) {
                    addBalanceToPlayer(currentField.getOwner(),currentField.getRent(property.getHouseCount()));
                    addBalanceToPlayer(currentPlayer,-currentField.getRent(property.getHouseCount()));
                } else  {
                    addBalanceToPlayer(currentField.getOwner(),currentPlayer.getPlayerBalance() + 1);
                    addBalanceToPlayer(currentPlayer,-currentPlayer.getPlayerBalance() - 1);
                }
            }
        }
        else {
            if (currentField.getOwner().getJailed()){
                mgui.showMessage(Language.getString("ownerJailed"));
            } else if (currentField.getMortgaged()) {
                mgui.showMessage(Language.getString("mortgaged"));
            }else {
            mgui.showMessage(Language.getString("selfown"));
            }
        }
    }
    /** Used to pay rent when a player lands on a ferry field
     * @param currentPlayer is the player who lands on the field
     * @param currentField is the field the player lands on.
     */
    public void payShipRent(Player currentPlayer, BuyableField currentField){
        if (currentField.getOwner() != currentPlayer && !currentField.getMortgaged() && !currentField.getOwner().getJailed()) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            if (currentField.getRent(currentField.getOwner().getShipsOwned() - 1) < currentPlayer.getPlayerBalance() || !getOwnedBuyableFields(currentPlayer).isEmpty()) {
                addBalanceToPlayer(currentField.getOwner(),currentField.getRent(currentField.getOwner().getShipsOwned() - 1));
                addBalanceToPlayer(currentPlayer,-currentField.getRent(currentField.getOwner().getShipsOwned() - 1));
                mgui.updatePlayerBalance(currentField.getOwner());
            } else {
                addBalanceToPlayer(currentField.getOwner(),currentPlayer.getPlayerBalance() + 1);
                addBalanceToPlayer(currentPlayer,-currentPlayer.getPlayerBalance() - 1);
            }
        } else {
            if (currentField.getOwner().getJailed()){
                mgui.showMessage(Language.getString("ownerJailed"));
            } else if (currentField.getMortgaged()) {
                mgui.showMessage(Language.getString("mortgaged"));
            }else {
                mgui.showMessage(Language.getString("selfown"));
            }
        }
    }
    /** Used to pay rent when a player lands on a brewery field
     * @param currentPlayer is the player who lands on the field
     * @param currentField is the field the player lands on.
     * @param diceSum is the sum of the dice.
     */
    public void payBrewRent(Player currentPlayer, BuyableField currentField, int diceSum){
        if (currentField.getOwner() != currentPlayer && !currentField.getMortgaged() && !currentField.getOwner().getJailed()) {
            mgui.showMessage(Language.getString("payrent") + " " + currentField.getOwner());
            int toPay = currentField.getRent(currentField.getOwner().getBrewsOwned() - 1) * diceSum;
            if (toPay < currentPlayer.getPlayerBalance() || !getOwnedBuyableFields(currentPlayer).isEmpty()) {
                addBalanceToPlayer(currentField.getOwner(),toPay);
                addBalanceToPlayer(currentPlayer,-toPay);
            } else {
                addBalanceToPlayer(currentField.getOwner(),currentPlayer.getPlayerBalance() + 1);
                addBalanceToPlayer(currentPlayer,-currentPlayer.getPlayerBalance() - 1);
            }
        } else{
        if (currentField.getOwner().getJailed()){
            mgui.showMessage(Language.getString("ownerJailed"));
        } else if (currentField.getMortgaged()) {
            mgui.showMessage(Language.getString("mortgaged"));
        }else {
            mgui.showMessage(Language.getString("selfown"));
        }
        }
    }
    /** Used to remove the owner of buyable fields if they're owned by a bankrupt player.
     * @param bankruptPlayer is a player who is bankrupt
     */
    public void removeOwner(Player bankruptPlayer){
        Field[] field = fields;
        for (int i=0; i<field.length;i++){
            if (field[i] instanceof BuyableField buyable){
                if (buyable.getOwner()==bankruptPlayer){
                    buyable.setOwner(null);
                    buyable.setMortgaged(false);
                    mgui.resetOwner(buyable,i);
                    if(field[i]  instanceof Property prop){
                        prop.setHouses(0);
                        mgui.buildHouse(prop,0);
                        mgui.buildHotel(prop, false);
                    }

                }
            }
        }
    }

    public void buildHouse(Player builder, Property field, int houses){
        if (field.getHouseCount()<4) {
            mgui.buildHouse(field, houses);
            addBalanceToPlayer(builder,-field.getHousePrice());
        }
        else {
            mgui.buildHotel(field,true);
            addBalanceToPlayer(builder,-field.getHousePrice());
        }
    }

    /** Used to show whose turn it is on the UI
     *@return String
     */
    public String getTurnMessage (){
        if(players.size() > 0 && gameStarted && !auctionMode){
            return "["+players.get(currentPlayerID).getName() +Language.getString("turn")+" ";
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

    public void auction(BuyableField currentField, Player currentPlayer){
        auctionMode=true;
        int bid=0;
        boolean bidOnGoing=true;
        String result;
        Player highestbidder=null;
        while (bidOnGoing){
        int increment=0;
        for (int i =currentPlayerID+1; i<currentPlayerID+1+players.size();i++) {
            int index= i;
            if (i>=players.size()){
                index-=players.size();
            }
            Player player=players.get(index);
            if (player.getPlayerBalance()>bid) {
                result = mgui.requestUserButton(player.getName() + Language.getString("aucTur") + " " + Language.getString("bid") + " " + bid, Language.getString("yesTxt"), Language.getString("noTxt"));
                if (result.equals(Language.getString("yesTxt"))) {
                    bid = mgui.requestInteger(Language.getString("yourbid"), bid + 1, player.getPlayerBalance());
                    highestbidder = player;
                }else {
                    increment++;
                }
            } else if (player.getPlayerBalance()<=bid){
                mgui.showMessage(player.getName() + Language.getString("aucTur") + " " +Language.getString("ikkeNok"));
                increment++;
            }

        }
        if (increment==players.size()){
            bidOnGoing=false;

        }
        }
        if (highestbidder!=null) {
            currentField.setOwner(highestbidder);
            mgui.setOwner(currentField, currentPlayer.getPlayerPosition());
            if (currentField instanceof Brewery){
                highestbidder.setBrewsOwned(currentPlayer.getBrewsOwned()+1);
            }
            if (currentField instanceof ShippingCompany){
                highestbidder.setShipsOwned(currentPlayer.getShipsOwned()+1);
            }
            addBalanceToPlayer(highestbidder,-bid);
            mgui.showMessage(highestbidder.getName() + Language.getString("auctionWon"));

        }
        auctionMode = false;
    }

    public void mortgage(Player currentplayer){
        int mortgage;
        ArrayList<String> ownedfields = new ArrayList<>();
        for (BuyableField buyableField : getOwnedBuyableFields(currentplayer)){
            if (!buyableField.getMortgaged()) {
                mortgage = buyableField.getPrice() / 2;
                ownedfields.add(buyableField.getName()+" "+mortgage+",-");
            }
        }
        ownedfields.add(Language.getString("cancelMortgage"));
        String result= mgui.requestUserDropDown(Language.getString("mortgage"),ownedfields.toArray(new String[0]));
        for (BuyableField buyableField:getOwnedBuyableFields(currentplayer)){
            mortgage = buyableField.getPrice() / 2;
            if ((buyableField.getName()+" "+ mortgage+",-").equals(result)){
                mortgage = buyableField.getPrice() / 2;
                addBalanceToPlayer(currentplayer,mortgage);
                buyableField.setMortgaged(true);
            }
        }


    }

    public void payMortgage(Player currentplayer){
        int mortgage;
        ArrayList<String> mortgagedFields = new ArrayList<>();
        for (BuyableField buyableField: getOwnedBuyableFields(currentplayer)){
            if (buyableField.getMortgaged()) {
                mortgage = (int) ((buyableField.getPrice()/2)+(Math.round((buyableField.getPrice()*0.1)/100))*100);
                mortgagedFields.add(buyableField.getName()+" "+mortgage+",-");
            }
        }
        mortgagedFields.add(Language.getString("cancelMortgage"));
        String result= mgui.requestUserDropDown(Language.getString("payMortgage"),mortgagedFields.toArray(new String[0]));
        for (BuyableField buyableField:getOwnedBuyableFields(currentplayer)){
            mortgage = (int) ((buyableField.getPrice()/2)+(Math.round((buyableField.getPrice()*0.1)/100))*100);
            if ((buyableField.getName()+" "+ mortgage+",-").equals(result)){
                mortgage = (int) ((buyableField.getPrice()/2)+(Math.round((buyableField.getPrice()*0.1)/100))*100);
                addBalanceToPlayer(currentplayer,-mortgage);
                buyableField.setMortgaged(false);
            }
        }
    }
    public void sellHousePrompt(Player currentplayer, ArrayList<Property> sellhouseProps){
        int mortgage=0;
        ArrayList<String> housedFields = new ArrayList<>();
        for(Property property : sellhouseProps){
                if (property.getHouseCount()>0 && property.getHouseCount()<=4){
                 mortgage=property.getHousePrice()/2;
                    housedFields.add(property.getName()+ " "+mortgage+",-" );
                } else if (property.getHouseCount()==5) {
                    mortgage=property.getHousePrice()*5/2;
                    housedFields.add(property.getName()+" "+ mortgage+",-");
                }
        }
        housedFields.add(Language.getString("cancelSellHouse"));
        String result= mgui.requestUserDropDown(Language.getString("sellHouse"),housedFields.toArray(new String[0]));
        for(Property property : sellhouseProps){
            if ((property.getName()+" "+ mortgage+",-").equals(result)){
                if (property.getHouseCount()<5) {
                    mgui.buildHouse(property, property.getHouseCount() - 1);
                    addBalanceToPlayer(currentplayer,property.getHousePrice()/2);
                } else if (property.getHouseCount()==5) {
                    mgui.buildHotel(property,false);
                    addBalanceToPlayer(currentplayer,property.getHousePrice()*5/2);
                }
            }
        }
    }
    public Field[] getFields() {
        return fields;
    }
}
