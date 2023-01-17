package G16.Controllers;

import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.Field;
import G16.Graphics.MatadorGUI;
import G16.Language;
import G16.PlayerUtils.Player;
import java.util.Random;
import java.util.stream.IntStream;


public class ChanceCardController {
    MatadorGUI mgui;
    static int[] numchance = IntStream.range(1,45).toArray();
    GameController controller;

    public ChanceCardController(MatadorGUI mgui) {
        this.mgui = mgui;
        shuffleChanceCard();
    }

    public void DoChanceCard(Player currentPlayer, GameController controller ) {
        this.controller = controller;
        //getNumchance()[0]
        switch (getNumchance()[0]) {
            case 1 -> wealthBasedCost(currentPlayer, "1",500,2000);
            case 2 -> wealthBasedCost(currentPlayer, "2",800,2300);
            case 3 -> giveOrTakeCash(currentPlayer,"3",-1000);
            case 4 -> giveOrTakeCash(currentPlayer,"4",-300);
            case 5 -> giveOrTakeCash(currentPlayer,"5",-200);
            case 6,7 -> giveOrTakeCash(currentPlayer,"6",-3000);
            case 8 -> giveOrTakeCash(currentPlayer,"7",-1000);
            case 9 -> giveOrTakeCash(currentPlayer,"8",-200);
            case 10 -> giveOrTakeCash(currentPlayer,"9",-1000);
            case 11 -> giveOrTakeCash(currentPlayer,"10",-200);
            case 12 -> giveOrTakeCash(currentPlayer,"11",-2000);
            case 13,14 -> giveOrTakeCash(currentPlayer,"12",500);
            case 15,16,17 -> giveOrTakeCash(currentPlayer,"13",1000);
            case 18 -> giveOrTakeCash(currentPlayer,"14",3000);
            case 19 -> giveOrTakeCash(currentPlayer,"15",1000);
            case 20 -> giveOrTakeCash(currentPlayer,"16",1000);
            case 21,22 -> giveOrTakeCash(currentPlayer,"17",1000);
            case 23 -> giveOrTakeCash(currentPlayer,"18",1000);
            case 24 -> giveOrTakeCash(currentPlayer,"19",200);
            case 25 -> matadorEndowment(currentPlayer);
            case 26 -> getMoneyFromPlayers(currentPlayer,200,"21");
            case 27 -> getMoneyFromPlayers(currentPlayer,500,"22");
            case 28 -> getMoneyFromPlayers(currentPlayer,500,"23");
            case 29,30 -> moveToStart(currentPlayer);
            case 31 -> moveForwardThree(currentPlayer);
            case 32,33 -> moveBackThree(currentPlayer);
            case 34 -> moveToProp(currentPlayer,11,"27");
            case 35 -> moveToShip(currentPlayer, true);
            case 36 -> moveToMols(currentPlayer);
            case 37 -> moveToProp(currentPlayer,24,"30");
            case 38 -> moveToProp(currentPlayer,32,"31");
            case 39 -> moveToShip(currentPlayer, false);
            case 40 -> moveToProp(currentPlayer,19,"33");
            case 41 -> moveToProp(currentPlayer,39,"34");
            case 42,43 -> jailFree(currentPlayer);
            case 44,45 -> goToJail(currentPlayer);
        }
        drawChancecard();
    }


    /* shuffles the values in the array. Code found on https://www.digitalocean.com/community/tutorials/shuffle-array-java
     */
    private void shuffleChanceCard(){
        Random rand = new Random();
        for (int i = 0; i < numchance.length; i++) {
            int randomIndexToSwap = rand.nextInt(numchance.length);
            int temp = numchance[randomIndexToSwap];
            numchance[randomIndexToSwap] = numchance[i];
            numchance[i] = temp;
        }
    }
    /**
     * Puts the chancecard you've drawn on the bottom pile
     * @return The array that contains the order of chancecards
     */
    private int[] leftshiftarray(){
        int[] proxy = new int[numchance.length];
        if (numchance.length - 1 >= 0) System.arraycopy(numchance, 1, proxy, 0, numchance.length - 1);
        proxy[numchance.length-1] = numchance[0];
        return proxy;
    }

    private int[] getNumchance(){
        return numchance;
    }
    private void drawChancecard() {
        setNumchance(leftshiftarray());
    }

    private void setNumchance(int[] proxy){
        numchance=proxy;
    }
    private void wealthBasedCost(Player player, String caseNum, int houseCost, int hotelCost){
        String mesg = "case";
        mesg = mesg + caseNum;
        mgui.showMessage(Language.getString(mesg));
        mgui.displayChanceCards(caseNum);
        int toPay = 0;
        for (int i = 0; i< controller.getOwnedProperties(player).size(); i++)
            if(controller.getOwnedProperties(player).get(i).getHouseCount()<5){
                toPay = toPay +(controller.getOwnedProperties(player).get(i).getHouseCount()*houseCost);
            } else {
                toPay = toPay +hotelCost;
            }
        controller.addBalanceToPlayer(player,-toPay);
        mgui.showMessage(Language.getString("toPay")+" "+toPay);
    }

    private void giveOrTakeCash(Player player, String caseNum, int amount){
        String mesg = "case";
        mesg = mesg + caseNum;
        mgui.displayChanceCards(caseNum);
        mgui.showMessage(Language.getString(mesg));
        controller.addBalanceToPlayer(player,amount);
    }

    private void matadorEndowment(Player player){
        mgui.showMessage(Language.getString("case20"));
        mgui.displayChanceCards("20");

        int wealth = 0;
        wealth = wealth + player.getPlayerBalance();
        wealth = wealth +(player.getShipsOwned() *4000);
        wealth = wealth +(player.getBrewsOwned()*3000);
        for (int i = 0; i < controller.getOwnedProperties(player).size(); i++){
            wealth = wealth + controller.getOwnedProperties(player).get(i).getPrice();
            if (controller.getOwnedProperties(player).get(i).getHouseCount()<5) {
                wealth = wealth + (controller.getOwnedProperties(player).get(i).getHouseCount() * controller.getOwnedProperties(player).get(i).getHousePrice());
            } else {
                wealth = wealth + (controller.getOwnedProperties(player).get(i).getHouseCount() * controller.getOwnedProperties(player).get(i).getHousePrice()*5);
            }
        }
        if(wealth<15000) {
            controller.addBalanceToPlayer(player, 40000);
        }
    }


    private void getMoneyFromPlayers(Player player, int amount, String caseNum){
        String mesg = "case";
        mesg = mesg + caseNum;
        mgui.showMessage(Language.getString(mesg));
        mgui.displayChanceCards(caseNum);
        int receiver = player.getID();
        for (int i = 0; i< controller.getPlayerAmount(); i++){
            if (i != receiver){
                controller.addPlayerBalanceByID(i,-amount);
            }
        }
        controller.addBalanceToPlayer(player,(controller.getPlayerAmount()-1)*amount);
    }
    private void moveToStart(Player player){
        mgui.showMessage(Language.getString("case24"));
        mgui.displayChanceCards("24");
        controller.movePlayer(player,40-player.getPlayerPosition());
        mgui.drawPlayerPosition(player);
    }
    private void moveForwardThree(Player player){
        mgui.showMessage(Language.getString("case25"));
        mgui.displayChanceCards("25");
        controller.movePlayer(player,3);
        mgui.drawPlayerPosition(player);
        controller.landOnField(player,0);
        //player.setPlayerPosition(player.getPlayerPosition()+3);
    }
    private void moveBackThree(Player player){
        mgui.showMessage(Language.getString("case26"));
        mgui.displayChanceCards("26");
        if (player.getPlayerPosition()<3){
            controller.movePlayer(player,player.getPlayerPosition()+35);
            mgui.drawPlayerPosition(player);
            controller.landOnField(player,0);
        }else {
            controller.movePlayer(player,-3);
            mgui.drawPlayerPosition(player);
            controller.landOnField(player,0);
        }
    }
    private void moveToShip(Player player, boolean doubleRent){
        Field[] fields = controller.getFields();
        mgui.showMessage(Language.getString("case28"));
        mgui.displayChanceCards("28");

        if (player.getPlayerPosition()== 36 || player.getPlayerPosition()== 2){
            if (player.getPlayerPosition()==36){
                controller.giveStartMoney(player);
            }
            controller.movePlayer(player, 5-player.getPlayerPosition());
            mgui.drawPlayerPosition(player);
            if(fields[5] instanceof ShippingCompany ship && ship.getOwner() != null) {
                controller.payShipRent(player, (BuyableField) fields[5]);
                if (doubleRent) {
                    controller.payShipRent(player, (BuyableField) fields[5]);
                }
            } else {
                controller.buyField(player, (BuyableField) fields[5]);
            }
        }
        if (player.getPlayerPosition() == 7){
            player.setPlayerPosition(15);
            mgui.drawPlayerPosition(player);
            if(fields[15] instanceof ShippingCompany ship && ship.getOwner() != null) {
                controller.payShipRent(player, (BuyableField) fields[15]);
                if (doubleRent) {
                    controller.payShipRent(player, (BuyableField) fields[15]);
                }
            }else {
                controller.buyField(player, (BuyableField) fields[15]);
            }
        }
        if (player.getPlayerPosition()== 17 || player.getPlayerPosition()== 22 ){
            player.setPlayerPosition(25);
            mgui.drawPlayerPosition(player);
            if(fields[25] instanceof ShippingCompany ship && ship.getOwner() != null) {
                controller.payShipRent(player, (BuyableField) fields[25]);
                if (doubleRent) {
                    controller.payShipRent(player, (BuyableField) fields[25]);
                }
            }else {
                controller.buyField(player, (BuyableField) fields[25]);
            }
        }
        if (player.getPlayerPosition() == 33){
            player.setPlayerPosition(35);
            mgui.drawPlayerPosition(player);
            if(fields[35] instanceof ShippingCompany ship && ship.getOwner() != null) {
                controller.payShipRent(player, (BuyableField) fields[35]);
                if (doubleRent) {
                    controller.payShipRent(player, (BuyableField) fields[35]);
                }
            }else {
                controller.buyField(player, (BuyableField) fields[35]);
            }
        }
    }
    private void moveToMols(Player player){
        Field[] fields = controller.getFields();
        if (player.getPlayerPosition()> 15) {
            controller.movePlayer(player, 40 - player.getPlayerPosition() + 15);
            mgui.drawPlayerPosition(player);
        } else {
            controller.movePlayer(player, 15 - player.getPlayerPosition());
            mgui.drawPlayerPosition(player);
        }
        mgui.showMessage(Language.getString("case29"));
        mgui.displayChanceCards("29");
        if(fields[15] instanceof ShippingCompany ship && ship.getOwner() != null) {
            controller.payShipRent(player, (BuyableField) fields[15]);
        }else {
            controller.buyField(player, (BuyableField) fields[15]);
        }
    }
    private void moveToProp(Player player, int address, String caseNum){
        Field[] fields = controller.getFields();
        String mesg = "case";
        mesg = mesg + caseNum;
        mgui.displayChanceCards(caseNum);
        if (player.getPlayerPosition()> address) {
            controller.movePlayer(player, 40 - player.getPlayerPosition() + address);
            mgui.drawPlayerPosition(player);
        } else {
            controller.movePlayer(player, address - player.getPlayerPosition());
            mgui.drawPlayerPosition(player);
        }
        mgui.showMessage(Language.getString(mesg));
        if(fields[address] instanceof Property prop && prop.getOwner() != null && !prop.getMortgaged()) {
            controller.payRent(player, (BuyableField) fields[address]);
        } else {
            controller.buyField(player, (BuyableField) fields[address]);
        }
    }
    private void jailFree(Player player){
        mgui.showMessage(Language.getString("case35"));
        mgui.displayChanceCards("35");
        player.addOutOfJailCard(1);
    }
    private void goToJail(Player player){
        mgui.showMessage(Language.getString("case36"));
        mgui.displayChanceCards("36");
        player.setJailed(true);
        player.setPlayerPosition(10);
        mgui.drawPlayerPosition(player);
    }
}

