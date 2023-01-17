package G16.PlayerUtils;

import G16.Fields.BuyableFields.BuyableField;

import java.util.ArrayList;

public class TradeOffer {

    Player proposer;

    Player receiver;

    private int proposerMoney = 0;
    private int receiverMoney = 0;
    private ArrayList<BuyableField> proposerFields = new ArrayList<>();
    private ArrayList<BuyableField> receiverFields = new ArrayList<>();

    private boolean finished = false;

    public TradeOffer(Player proposer, Player receiver){
        this.proposer = proposer;
        this.receiver = receiver;
    }


    public void setFields(ArrayList<BuyableField> fields, boolean proposer){

        if(proposer){
            this.proposerFields = fields;
        } else {
            this.receiverFields = fields;
        }

    }

    public ArrayList<BuyableField> getFields(boolean proposer){
        if(proposer){
            return proposerFields;
        } else {
            return receiverFields;
        }

    }

    public void setMoney(int amount, boolean proposer){
        if(proposer){
            this.proposerMoney = amount;
        } else {
            this.receiverMoney = amount;
        }
    }

    public int getMoney(boolean proposer){
        if (proposer){
            return proposerMoney;
        } else {
            return receiverMoney;
        }

    }

    public Player getTradeParty(boolean isProposer){
        if(isProposer){
            return this.proposer;

        } else {
            return receiver;
        }
    }

    public boolean getFinished(){
        return finished;
    }

    public void setFinished(boolean finished){
        this.finished = finished;
    }


}
