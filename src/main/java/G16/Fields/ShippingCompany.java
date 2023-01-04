package G16.Fields;

public class ShippingCompany extends Field {
    protected int price;
    protected int[] rents = new int[4];

    public ShippingCompany(String name, int price, int[] rents) {
        super(name);
        this.price = price;
        this.rents = rents;
    }
    public int getPrice(){
        return price;
    }
    public int getRent(int numShips) {
        return rents[numShips];
    }
}
