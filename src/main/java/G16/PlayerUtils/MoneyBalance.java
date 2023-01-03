package G16.PlayerUtils;

public class MoneyBalance {
    private final int STARTINGBALANCE = 30000;
    private int balance;
    public void setStartingBalance() {
        balance = STARTINGBALANCE;
    }
    public void updateMoney(int money){
        balance+=money;
    }
    public int getBalance(){
        return balance;
    }
}
