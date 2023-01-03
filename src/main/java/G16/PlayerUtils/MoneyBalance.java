package G16.PlayerUtils;

public class MoneyBalance {
    private int startingbalance;
    private int balance;

    public void setBalance(int balance) {
        this.balance = balance;
    }
    public void updateMoney(int money){
        balance+=money;
    }
}
