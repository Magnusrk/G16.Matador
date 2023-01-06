package G16.Dev;

import G16.Controllers.GameController;

import java.util.Scanner;

public class DevConsole {

    private GameController gc;

    public DevConsole(GameController gc){
        this.gc = gc;
        Thread commandlineThread = new Thread(this::waitForCommand);
        System.out.println("Developer console. Type 'help' for commands.");
        commandlineThread.start();
    }


    private void waitForCommand(){
        Scanner sc = new Scanner(System.in);

        System.out.print("> ");
        String[] command = sc.nextLine().split(" ");


        if(!command[0].equals("exit")){
            int id;
            switch (command[0]){
                default:
                    System.out.println("Unknown command. Type 'help' for commands. ");
                    break;
                case "money":

                    int amount;
                    try {
                        id = Integer.parseInt(command[1]);
                        amount = Integer.parseInt(command[2]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    gc.addPlayerMoney(id, amount);
                    break;

                case "turn":
                    try {
                        id = Integer.parseInt(command[1]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    gc.setPlayerTurn(id);
                    break;
                case "jail":
                    try {
                        id = Integer.parseInt(command[1]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    gc.goToJailByID(id);

                    break;
                case "rd":
                    int value;
                    try {
                        value = Integer.parseInt(command[1]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    gc.rigDice(value);
                    break;

                case "rp":
                    int face1;
                    int face2;
                    try {
                        face1 = Integer.parseInt(command[1]);
                        face2 = Integer.parseInt(command[2]);
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    //gc.(pair);
                    break;
                case "rld":
                    try {
                        //RESET DICE
                    } catch (NumberFormatException | ArrayIndexOutOfBoundsException e){
                        System.out.println("Invalid syntax.");
                        break;
                    }
                    //gc.(pair);
                    break;

                case "help":
                    System.out.println("""
                            --AVAILABLE COMMANDS--
                            money <player id> <amount>  #Give money to player
                            turn <player id>            #Give the turn to player
                            jail <player id>            #Jail player
                            rd <value>                  #Rig next dice throw to roll given value
                            rp  <value> <value>         #Rig next dice throw to roll given value faces
                            rld                         #Remove loaded dice.
                            help                        #Display this list
                            
                            """);

                    break;

            }
            waitForCommand();
        }
        System.out.println("Exiting console...");

    }
}
