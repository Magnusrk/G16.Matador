package G16.Fields;

public class Property extends Field {
    protected int price;
    protected int rent;
    protected Color color;
    //protected Player owner;
    public Property(String name, Color statingColor, int price0, int rent0){
        super(name);
        //this.color = startingColor;
        this.price = price;
    }

}
