package G16.Fields;
import java.awt.Color;
public class Property extends Field {
    protected int price;
    protected int housePrice;

    protected int rent;
    protected Color color;
    //protected Player owner;
    public Property(String name, Color startingColor, int price, int housePrice, int rent){
        super(name);
        this.color = startingColor;
        this.price = price;
        this.rent = rent;
    }

    public int getPrice(){
        return price;
    }

    public int getRent() {
        return rent;
    }
    public java.awt.Color getColor(){
        return color;
    }
}
