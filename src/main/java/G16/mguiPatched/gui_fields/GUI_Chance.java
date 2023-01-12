package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import javax.swing.JLabel;

public final class GUI_Chance extends GUI_Field {
   private static final int TITLEHEIGHT = 47;
   private static final int SUBTEXTHEIGHT = 14;
   private SwingComponentFactory factory;

   public GUI_Chance() {
      this("<html><b><font size=\"7\">" + Attrs.getString("GUI_Field.Label.Chance.Format"), Attrs.getString("GUI_Field.Label.Chance.Subtext"), DESCRIPTION, new Color(204, 204, 204), FG_COLOR);
   }

   public GUI_Chance(String title, String subText, String description, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description);
      this.factory = new SwingComponentFactory();
      this.titleLabel = this.makeStreetNameLabel();
      this.subTextLabel = this.makeBottomLabel();
      this.layered.add(this.titleLabel, this.factory.createGridBagConstraints(0, 0));
      this.layered.add(this.subTextLabel, this.factory.createGridBagConstraints(0, 1));
   }

   private JLabel makeStreetNameLabel() {
      JLabel roadnameLabel = this.makeLabel(47);
      roadnameLabel.setText("<html><b><font size=\"7\">" + super.title.substring(super.title.lastIndexOf(">") + 1) + "</b>");
      return roadnameLabel;
   }

   private JLabel makeBottomLabel() {
      JLabel bottomLabel = this.makeLabel(14);
      bottomLabel.setText(this.subText);
      return bottomLabel;
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      String path = Attrs.getImagePath("GUI_Field.Image.Luck");
      GUI_Center.label[1].setIcon(this.factory.createIcon(path));
      GUI_Center.label[2].setText("__________________________");
      GUI_Center.label[3].setText(this.description);
      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Chance [bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
