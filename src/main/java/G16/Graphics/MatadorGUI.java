package G16.Graphics;
import G16.Controllers.GameController;
import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.Field;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Language;
import G16.PlayerUtils.Player;
import G16.mguiPatched.gui_fields.GUI_Ownable;
import G16.mguiPatched.gui_fields.GUI_Tax;
import G16.mguiPatched.gui_main.GUI;

import java.awt.*;
import java.util.ArrayList;

import static G16.mguiPatched.gui_fields.GUI_Car.Pattern.*;
import static G16.mguiPatched.gui_fields.GUI_Car.Type.*;
/** Represents the GUI of the game
 * @author G16
 * @version 0.1
 */
public class MatadorGUI {

    protected final GUI gui; //Instance of the diplomitdtu.matadorGUI
    protected final ArrayList<G16.mguiPatched.gui_fields.GUI_Player> guiPlayers = new ArrayList<>();

    protected GameController gc;

    public MatadorGUI(GameController gc, Field[] fields){
        this.gc = gc;

        G16.mguiPatched.gui_fields.GUI_Field[] defaultFields = getFields();
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


    /** Add a player to the GUI
     * @param name Display name of the new player
     * @param balance Displayed balance of the new player
     * @param playerNum Index of the player. Used to determine the displayed car of the player.
     */
    public void addPlayer(String name, int balance,int playerNum){

        switch (playerNum) {
            case 0 -> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.RED, Color.LIGHT_GRAY, CAR, HORIZONTAL_GRADIANT));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
            case 1-> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.YELLOW, Color.BLUE, TRACTOR, DIAGONAL_DUAL_COLOR));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 2-> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.RED, Color.YELLOW, RACECAR, HORIZONTAL_LINE));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 3-> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.LIGHT_GRAY, Color.LIGHT_GRAY, UFO, HORIZONTAL_DUAL_COLOR));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
            case 4-> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.BLACK, Color.WHITE, CAR, CHECKERED));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }case 5-> {
                G16.mguiPatched.gui_fields.GUI_Player guiPlayer = new G16.mguiPatched.gui_fields.GUI_Player(name,balance,new G16.mguiPatched.gui_fields.GUI_Car(Color.PINK, Color.MAGENTA, TRACTOR, HORIZONTAL_GRADIANT));
                gui.addPlayer(guiPlayer);
                guiPlayers.add(guiPlayer);
            }
        }
    }

    /** Remove a players car from the GUI
     * @param player Player object of the player.
     */
    public void removeCar(Player player){
        G16.mguiPatched.gui_fields.GUI_Player bankruptplayer = guiPlayers.get(player.getID());
        if(bankruptplayer.getCar().getPosition() != null){
            bankruptplayer.getCar().getPosition().drawCar(bankruptplayer, false);
        }
    }

    public void displayChanceCards(String caseNum){
        String mesg = "case";
        mesg = mesg + caseNum;
        gui.displayChanceCard(Language.getString(mesg));
    }

    /** Draw/update a players position.
     * @param player player object of the player who should be updated.
     */
    public void drawPlayerPosition(Player player){
        G16.mguiPatched.gui_fields.GUI_Player selectedPlayer = guiPlayers.get(player.getID());
        int fieldCount = gui.getFields().length;
        int currentPosition = player.getPreviousPlayerPosition();

            while (currentPosition != player.getPlayerPosition()){
                if(currentPosition >= fieldCount){
                    currentPosition -= fieldCount;
                }
                currentPosition++;
                selectedPlayer.getCar().setPosition(gui.getFields()[currentPosition]);
                try {
                    Thread.sleep(110);
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
        G16.mguiPatched.gui_fields.GUI_Field[] defaultFields = gui.getFields();
        if (field instanceof BuyableField prop){
            defaultFields[pos].setSubText(prop.getOwner().getName());
        }
    }

    /** Update a field to display that it is NOT owned
     * @param field The field that should become available for purchase
     * @param pos The position of the field.
     */
    public void resetOwner(Field field , int pos){
        G16.mguiPatched.gui_fields.GUI_Field[] defaultFields = gui.getFields();
        if (field instanceof BuyableField prop){
            defaultFields[pos].setSubText(prop.getPrice()+",-");
        }
    }

    public void buildHouse(Property field, int houses){
        field.setHouses(houses);
        G16.mguiPatched.gui_fields.GUI_Field plot = gui.getFields()[field.getID()];
        G16.mguiPatched.gui_fields.GUI_Street street = (G16.mguiPatched.gui_fields.GUI_Street) plot;
        street.setHouses(houses);
    }

    public void buildHotel(Property field){
        field.setHouses(5);
        G16.mguiPatched.gui_fields.GUI_Field plot = gui.getFields()[field.getID()];
        G16.mguiPatched.gui_fields.GUI_Street street = (G16.mguiPatched.gui_fields.GUI_Street) plot;
        street.setHotel(true);
    }

    private String getTurnInfo(){
        return gc.getTurnMessage();
    }

    public void displayChanceCard(String text){
        gui.displayChanceCard(text);
    }

    /** Manually instantiated GUI_Field array. Copied from diplomitdtu.matadorgui
     * @return GUI_Field[] array of default fields.
     */
    public G16.mguiPatched.gui_fields.GUI_Field[] getFields() {
        G16.mguiPatched.gui_fields.GUI_Field[] fields = new G16.mguiPatched.gui_fields.GUI_Field[40];
        int i = 0;
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Start("Start", "Modtag: 4k", "Modtag kr. 200,-\nnår de passerer start", Color.RED, Color.BLACK);
        (GUI_Ownable)fields[i]
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Rødovrevej", "1200,-", "Rødovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Hvidovrevej", "Pris:  60", "Hvidovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Tax("Betal\nindkomst-\nskat", "Betal 4000,-", "Betal indkomstskat 4000,-", Color.GRAY, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Shipping("default", "Forsea", "Pris:  200", "Øresundsredderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Roskildevej", "Pris:  100", "Roskildevej", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Valby Langgade", "Pris:  100", "Valby Langgade", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Allégade", "Pris:  120", "Allégade", "Leje:  45", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Jail("default", "Fængsel", "Fængsel", "På besøg i fængslet", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Frederiksberg Allé", "Pris:  140", "Frederiksberg Allé", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Brewery("src/main/resources/sqaus.png", "Sqaush", "Pris:  150", "Tuborg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Bülowsvej", "Pris:  140", "Bülowsvej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Gammel Kongevej", "Pris:  140", "Gammel Kongevej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Shipping("default", "Molslinjen", "Pris:  200", "D.F.D.S.", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Bernstorffsvej", "Pris:  180", "Bernstorffsvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Hellerupvej", "Pris:  180", "Hellerupvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Strandvejen", "Pris:  180", "Strandvejen", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Refuge("default", "Helle", "Helle", "Ta' en pause", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Trianglen", "Pris:  220", "Trianglen", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Østerbrogade", "Pris:  220", "Østerbrogade", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Grønningen", "Pris:  240", "Grønningen", "Leje:  80", Color.RED, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Shipping("default", "Scandlines", "Pris:  200", "Ø.S. redderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Bredgade", "Pris:  260", "Bredgade", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Kgs. Nytorv", "Scandlines", "Kongens Nytorv", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Brewery("src/main/resources/coca.png", "Coca-Cola", "Pris:  150", "Carlsberg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Østergade", "Pris:  280", "Østergade", "Leje:  85", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Jail("default", "Gå i fængsel", "Gå i fængsel", "De fængsles\nSlå to ens for at komme ud", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Amagertorv", "Pris:  300", "Amagertorv", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Vimmelskaftet", "Pris:  300", "Vimmelskaftet", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Nygade", "Pris:  320", "Nygade", "Leje:  100", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Shipping("default", "D.F.D.S", "Pris:  200", "Bornholms redderi", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new G16.mguiPatched.gui_fields.GUI_Street("Frederiksberggade", "Pris:  350", "Frederiksberggade", "Leje:  120", new Color(150, 60, 150), Color.WHITE);
        fields[i++] = new GUI_Tax("Ekstra-\nordinær\nstatsskat", "Betal 2000,-", "Betal ekstraordinær\nstatsskat: 2000,-", Color.GRAY, Color.BLACK);
        fields[i] = new G16.mguiPatched.gui_fields.GUI_Street("Rådhuspladsen", "Pris:  400", "Rådhuspladsen", "Leje:  150", new Color(150, 60, 150), Color.WHITE);
        return fields;
    }
}
