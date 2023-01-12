package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_fields.GUI_Car;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_main.GUI;
import java.awt.Color;

public class PlayerTest {
   public PlayerTest() {
   }

   public static void main(String[] args) {
      GUI gui = new GUI();
      GUI_Car car1 = new GUI_Car(Color.RED, Color.BLACK, GUI_Car.Type.RACECAR, GUI_Car.Pattern.HORIZONTAL_GRADIANT);
      GUI_Player player1 = new GUI_Player("Sebastian Vettel", 1000, car1);
      gui.addPlayer(player1);
      GUI_Car car2 = new GUI_Car(Color.RED, Color.BLACK, GUI_Car.Type.RACECAR, GUI_Car.Pattern.HORIZONTAL_GRADIANT);
      GUI_Player player2 = new GUI_Player("Michael Schumacher", 1000, car2);
      gui.addPlayer(player2);

      try {
         Thread.sleep(2000L);
      } catch (InterruptedException var8) {
         var8.printStackTrace();
      }

      car1.setPrimaryColor(Color.YELLOW);
      player1.setName(player2.getName());

      try {
         Thread.sleep(2000L);
      } catch (InterruptedException var7) {
         var7.printStackTrace();
      }

      car1.setSecondaryColor(Color.BLUE);
      player1.setName("Fabio Alonso");
      player2.setBalance(99999);
   }
}
