package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_main.GUI;

public class InputTest {
   public InputTest() {
   }

   public static void main(String[] args) {
      GUI gui = new GUI();
      String res = gui.getUserString("TEST");
      System.out.println("::-->" + res);
      int resi = gui.getUserInteger("TEST");
      System.out.println("||-->" + resi);
   }
}
