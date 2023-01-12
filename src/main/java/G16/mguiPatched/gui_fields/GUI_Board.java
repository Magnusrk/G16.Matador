package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_codebehind.JLabelRotatable;
import G16.mguiPatched.gui_codebehind.Observer;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_fields.FieldMouseListener;
import G16.mguiPatched.gui_fields.GUI_Car;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.GroupLayout.Alignment;
import org.jetbrains.annotations.NotNull;

public final class GUI_Board extends JFrame implements Observer {
   private static final long serialVersionUID = -2551372048143397506L;
   public static final String FONT = "Tahoma";
   public static final int FONTSIZE = 10;
   public static final Color BASECOLOR = new Color(51, 204, 0);
   public static final int MAX_PLAYER_COUNT = 6;
   private SwingComponentFactory factory;
   public G16.mguiPatched.gui_fields.GUI_Player[] playerList;
   private JLayeredPane base;
   private JLayeredPane[][] carPanes;
   private JLabelRotatable[][] diceLabels;
   private JLabel[] playerLabels;
   private JLabel[] iconLabels;
   private JPanel inputPanel;
   private JTextArea messageArea;
   private ImageIcon[] diceIcons;
   private G16.mguiPatched.gui_fields.GUI_Field[] fields;
   private int die1x;
   private int die1y;
   private int die2x;
   private int die2y;
   public static Point[] points;
   public static int nextPoint = 0;

   private static void generateSquareBoard(int sideLength) {
      int offSet = (10 - sideLength) / 2;
      points = new Point[sideLength * 4];
      int i = 0;

      int y;
      for(y = sideLength; y > 0; --y) {
         points[i++] = new Point(y + offSet, sideLength + offSet);
      }

      for(y = sideLength; y > 0; --y) {
         points[i++] = new Point(0 + offSet, y + offSet);
      }

      for(y = 0; y < sideLength; ++y) {
         points[i++] = new Point(y + offSet, 0 + offSet);
      }

      for(y = 0; y < sideLength; ++y) {
         points[i++] = new Point(sideLength + offSet, y + offSet);
      }

   }

   public GUI_Board(G16.mguiPatched.gui_fields.GUI_Field[] fields) {
      this(fields, BASECOLOR);
   }

   public GUI_Board(G16.mguiPatched.gui_fields.GUI_Field[] fields, Color backGroundColor) {
      this.factory = new SwingComponentFactory();
      this.playerList = new G16.mguiPatched.gui_fields.GUI_Player[6];
      this.carPanes = new JLayeredPane[11][11];
      this.diceLabels = new JLabelRotatable[11][11];
      this.playerLabels = new JLabel[6];
      this.iconLabels = new JLabel[6];
      this.inputPanel = new JPanel();
      this.messageArea = new JTextArea();
      this.diceIcons = new ImageIcon[6];
      this.fields = null;
      this.die1x = 1;
      this.die1y = 1;
      this.die2x = 1;
      this.die2y = 1;
      int sideLength = fields.length / 4;
      if (sideLength < 4) {
         sideLength = 4;
      }

      if (fields.length % 4 != 0) {
         ++sideLength;
      }

      generateSquareBoard(sideLength);
      this.fields = fields;
      nextPoint = 0;
      this.setTitle(Attrs.getString("GUI_Board.Title"));
      this.setDefaultCloseOperation(3);
      G16.mguiPatched.gui_fields.GUI_Field[] var4 = fields;
      int var5 = fields.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         G16.mguiPatched.gui_fields.GUI_Field field = var4[var6];
         if (field != null) {
            field.addMouseListener(new FieldMouseListener(field, this.playerList));
         }
      }

      this.makeDice();
      this.makeBase(backGroundColor);
      this.makeBackGroundPanels(sideLength, backGroundColor);
      this.makeDiceLabels();
      this.makePlayerAreas();
      this.makeCenter();
      this.makeFieldPanels();
      this.makeCarPanes();
      this.makeInputPanel(sideLength);
      this.setResizable(false);
      this.makeAutogeneratedCrap();
      this.playerList = new G16.mguiPatched.gui_fields.GUI_Player[6];
      this.setVisible(true);
   }

   private void makeDice() {
      try {
         String path = Attrs.getImagePath("GUI_Board.Dice");
         BufferedImage image = ImageIO.read(this.getClass().getResource(path));

         for(int value = 0; value < 6; ++value) {
            int x = 0;
            int y = 55 * value;
            this.diceIcons[value] = new ImageIcon(image.getSubimage(x, y, 54, 54));
         }
      } catch (IOException var6) {
         var6.printStackTrace();
      }

   }

   private void makeFieldPanels() {
      int i = 0;
      G16.mguiPatched.gui_fields.GUI_Field[] var2 = this.fields;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Field f = var2[var4];
         if (f != null) {
            Point point = points[i];
            JLayeredPane panel = f.getPanel();
            this.base.add(panel, this.factory.createGridBagConstraints(point.x, point.y));
         }

         ++i;
      }

   }

   private void makeInputPanel(int sideLength) {
      this.inputPanel.setBackground(new Color(0, 128, 0));
      this.inputPanel.setOpaque(false);
      this.inputPanel = (JPanel)this.factory.setSize(this.inputPanel, 557, 179);
      this.inputPanel.setLayout(new FlowLayout());
      this.messageArea.setWrapStyleWord(true);
      this.messageArea.setLineWrap(true);
      this.messageArea.setSize(557, 10);
      this.messageArea.setOpaque(false);
      this.messageArea.setEditable(false);
      this.messageArea.setFocusable(false);
      this.inputPanel.add(this.messageArea);
      this.base.setLayer(this.inputPanel, 4);
      int labelOffset = 0;
      if (sideLength == 10) {
         labelOffset = 1;
      }

      this.base.add(this.inputPanel, this.factory.createGridBagConstraints(labelOffset, labelOffset, 9, 3));
   }

   public void getUserInput(String message, Component... components) {
      this.clearInputPanel();
      this.messageArea.setText(message);
      Component[] var3 = components;
      int var4 = components.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Component c = var3[var5];
         this.inputPanel.add(c);
      }

      this.inputPanel.validate();
      this.inputPanel.repaint();
   }

   public void clearInputPanel() {
      this.messageArea.setText("");
      this.inputPanel.removeAll();
      this.inputPanel.add(this.messageArea);
      this.inputPanel.validate();
      this.inputPanel.repaint();
   }

   private void makeCarPanes() {
      int fieldNo = 0;
      G16.mguiPatched.gui_fields.GUI_Field[] var2 = this.fields;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Field f = var2[var4];
         if (f != null) {
            Point point = points[fieldNo];
            int x = point.x;
            int y = point.y;
            JLayeredPane layered = new JLayeredPane();
            this.factory.setSize(layered, 63, 63);
            this.carPanes[x][y] = layered;
            layered.setOpaque(false);
            JLabel[] cars = new JLabel[6];

            for(int i = 0; i < 6; ++i) {
               JLabel label = new JLabel();
               cars[i] = label;
               label.setOpaque(false);
               this.factory.setSize(label, 63, 63);
               label.setBounds(3 * i + 3, 6 * i + 1, 41, 22);
               layered.setLayer(label, i + 5);
               label.setVisible(false);
               layered.add(label);
            }

            f.setCarIcons(cars);
            this.base.setLayer(layered, 1);
            this.base.add(layered, this.factory.createGridBagConstraints(x, y));
         }

         ++fieldNo;
      }

   }

   private void makeCenter() {
      this.base.setLayer(GUI_Center.getInstance().getCenterPanel(), 1);
      this.base.add(GUI_Center.getInstance().getCenterPanel(), this.factory.createGridBagConstraints(4, 4, 3, 3));
   }

   private void makeBase(Color backGroundColor) {
      this.base = new JLayeredPane();
      this.factory.setSize(this.base, 693, 693);
      this.base.setLayout(new GridBagLayout());
      this.base.setBackground(backGroundColor);
      this.base.setOpaque(true);
   }

   private void makeBackGroundPanels(int sidelength, Color backGroundColor) {
      int offSet = (10 - sidelength) / 2;

      for(int x = 1; x < sidelength; ++x) {
         for(int y = 1; y < sidelength; ++y) {
            JPanel panel = new JPanel();
            panel.setBackground(backGroundColor);
            this.factory.setSize(panel, 63, 63);
            this.base.setLayer(panel, 0);
            this.base.add(panel, this.factory.createGridBagConstraints(x + offSet, y + offSet));
         }
      }

   }

   private void makeDiceLabels() {
      for(int x = 0; x < 11; ++x) {
         for(int y = 0; y < 11; ++y) {
            JLabelRotatable label = new JLabelRotatable();
            this.diceLabels[x][y] = label;
            label.setOpaque(false);
            this.factory.setSize(label, 63, 63);
            this.base.setLayer(label, 3);
            this.base.add(label, this.factory.createGridBagConstraints(x, y), 0);
         }
      }

   }

   private void makePlayerAreas() {
      int x = 7;
      int y = 9;
      int nameLabelSize = 2;
      if (this.fields.length <= 32) {
         y = 8;
         nameLabelSize = 1;
      }

      if (this.fields.length <= 28) {
         x = 0;
         nameLabelSize = 1;
      }

      for(int i = 0; i < 6; ++i) {
         int ycalc = y - i;
         JLabel iconLabel = new JLabel();
         this.factory.setSize(iconLabel, 63, 63);
         this.base.setLayer(iconLabel, 1);
         this.base.add(iconLabel, this.factory.createGridBagConstraints(x, ycalc));
         this.iconLabels[i] = iconLabel;
         JLabel playerLabel = new JLabel();
         this.factory.setSize(playerLabel, nameLabelSize * 63, 63);
         this.base.setLayer(playerLabel, 1);
         this.base.add(playerLabel, this.factory.createGridBagConstraints(x + 1, ycalc, nameLabelSize, 1));
         this.playerLabels[i] = playerLabel;
      }

   }

   public G16.mguiPatched.gui_fields.GUI_Field[] getFields() {
      return this.fields;
   }

   public void setDice(int x1, int y1, int facevalue1, int rotation1, int x2, int y2, int facevalue2, int rotation2) {
      this.diceLabels[this.die1x][this.die1y].setIcon((Icon)null);
      this.diceLabels[this.die2x][this.die2y].setIcon((Icon)null);
      this.die1x = x1;
      this.die1y = y1;
      this.die2x = x2;
      this.die2y = y2;
      this.diceLabels[x1][y1].setRotation(rotation1);
      this.diceLabels[x1][y1].setHorizontalAlignment(0);
      this.diceLabels[x1][y1].setVerticalAlignment(0);
      this.diceLabels[x1][y1].setIcon(this.diceIcons[facevalue1 - 1]);
      this.diceLabels[x2][y2].setRotation(rotation2);
      this.diceLabels[x2][y2].setHorizontalAlignment(0);
      this.diceLabels[x2][y2].setVerticalAlignment(0);
      this.diceLabels[x2][y2].setIcon(this.diceIcons[facevalue2 - 1]);
   }

   public boolean addPlayer(G16.mguiPatched.gui_fields.GUI_Player player) {
      if (this.playerList[5] != null) {
         return false;
      } else {
         int i;
         for(i = 0; i < 6 && this.playerList[i] != null; ++i) {
            if (this.playerList[i].getName().equals(player.getName())) {
               return false;
            }
         }

         player.setNumber(i);
         player.addObserver(this);
         player.setValidator((name) -> {
            if (name != null && !name.isEmpty()) {
               G16.mguiPatched.gui_fields.GUI_Player[] var2 = this.playerList;
               int var3 = var2.length;

               for(int var4 = 0; var4 < var3; ++var4) {
                  G16.mguiPatched.gui_fields.GUI_Player p = var2[var4];
                  if (p != null && name.equals(p.getName())) {
                     return false;
                  }
               }

               return true;
            } else {
               return false;
            }
         });
         player.getCar().addObserver(this);
         player.getCar().addPositionChangedListener(this::carPositionChanged);
         this.playerList[i] = player;
         this.updatePlayers();
         return true;
      }
   }

   public void updatePlayers() {
      int position = 0;
      G16.mguiPatched.gui_fields.GUI_Player[] var2 = this.playerList;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Player p = var2[var4];
         if (p == null) {
            break;
         }

         Icon icon = new ImageIcon(p.getImage());
         this.iconLabels[position].setIcon(icon);
         this.playerLabels[position].setText("<html>" + p.getName() + "<br>" + p.getBalance());
         ++position;
      }

   }

   public G16.mguiPatched.gui_fields.GUI_Player getPlayer(String name) {
      G16.mguiPatched.gui_fields.GUI_Player[] var2 = this.playerList;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Player p = var2[var4];
         if (p != null && name.equalsIgnoreCase(p.getName())) {
            return p;
         }
      }

      return null;
   }

   public int getPlayerCount() {
      int count = 0;
      G16.mguiPatched.gui_fields.GUI_Player[] var2 = this.playerList;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Player p = var2[var4];
         if (p == null) {
            break;
         }

         ++count;
      }

      return count;
   }

   private void makeAutogeneratedCrap() {
      GroupLayout layout = new GroupLayout(this.getContentPane());
      this.getContentPane().setLayout(layout);
      layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.base, -2, -1, -2));
      layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addComponent(this.base, -2, -1, -2));
      this.pack();
   }

   public void onUpdate() {
      this.updatePlayers();
   }

   private void carPositionChanged(G16.mguiPatched.gui_fields.GUI_Car car, G16.mguiPatched.gui_fields.GUI_Field oldPosition, G16.mguiPatched.gui_fields.GUI_Field newPosition) {
      if (newPosition != null && !this.hasField(newPosition)) {
         throw new IllegalArgumentException("Car's old position is not a field added to the GUI");
      } else {
         G16.mguiPatched.gui_fields.GUI_Player player = this.getCarOwner(car);
         if (player == null) {
            throw new NullPointerException("Player was null and it was not expected - contact developers!");
         } else {
            if (oldPosition != null) {
               oldPosition.drawCar(player, false);
            }

            if (newPosition != null) {
               newPosition.drawCar(player, true);
            }

         }
      }
   }

   public boolean hasField(@NotNull G16.mguiPatched.gui_fields.GUI_Field field) {
      G16.mguiPatched.gui_fields.GUI_Field[] var2 = this.fields;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         G16.mguiPatched.gui_fields.GUI_Field match = var2[var4];
         if (match.equals(field)) {
            return true;
         }
      }

      return false;
   }

   public G16.mguiPatched.gui_fields.GUI_Player getCarOwner(G16.mguiPatched.gui_fields.GUI_Car car) {
      G16.mguiPatched.gui_fields.GUI_Player[] var2 = this.playerList;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         GUI_Player player = var2[var4];
         if (player.getCar().equals(car)) {
            return player;
         }
      }

      return null;
   }
}
