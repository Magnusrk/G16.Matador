package G16.Graphics;

import G16.PlayerUtils.Player;




public abstract class GameGUI {

    public abstract void addPlayer(String name, int balance);

    public abstract void drawPlayerPosition(Player player);
    public abstract void drawDice(int faceValue1, int faceValue2);

    public abstract void showMessage(String message);

    public abstract String requestString(String message);

    public abstract String requestUserButton(String msg,String...options);

    public abstract int requestInteger(String message, int minValue, int maxValue);

    public abstract void updatePlayerBalance(Player player);

}
