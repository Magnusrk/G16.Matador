package G16.mguiPatched.gui_codebehind;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_fields.GUI_Board;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_input.EnterButton;
import G16.mguiPatched.gui_input.IntegerInput;
import G16.mguiPatched.gui_input.StringInput;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import javax.swing.JButton;
import javax.swing.JComboBox;

public final class GUI_BoardController {
   private String userInput = null;
   private G16.mguiPatched.gui_fields.GUI_Board board;
   private static volatile Random rand = null;

   public static Random rand() {
      if (rand == null) {
         Class var0 = GUI_BoardController.class;
         synchronized(GUI_BoardController.class) {
            if (rand == null) {
               rand = new Random();
            }
         }
      }

      return rand;
   }

   public GUI_BoardController(GUI_Field[] fields) {
      this.board = new GUI_Board(fields);
   }

   public GUI_BoardController(GUI_Field[] fields, Color backGroundColor) {
      this.board = new GUI_Board(fields, backGroundColor);
   }

   public void showMessage(String msg) {
      this.board.clearInputPanel();
      CountDownLatch latch = new CountDownLatch(1);
      EnterButton okButton = new EnterButton("OK", () -> {
         this.board.clearInputPanel();
         latch.countDown();
      });
      this.board.getUserInput(msg, okButton.getJButton());
      okButton.focus();

      try {
         latch.await();
      } catch (InterruptedException var5) {
         var5.printStackTrace();
      }

   }

   public String getUserString(String msg, int minLength, int maxLength, boolean allowWhitespace) {
      StringInput input = new StringInput(this.board, msg, minLength, maxLength, allowWhitespace);
      return input.getResult();
   }

   public int getUserInteger(String msg, int min, int max) {
      this.board.clearInputPanel();
      IntegerInput input = new IntegerInput(this.board, msg, min, max);
      return input.getResult();
   }

   public String getUserButtonPressed(String msg, String... buttonTexts) {
      if (buttonTexts != null && buttonTexts.length >= 1) {
         CountDownLatch latch = new CountDownLatch(1);
         JButton[] buttons = new JButton[buttonTexts.length];

         for(int i = 0; i < buttonTexts.length; ++i) {
            String string = buttonTexts[i];
            if ("".equals(string)) {
               return null;
            }

            EnterButton button = new EnterButton(string, () -> {
               this.userInput = string;
               this.board.clearInputPanel();
               latch.countDown();
            });
            buttons[i] = button.getJButton();
            button.focus();
         }

         this.board.getUserInput(msg, buttons);

         try {
            latch.await();
            return this.userInput;
         } catch (InterruptedException var8) {
            var8.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   public String getUserSelection(String msg, String... options) {
      if (options != null && options.length >= 1) {
         CountDownLatch latch = new CountDownLatch(1);

         for(int i = 0; i < options.length; ++i) {
            String string = options[i];
            if ("".equals(string)) {
               return null;
            }
         }

         JComboBox<String> dropdown = new JComboBox(options);
         EnterButton okButton = new EnterButton("OK", () -> {
            this.userInput = dropdown.getSelectedItem().toString();
            this.board.clearInputPanel();
            latch.countDown();
         });
         this.board.getUserInput(msg, dropdown, okButton.getJButton());

         try {
            latch.await();
            return this.userInput;
         } catch (InterruptedException var7) {
            var7.printStackTrace();
            return null;
         }
      } else {
         return null;
      }
   }

   public boolean addPlayer(GUI_Player player) {
      return this.board.addPlayer(player);
   }

   public void setDice(int faceValue1, int faceValue2) {
      int rotation1 = rand().nextInt(360);
      int rotation2 = rand().nextInt(360);
      this.setDice(faceValue1, rotation1, faceValue2, rotation2);
   }

   public void setDice(int faceValue1, int x1, int y1, int faceValue2, int x2, int y2) {
      int rotation1 = rand().nextInt(360);
      int rotation2 = rand().nextInt(360);
      this.setDice(faceValue1, rotation1, x1, y1, faceValue2, rotation2, x2, y2);
   }

   public void setDice(int faceValue1, int rotation1, int faceValue2, int rotation2) {
      List<Point> dicePlaces = new ArrayList();
      int x;
      int y;
      if (this.getFields().length == 40) {
         for(x = 1; x < 10; ++x) {
            for(y = 1; y < 10; ++y) {
               if ((x < 4 || x > 6 || y < 4 || y > 6) && (x <= 6 || y <= 9 - this.board.getPlayerCount())) {
                  dicePlaces.add(new Point(x, y));
               }
            }
         }
      } else if (this.getFields().length < 40 && this.getFields().length >= 32) {
         for(x = 2; x < 4; ++x) {
            for(y = 2; y < 9; ++y) {
               dicePlaces.add(new Point(x, y));
            }
         }
      } else {
         for(x = 1; x < 9; ++x) {
            for(y = 0; y < 2; ++y) {
               dicePlaces.add(new Point(x, y));
            }
         }
      }

      int index1;
      x = (int)(Math.random() * (double)dicePlaces.size());
      Point dice1Position = (Point)dicePlaces.remove(x);
      int MAX_DISTANCE;
      ArrayList<Point> toBeRemoved = new ArrayList();
      Iterator var10 = dicePlaces.iterator();

      while(true) {
         Point p;
         do {
            if (!var10.hasNext()) {
               dicePlaces.removeAll(toBeRemoved);
               int index2;
               index2 = (int)(Math.random() * (double)dicePlaces.size());
               p = (Point)dicePlaces.get(index2);
               this.setDice(faceValue1, rotation1, dice1Position.x, dice1Position.y, faceValue2, rotation2, p.x, p.y);
               return;
            }

            p = (Point)var10.next();
         } while(p.x >= dice1Position.x - 2 && p.x <= dice1Position.x + 2 && p.y >= dice1Position.y - 2 && p.y <= dice1Position.y + 2);

         toBeRemoved.add(p);
      }
   }

   public void setDice(int faceValue1, int rotation1, int x1, int y1, int faceValue2, int rotation2, int x2, int y2) {
      boolean faceValuesAreValid = this.areFaceValuesValid(faceValue1, faceValue2);
      boolean rotationsAreValid = this.areRotationsValid(rotation1, rotation2);
      boolean positionsAreValid = this.arePositionsValid(x1, y1, x2, y2);
      if (faceValuesAreValid && rotationsAreValid && positionsAreValid) {
         this.board.setDice(x1, y1, faceValue1, rotation1, x2, y2, faceValue2, rotation2);
      }

   }

   private boolean arePositionsValid(int x1, int y1, int x2, int y2) {
      return x1 >= 0 && x1 <= 10 && y1 >= 0 && y1 <= 10 && x2 >= 0 && x2 <= 10 && y2 >= 0 && y2 <= 10;
   }

   private boolean areRotationsValid(int rotation1, int rotation2) {
      return rotation1 >= 0 && rotation1 <= 359 && rotation2 >= 0 && rotation2 <= 359;
   }

   private boolean areFaceValuesValid(int faceValue1, int faceValue2) {
      return faceValue1 >= 1 && faceValue1 <= 6 && faceValue2 >= 1 && faceValue2 <= 6;
   }

   public void displayChanceCard(String txt) {
      gui_codebehind.GUI_Center.getInstance().setChanceCard(txt);
      gui_codebehind.GUI_Center.getInstance().displayChanceCard();
   }

   public void setChanceCard(String txt) {
      gui_codebehind.GUI_Center.getInstance().setChanceCard(txt);
   }

   public void displayChanceCard() {
      GUI_Center.getInstance().displayChanceCard();
   }

   public GUI_Field[] getFields() {
      return this.board.getFields();
   }

   public void setDie(int faceValue) {
      int rotation1 = rand().nextInt(360);
      int x = rand().nextInt(9);
      this.setDice(faceValue, rotation1, x, 9, faceValue, rotation1, x, 9);
   }

   public void close() {
      this.board.dispose();
   }
}
