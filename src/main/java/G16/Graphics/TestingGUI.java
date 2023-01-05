package G16.Graphics;
import G16.Fields.Field;

public class TestingGUI extends MatadorGUI{

    public TestingGUI(Field[] fields){
        super(fields);
    }

    @Override
    public void showMessage(String message){
        System.out.println(message);

    }

    @Override
    public String requestString(String message){
        return "dummy string" + (int) ((Math.random() * (Integer.MAX_VALUE)));

    }

    @Override
    public String requestUserButton(String msg,String...options){
        int choice = (int) ((Math.random() * (options.length)));
        return options[choice];
    }

    @Override
    public int requestInteger(String message, int minValue, int maxValue) {

        return minValue;
    }

}
