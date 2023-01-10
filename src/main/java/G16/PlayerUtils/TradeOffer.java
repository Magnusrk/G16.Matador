package G16.PlayerUtils;

import G16.Fields.BuyableFields.BuyableField;

import java.util.ArrayList;

public class TradeOffer {

    private boolean accepted = false;
    private int money = 0;
    private ArrayList<BuyableField> fields = new ArrayList<>();

    public void setFields(ArrayList<BuyableField> fields){
        this.fields = fields;
    }

    public ArrayList<BuyableField> getFields(){
        return fields;
    }

    public void setMoney(int amount){
        money = amount;
    }

    public int getMoney(){
        return money;
    }

    public void setAccepted(boolean accepted){
        this.accepted = accepted;
    }

    public boolean getAccepted(){
        return accepted;
    }

}
