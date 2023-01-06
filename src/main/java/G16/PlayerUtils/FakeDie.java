package G16.PlayerUtils;

public class FakeDie extends Die {


    private int value1 = 6;
    private int value2 = 6;

    public FakeDie(){

    }

    @Override
    public int[] throwDice(){
        int firstNum = value1;
        int secondNum = value2;
        return new int[]{firstNum,secondNum};
    }

    public void setFaces(int value1, int value2){
        this.value1 = value1;
        this.value2 = value2;
    }

}
