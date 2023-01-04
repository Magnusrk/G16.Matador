package G16.Fields;
import java.awt.Color;
public class Property extends Field {
    protected int price;
    protected int housePrice;
    protected int[] rents = new int[5];
    protected Color color;
    //protected Player owner;
    public Property(String name, Color startingColor, int price, int housePrice, int[] rents){
        super(name);
        this.color = startingColor;
        this.price = price;
        this.rents = rents;
        this.housePrice = housePrice;
    }

    public int getPrice(){
        return price;
    }
    public int getRent(int numHouses) {
        return rents[numHouses];
    }
    public int getHousePrice(){
        return housePrice;
    }
    public java.awt.Color getColor(){
        return color;
    }
}
