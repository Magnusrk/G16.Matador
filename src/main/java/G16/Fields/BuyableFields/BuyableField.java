package G16.Fields.BuyableFields;

import G16.Fields.Field;
import G16.Graphics.MatadorGUI;
import G16.PlayerUtils.Player;

import java.util.ArrayList;

public abstract class BuyableField extends Field {

    private Player owner;

    protected int price;
    protected int[] rents;
    public BuyableField(String name, int price, int[] rents) {
        super(name);
        this.price = price;
        this.rents = rents;
    }

    public int getPrice(){
        return price;
    }
    public int getRent(int index) {
        return rents[index];
    }

    public void setOwner(Player currentplayer){
        owner= currentplayer;
    }

    public Player getOwner(){
        return owner;
    }

    @Override
    public String toString() {
        return "<div style=\"text-align:center; margin: 0px; width:130px;;\"><span style=\"font-size: 11px;\">"+name+"</span></div>";
    }
}
