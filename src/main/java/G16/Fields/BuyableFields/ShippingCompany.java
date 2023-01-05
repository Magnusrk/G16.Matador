package G16.Fields.BuyableFields;

import G16.Fields.Field;

public class ShippingCompany extends BuyableField {

    public ShippingCompany(String name, int price, int[] rents) {
        super(name,price,rents);
    }

    //Show rental price for Ship companies.
    public String toString() {
        //Ship companies deed details.
        return super.toString() + "Pris: "+this.getPrice()+ ",-<br/>"+
                " 1 rederi ejet: " + this.getRent(0)+ ",-<br/>" +
                " 2 rederi ejet: "+ this.getRent(1)+ ",-<br/>" +
                " 3 rederi ejet: "+ this.getRent(2)+ ",-<br/>"+
                " 4 rederi ejet: "+ this.getRent(3)+ ",-<br/>";

    }
}


