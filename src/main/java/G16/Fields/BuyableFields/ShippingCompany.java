package G16.Fields.BuyableFields;

import G16.Fields.Field;
import G16.Language;

public class ShippingCompany extends BuyableField {

    public ShippingCompany(String name, int price, int[] rents) {
        super(name,price,rents);
    }

    //Show rental price for Ship companies.
    public String toString() {
        //Ship companies deed details.
        return super.toString() + Language.getString("price")+this.getPrice()+ ",-<br>"+
                Language.getString("1ship")+" "+  this.getRent(0)+ ",-<br>" +
                Language.getString("2ship")+" "+ this.getRent(1)+ ",-<br>" +
                Language.getString("3ship")+" "+ this.getRent(2)+ ",-<br>"+
                Language.getString("4ship")+" "+ this.getRent(3)+ ",-<br>";


    }
}


