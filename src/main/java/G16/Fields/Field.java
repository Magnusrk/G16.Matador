package G16.Fields;

public abstract class Field {
    protected String name;
    //protected String title;
    protected int ID;
    public Field(String name){
        this.name = name;
    }
    //public Field(String title){ this.title = title;}

    /*public String getShippinglineName(){
        return title;
    }*/
    public String getName() {
        return name;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

}
