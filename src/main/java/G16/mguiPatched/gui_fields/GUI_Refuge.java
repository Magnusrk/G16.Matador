package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class GUI_Refuge extends GUI_Field {
   private static final int TOPHEIGHT = 47;
   private static final int SUBTEXTHEIGHT = 14;
   private ImageIcon icon;
   private JLabel topLabel;
   private SwingComponentFactory factory;

   public GUI_Refuge() {
      this(PICTURE, TITLE, SUBTEXT, DESCRIPTION, BG_COLOR, FG_COLOR);
   }

   public GUI_Refuge(String picture, String title, String subText, String description, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description);
      this.factory = new SwingComponentFactory();
      if ("default".equalsIgnoreCase(picture)) {
         String path = Attrs.getImagePath("GUI_Field.Image.Cones");
         this.icon = this.factory.createIcon(path);
      } else {
         try {
            this.icon = new ImageIcon(picture);
         } catch (Exception var8) {
            var8.printStackTrace();
            System.out.println(Attrs.getString("Error.BadArgument.ImagePath", picture));
         }
      }

      this.topLabel = this.makeTopLabel();
      this.subTextLabel = this.makeBottomLabel(this.subText);
      this.layered.add(this.topLabel, this.factory.createGridBagConstraints(0, 0));
      this.layered.add(this.subTextLabel, this.factory.createGridBagConstraints(0, 1));
   }

   private JLabel makeTopLabel() {
      JLabel l = this.makeLabel(47);
      l.setIcon(this.icon);
      return l;
   }

   private JLabel makeBottomLabel(String subTextRefuge) {
      JLabel bottomLabel = this.makeLabel(14);
      bottomLabel.setText(subTextRefuge);
      return bottomLabel;
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      GUI_Center.label[1].setText(this.title.replace("<html><center>", ""));
      GUI_Center.label[2].setIcon(this.icon);
      GUI_Center.label[3].setText("__________________________");
      GUI_Center.label[4].setText(this.description);
      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Refuge [bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
