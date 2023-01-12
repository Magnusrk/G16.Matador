package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_main.GUI;

public class InputMethods {
   public InputMethods() {
   }

   public static void main(String[] args) {
      GUI gui = new GUI();

      while(true) {
         String output = "";
         String[] valgmuligheder = new String[]{"Knapper", "Drop-down", "Tekstfelt", "Tal", "Tal med grænser", "Boolean-valg"};
         String valg = gui.getUserButtonPressed("Vælg input metode", valgmuligheder);
         if (valg.equals("Knapper")) {
            output = gui.getUserButtonPressed("Metode: gui.getUserButtonPressed(..)  -  Vælg en knap", "Knap 1", "Knap 2", "Knap 3");
         }

         if (valg.equals("Drop-down")) {
            output = gui.getUserSelection("Metode: gui.getUserSelection(..)  -  Tag et valg", "Valg 1", "Valg 2", "Valg 3");
         }

         if (valg.equals("Tekstfelt")) {
            output = gui.getUserString("Metode: gui.getUserString(..)  -  Skriv en tekst");
         }

         if (valg.equals("Tal")) {
            output = String.valueOf(gui.getUserInteger("Metode: gui.getUserInteger(..)  -  Indtast et tal"));
         }

         if (valg.equals("Tal med grænser")) {
            output = String.valueOf(gui.getUserInteger("Metode: gui.getUserInteger(.., 1, 10)  -  Indtast et mellem 1 og 10", -10, 10));
         }

         if (valg.equals("Boolean-valg")) {
            output = String.valueOf(gui.getUserLeftButtonPressed("Metode: gui.getUserLeftButtonPressed(..)  -  Vælg ja eller nej", "Ja (True)", "Nej (False)"));
         }

         gui.showMessage("Du valgte: " + output);
      }
   }
}
