package G16.Fields;
import G16.Fields.BuyableFields.Brewery;
import G16.Fields.BuyableFields.Property;
import G16.Fields.BuyableFields.ShippingCompany;
import G16.Fields.UnbuyableFields.*;

import java.awt.Color;

public class Initializer {
    static Field[] fields;

    /**
     * Sets values for every field in the game
     * And adds them into an array
     * @return array with fields
     */
    public static Field[] InitFields(){
        fields = new Field[]{
                new VisitorField("start"),
                new Property("Rødovrevej", Color.BLUE, 1200, 1000, new int[]{50, 250, 750, 2250, 4000, 6000}),
                new Chance("chance"),
                new Property("Hvidovrevej",Color.BLUE, 1200, 1000, new int[]{50, 250, 750, 2250, 4000, 6000}),
                new Tax("Indkomstskat", 4000),
                new ShippingCompany("Helsingør - Helsingborg", 4000, new int[]{500, 1000, 2000, 4000}),
                new Property("Roskildevej",Color.ORANGE, 2000, 1000, new int[]{100, 600, 1800, 5400, 8000, 11000}),
                new Chance("chance"),
                new Property("Valby Langgade",Color.ORANGE, 2000, 1000, new int[]{100, 600, 1800, 5400, 8000, 11000}),
                new Property("Allégade",Color.ORANGE, 2400, 1000, new int[]{150, 800, 2000, 6000, 9000, 12000}),
                new Jail("Jailcell"),
                new Property("Frederiksberg Allé",Color.GREEN, 2800, 2000, new int[]{200, 1000, 3000, 9000, 12500, 15000}),
                new Brewery("Squash", 3000, 100),
                new Property("Bülowsvej",Color.GREEN, 2800, 2000, new int[]{200, 1000, 3000, 9000, 12500, 15000}),
                new Property("Gl. Kongevej",Color.GREEN, 3200, 2000, new int[]{250, 1250, 3750, 10000, 14000, 18000}),
                new ShippingCompany("Mols-linien", 4000, new int[]{500, 1000, 2000, 4000}),
                new Property("Bernstorffsvejj",Color.GRAY, 3600, 2000, new int[]{300, 1400, 4000, 11000, 15000, 19000}),
                new Chance("chance"),
                new Property("Hellerupvej",Color.GRAY, 3600, 2000, new int[]{300, 1400, 4000, 11000, 15000, 19000}),
                new Property("Strandvejen",Color.GRAY, 4000, 2000, new int[]{350, 1600, 4400, 12000, 16000, 20000}),
                new VisitorField("Parkering"),
                new Property("Trianglen",Color.RED, 4400, 3000, new int[]{350, 1800, 5000, 14000, 17500, 21000}),
                new Chance("chance"),
                new Property("Østerbrogade",Color.RED, 4400, 3000, new int[]{350, 1800, 5000, 14000, 17500, 21000}),
                new Property("Grønningen",Color.RED, 4800, 3000, new int[]{400, 2000, 6000, 15000, 18500, 22000}),
                new ShippingCompany("Gedser - Rostock", 4000, new int[]{500, 1000, 2000, 4000}),
                new Property("Bredgade",Color.WHITE, 5200, 3000, new int[]{450, 2200, 6600, 16000, 19500, 23000}),
                new Property("Kgs. Nytorv",Color.WHITE, 5200, 3000, new int[]{450, 2200, 6600, 16000, 19500, 23000}),
                new Brewery("Coca Cola", 3000, 100),
                new Property("Østergade",Color.WHITE, 5600, 3000, new int[]{500, 2400, 7200, 17000, 20500, 24000}),
                new GoToJail("De fængsles"), new Property("Amagertorv",Color.YELLOW, 6000, 4000, new int[]{550, 2600, 7800, 18000, 22000, 25000}),
                new Property("Vimmelskaftet",Color.YELLOW, 6000, 4000, new int[]{550, 2600, 7800, 18000, 22000, 25000}),
                new Chance("chance"),
                new Property("Nygade",Color.YELLOW, 6400, 4000, new int[]{600, 3000, 9000, 20000, 24000, 28000}),
                new ShippingCompany("Rødby - Puttgarden", 4000, new int[]{500, 1000, 2000, 4000}),
                new Chance("chance"),
                new Property("Frederiksberggade",new Color(150, 60, 150), 7000, 4000, new int[]{700, 3500, 10000, 22000, 26000, 30000}),
                new Tax("Statsskat", 2000),
                new Property("Rådhuspladsen",new Color(150, 60, 150), 8000, 4000, new int[]{1000, 4000, 12000, 28000, 34000, 40000})
        };
        return fields;
    }
}
