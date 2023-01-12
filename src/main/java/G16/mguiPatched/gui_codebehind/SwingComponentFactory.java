package G16.mguiPatched.gui_codebehind;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.border.Border;

public class SwingComponentFactory {
   public SwingComponentFactory() {
   }

   private Dimension createDimension(int width, int height) {
      return new Dimension(width, height);
   }

   public GridBagConstraints createGridBagConstraints(int x, int y, int width, int height) {
      GridBagConstraints gridBagConstraints = new GridBagConstraints();
      gridBagConstraints.gridx = x;
      gridBagConstraints.gridy = y;
      gridBagConstraints.gridwidth = width;
      gridBagConstraints.gridheight = height;
      return gridBagConstraints;
   }

   public GridBagConstraints createGridBagConstraints(int x, int y) {
      return this.createGridBagConstraints(x, y, 1, 1);
   }

   public ImageIcon createIcon(String path) {
      return new ImageIcon(this.getClass().getResource(path));
   }

   public Component setSize(Component component, int width, int height) {
      component.setMaximumSize(this.createDimension(width, height));
      component.setMinimumSize(this.createDimension(width, height));
      component.setPreferredSize(this.createDimension(width, height));
      return component;
   }

   public BufferedImage createImage(String src) {
      BufferedImage image = null;

      try {
         image = ImageIO.read(this.getClass().getResource(src));
      } catch (IOException var4) {
         var4.printStackTrace();
      }

      return image;
   }

   public Border createDashedBorder(int thickness, int stroke, Color color1, Color color2) {
      int w = stroke * 2;
      int h = stroke * 2;
      BufferedImage i = new BufferedImage(w, h, 1);

      for(int y = 0; y < h; ++y) {
         for(int x = 0; x < w; ++x) {
            if (x < stroke && y < stroke) {
               i.setRGB(x, y, color1.getRGB());
            } else if (x >= stroke && y < stroke) {
               i.setRGB(x, y, color2.getRGB());
            } else if (x < stroke && y >= stroke) {
               i.setRGB(x, y, color2.getRGB());
            } else if (x >= stroke && y >= stroke) {
               i.setRGB(x, y, color1.getRGB());
            }
         }
      }

      return BorderFactory.createMatteBorder(thickness, thickness, thickness, thickness, new ImageIcon(i));
   }
}
