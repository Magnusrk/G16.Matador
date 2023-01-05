package G16.Fields.BuyableFields;

import G16.Fields.Field;

public class Brewery extends BuyableField {

    public Brewery(String name, int price, int[] rents) {
        super(name,price,rents);
    }

    public String toString() {

        return super.toString() + "Pris: "+this.getPrice()+ ",-<br>"+
                " 1 bryggeri ejet: " + this.getRent(0)+ ",-<br>" +
                " 2 bryggerier ejet: "+ this.getRent(1)+ ",-<br>";


    }
}
