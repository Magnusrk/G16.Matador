package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_fields.GUI_Street;
import G16.mguiPatched.gui_main.GUI;
import java.util.ArrayList;

public class MoveCar {
   private static final int NUM_FIELDS = 24;
   private static final int NUM_PLAYERS = 4;

   public MoveCar() {
   }

   public static void main(String[] args) {
      ArrayList<String> fieldNames = new ArrayList();
      ArrayList<GUI_Field> fields = new ArrayList();

      for(int i = 0; i < 24; ++i) {
         GUI_Field field = new GUI_Street();
         field.setTitle("Field " + i);
         fieldNames.add("Field " + i);
         fields.add(field);
      }

      GUI gui = new GUI((GUI_Field[])fields.toArray(new GUI_Field[0]));
      ArrayList<GUI_Player> players = new ArrayList();
      ArrayList<String> playerNames = new ArrayList();

      for(int i = 0; i < 4; ++i) {
         String playerName = "Player " + (i + 1);
         playerNames.add(playerName);
         GUI_Player player = new GUI_Player(playerName);
         players.add(player);
         gui.addPlayer(player);
      }

      while(true) {
         String button = gui.getUserButtonPressed("Choose player to move", (String[])playerNames.toArray(new String[0]));
         GUI_Player playerToMove = (GUI_Player)players.get(playerNames.indexOf(button));
         String targetFieldName = gui.getUserSelection("Choose field to move to", (String[])fieldNames.toArray(new String[0]));
         playerToMove.getCar().setPosition(gui.getFields()[fieldNames.indexOf(targetFieldName)]);
      }
   }
}
