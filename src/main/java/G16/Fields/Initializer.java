package G16.Fields;
import java.awt.Color;

public class Initializer {
    static Field[] fields = new Field[40];

    /**
     * Sets values for every field in the game
     * And adds them into an array
     * @return array with fields
     */
    public static Field[] InitFields(){
        //field 1
        VisitorField start = new VisitorField("start");
        fields[0] = start;

        //field 2
        Property Rødovrevej = new Property("Rødovrevej", Color.BLUE, 1200, 1000, new int[]{50, 250, 750, 2250, 4000, 6000});
        fields[1] = Rødovrevej;

        //field 3
        Chance chance1 = new Chance("chance");
        fields[2] = chance1;

        //field 4
        Property Hvidovrevej = new Property("Hvidovrevej",Color.BLUE, 1200, 1000, new int[]{50, 250, 750, 2250, 4000, 6000});
        fields[3] = Hvidovrevej;

        //field 5
        Tax tax1 = new Tax("Indkomstskat", 4000);
        fields[4] = tax1;

        //field 6
        ShippingCompany Scandlines1 = new ShippingCompany("Helsingør - Helsingborg", 4000, new int[]{50, 250, 750, 2250});
        fields[5] = Scandlines1;

        //field 7
        Property Roskildevej = new Property("Roskildevej",Color.ORANGE, 2000, 1000, new int[]{100, 600, 1800, 5400, 8000, 11000});
        fields[6] = Roskildevej;

        //field 8
        Chance chance2 = new Chance("chance");
        fields[7] = chance2;

        //field 9
        Property Valby = new Property("Valby Langgade",Color.ORANGE, 2000, 1000, new int[]{100, 600, 1800, 5400, 8000, 11000});
        fields[8] = Valby;

        //field 10
        Property Allegade = new Property("Allégade",Color.ORANGE, 2400, 1000, new int[]{150, 800, 2000, 6000, 9000, 12000});
        fields[9] = Allegade;

        //field 11
        Jail Jailcell = new Jail("Jailcell");
        fields[10] = Jailcell;

        //field 12
        Property Frederiksberg = new Property("Frederiksberg Allé",Color.GREEN, 2800, 2000, new int[]{200, 1000, 3000, 9000, 12500, 15000});
        fields[11] = Frederiksberg;

        //field 13
        Brewery Squash = new Brewery("Squash", 3000, 100);
        fields[12] = Squash;

        //field 14
        Property Bulowsvej = new Property("Bülowsvej",Color.GREEN, 2800, 2000, new int[]{200, 1000, 3000, 9000, 12500, 15000});
        fields[13] = Bulowsvej;

        //field 15
        Property GlKongevej = new Property("Gl. Kongevej",Color.GREEN, 3200, 2000, new int[]{250, 1250, 3750, 10000, 14000, 18000});
        fields[14] = GlKongevej;

        //field 16
        ShippingCompany Mols = new ShippingCompany("Mols-linien", 4000, 500);
        fields[15] = Mols;

        //field 17
        Property Bernstorffsvej = new Property("Bernstorffsvejj",Color.GRAY, 3600, 2000, new int[]{300, 1400, 4000, 11000, 15000, 19000});
        fields[16] = Bernstorffsvej;

        //field 18
        Chance chance3 = new Chance("chance");
        fields[17] = chance3;

        //field 19
        Property Hellerupvej = new Property("Hellerupvej",Color.GRAY, 3600, 2000, new int[]{300, 1400, 4000, 11000, 15000, 19000});
        fields[18] = Hellerupvej;

        //field 20
        Property Strandvejen = new Property("Strandvejen",Color.GRAY, 4000, 2000, new int[]{350, 1600, 4400, 12000, 16000, 20000});
        fields[19] = Strandvejen;

        //field 21
        VisitorField Parkering = new VisitorField("Parkering");
        fields[20] = Parkering;

        //field 22
        Property Trianglen = new Property("Trianglen",Color.RED, 4400, 3000, new int[]{350, 1800, 5000, 14000, 17500, 21000});
        fields[21] = Trianglen;

        //field 23
        Chance chance4 = new Chance("chance");
        fields[22] = chance4;

        //field 24
        Property Østerbrogade = new Property("Østerbrogade",Color.RED, 4400, 3000, new int[]{350, 1800, 5000, 14000, 17500, 21000});
        fields[23] = Østerbrogade;

        //field 25
        Property Grønningen = new Property("Grønningen",Color.RED, 4800, 3000, new int[]{400, 2000, 6000, 15000, 18500, 22000});
        fields[24] = Grønningen;

        //field 26
        ShippingCompany Scandlines2 = new ShippingCompany("Gedser - Rostock", 4000, 500);
        fields[25] = Scandlines2;

        //field 27
        Property Bredgade = new Property("Bredgade",Color.WHITE, 5200, 3000, new int[]{450, 2200, 6600, 16000, 19500, 23000});
        fields[26] = Bredgade;

        //field 28
        Property KgsNytorv = new Property("Kgs. Nytorv",Color.WHITE, 5200, 3000, new int[]{450, 2200, 6600, 16000, 19500, 23000});
        fields[27] = KgsNytorv;

        //field 29
        Brewery Cola = new Brewery("Coca Cola", 3000, 100);
        fields[28] = Cola;

        //field 30
        Property Østergade = new Property("Østergade",Color.WHITE, 5600, 3000, new int[]{500, 2400, 7200, 17000, 20500, 24000});
        fields[29] = Østergade;

        //field 31
        Jail GoToJail = new Jail("De fængsles");
        fields[30] = GoToJail;

        //field 32
        Property Amagertorv = new Property("Amagertorv",Color.YELLOW, 6000, 4000, new int[]{550, 2600, 7800, 18000, 22000, 25000});
        fields[31] = Amagertorv;

        //field 33
        Property Vimmelskaftet = new Property("Vimmelskaftet",Color.YELLOW, 6000, 4000, new int[]{550, 2600, 7800, 18000, 22000, 25000});
        fields[32] = Vimmelskaftet;

        //field 34
        Chance chance5 = new Chance("chance");
        fields[33] = chance5;

        //field 35
        Property Nygade = new Property("Nygade",Color.YELLOW, 6400, 4000, new int[]{600, 3000, 9000, 20000, 24000, 28000});
        fields[34] = Nygade;

        //field 36
        ShippingCompany Scandlines3 = new ShippingCompany("Rødby - Puttgarden", 4000, 500);
        fields[35] = Scandlines3;

        //field 37
        Chance chance6 = new Chance("chance");
        fields[36] = chance6;

        //field 38
        Property Frederiksberggade = new Property("Frederiksberggade",new Color(150, 60, 150), 7000, 4000, new int[]{700, 3500, 10000, 22000, 26000, 30000});
        fields[37] = Frederiksberggade;

        //field 39
        Tax tax2 = new Tax("Statsskat", 2000);
        fields[38] = tax2;

        //field 40
        Property Rådhuspladsen = new Property("Rådhuspladsen",new Color(150, 60, 150), 8000, 4000, new int[]{1000, 4000, 12000, 28000, 34000, 40000});
        fields[39] = Rådhuspladsen;
        return fields;
    }
}
