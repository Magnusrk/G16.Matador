package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_codebehind.GUI_FieldFactory;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_main.GUI;

public class Field_Test {
   public Field_Test() {
   }

   public static void main(String[] args) {
      GUI_Field[] fields = GUI_FieldFactory.makeFields();
      GUI_Field[] fields2 = new GUI_Field[24];

      for(int i = 0; i < fields2.length; ++i) {
         fields2[i] = fields[i];
      }

      GUI gui = new GUI(fields2);
      System.out.println(gui.getUserButtonPressed("test\ntest", "test1", "test2"));
      gui.getUserString("test\ntest");
      gui.showMessage("test\ntest");
      GUI_Player gui_Player = new GUI_Player("Brian123456789012345667");
      gui.addPlayer(gui_Player);
      gui.addPlayer(new GUI_Player("Brian123123213213213213"));
      gui.addPlayer(new GUI_Player("Brian2"));
      gui.addPlayer(new GUI_Player("Brian3"));
      fields[23].setSubText("No Luck");
      fields[0].setCar(gui_Player, true);
      fields[0].setCar(gui_Player, false);
      System.out.println(fields[0].hasCar(gui_Player));

      for(int i = 0; i < 100; ++i) {
         gui.setDie(2);

         try {
            Thread.sleep(100L);
         } catch (InterruptedException var7) {
            var7.printStackTrace();
         }
      }

      gui.close();
   }
}
