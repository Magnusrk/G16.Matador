package G16.Fields;

public class Initializer {
    Field[] fields = new Field[40];

    /**
     * Sets values for every field in the game
     * And adds them into an array
     * @return array with fields
     */
    public Field[] InitFields(){
        //field 1
        VisitorField start = new VisitorField("start");
        fields[0] = start;

        //field 2
        Property Rødovrevej = new Property("Rødovrevej", Color.Blue, 1200,20);
        fields[1] = Rødovrevej;

        //field 3
        Chance chance1 = new Chance("chance");
        fields[2] = chance1;

        //field 4
        Property Hvidovrevej = new Property("Hvidovrevej",Color.Blue, 1200, 20);
        fields[3] = Hvidovrevej;

        //field 5
        Tax tax1 = new Tax("Indkomstskat");
        fields[4] = tax1;

        //field 6
        ShippingCompany Scandlines = new ShippingCompany("Scandlines");
        fields[5] = Scandlines;

        //field 7
        Property Roskildevej = new Property("Roskildevej",Color.Orange, 2000, 20);
        fields[6] = Roskildevej;

        //field 8
        Chance chance2 = new Chance("chance");
        fields[7] = chance2;

        //field 9
        Property Valby = new Property("Valby Langgade",Color.Orange, 2000, 20);
        fields[8] = Valby;

        //field 10
        Property Allegade = new Property("Allégade",Color.Orange, 2400, 20);
        fields[9] = Allegade;

        //field 11
        Jail Jailcell = new Jail("Jailcell");
        fields[10] = Jailcell;

        //field 12
        Property Frederiksberg = new Property("Frederiksberg Allé",Color.Green, 2800, 20);
        fields[11] = Frederiksberg;

        //field 13
        Brewery Squash = new Brewery("Squash");
        fields[12] = Squash;

        //field 14
        Property Bulowsvej = new Property("Bülowsvej",Color.Green, 2800, 20);
        fields[13] = Bulowsvej;

        //field 15
        Property GlKongevej = new Property("Gl. Kongevej",Color.Green, 3200, 20);
        fields[14] = GlKongevej;

        //field 16
        ShippingCompany Mols = new ShippingCompany("Mols-linien");
        fields[15] = Mols;

        //field 17
        Property Bernstorffsvej = new Property("Bernstorffsvejj",Color.Gray, 3600, 20);
        fields[16] = Bernstorffsvej;

        //field 18
        Chance chance3 = new Chance("chance");
        fields[17] = chance3;

        //field 19
        Property Hellerupvej = new Property("Hellerupvej",Color.Gray, 3600, 20);
        fields[18] = Hellerupvej;

        //field 20
        Property Strandvejen = new Property("Strandvejen",Color.Gray, 4000, 20);
        fields[19] = Strandvejen;

        //field 21
        VisitorField Parkering = new VisitorField("Parkering");
        fields[20] = Parkering;

        //field 22
        Property Trianglen = new Property("Trianglen",Color.Red, 4400, 20);
        fields[21] = Trianglen;

        //field 23
        Chance chance4 = new Chance("chance");
        fields[22] = chance4;

        //field 24
        Property Østerbrogade = new Property("Østerbrogade",Color.Red, 4400, 20);
        fields[23] = Østerbrogade;

        //field 25
        Property Grønningen = new Property("Grønningen",Color.Red, 4800, 20);
        fields[24] = Grønningen;

        //field 26
        ShippingCompany Scandlines2 = new ShippingCompany("Scandlines2");
        fields[25] = Scandlines2;

        //field 27
        Property Bredgade = new Property("Bredgade",Color.White, 5200, 20);
        fields[26] = Bredgade;

        //field 28
        Property KgsNytorv = new Property("Kgs. Nytorv",Color.White, 5200, 20);
        fields[27] = KgsNytorv;

        //field 29
        Brewery Cola = new Brewery("Coca Cola");
        fields[28] = Cola;

        //field 30
        Property Østergade = new Property("Østergade",Color.White, 5600, 20);
        fields[29] = Østergade;

        //field 31
        Jail GoToJail = new Jail("De fængsles");
        fields[30] = GoToJail;

        //field 32
        Property Amagertorv = new Property("Amagertorv",Color.Yellow, 6000, 20);
        fields[31] = Amagertorv;

        //field 33
        Property Vimmelskaftet = new Property("Vimmelskaftet",Color.Yellow, 6000, 20);
        fields[32] = Vimmelskaftet;

        //field 34
        Chance chance5 = new Chance("chance");
        fields[33] = chance5;

        //field 35
        Property Nygade = new Property("Nygade",Color.Yellow, 6400, 20);
        fields[34] = Nygade;

        //field 36
        ShippingCompany Scandlines3 = new ShippingCompany("Scandlines3");
        fields[35] = Scandlines3;

        //field 37
        Chance chance6 = new Chance("chance");
        fields[36] = chance6;

        //field 38
        Property Frederiksberggade = new Property("Frederiksberggade",Color.Purple, 7000, 20);
        fields[37] = Frederiksberggade;

        //field 39
        Tax tax2 = new Tax("Statsskat");
        fields[38] = tax2;

        //field 40
        Property Rådhuspladsen = new Property("Rådhuspladsen",Color.Purple, 8000, 20);
        fields[39] = Rådhuspladsen;
        return fields;
    }
}
