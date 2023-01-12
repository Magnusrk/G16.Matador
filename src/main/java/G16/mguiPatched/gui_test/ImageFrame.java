package G16.mguiPatched.gui_test;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImageFrame {
   private String title = "Title";
   private int x = 0;
   private int y = 0;
   private int delay = 0;
   private boolean showSaveButton = true;
   private BufferedImage image;
   private JPanel imagePanel;

   public ImageFrame(BufferedImage image) {
      this.image = image;
   }

   public void display() {
      if (this.image == null) {
         this.image = this.createEmptyImage();
      }

      final JFrame frame = new JFrame();
      frame.setBounds(this.x, this.y, 0, 0);
      frame.setDefaultCloseOperation(3);
      frame.setTitle(this.title);
      frame.setResizable(false);
      JPanel base = new JPanel();
      base.setLayout(new BoxLayout(base, 1));
      frame.add(base);
      this.imagePanel = new JPanel();
      this.imagePanel.setAlignmentX(1.0F);
      JLabel imageLabel = new JLabel(new ImageIcon(this.image));
      imageLabel.setAlignmentX(1.0F);
      this.imagePanel.add(imageLabel);
      base.add(this.imagePanel);
      JPanel bottomPanel = new JPanel();
      bottomPanel.setLayout(new BoxLayout(bottomPanel, 0));
      bottomPanel.setAlignmentX(1.0F);
      JLabel dim = new JLabel(this.image.getWidth() + " x " + this.image.getHeight());
      dim.setAlignmentX(1.0F);
      bottomPanel.add(Box.createGlue());
      bottomPanel.add(dim);
      bottomPanel.add(Box.createGlue());
      if (this.showSaveButton) {
         JButton saveButton = new JButton("Save");
         saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               ImageFrame.this.savePic();
            }
         });
         saveButton.setAlignmentX(1.0F);
         bottomPanel.add(saveButton);
      }

      base.add(bottomPanel);
      frame.setVisible(true);
      frame.repaint();
      frame.validate();
      frame.pack();
      if (this.delay > 0) {
         (new Timer()).schedule(new TimerTask() {
            public void run() {
               frame.setVisible(false);
            }
         }, (long)(this.delay * 1000));
      }
   }

   void setImage(BufferedImage image) {
      this.image = image;
      this.imagePanel.removeAll();
      JLabel imageLabel = new JLabel(new ImageIcon(image));
      this.imagePanel.add(imageLabel, 0);
      this.imagePanel.validate();
      this.imagePanel.repaint();
   }

   void savePic() {
      Calendar instance = Calendar.getInstance();
      int sec = instance.get(13);
      int min = instance.get(12);
      int hour = instance.get(11);
      int day = instance.get(5);
      int month = instance.get(2) + 1;
      int year = instance.get(1);
      String timestamp = sec + "" + min + "." + hour + " " + day + "." + month + "." + year;
      String path = System.getProperty("user.home") + "/Desktop";
      File file = new File(path + "/image " + timestamp + ".jpg");

      try {
         ImageIO.write(this.image, "jpg", new FileOutputStream(file));
      } catch (FileNotFoundException var12) {
         System.err.println("Bad file! [ImageFrame.savePic(...)]");
      } catch (IOException var13) {
         System.err.println("IO error! [ImageFrame.savePic(...)]");
      }

   }

   public ImageFrame setTitle(String title) {
      this.title = title;
      return this;
   }

   public ImageFrame setTopLeftCorner(Point p) {
      this.x = p.x;
      this.y = p.y;
      return this;
   }

   public ImageFrame setX(int x) {
      this.x = x;
      return this;
   }

   public ImageFrame setY(int y) {
      this.y = y;
      return this;
   }

   public ImageFrame setDelay(int delay) {
      this.delay = delay;
      return this;
   }

   public ImageFrame showSaveButton(boolean visible) {
      this.showSaveButton = visible;
      return this;
   }

   private BufferedImage createEmptyImage() {
      BufferedImage img = new BufferedImage(400, 170, 1);
      Graphics2D g = (Graphics2D)img.getGraphics();
      Font font = new Font("Verdana", 1, 48);
      g.setFont(font);
      g.drawString("NO IMAGE", 60, 100);
      return img;
   }
}
