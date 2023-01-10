package G16.Fields.BuyableFields;
import G16.Language;
import gui_resources.Attrs;

import javax.swing.*;
import java.awt.Color;
public class Property extends BuyableField {
    protected int housePrice;
    protected Color color;

    public Property(String name, Color startingColor, int price, int housePrice, int[] rents){
        super(name, price, rents);
        this.color = startingColor;
        this.housePrice = housePrice;
    }


    public int getHousePrice(){
        return housePrice;
    }
    public java.awt.Color getColor(){
        return color;
    }

    @Override
    public int getID() {
        return super.getID();
    }

    @Override
    public String toString() {

         return super.toString()+Language.getString("price")+" "+this.getPrice()+ ",-<br>"+
                 Language.getString("housePrice")+ " " + this.getHousePrice()+ ",-<br>" +
                 Language.getString("allOwned")+ "<br>" +
                 Language.getString("rent")+ " "+ this.getRent(0)+ ",-<br>" +
                 Language.getString("1house")+ " "+ this.getRent(1)+ ",-<br>"+
                 Language.getString("2house")+ " "+ this.getRent(2)+ ",-<br>"+
                 Language.getString("3house")+ " "+ this.getRent(3)+ ",-<br>"+
                 Language.getString("4house")+ " "+ this.getRent(4)+ ",-<br>"+
                Language.getString("hotel")+" "+ this.getRent(5)+ ",-<br/><br/>";

    }
}
