package G16.Graphics;
import G16.Controllers.GameController;
import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.Field;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.UnbuyableFields.*;
import G16.Language;
import G16.PlayerUtils.Player;
import gui_fields.*;
import gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

import static gui_fields.GUI_Car.Pattern.*;
import static gui_fields.GUI_Car.Type.*;
/** Represents the GUI of the game
 * @author G16
 * @version 0.1
 */
public class MatadorGUI {

    protected GUI gui; //Instance of the diplomitdtu.matadorGUI
    protected final ArrayList<GUI_Player> guiPlayers = new ArrayList<>();

    protected GameController gc;

    public MatadorGUI(GameController gc, Field[] fields){
        this.gc = gc;
        GUI_Field[] defaultFields = getFields();
        for (int i = 0; i<defaultFields.length; i++){
            defaultFields[0].setSubText(Language.getString("startsub"));
            defaultFields[0].setDescription(Language.getString("startdesc"));
            if (fields[i] instanceof Property prop) {
                defaultFields[i].setSubText(prop.getPrice()+",-");
                defaultFields[i].setBackGroundColor(prop.getColor());
                defaultFields[i].setTitle(prop.getName());
                defaultFields[i].setDescription(String.valueOf(prop));

            }
            if (fields[i] instanceof ShippingCompany ship){
                defaultFields[i].setSubText(ship.getPrice()+",-");
                defaultFields[i].setDescription(String.valueOf(ship));
            }
            if (fields[i] instanceof Brewery brew){
                defaultFields[i].setSubText(brew.getPrice()+",-");
                defaultFields[i].setDescription(String.valueOf(brew));
            }
        }
        this.gui = new GUI(defaultFields, new Color(54, 236, 189));
    }

    public void updateGUI(Field[] fields){
        GUI_Field[] defaultFields = gui.getFields();
        for (int i = 0; i<defaultFields.length; i++){
            defaultFields[0].setTitle(Language.getString("start"));
            defaultFields[0].setSubText(Language.getString("startsub"));
            defaultFields[0].setDescription(Language.getString("startdesc"));
            if (fields[i] instanceof Property prop) {
                defaultFields[i].setTitle(prop.getName());
                defaultFields[i].setDescription(String.valueOf(prop));
            }
            if (fields[i] instanceof ShippingCompany ship){
                defaultFields[i].setDescription(String.valueOf(ship));
            }
            if (fields[i] instanceof Brewery brew){
                defaultFields[i].setDescription(brew.toString());
            }
            if (fields[i] instanceof Chance){
                defaultFields[i].setSubText(Language.getString("tryluck"));
                defaultFields[i].setDescription(Language.getString("takeChance"));
            }
            if (fields[i] instanceof  Tax tax) {
                if (Objects.equals(tax.getName(), "Indkomstskat")){
                    defaultFields[i].setTitle(Language.getString("tax1"));
                    defaultFields[i].setSubText(Language.getString("taxPrice1"));
                    defaultFields[i].setDescription(Language.getString("taxdesc1"));
                } else {
                    defaultFields[i].setTitle(Language.getString("tax2"));
                    defaultFields[i].setSubText(Language.getString("taxPrice2"));
                    defaultFields[i].setDescription(Language.getString("taxdesc2"));
                }
            }
            if (fields[i] instanceof Jail){
                defaultFields[i].setSubText(Language.getString("jail"));
                defaultFields[i].setDescription(Language.getString("visitJail"));
            }
            if (fields[i] instanceof VisitorField){
                defaultFields[i].setTitle(Language.getString("parking"));
                defaultFields[i].setSubText(Language.getString("parking"));
                defaultFields[i].setDescription(Language.getString("parkingDesc"));
            }
            if (fields[i] instanceof GoToJail){
                defaultFields[i].setSubText(Language.getString("gotojail"));
                defaultFields[i].setDescription(Language.getString("gotojailDesc"));
            }
        }
    }


    /** Add a player to the GUI
     * @param name Display name of the new player
     * @param balance Displayed balance of the new player
     * @param playerNum Index of the player. Used to determine the displayed car of the player.
     */
    public void addPlayer(String name, int balance,int playerNum){

        switch (playerNum) {
            case 0 -> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.BLUE, Color.WHITE, CAR, HORIZONTAL_GRADIANT));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
            case 1-> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.YELLOW, Color.BLUE, TRACTOR, DIAGONAL_DUAL_COLOR));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 2-> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.RED, Color.YELLOW, RACECAR, HORIZONTAL_LINE));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 3-> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.LIGHT_GRAY, Color.LIGHT_GRAY, UFO, HORIZONTAL_DUAL_COLOR));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
            case 4-> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.BLACK, Color.WHITE, CAR, CHECKERED));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 5-> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.PINK, Color.MAGENTA, TRACTOR, HORIZONTAL_GRADIANT));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
        }
    }

    /** Remove a players car from the GUI
     * @param player Player object of the player.
     */
    public void removeCar(Player player){
        GUI_Player bankruptplayer = guiPlayers.get(player.getID());
        if(bankruptplayer.getCar().getPosition() != null){
            bankruptplayer.getCar().getPosition().drawCar(bankruptplayer, false);
        }
    }

    public void displayChanceCards(String caseNum){
        String mesg = "case";
        mesg = mesg + caseNum;
        gui.displayChanceCard(Language.getString(mesg));
    }

    /** Draw/update a players position with an animation.
     * @param player player object of the player who should be updated.
     */
    public void drawPlayerPosition(Player player){
        GUI_Player selectedPlayer = guiPlayers.get(player.getID());
        int fieldCount = gui.getFields().length;
        int currentPosition = player.getPreviousPlayerPosition();

        int capTime = 1500; //Max animation time
        int deltaTime = 100; //Default time per move
        int deltaPosition; //Amount ot of moves

        if(currentPosition > player.getPlayerPosition()){
            deltaPosition = 40-currentPosition+ player.getPlayerPosition();
        } else {
            deltaPosition = player.getPlayerPosition() - currentPosition;
        }

        if(deltaPosition > 0 && (deltaPosition *deltaTime) > capTime){
            deltaTime = capTime/deltaPosition;
        }



        while (currentPosition != player.getPlayerPosition()){
                currentPosition++;
                if((currentPosition >= fieldCount)){
                    currentPosition -= fieldCount;
                }

                selectedPlayer.getCar().setPosition(gui.getFields()[currentPosition]);
                try {
                    Thread.sleep(deltaTime);
                } catch (InterruptedException e){
                    System.out.println("Animation interrupted");
                }

            }

        player.setPlayerPosition(player.getPlayerPosition());
        selectedPlayer.getCar().setPosition(gui.getFields()[player.getPlayerPosition()]);
    }

    /** Draw the dice on the board with the given facevalues. If the values are not in the range 1-6, they
     * will not be updated.
     * @param faceValue1 face value of the first die
     * @param faceValue2 face value of the second die
     */
    public void drawDice(int faceValue1, int faceValue2){
        gui.setDice(faceValue1, faceValue2);
    }

    /** Display a message on the board. The message must be dismissed by clicking the displayed "OK" button.
     * will not be updated.
     * @param message The displayed string.
     */
    public void showMessage(String message){
        gui.showMessage(getTurnInfo() + message);
    }

    /** Request a string input from the user.
     * @param message The displayed message.
     * @return String the written input from the user.
     */
    public String requestString(String message){
        return gui.getUserString(getTurnInfo() + message);

    }

    /** Request the user to choose between different buttons.
     * @param msg The displayed message.
     * @param options The name of the different options
     * @return String the name of the picked option.
     */
    public String requestUserButton(String msg,String...options){
        return gui.getUserButtonPressed(getTurnInfo() + msg,options);
    }

    public String requestUserDropDown(String msg,String...options){
        return gui.getUserSelection(getTurnInfo() + msg,options);
    }

    /** Request the user to write an integer
     * @param message The displayed message.
     * @param minValue The minimum value the user can select
     * @param maxValue The maximum value the user can select
     * @return int The written integer.
     */
    public int requestInteger(String message, int minValue, int maxValue) {

        return gui.getUserInteger(getTurnInfo() + message, minValue, maxValue);
    }

    /** Update a players displayed balance
     * @param player The player whos balance should be updated.
     */
    public void updatePlayerBalance(Player player){
        try{
            if(guiPlayers.size() > player.getID() && player.getID() >= 0){
                guiPlayers.get(player.getID()).setBalance(player.getPlayerBalance());
            }
        } catch (NullPointerException e){
            System.out.println("UI ERROR: " + e.getMessage());
        }



    }

    /** Update a field to display that it is owned by a player.
     * @param field The field that the player should now own
     * @param pos The position of the field.
     */
    public void setOwner(Field field,int pos){
        GUI_Field[] defaultFields = gui.getFields();
        GUI_Field position =  defaultFields[pos];
        if (field instanceof BuyableField prop){
           position.setSubText(prop.getOwner().getName());
            if(position instanceof  GUI_Ownable ownable){
                ownable.setBorder(guiPlayers.get(prop.getOwner().getID()).getPrimaryColor());
            }
        }
    }

    /** Update a field to display that it is NOT owned
     * @param field The field that should become available for purchase
     * @param pos The position of the field.
     */
    public void resetOwner(Field field , int pos){
        GUI_Field[] defaultFields = gui.getFields();
        GUI_Field position = defaultFields[pos];
        if (field instanceof BuyableField prop){
            position.setSubText(prop.getPrice()+",-");
            if(position instanceof  GUI_Ownable ownable){
                ownable.setBorder(null);
            }
        }

    }

    public void buildHouse(Property field, int houses){
        field.setHouses(houses);
        GUI_Field plot = gui.getFields()[field.getID()];
        GUI_Street street = (GUI_Street) plot;
        street.setHouses(houses);
    }

    public void buildHotel(Property field, Boolean build){
        if (build) {
            field.setHouses(5);
            GUI_Field plot = gui.getFields()[field.getID()];
            GUI_Street street = (GUI_Street) plot;
            street.setHotel(true);
        }else {
            field.setHouses(0);
            GUI_Field plot = gui.getFields()[field.getID()];
            GUI_Street street = (GUI_Street) plot;
            street.setHotel(false);
        }
    }


    private String getTurnInfo(){
        return gc.getTurnMessage();
    }

    /** Manually instantiated GUI_Field array. Copied from diplomitdtu.matadorgui
     * @return GUI_Field[] array of default fields.
     */
    public GUI_Field[] getFields() {
        GUI_Field[] fields = new GUI_Field[40];
        int i = 0;
        fields[i++] = new GUI_Start("Start", Language.getString("startsub"), "Modtag kr. 200,-\nn??r de passerer start", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Street("R??dovrevej", "1200,-", "R??dovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Hvidovrevej", "Pris:  60", "Hvidovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new GUI_Tax("Betal\nindkomst-\nskat", "Betal 4000,-", "Betal indkomstskat 4000,-", Color.GRAY, Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Forsea", "Pris:  200", "??resundsredderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Roskildevej", "Pris:  100", "Roskildevej", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Valby Langgade", "Pris:  100", "Valby Langgade", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Street("All??gade", "Pris:  120", "All??gade", "Leje:  45", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Jail("default", "F??ngsel", "F??ngsel", "P?? bes??g i f??ngslet", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new GUI_Street("Frederiksberg All??", "Pris:  140", "Frederiksberg All??", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Brewery("src/main/resources/sqaus.png", "Sqaush", "Pris:  150", "Tuborg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new GUI_Street("B??lowsvej", "Pris:  140", "B??lowsvej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Street("Gammel Kongevej", "Pris:  140", "Gammel Kongevej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Molslinjen", "Pris:  200", "D.F.D.S.", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Bernstorffsvej", "Pris:  180", "Bernstorffsvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Hellerupvej", "Pris:  180", "Hellerupvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Street("Strandvejen", "Pris:  180", "Strandvejen", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Refuge("default", "Helle", "Helle", "Ta' en pause", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Trianglen", "Pris:  220", "Trianglen", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("??sterbrogade", "Pris:  220", "??sterbrogade", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Street("Gr??nningen", "Pris:  240", "Gr??nningen", "Leje:  80", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Scandlines", "Pris:  200", "??.S. redderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Bredgade", "Pris:  260", "Bredgade", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Kgs. Nytorv", "Scandlines", "Kongens Nytorv", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Brewery("src/main/resources/coca.png", "Coca-Cola", "Pris:  150", "Carlsberg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new GUI_Street("??stergade", "Pris:  280", "??stergade", "Leje:  85", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Jail("default", "G?? i f??ngsel", "G?? i f??ngsel", "De f??ngsles\nSl?? to ens for at komme ud", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new GUI_Street("Amagertorv", "Pris:  300", "Amagertorv", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Street("Vimmelskaftet", "Pris:  300", "Vimmelskaftet", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Nygade", "Pris:  320", "Nygade", "Leje:  100", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "D.F.D.S", "Pris:  200", "Bornholms redderi", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Chance("?", Language.getString("tryluck"), "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Frederiksberggade", "Pris:  350", "Frederiksberggade", "Leje:  120", new Color(150, 60, 150), Color.WHITE);
        fields[i++] = new GUI_Tax("Ekstra-\nordin??r\nstatsskat", "Betal 2000,-", "Betal ekstraordin??r\nstatsskat: 2000,-", Color.GRAY, Color.BLACK);
        fields[i] = new GUI_Street("R??dhuspladsen", "Pris:  400", "R??dhuspladsen", "Leje:  150", new Color(150, 60, 150), Color.WHITE);
        return fields;
    }
}
