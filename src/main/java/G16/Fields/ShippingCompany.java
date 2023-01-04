package G16.Fields;

public class ShippingCompany extends Field {
    protected int price;
    protected int rent;
    public ShippingCompany(String name, int price, int rent0) {
        super(name);
        this.price = price;
    }
    public int getPrice(){
        return price;
    }
}
