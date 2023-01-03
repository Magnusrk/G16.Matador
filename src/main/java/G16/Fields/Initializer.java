package G16.Fields;

public class Initializer {
    Field[] fields = new Field[40];

    /**
     * Sets values for every field in the game
     * And adds them into an array
     * @return array with fields
     */
    public Field[] InitFields(){
        //field 0
        VisitorField start = new VisitorField("start");
        fields[0] = start;
        //field 1
        Property Rødovrevej = new Property("Rødovrevej", Color.Brown, 1);
        fields[1] = Rødovrevej;
        //field 2
        Chance chance1 = new Chance("chance");
        fields[3] = chance1;
        //field 3
        Property Hvidovrevej = new Property("candy",Color.Blue, 1);
        fields[4] = Hvidovrevej;
        //field 4

        return fields;
    }
}
