package G16.PlayerUtils;

public class FakeDie extends Die {

    public FakeDie(){

    }
    public static int[] throwDice(){
        int firstNum = 6;
        int secondNum = 6;
        return new int[]{firstNum,secondNum};
    }

}
