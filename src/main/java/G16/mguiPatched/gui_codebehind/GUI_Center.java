package G16.mguiPatched.gui_codebehind;

import G16.mguiPatched.gui_fields.GUI_Board;
import gui_resources.Attrs;
import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public final class GUI_Center {
   private static GUI_Center center;
   private static JPanel centerPanel;
   public static JLabel[] label = new JLabel[7];
   public static JLabel[] cars = new JLabel[6];
   public static String chanceCardText = "Prøv Lykken";
   private JLayeredPane playersPane = new JLayeredPane();
   private SwingComponentFactory factory = new SwingComponentFactory();

   private GUI_Center() {
      centerPanel = new JPanel();
      this.factory.setSize(centerPanel, 189, 189);
      centerPanel.addMouseListener(new CenterMouseListener(this));
      centerPanel.setLayout(new BoxLayout(centerPanel, 1));

      int i;
      for(i = 0; i < label.length; ++i) {
         label[i] = new JLabel();
         label[i].setOpaque(false);
         label[i].setVerticalAlignment(0);
         label[i].setAlignmentX(0.5F);
         centerPanel.add(label[i]);
      }

      for(i = 0; i < cars.length; ++i) {
         JLabel l = new JLabel();
         cars[i] = l;
         l.setOpaque(false);
         l.setBounds(25 * i + 3, 0, 41, 22);
         this.playersPane.setLayer(l, i + 6);
         l.setVisible(false);
         this.playersPane.add(l);
      }

      centerPanel.add(this.playersPane);
      this.displayDefault();
   }

   public static GUI_Center getInstance() {
      if (center == null) {
         center = new GUI_Center();
      }

      return center;
   }

   public void clearLabels() {
      JLabel[] var1 = label;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         JLabel l = var1[var3];
         l.setText("");
         l.setIcon((Icon)null);
      }

   }

   public void setBGColor(Color bgColor) {
      centerPanel.setBackground(bgColor);
   }

   public void setFGColor(Color fgColor) {
      centerPanel.setForeground(fgColor);
   }

   public void displayDefault() {
      centerPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
      this.clearLabels();
      String path = Attrs.getImagePath("GUI_Field.Image.Luck");
      label[0].setIcon(new ImageIcon(this.getClass().getResource(path)));
      label[0].setText(" ");
      label[0].setFont(new Font(label[1].getFont().getName(), 1, 20));
      label[0].setVerticalTextPosition(1);
      label[0].setHorizontalTextPosition(0);
      centerPanel.setBackground(GUI_Board.BASECOLOR);
      JLabel[] var2 = label;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         JLabel l = var2[var4];
         l.setBackground(GUI_Board.BASECOLOR);
         l.setForeground(Color.BLACK);
      }

      for(int i = 0; i < 6; ++i) {
         cars[i].setVisible(false);
      }

   }

   public void displayChanceCard() {
      this.clearLabels();
      centerPanel.setBackground(Color.WHITE);
      label[1].setText("<html><table><tr><td>" + chanceCardText);
   }

   public void setChanceCard(String txt) {
      chanceCardText = txt;
   }

   public JPanel getCenterPanel() {
      return centerPanel;
   }
}
