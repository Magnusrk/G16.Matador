package G16.Fields.BuyableFields;

import G16.Language;

public class Brewery extends BuyableField {

    public Brewery(String name, int price, int[] rents) {
        super(name,price,rents);
    }

    public String toString() {

        return super.toString() + Language.getString("price")+" "+ this.getPrice()+ ",-<br>"+
                Language.getString("1brew")+ " " + this.getRent(0)+ ",-<br>" +
                Language.getString("2brew")+" "+ this.getRent(1)+ ",-<br>";


    }
}
