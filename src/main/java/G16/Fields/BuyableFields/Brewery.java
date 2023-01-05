package G16.Fields.BuyableFields;

import G16.Fields.Field;

public class Brewery extends Field {
    protected int price;
    protected int rent;
    public Brewery(String name, int price, int rent0) {
        super(name);
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
}
