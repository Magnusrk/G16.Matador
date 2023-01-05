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

    @Override
    public String toString() {

         return super.toString()+"Pris: "+this.getPrice()+ ",-<br>"+
                " Huspris: " + this.getHousePrice()+ ",-<br>" +
                " Leje: "+ this.getRent(0)+ ",-<br>" +
                " 1 hus: "+ this.getRent(1)+ ",-<br>"+
                " 2 huse: "+ this.getRent(2)+ ",-<br>"+
                " 3 huse: "+ this.getRent(3)+ ",-<br>"+
                " 4 huse: "+ this.getRent(4)+ ",-<br>"+
                " Hotel leje: "+ this.getRent(5)+ ",-<br>";

    }

}
