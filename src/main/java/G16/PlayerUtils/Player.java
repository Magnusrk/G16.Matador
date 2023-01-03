package G16.PlayerUtils;

public class Player {
    private String name;
    private MoneyBalance balance = new MoneyBalance();
    private int playerPosition = 0;
    private int prevPlayerPosition = 0;
    private boolean jailed = false;
    private boolean bankrupt = false;
    private int outOfJailCards = 0;
    private int ID = -1;


    public void setName(String name){
        this.name= name;
    }
    public String getName(){
        return name;
    }
    public void setID(int ID){
        this.ID = ID;
    }
    public int getID(){
        return ID;
    }
    public int getPlayerBalance(){
        return balance.getBalance();
    }
    public int getPlayerPosition(){
        return playerPosition;
    }
    public int getPrevPlayerPosition(){
        return prevPlayerPosition;
    }
    public boolean getBankrupt(){
        return bankrupt;
    }
    public boolean getJailed(){
        return jailed;
    }
    public void addOutOfJailCard(int card){
        outOfJailCards = outOfJailCards + card;
    }
    public int getOutOfJailCards(){
        return outOfJailCards;
    }
    public void AddBalance(int add){
        balance.updateMoney(add);
        if (balance.getBalance() < 0){
            bankrupt = true;
        }
    }
    public void setBalance(int ba) {
        //balance.setBalance(ba);
    }

    public void setPlayerPosition(int position){
        prevPlayerPosition = playerPosition;
        playerPosition = position;
    }
    public void setJailed(boolean jail){
        jailed = jail;
    }

}
