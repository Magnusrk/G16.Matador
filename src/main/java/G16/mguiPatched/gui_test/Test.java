package G16.mguiPatched.gui_test;

import G16.mguiPatched.gui_fields.GUI_Car;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Ownable;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_fields.GUI_Street;
import G16.mguiPatched.gui_main.GUI;
import java.awt.Color;

public class Test {
   private GUI gui = new GUI();
   private GUI_Player arthur;
   private GUI_Player ford;
   private GUI_Player zaphod;
   private GUI_Player tricia;
   private GUI_Player marvin;
   private GUI_Player slart;
   private GUI_Player dt;

   public Test() {
   }

   public static void main(String[] args) {
      Test test = new Test();
      test.test();
   }

   private void test() {
      this.setDiceAngleAndCoordinates();
      this.testAddPlayer();
      this.testSetBalance();
      this.testSetDice();
      this.testInput();
      this.testSetNextChanceCardText();
      this.testSetCar();
      this.testSetOwner();
      this.testSetDiceAngleAndCoordinatesMultipleTimes();
      this.testSetDiceAllAngles();
      this.testRemoveCar();
      this.testRemoveOwner();
      this.testSetHouses();
      this.testRemoveHouses();
   }

   private void setDiceAngleAndCoordinates() {
      this.gui.setDice(6, 0, 1, 1, 6, 90, 2, 1);
   }

   private void testInput() {
      System.out.println("testInput:" + this.gui.getUserSelection("Vælg en grund", "Hvidovrevej", "Rødovrevej", "Peters vej", "Oskars vej") + "");
   }

   private void testAddPlayer() {
      this.arthur = new GUI_Player("Arthur Dent", 1000);
      this.gui.addPlayer(this.arthur);
      GUI_Car car1 = new GUI_Car(Color.MAGENTA, Color.BLUE, GUI_Car.Type.TRACTOR, GUI_Car.Pattern.DOTTED);
      this.ford = new GUI_Player("Ford Prefect", 1000, car1);
      this.gui.addPlayer(this.ford);
      GUI_Car car2 = new GUI_Car(Color.BLACK, Color.RED, GUI_Car.Type.UFO, GUI_Car.Pattern.ZEBRA);
      this.zaphod = new GUI_Player("Zaphod Beeblebrox", 100000, car2);
      this.gui.addPlayer(this.zaphod);
      GUI_Car car3 = new GUI_Car(Color.DARK_GRAY, Color.CYAN, GUI_Car.Type.RACECAR, GUI_Car.Pattern.HORIZONTAL_LINE);
      this.tricia = new GUI_Player("Tricia McMillan", 100000, car3);
      this.gui.addPlayer(this.tricia);
      GUI_Car car4 = new GUI_Car(new Color(160, 32, 240), Color.YELLOW, GUI_Car.Type.CAR, GUI_Car.Pattern.CHECKERED);
      this.marvin = new GUI_Player("Marvin", 1000, car4);
      this.gui.addPlayer(this.marvin);
      GUI_Car car5 = new GUI_Car(Color.BLACK, Color.WHITE, GUI_Car.Type.CAR, GUI_Car.Pattern.DOTTED);
      this.slart = new GUI_Player("Slartibartfast", 100000, car5);
      this.gui.addPlayer(this.slart);
      this.dt = new GUI_Player("Deep Thought", 100000);
      this.gui.addPlayer(this.dt);
   }

   private void testSetBalance() {
      this.ford.setBalance(100);
   }

   private void testSetDice() {
      this.gui.setDice(2, 3);
   }

   private void testSetDiceAllAngles() {
      for(int a = 0; a <= 360; ++a) {
         this.gui.setDice(5, a, 1, 1, 6, 359 - a, 2, 1);

         try {
            Thread.sleep(5L);
         } catch (InterruptedException var3) {
            var3.printStackTrace();
         }
      }

   }

   private void testSetDiceAngleAndCoordinatesMultipleTimes() {
      for(int y = -1; y <= 11; ++y) {
         for(int x = -1; x <= 11; ++x) {
            int d1 = (int)(Math.random() * 6.0 + 1.0);
            int a1 = (int)(Math.random() * 360.0);
            int d2 = (int)(Math.random() * 6.0 + 1.0);
            int a2 = (int)(Math.random() * 360.0);
            this.gui.setDice(d1, a1, 2, 2, d2, a2, x, y);

            try {
               Thread.sleep(25L);
            } catch (InterruptedException var8) {
               var8.printStackTrace();
            }
         }
      }

   }

   private void testSetNextChanceCardText() {
      this.gui.displayChanceCard("De har modtaget Bjørne Bandit - legatet og fængsles!");
   }

   private void testSetCar() {
      int i;
      for(i = 0; i < 10; ++i) {
         this.gui.getFields()[i].setCar(this.zaphod, true);
      }

      for(i = 10; i < 20; ++i) {
         this.gui.getFields()[i].setCar(this.tricia, true);
         this.gui.getFields()[i].setCar(this.ford, true);
      }

      for(i = 20; i < 30; ++i) {
         this.gui.getFields()[i].setCar(this.arthur, true);
         this.gui.getFields()[i].setCar(this.marvin, true);
         this.gui.getFields()[i].setCar(this.slart, true);
      }

      for(i = 30; i < 40; ++i) {
         this.gui.getFields()[i].setCar(this.tricia, true);
         this.gui.getFields()[i].setCar(this.zaphod, true);
         this.gui.getFields()[i].setCar(this.arthur, true);
         this.gui.getFields()[i].setCar(this.ford, true);
         this.gui.getFields()[i].setCar(this.marvin, true);
         this.gui.getFields()[i].setCar(this.slart, true);
      }

   }

   private void testRemoveCar() {
      GUI_Field[] var1 = this.gui.getFields();
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         GUI_Field f = var1[var3];
         f.removeAllCars();
      }

   }

   private void testSetOwner() {
      int i;
      GUI_Field f;
      GUI_Ownable o;
      for(i = 1; i <= 10; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder(this.ford.getPrimaryColor(), this.ford.getSecondaryColor());
         }
      }

      for(i = 11; i <= 20; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder(this.slart.getPrimaryColor(), this.slart.getSecondaryColor());
         }
      }

      for(i = 21; i <= 30; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder(this.arthur.getPrimaryColor(), this.arthur.getSecondaryColor());
         }
      }

   }

   private void testRemoveOwner() {
      int i;
      GUI_Field f;
      GUI_Ownable o;
      for(i = 1; i <= 5; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder((Color)null);
         }
      }

      for(i = 11; i <= 15; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder((Color)null);
         }
      }

      for(i = 21; i <= 25; ++i) {
         f = this.gui.getFields()[i];
         if (f instanceof GUI_Ownable) {
            o = (GUI_Ownable)f;
            o.setBorder((Color)null);
         }
      }

   }

   private void testSetHouses() {
      int i;
      GUI_Field f40;
      GUI_Street s;
      for(i = 0; i < 10; ++i) {
         f40 = this.gui.getFields()[i];
         if (f40 instanceof GUI_Street) {
            s = (GUI_Street)f40;
            s.setHouses(1);
         }
      }

      for(i = 10; i < 20; ++i) {
         f40 = this.gui.getFields()[i];
         if (f40 instanceof GUI_Street) {
            s = (GUI_Street)f40;
            s.setHouses(2);
         }
      }

      for(i = 20; i < 30; ++i) {
         f40 = this.gui.getFields()[i];
         if (f40 instanceof GUI_Street) {
            s = (GUI_Street)f40;
            s.setHouses(3);
         }
      }

      for(i = 30; i < 40; ++i) {
         f40 = this.gui.getFields()[i];
         if (f40 instanceof GUI_Street) {
            s = (GUI_Street)f40;
            s.setHouses(4);
         }
      }

      GUI_Field f38 = this.gui.getFields()[37];
      System.out.println(f38.getTitle());
      if (f38 instanceof GUI_Street) {
         GUI_Street a = (GUI_Street)f38;
         a.setHotel(true);
      }

      f40 = this.gui.getFields()[39];
      System.out.println(f40.getTitle());
      if (f40 instanceof GUI_Street) {
         s = (GUI_Street)f40;
         s.setHotel(true);
      }

   }

   private void testRemoveHouses() {
      try {
         Thread.sleep(3000L);
      } catch (InterruptedException var7) {
         var7.printStackTrace();
      }

      int i = 0;
      GUI_Field[] var2 = this.gui.getFields();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         GUI_Field f = var2[var4];
         if (i < 38 && i % 3 == 0 && f instanceof GUI_Street) {
            GUI_Street s = (GUI_Street)f;
            s.setHouses(0);
         }

         ++i;
      }

      GUI_Street s40 = (GUI_Street)this.gui.getFields()[39];
      s40.setHotel(false);
   }
}
