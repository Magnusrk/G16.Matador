package G16.mguiPatched.gui_main;

import G16.mguiPatched.gui_codebehind.GUI_BoardController;
import G16.mguiPatched.gui_codebehind.GUI_FieldFactory;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;
import java.awt.Color;
import org.jetbrains.annotations.NotNull;

public final class GUI {
   private GUI_BoardController boardController;
   private static boolean null_fields_allowed = false;

   public GUI() {
      GUI_Field[] fields = GUI_FieldFactory.makeFields();

      for(int i = 0; i < fields.length; ++i) {
         fields[i] = fields[i];
      }

      this.boardController = new GUI_BoardController(fields);
   }

   public GUI(GUI_Field[] fields) {
      this.checkNullArray(fields);
      if (!null_fields_allowed) {
         this.check_for_null_fields(fields);
      }

      this.boardController = new GUI_BoardController(fields);
   }

   public GUI(GUI_Field[] fields, Color backGroundColor) {
      this.checkNullArray(fields);
      if (!null_fields_allowed) {
         this.check_for_null_fields(fields);
      }

      this.boardController = new GUI_BoardController(fields, backGroundColor);
   }

   private void checkNullArray(GUI_Field[] fields) {
      if (fields == null) {
         throw new NullPointerException("GUI_Field[] fields is null - pass array with fields or use default constructor");
      }
   }

   private void check_for_null_fields(GUI_Field[] fields) {
      String msg = "Null fields!\nNull fields are not recommended! the following indices are null: ";
      String str = "{";
      String howTo = "Disable this Exception by calling the static method GUI.setNull_fields_allowed(true);";

      for(int i = 0; i < fields.length; ++i) {
         GUI_Field f = fields[i];
         if (f == null) {
            str = str + i + ", ";
         }
      }

      str = str + "}";
      str = str.replace(", }", "}");
      if (str.length() > 2) {
         throw new NullPointerException(msg + str + "\n" + howTo);
      }
   }

   public void showMessage(String msg) {
      this.boardController.showMessage(msg);
   }

   public @NotNull String getUserString(String msg) {
      return this.boardController.getUserString(msg, 1, 2147483647, true);
   }

   public @NotNull String getUserString(String msg, int minLength, int maxLength, boolean allowWhitespace) {
      return this.boardController.getUserString(msg, minLength, maxLength, allowWhitespace);
   }

   public int getUserInteger(String msg, int minValue, int maxValue) {
      return this.boardController.getUserInteger(msg, minValue, maxValue);
   }

   public int getUserInteger(String msg) {
      return this.boardController.getUserInteger(msg, -2147483648, 2147483647);
   }

   public String getUserButtonPressed(String msg, String... buttons) {
      for(int i = 0; i < buttons.length; ++i) {
         buttons[i] = buttons[i].replace("\n", "<BR>");
      }

      return this.boardController.getUserButtonPressed(msg, buttons);
   }

   public String getUserSelection(String msg, String... options) {
      if (options != null && options.length != 0) {
         msg = msg.replace("\n", "<BR>");

         for(int i = 0; i < options.length; ++i) {
            options[i] = options[i].replace("\n", "<BR>");
         }

         return this.boardController.getUserSelection(msg, options).toString();
      } else {
         throw new NullPointerException("You must supply at least one option!");
      }
   }

   public boolean getUserLeftButtonPressed(String msg, String trueButton, String falseButton) {
      msg = msg.replace("\n", "<BR>");
      trueButton = trueButton.replace("\n", "<BR>");
      falseButton = falseButton.replace("\n", "<BR>");
      return this.boardController.getUserButtonPressed(msg, trueButton, falseButton).equals(trueButton);
   }

   public boolean addPlayer(GUI_Player player) {
      return this.boardController.addPlayer(player);
   }

   public void setDice(int faceValue1, int rotation1, int x1, int y1, int faceValue2, int rotation2, int x2, int y2) {
      this.boardController.setDice(faceValue1, rotation1, x1, y1, faceValue2, rotation2, x2, y2);
   }

   public void setDice(int faceValue1, int x1, int y1, int faceValue2, int x2, int y2) {
      this.boardController.setDice(faceValue1, x1, y1, faceValue2, x2, y2);
   }

   public void setDice(int faceValue1, int rotation1, int faceValue2, int rotation2) {
      this.boardController.setDice(faceValue1, rotation1, faceValue2, rotation2);
   }

   public void setDice(int faceValue1, int faceValue2) {
      this.boardController.setDice(faceValue1, faceValue2);
   }

   public void setDie(int faceValue) {
      this.boardController.setDie(faceValue);
   }

   public void displayChanceCard(String txt) {
      txt = txt.replace("\n", "<BR>");
      this.boardController.displayChanceCard(txt);
   }

   public void setChanceCard(String txt) {
      txt = txt.replace("\n", "<BR>");
      this.boardController.setChanceCard(txt);
   }

   public void displayChanceCard() {
      this.boardController.displayChanceCard();
   }

   public GUI_Field[] getFields() {
      return this.boardController.getFields();
   }

   public void close() {
      this.boardController.close();
   }

   public static boolean isNull_fields_allowed() {
      return null_fields_allowed;
   }

   public static void setNull_fields_allowed(boolean allowed) {
      null_fields_allowed = allowed;
   }
}
