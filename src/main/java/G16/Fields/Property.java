package G16.Fields;

public class Property extends Field {
    protected int price;
    protected Color color;
    //protected Player owner;
    public Property(String name, Color statingColor, int price0){
        super(name);
        //this.color = startingColor;
        this.price = price;
    }

}
