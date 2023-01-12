package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class GUI_Shipping extends GUI_Ownable {
   private static final int TOPHEIGHT = 31;
   private static final int TITLEHEIGHT = 16;
   private static final int SUBTEXTHEIGHT = 14;
   private JLabel topLabel;
   private ImageIcon icon;
   private SwingComponentFactory factory;
   private static int picCounter = 0;

   public GUI_Shipping() {
      this(PICTURE, TITLE, SUBTEXT, DESCRIPTION, RENT, BG_COLOR, FG_COLOR);
   }

   public GUI_Shipping(String picture, String title, String subText, String description, String rent, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description, rent);
      this.factory = new SwingComponentFactory();
      if ("default".equalsIgnoreCase(picture)) {
         int p = picCounter++ % 4 + 1;
         String path = Attrs.getImagePath(String.format("GUI_Field.Image.Ferry%d", p));
         this.icon = this.factory.createIcon(path);
      } else {
         try {
            this.icon = new ImageIcon(picture);
         } catch (Exception var10) {
            var10.printStackTrace();
            System.out.println(Attrs.getString("Error.BadArgument.ImagePath", picture));
         }
      }

      this.topLabel = this.makeTopLabel();
      super.titleLabel = this.makeRoadNameLabel(this.title);
      super.subTextLabel = this.makeBottomLabel(this.subText);
      this.layered.add(this.topLabel, this.factory.createGridBagConstraints(0, 0));
      this.layered.add(this.titleLabel, this.factory.createGridBagConstraints(0, 1));
      this.layered.add(this.subTextLabel, this.factory.createGridBagConstraints(0, 2));
   }

   private JLabel makeTopLabel() {
      JLabel l = this.makeLabel(31);
      l.setIcon(this.icon);
      return l;
   }

   private JLabel makeRoadNameLabel(String titleShipping) {
      JLabel roadnameLabel = this.makeLabel(16);
      roadnameLabel.setText(titleShipping);
      return roadnameLabel;
   }

   private JLabel makeBottomLabel(String subTextShipping) {
      JLabel bottomLabel = this.makeLabel(14);
      bottomLabel.setText(subTextShipping);
      return bottomLabel;
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      GUI_Center.label[1].setIcon(this.icon);
      GUI_Center.label[2].setText("__________________________");
      GUI_Center.label[3].setText(this.description);
      GUI_Center.label[4].setText(this.subText);
      if (this.ownerName != null) {
         GUI_Center.label[5].setText(this.getOwnableLabel() + this.getOwnerName());
         GUI_Center.label[6].setText(this.getRentLabel() + this.getRent());
      }

      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Shipping [ownerName=" + this.ownerName + ", bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
