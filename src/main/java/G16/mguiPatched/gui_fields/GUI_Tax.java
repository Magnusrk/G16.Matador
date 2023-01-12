package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import java.awt.Color;
import javax.swing.JLabel;

public final class GUI_Tax extends GUI_Field {
   private static final int TITLEHEIGHT = 47;
   private static final int SUBTEXTHEIGHT = 14;
   private SwingComponentFactory factory;

   public GUI_Tax() {
      this(TITLE, SUBTEXT, DESCRIPTION, BG_COLOR, FG_COLOR);
   }

   public GUI_Tax(String title, String subText, String description, Color bgColor, Color fgColor) {
      super(bgColor, fgColor, title, subText, description);
      this.factory = new SwingComponentFactory();
      this.titleLabel = this.makeTitleLabel(this.title);
      this.subTextLabel = this.makeSubTextLabel(this.subText);
      this.layered.add(this.titleLabel, this.factory.createGridBagConstraints(0, 0));
      this.layered.add(this.subTextLabel, this.factory.createGridBagConstraints(0, 1));
   }

   private JLabel makeTitleLabel(String titleTax) {
      JLabel l = this.makeLabel(47);
      l.setText(titleTax);
      return l;
   }

   private JLabel makeSubTextLabel(String subTextTax) {
      JLabel l = this.makeLabel(14);
      l.setText(subTextTax);
      return l;
   }

   protected void displayOnCenter( GUI_Player[] playerList) {
      super.displayOnCenter(playerList);
      GUI_Center.label[1].setText("__________________________");
      GUI_Center.label[2].setText(this.description);
      super.displayCarOnCenter(playerList);
   }

   public String toString() {
      return "GUI_Tax [bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
