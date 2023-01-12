package G16.PlayerUtils;

public class Player {
    private String name;
    private final MoneyBalance balance = new MoneyBalance();
    private int playerPosition = 0;
    private int previousPlayerPosition = 0;
    private boolean jailed = false;

    private boolean bankrupt = false;
    private int outOfJailCards = 0;
    private int ID = -1;

    private int shipsOwned = 0;
    private int brewsOwned = 0;

    private int turnsInJail = 0;




    public Player(){
        balance.setStartingBalance();
    }




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
    public void addBalance(int add){
        balance.updateMoney(add);
        /*
        if (balance.getBalance() < 0){
            bankrupt = true;
        }
         */
    }

    public int getShipsOwned() {
        return shipsOwned;
    }
    public int getBrewsOwned(){
        return brewsOwned;
    }

    public void setShipsOwned(int shipsOwned) {
        this.shipsOwned = shipsOwned;
    }
    public void setBrewsOwned(int brewsOwned){
        this.brewsOwned = brewsOwned;
    }

    public void setPlayerPosition(int position){
        previousPlayerPosition = playerPosition;
        playerPosition = position;
    }

    public int getPreviousPlayerPosition(){
        return previousPlayerPosition;
    }
    public void setJailed(boolean jail){
        jailed = jail;
        if (!jailed){
            turnsInJail =0;
        }
    }
    public int getTurnsInJail(){
        return turnsInJail;
    }

    public void increaseTurnsInJail(){
        turnsInJail++;
    }

    @Override
    public String toString() {
        return name;
    }

    public void setBankrupt(boolean bankrupt) {
        this.bankrupt = bankrupt;
    }
}
