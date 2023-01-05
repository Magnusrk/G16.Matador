package G16.Fields.BuyableFields;

import G16.Fields.Field;

public abstract class BuyableField extends Field {
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
}
