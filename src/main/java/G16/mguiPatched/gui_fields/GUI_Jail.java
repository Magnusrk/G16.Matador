package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public final class GUI_Jail extends GUI_Field {
   private static final int TOPHEIGHT = 47;
   private static final int SUBTEXTHEIGHT = 14;
   private ImageIcon icon;
   private JLabel topLabel;
   private SwingComponentFactory factory;
   private static int picCounter = 0;

   public GUI_Jail() {
      this(PICTURE, TITLE, SUBTEXT, DESCRIPTION, new Color(125, 125, 125), Color.BLACK);
   }

   public GUI_Jail(String picture, String title, String subText, String description, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description);
      this.factory = new SwingComponentFactory();
      if ("default".equalsIgnoreCase(picture)) {
         int p = picCounter++ % 2;
         String path1 = Attrs.getImagePath("GUI_Field.Image.GoToJail");
         String path2 = Attrs.getImagePath("GUI_Field.Image.Jail");
         this.icon = this.factory.createIcon(p > 0 ? path1 : path2);
      } else {
         try {
            this.icon = new ImageIcon(picture);
         } catch (Exception var10) {
            var10.printStackTrace();
            System.out.println("Bad Resource: " + picture);
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

   private JLabel makeBottomLabel(String subTextJail) {
      JLabel bottomLabel = this.makeLabel(14);
      bottomLabel.setText(subTextJail);
      return bottomLabel;
   }

   public String getBottomText() {
      return this.subText;
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      GUI_Center.label[1].setIcon(this.icon);
      GUI_Center.label[2].setText("__________________________");
      GUI_Center.label[3].setText(this.description);
      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Jail [bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
