package G16.Graphics;
import G16.Controllers.GameController;
import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.Field;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.UnbuyableFields.VisitorField;
import G16.Language;
import G16.PlayerUtils.Player;
import gui_fields.*;
import gui_main.GUI;
import java.awt.Window;

import java.awt.*;
import java.util.ArrayList;

import static gui_fields.GUI_Car.Pattern.*;
import static gui_fields.GUI_Car.Type.*;

public class MatadorGUI {

    protected final GUI gui;
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
        this.gui = new GUI(defaultFields);
    }

    public void addPlayer(String name, int balance,int playerNum){
        switch (playerNum) {
            case 0 -> {
                GUI_Player guiPlayer = new GUI_Player(name,balance,new GUI_Car(Color.RED, Color.LIGHT_GRAY, CAR, HORIZONTAL_GRADIANT));
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

    public void removecar(Player player){
        GUI_Player bankruptplayer = guiPlayers.get(player.getID());
        if(bankruptplayer.getCar().getPosition() != null){
            bankruptplayer.getCar().getPosition().drawCar(bankruptplayer, false);
        }
    }

    public void drawPlayerPosition(Player player){
        GUI_Player selectedPlayer = guiPlayers.get(player.getID());
        selectedPlayer.getCar().setPosition(gui.getFields()[player.getPlayerPosition()]);
    }
    public void drawDice(int faceValue1, int faceValue2){
        gui.setDice(faceValue1, faceValue2);
    }

    public void showMessage(String message){
        gui.showMessage(getTurnInfo() + message);
    }

    public String requestString(String message){
        return gui.getUserString(getTurnInfo() + message);

    }

    public String requestUserButton(String msg,String...options){
        return gui.getUserButtonPressed(getTurnInfo() + msg,options);
    }

    public int requestInteger(String message, int minValue, int maxValue) {

        return gui.getUserInteger(getTurnInfo() + message, minValue, maxValue);
    }

    public void updatePlayerBalance(Player player){
        guiPlayers.get(player.getID()).setBalance(player.getPlayerBalance());
    }

    public void setOwner(Field field,int post){
        GUI_Field[] defualtfields = gui.getFields();
        if (field instanceof BuyableField prop){
            defualtfields[post].setSubText(prop.getOwner().getName());
        }
    }

    private String getTurnInfo(){
        return gc.getTurnMessage();
    }

   

    private GUI_Field[] getFields() {
        GUI_Field[] fields = new GUI_Field[40];
        int i = 0;
        fields[i++] = new GUI_Start("Start", "Modtag: 4k", "Modtag kr. 200,-\nnår de passerer start", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Street("Rødovrevej", "1200,-", "Rødovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Hvidovrevej", "Pris:  60", "Hvidovrevej", "Leje:  20", new Color(75, 155, 225), Color.WHITE);
        fields[i++] = new GUI_Tax("Betal\nindkomst-\nskat", "Betal 4000,-", "Betal indkomstskat 4000,-", Color.GRAY, Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Forsea", "Pris:  200", "Øresundsredderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Roskildevej", "Pris:  100", "Roskildevej", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Valby\nLanggade", "Pris:  100", "Valby Langgade", "Leje:  40", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Street("Allégade", "Pris:  120", "Allégade", "Leje:  45", new Color(255, 135, 120), Color.BLACK);
        fields[i++] = new GUI_Jail("default", "Fængsel", "Fængsel", "På besøg i fængslet", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new GUI_Street("Frederiks-\nberg Allé", "Pris:  140", "Frederiksberg Allé", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Brewery("src/main/resources/sqaus.png", "Sqaush", "Pris:  150", "Tuborg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new GUI_Street("Bülowsvej", "Pris:  140", "Bülowsvej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Street("Gammel Kongevej", "Pris:  140", "Gammel Kongevej", "Leje:  50", new Color(102, 204, 0), Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Molslinjen", "Pris:  200", "D.F.D.S.", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Bernstorffsvej", "Pris:  180", "Bernstorffsvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Hellerupvej", "Pris:  180", "Hellerupvej", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Street("Strandvejen", "Pris:  180", "Strandvejen", "Leje:  60", new Color(153, 153, 153), Color.BLACK);
        fields[i++] = new GUI_Refuge("default", "Helle", "Helle", "Ta' en pause", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Trianglen", "Pris:  220", "Trianglen", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Østerbro-\ngade", "Pris:  220", "Østerbrogade", "Leje:  70", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Street("Grønningen", "Pris:  240", "Grønningen", "Leje:  80", Color.RED, Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "Scandlines", "Pris:  200", "Ø.S. redderiet", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Bredgade", "Pris:  260", "Bredgade", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Street("Kgs. Nytorv", "Scandlines", "Kongens Nytorv", "Leje:  80", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Brewery("src/main/resources/coca.png", "Coca-Cola", "Pris:  150", "Carlsberg bryggeri", "10 x [Terningslag]", Color.BLACK, Color.WHITE);
        fields[i++] = new GUI_Street("Østergade", "Pris:  280", "Østergade", "Leje:  85", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Jail("default", "Gå i fængsel", "Gå i fængsel", "De fængsles\nSlå to ens for at komme ud", new Color(125, 125, 125), Color.BLACK);
        fields[i++] = new GUI_Street("Amagertorv", "Pris:  300", "Amagertorv", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Street("Vimmel-\nskaftet", "Pris:  300", "Vimmelskaftet", "Leje:  95", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Nygade", "Pris:  320", "Nygade", "Leje:  100", new Color(255, 255, 50), Color.BLACK);
        fields[i++] = new GUI_Shipping("default", "D.F.D.S", "Pris:  200", "Bornholms redderi", "Leje:  75", Color.WHITE, Color.BLACK);
        fields[i++] = new GUI_Chance("?", "Prøv lykken", "Ta' et chancekort.", new Color(204, 204, 204), Color.BLACK);
        fields[i++] = new GUI_Street("Frederiks-\nberggade", "Pris:  350", "Frederiksberggade", "Leje:  120", new Color(150, 60, 150), Color.WHITE);
        fields[i++] = new GUI_Tax("Ekstra-\nordinær\nstatsskat", "Betal 2000,-", "Betal ekstraordinær\nstatsskat: 2000,-", Color.GRAY, Color.BLACK);
        fields[i++] = new GUI_Street("Rådhuspladsen", "Pris:  400", "Rådhuspladsen", "Leje:  150", new Color(150, 60, 150), Color.WHITE);
        return fields;
    }
}
