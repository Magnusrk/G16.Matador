package G16.Controllers;

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
        switch (1) {
            case 1 -> {
                oilPrices(currentPlayer);
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
        controller.addBalanceToPlayer(player,toPay);
        mgui.showMessage("To pay: "+toPay);
    }

    public void parkingTicket(Player player){
        mgui.showMessage("PARKERINGS BØFDEKD");
    }
}

