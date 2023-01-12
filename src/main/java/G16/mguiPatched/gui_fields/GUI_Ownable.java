package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public abstract class GUI_Ownable extends GUI_Field {
   public String ownableLable;
   public String rentLable;
   private Color color1;
   private Color color2;
   protected String ownerName;
   private String rent;

   public GUI_Ownable(Color bgColor, Color fgColor, String title, String subText, String description, String leje) {
      super(bgColor, fgColor, title, subText, description);
      this.rent = leje;
      this.ownableLable = Attrs.getString("GUI_Field.Label.owns");
      this.rentLable = Attrs.getString("GUI_Field.Label.rent");
      if (this.color1 == null) {
         this.color1 = Color.BLACK;
      }

      if (this.color2 == null) {
         this.color2 = new Color(this.color1.getRed(), this.color1.getGreen(), this.color1.getBlue());
      }
      setBorder(Color.RED);

   }

   public void setBorder(Color color) {
      this.setBorder(color, color);
   }

   public void setBorder(Color color1, Color color2) {
      if (color1 == null) {
         color1 = Color.BLACK;
      }

      if (color2 == null) {
         color2 = new Color(color1.getRed(), color1.getGreen(), color1.getBlue());
      }

      this.color1 = color1;
      this.color2 = color2;
      SwingComponentFactory factory = new SwingComponentFactory();
      Border border = factory.createDashedBorder(2, 5, color1, color2);
      this.layered.setBorder(border);
   }

   public String getOwnerName() {
      return this.ownerName;
   }

   public void setOwnerName(String ownerName) {
      this.ownerName = ownerName;
   }

   public String getOwnableLabel() {
      return this.ownableLable;
   }

   public void setOwnableLabel(String text) {
      this.ownableLable = text;
   }

   public String getRentLabel() {
      return this.rentLable;
   }

   public void setRentLabel(String text) {
      this.rentLable = text;
   }

   public String getRent() {
      return this.rent;
   }

   public void setRent(String rent) {
      this.rent = rent;
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      Border border;
      if (this.ownerName != null) {
         SwingComponentFactory factory = new SwingComponentFactory();
         border = factory.createDashedBorder(3, 10, this.color1, this.color2);
         GUI_Center.getInstance().getCenterPanel().setBorder(border);
      } else {
         border = BorderFactory.createLineBorder(Color.BLACK, 3);
      }

      GUI_Center.getInstance().getCenterPanel().setBorder(border);
   }
}
