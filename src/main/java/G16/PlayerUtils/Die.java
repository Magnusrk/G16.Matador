package G16.PlayerUtils;

public class Die {
    private static final int DIESIDE = 6;

    public Die(){

    }
    public int[] throwDice(){
        int firstNum = (int) ((Math.random() * (DIESIDE)) + 1);
        int secondNum = (int) ((Math.random() * (DIESIDE)) + 1);
        return new int[]{firstNum,secondNum};
    }

}
