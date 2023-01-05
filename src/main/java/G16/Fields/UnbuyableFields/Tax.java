package G16.Fields.UnbuyableFields;

import G16.Fields.Field;

public class Tax extends Field {
    protected int tax;
    public Tax(String name, int tax) {
        super(name);
        this.tax = tax;
    }
    public int getTax(){
        return tax;
    }
}
