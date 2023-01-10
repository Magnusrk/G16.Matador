package G16.Controllers;

import G16.Fields.BuyableFields.BuyableField;
import G16.Fields.Field;
import G16.Graphics.MatadorGUI;
import G16.Language;
import G16.PlayerUtils.Player;
import gui_main.GUI;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;


public class ChanceCardController {
    MatadorGUI mgui;
    static int[] numchance = IntStream.range(1,45).toArray();
    GameController controller;
    //ChanceCard chancecard = new ChanceCard(IntStream.range(1,21).toArray());
    int[] chanceArray=Shufflechancecard();
    //ChanceField chanceField= new ChanceField("chancefield");

    public ChanceCardController(MatadorGUI mgui) {
        this.mgui = mgui;
    }

    public void DoChanceCard(Player currentPlayer, GameController controller ) {
        setNumchance(chanceArray);
        this.controller = controller;
        //getNumchance()[0]
        switch (35) {
            case 1 -> {
                oilPrices(currentPlayer);
            }
            case 2 -> {
                propertyTaxes(currentPlayer);
            }
            case 3 -> {
                ranRedLight(currentPlayer);
            }
            case 4 -> {
                carWash(currentPlayer);
            }
            case 5 -> {
                beer(currentPlayer);
            }
            case 6,7 -> {
                carRepair(currentPlayer);
            }
            case 8 -> {
                tirePurchase(currentPlayer);
            }
            case 9 -> {
                parkingTicket(currentPlayer);
            }
            case 10 -> {
                carInsurance(currentPlayer);
            }
            case 11 -> {
                cigarettesToll(currentPlayer);
            }
            case 12 -> {
                dentalBill(currentPlayer);
            }
            case 13,14 -> {
                lotteryWin(currentPlayer);
            }
            case 15,16,17 -> {
                stockDividend(currentPlayer);
            }
            case 18 -> {
                taxReturn(currentPlayer);
            }
            case 19 -> {
                bettingWin(currentPlayer);
            }
            case 20 -> {
                salaryIncrease(currentPlayer);
            }
            case 21,22 -> {
                bondMaturity(currentPlayer);
            }
            case 23 -> {
                furnitureSale(currentPlayer);
            }
            case 24 -> {
                cropYield(currentPlayer);
            }
            case 25 -> {
                matadorEndowment(currentPlayer);
            }
            case 26 -> {
                birthday(currentPlayer);
            }
            case 27 -> {
                partyPot(currentPlayer);
            }
            case 28 -> {
                familyParty(currentPlayer);
            }
            case 29,30 -> {
                moveToStart(currentPlayer);
            }
            case 31 -> {
                moveForwardThree(currentPlayer);
            }
            case 32,33 -> {
                moveBackThree(currentPlayer);
            }
            case 34 -> {
                moveToFrederiksberg(currentPlayer);
            }
            case 35 -> {
                moveToShip(currentPlayer);
            }
        }
        leftshiftarray();
    }


    /* shuffles the values in the array. Code found on https://www.digitalocean.com/community/tutorials/shuffle-array-java
     */
    public int[] Shufflechancecard(){
        Random rand = new Random();

        for (int i = 0; i < numchance.length; i++) {
            int randomIndexToSwap = rand.nextInt(numchance.length);
            int temp = numchance[randomIndexToSwap];
            numchance[randomIndexToSwap] = numchance[i];
            numchance[i] = temp;
        }
        System.out.println(Arrays.toString(numchance));
        return numchance;
    }

    public int[] leftshiftarray(){
        int[] proxy = new int[numchance.length];
        for (int i = 0; i < numchance.length-1; i++) {
            proxy[i] = numchance[i + 1];
        }
        proxy[numchance.length-1] = numchance[0];
        return proxy;
    }

    public int[] getNumchance(){
        return numchance;
    }

    public void setNumchance(int[] proxy){
        numchance=proxy;
    }

    public void oilPrices(Player player){
        mgui.showMessage(Language.getString("case1"));
        int toPay = 0;
        for (int i = 0; i< controller.getOwnedProperties(player).size(); i++)
            if(controller.getOwnedProperties(player).get(i).getHouseCount()<5){
                toPay = toPay +(controller.getOwnedProperties(player).get(i).getHouseCount()*500);
            } else {
                toPay = toPay +2000;
            }
        controller.addBalanceToPlayer(player,-toPay);
        mgui.showMessage("To pay: "+toPay);
    }

    public void propertyTaxes(Player player){
        mgui.showMessage(Language.getString("case2"));
        int toPay = 0;
        for (int i = 0; i< controller.getOwnedProperties(player).size(); i++)
            if(controller.getOwnedProperties(player).get(i).getHouseCount()<5){
                toPay = toPay +(controller.getOwnedProperties(player).get(i).getHouseCount()*800);
            } else {
                toPay = toPay +2300;
            }
        controller.addBalanceToPlayer(player,-toPay);
        mgui.showMessage("To pay: "+toPay);
    }

    public void ranRedLight(Player player){
        mgui.showMessage(Language.getString("case3"));
        controller.addBalanceToPlayer(player,-1000);
    }

    public void carWash(Player player){
        mgui.showMessage(Language.getString("case4"));
        controller.addBalanceToPlayer(player,-300);
    }

    public void beer(Player player){
        mgui.showMessage(Language.getString("case5"));
        controller.addBalanceToPlayer(player,-200);
    }

    public void carRepair(Player player){
        mgui.showMessage(Language.getString("case6"));
        controller.addBalanceToPlayer(player,-3000);
    }

    public void tirePurchase(Player player){
        mgui.showMessage(Language.getString("case7"));
        controller.addBalanceToPlayer(player,-1000);
    }

    public void parkingTicket(Player player){
        mgui.showMessage(Language.getString("case8"));
        controller.addBalanceToPlayer(player,-200);
    }

    public void carInsurance(Player player){
        mgui.showMessage(Language.getString("case9"));
        controller.addBalanceToPlayer(player,-1000);
    }

    public void cigarettesToll(Player player){
        mgui.showMessage(Language.getString("case10"));
        controller.addBalanceToPlayer(player,-200);
    }

    public void dentalBill(Player player){
        mgui.showMessage(Language.getString("case11"));
        controller.addBalanceToPlayer(player,-2000);
    }

    public void lotteryWin(Player player){
        mgui.showMessage(Language.getString("case12"));
        controller.addBalanceToPlayer(player,500);
    }
    public void stockDividend(Player player){
        mgui.showMessage(Language.getString("case13"));
        controller.addBalanceToPlayer(player,1000);
    }

    public void taxReturn(Player player){
        mgui.showMessage(Language.getString("case14"));
        controller.addBalanceToPlayer(player,3000);
    }
    public void bettingWin(Player player){
        mgui.showMessage(Language.getString("case15"));
        controller.addBalanceToPlayer(player,1000);
    }
    public void salaryIncrease(Player player){
        mgui.showMessage(Language.getString("case16"));
        controller.addBalanceToPlayer(player,1000);
    }
    public void bondMaturity(Player player){
        mgui.showMessage(Language.getString("case17"));
        controller.addBalanceToPlayer(player,1000);
    }
    public void furnitureSale(Player player){
        mgui.showMessage(Language.getString("case18"));
        controller.addBalanceToPlayer(player,1000);
    }
    public void cropYield(Player player){
        mgui.showMessage(Language.getString("case19"));
        controller.addBalanceToPlayer(player,200);
    }
    public void matadorEndowment(Player player){
        mgui.showMessage(Language.getString("case20"));
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

    public void birthday(Player player){
        mgui.showMessage(Language.getString("case21"));
        int birthdayBoy = player.getID();
        for (int i = 0; i< controller.getPlayerAmount(); i++){
            if (i != birthdayBoy){
                controller.addPlayerBalanceByID(i,-200);
            }
        }
        controller.addBalanceToPlayer(player,(controller.getPlayerAmount()-1)*200);
    }

    public void partyPot(Player player){
        mgui.showMessage(Language.getString("case22"));
        int birthdayBoy = player.getID();
        for (int i = 0; i< controller.getPlayerAmount(); i++){
            if (i != birthdayBoy){
                controller.addPlayerBalanceByID(i,-500);
            }
        }
        controller.addBalanceToPlayer(player,(controller.getPlayerAmount()-1)*500);
    }
    public void familyParty(Player player){
        mgui.showMessage(Language.getString("case23"));
        int birthdayBoy = player.getID();
        for (int i = 0; i< controller.getPlayerAmount(); i++){
            if (i != birthdayBoy){
                controller.addPlayerBalanceByID(i,-500);
            }
        }
        controller.addBalanceToPlayer(player,(controller.getPlayerAmount()-1)*500);
    }
    public void moveToStart(Player player){
        mgui.showMessage(Language.getString("case24"));
        player.setPlayerPosition(0);
    }
    public void moveForwardThree(Player player){
        mgui.showMessage(Language.getString("case25"));
        player.setPlayerPosition(player.getPlayerPosition()+3);
    }
    public void moveBackThree(Player player){
        mgui.showMessage(Language.getString("case26"));
        if (player.getPlayerPosition()<3){
            player.setPlayerPosition(39);
        }else {
            player.setPlayerPosition(player.getPlayerPosition() - 3);
        }
    }
    public void moveToFrederiksberg(Player player){
        mgui.showMessage(Language.getString("case27"));
        player.setPlayerPosition(37);
    }
    public void moveToShip(Player player){
        Field[] fields = controller.getFields();
        mgui.showMessage(Language.getString("case28"));
        if (player.getPlayerPosition()== 2 || player.getPlayerPosition() == 7){
            player.setPlayerPosition(5);
            controller.payShipRent(player, (BuyableField) fields[5]);
            controller.payShipRent(player, (BuyableField) fields[5]);
        }
        if (player.getPlayerPosition()== 17 ){
            player.setPlayerPosition(15);
            controller.payShipRent(player, (BuyableField) fields[15]);
            controller.payShipRent(player, (BuyableField) fields[15]);
        }
        if (player.getPlayerPosition()== 22 ){
            player.setPlayerPosition(25);
            controller.payShipRent(player, (BuyableField) fields[25]);
            controller.payShipRent(player, (BuyableField) fields[25]);
        }
        if (player.getPlayerPosition()== 36 || player.getPlayerPosition() == 33){
            player.setPlayerPosition(35);
            controller.payShipRent(player, (BuyableField) fields[35]);
            controller.payShipRent(player, (BuyableField) fields[35]);
        }
    }
}

