package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class GUI_Street extends GUI_Ownable {
   private static final int TITLEHEIGHT = 24;
   private static final int SUBTEXTHEIGHT = 10;
   private JLabel houseLabel;
   private SwingComponentFactory factory;

   public GUI_Street() {
      this(TITLE, SUBTEXT, DESCRIPTION, RENT, BG_COLOR, FG_COLOR);
   }

   public GUI_Street(String title, String subText, String description, String rent, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description, rent);
      this.factory = new SwingComponentFactory();
      title = title.replace("\n", "<BR>");
      super.subTextLabel = this.makeSubTextLabel();
      this.houseLabel = this.makeHouseLabel();
      this.titleLabel = this.makeLabel(24);
      this.titleLabel.setHorizontalTextPosition(0);
      this.titleLabel.setText(this.title);
      super.layered.add(this.titleLabel, this.factory.createGridBagConstraints(0, 0));
      super.layered.add(this.houseLabel, this.factory.createGridBagConstraints(0, 1));
      super.layered.add(super.subTextLabel, this.factory.createGridBagConstraints(0, 2));
      super.layered.setLayer(this.titleLabel, 1);
      super.layered.setLayer(super.subTextLabel, 1);
      super.layered.setLayer(this.houseLabel, 0);
   }

   public GUI_Street setTextColor(Color textColor) {
      this.titleLabel.setForeground(textColor);
      this.subTextLabel.setForeground(textColor);
      return this;
   }

   private JLabel makeSubTextLabel() {
      JLabel l = this.makeLabel(10);
      l.setHorizontalTextPosition(0);
      l.setText(super.subText);
      return l;
   }

   private JLabel makeHouseLabel() {
      JLabel l = this.makeLabel(24);
      l.setOpaque(false);
      return l;
   }

   public void setHouses(int houseCount) {
      if (houseCount >= 0 && houseCount <= 4) {
         ImageIcon icon;
         if (houseCount == 0) {
            icon = null;
         } else {
            String path = Attrs.getImagePath(String.format("GUI_Field.Image.House%d", houseCount));
            icon = this.factory.createIcon(path);
         }

         this.houseLabel.setIcon(icon);
      } else {
         throw new IllegalArgumentException(Attrs.getString("Error.BadArgument.houseCount", houseCount));
      }
   }

   public void setHotel(boolean hasHotel) {
      ImageIcon icon;
      if (hasHotel) {
         String path = Attrs.getImagePath("GUI_Field.Image.Hotel");
         icon = this.factory.createIcon(path);
      } else {
         icon = null;
      }

      this.houseLabel.setIcon(icon);
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      GUI_Center.label[1].setText("__________________________");
      GUI_Center.label[2].setText(this.description);
      GUI_Center.label[3].setText(this.subText);
      if (this.ownerName != null) {
         GUI_Center.label[4].setText(this.getOwnableLabel() + this.getOwnerName());
         GUI_Center.label[5].setText(this.getRentLabel() + this.getRent());
      }

      GUI_Center.label[6].setIcon(this.houseLabel.getIcon());
      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Street [ownerName=" + this.ownerName + ", bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
