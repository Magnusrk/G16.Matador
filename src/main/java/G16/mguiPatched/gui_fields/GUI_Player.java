package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.Observable;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class GUI_Player extends Observable {
   private int number;
   private String name;
   private int balance;
   private GUI_Car car;
   private static int nextId = 0;
   private int id;
   public static final int ICON_WIDTH = 41;
   public static final int ICON_HEIGHT = 22;
   private IPlayerNameValidator validator;

   public GUI_Player(String name) {
      this(name, 1000, new GUI_Car());
   }

   public GUI_Player(String name, int balance) {
      this(name, balance, new GUI_Car());
   }

   public GUI_Player(String name, int balance, GUI_Car car) {
      this.number = -1;
      this.validator = null;
      this.name = name;
      this.balance = balance;
      this.car = car;
      this.id = nextId++;
   }

   public int getNumber() {
      return this.number;
   }

   public String getName() {
      return this.name;
   }

   public int getBalance() {
      return this.balance;
   }

   public Color getPrimaryColor() {
      return this.car.getPrimaryColor();
   }

   public Color getSecondaryColor() {
      return this.car.getSecondaryColor();
   }

   protected BufferedImage getImage() {
      return this.car.getImage();
   }

   public GUI_Car getCar() {
      return this.car;
   }

   protected int getId() {
      return this.id;
   }

   protected void setNumber(int number) {
      this.number = number;
   }

   public boolean setName(String name) {
      if (this.validator == null) {
         return false;
      } else if (!this.validator.checkName(name)) {
         System.err.println(Attrs.getString("Error.Conflict.PlayerName", name));
         return false;
      } else {
         this.name = name;
         this.notifyObservers();
         return true;
      }
   }

   public void setBalance(int balance) {
      this.balance = balance;
      this.notifyObservers();
   }

   public int hashCode() {
      int prime;
      int result = 1;
      result = 31 * result + (this.name == null ? 0 : this.name.hashCode());
      return result;
   }

   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (!(obj instanceof GUI_Player)) {
         return false;
      } else {
         GUI_Player other = (GUI_Player)obj;
         if (this.name == null) {
            if (other.name != null) {
               return false;
            }
         } else if (!this.name.equals(other.name)) {
            return false;
         }

         return true;
      }
   }

   protected void setValidator(IPlayerNameValidator validator) {
      this.validator = validator;
   }

   public String toString() {
      return "GUI_Player [number=" + this.number + ", name=" + this.name + ", balance=" + this.balance + ", car=" + this.car + "]";
   }

   public interface IPlayerNameValidator {
      boolean checkName(String var1);
   }
}
