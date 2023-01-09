package G16.PlayerUtils;

public class MoneyBalance {
    private int balance;
    public void setStartingBalance() {
        balance = 30000;
    }
    public void updateMoney(int money){
        balance+=money;
    }
    public int getBalance(){
        return balance;
    }
}
