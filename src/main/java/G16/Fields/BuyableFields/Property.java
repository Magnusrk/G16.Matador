package G16.Fields.BuyableFields;
import G16.Fields.Field;

import java.awt.Color;
public class Property extends BuyableField {
    protected int housePrice;
    protected Color color;
    //protected Player owner;
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
}
