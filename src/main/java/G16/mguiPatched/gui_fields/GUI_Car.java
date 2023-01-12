package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.Observable;
import G16.mguiPatched.gui_codebehind.SwingComponentFactory;
import G16.mguiPatched.gui_resources.Attrs;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class GUI_Car extends Observable {
   private Color primaryColor;
   private Color secondaryColor;
   private Type type;
   private Pattern pattern;
   private BufferedImage image;
   private GUI_Field position;
   private ArrayList<PositionChangedListener> positionChangedListeners;
   private static final int WIDTH = 40;
   private static final int HEIGHT = 21;
   private static final String PATH = Attrs.getImagePath("GUI_Car.Image");
   private static final int PRIMARYCOLORSTANDIN = -65536;
   private static final Color[] COLORS;
   private static final Map<Pattern, String> patternImages;

   public GUI_Car() {
      this((Color)null, (Color)null, GUI_Car.Type.CAR, GUI_Car.Pattern.FILL);
   }

   public GUI_Car(Color primaryColor, Color patternColor, Type type, Pattern pattern) {
      this.position = null;
      this.positionChangedListeners = new ArrayList();
      this.primaryColor = primaryColor;
      this.secondaryColor = patternColor;
      this.type = type;
      this.pattern = pattern;
      this.repaint();
   }

   private void repaint() {
      int X = this.type.x();
      int Y;
      if (this.primaryColor == null) {
         this.primaryColor = this.secondaryColor == null ? COLORS[(int)(Math.random() * 6.0)] : this.secondaryColor;
      }

      if (this.secondaryColor == null) {
         this.secondaryColor = this.primaryColor;
      }

      BufferedImage template = (new SwingComponentFactory()).createImage(PATH).getSubimage(X, 0, 40, 21);
      switch (this.pattern) {
         case FILL:
            this.image = this.paintFill(template, this.primaryColor);
            break;
         case HORIZONTAL_GRADIANT:
            this.image = this.paintHorizontalGradiant(template, this.primaryColor, this.secondaryColor);
            break;
         case DIAGONAL_DUAL_COLOR:
            this.image = this.paintDiagonalDualColor(template, this.primaryColor, this.secondaryColor);
            break;
         case HORIZONTAL_DUAL_COLOR:
            this.image = this.paintHorizontalDualColor(template, this.primaryColor, this.secondaryColor, this.type);
            break;
         case HORIZONTAL_LINE:
            this.image = this.paintHorizontalLine(template, this.primaryColor, this.secondaryColor, this.type);
            break;
         case CHECKERED:
            this.image = this.paintCheckered(template, this.primaryColor, this.secondaryColor);
            break;
         case DOTTED:
            this.image = this.paintDotted(template, this.primaryColor, this.secondaryColor);
            break;
         case ZEBRA:
            this.image = this.paintZebra(template, this.primaryColor, this.secondaryColor);
            break;
         default:
            throw new RuntimeException(Attrs.getString("Error.BadArgument.Car.pattern"));
      }

   }

   protected BufferedImage getImage() {
      return this.image;
   }

   public Color getPrimaryColor() {
      return this.primaryColor;
   }

   public Color getSecondaryColor() {
      return this.secondaryColor;
   }

   public void setPrimaryColor(Color color) {
      this.primaryColor = color;
      this.repaint();
      this.notifyObservers();
   }

   public void setSecondaryColor(Color color) {
      this.secondaryColor = color;
      this.repaint();
      this.notifyObservers();
   }

   private BufferedImage paintFill(BufferedImage img, Color c1) {
      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               img.setRGB(x, y, c1.getRGB());
            }
         }
      }

      return img;
   }

   private BufferedImage paintHorizontalGradiant(BufferedImage img, Color c1, Color c2) {
      int r1 = c1.getRed();
      int r2 = c2.getRed();
      int g1 = c1.getGreen();
      int g2 = c2.getGreen();
      int b1 = c1.getBlue();
      int b2 = c2.getBlue();
      int min = img.getWidth();
      int max = 0;

      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               if (x < min) {
                  min = x;
               }

               if (x > max) {
                  max = x;
               }
            }
         }
      }

      double width = (double)(max - min);

      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               double p = (double)(x - min) / width;
               int r = (int)(p * (double)(r2 - r1) + (double)r1);
               int g = (int)(p * (double)(g2 - g1) + (double)g1);
               int b = (int)(p * (double)(b2 - b1) + (double)b1);
               int rgb = -16777216 + (r << 16) + (g << 8) + b;
               img.setRGB(x, y, rgb);
            }
         }
      }

      return img;
   }

   private BufferedImage paintDiagonalDualColor(BufferedImage img, Color c1, Color c2) {
      String path = (String)patternImages.get(GUI_Car.Pattern.DIAGONAL_DUAL_COLOR);
      BufferedImage patternImg = (new SwingComponentFactory()).createImage(path);
      return this.paintPattern(patternImg, img, c1, c2);
   }

   private BufferedImage paintHorizontalDualColor(BufferedImage img, Color c1, Color c2, Type t) {
      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               int y1 = t.h();
               if (y < y1) {
                  img.setRGB(x, y, c1.getRGB());
               } else {
                  img.setRGB(x, y, c2.getRGB());
               }
            }
         }
      }

      return img;
   }

   private BufferedImage paintHorizontalLine(BufferedImage img, Color c1, Color c2, Type t) {
      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               int y1 = t.h();
               if (y != y1 && y != y1 + 1) {
                  img.setRGB(x, y, c1.getRGB());
               } else {
                  img.setRGB(x, y, c2.getRGB());
               }
            }
         }
      }

      return img;
   }

   private BufferedImage paintCheckered(BufferedImage img, Color c1, Color c2) {
      String path = (String)patternImages.get(GUI_Car.Pattern.CHECKERED);
      BufferedImage patternImg = (new SwingComponentFactory()).createImage(path);
      return this.paintPattern(patternImg, img, c1, c2);
   }

   private BufferedImage paintDotted(BufferedImage img, Color c1, Color c2) {
      String path = (String)patternImages.get(GUI_Car.Pattern.DOTTED);
      BufferedImage patternImg = (new SwingComponentFactory()).createImage(path);
      return this.paintPattern(patternImg, img, c1, c2);
   }

   private BufferedImage paintZebra(BufferedImage img, Color c1, Color c2) {
      String path = (String)patternImages.get(GUI_Car.Pattern.ZEBRA);
      BufferedImage patternImg = (new SwingComponentFactory()).createImage(path);
      return this.paintPattern(patternImg, img, c1, c2);
   }

   private BufferedImage paintPattern(BufferedImage patternImg, BufferedImage img, Color c1, Color c2) {
      for(int y = 0; y < img.getHeight(); ++y) {
         for(int x = 0; x < img.getWidth(); ++x) {
            if (img.getRGB(x, y) == -65536) {
               if (patternImg.getRGB(x, y) == -65536) {
                  img.setRGB(x, y, c1.getRGB());
               } else {
                  img.setRGB(x, y, c2.getRGB());
               }
            }
         }
      }

      return img;
   }

   public void setPosition(@Nullable GUI_Field newPosition) {
      if (!Objects.equals(newPosition, this.position)) {
         GUI_Field oldPosition = this.position;
         this.position = newPosition;
         Iterator var3 = this.positionChangedListeners.iterator();

         while(var3.hasNext()) {
            PositionChangedListener listener = (PositionChangedListener)var3.next();
            listener.positionChanged(this, oldPosition, newPosition);
         }

      }
   }

   public GUI_Field getPosition() {
      return this.position;
   }

   public void addPositionChangedListener(@NotNull PositionChangedListener listener) {
      this.positionChangedListeners.add(listener);
   }

   public String toString() {
      return "GUI_Car [primaryColor=" + this.primaryColor + ", secondaryColor=" + this.secondaryColor + ", type=" + this.type + ", pattern=" + this.pattern + ", image=" + this.image + "]";
   }

   static {
      COLORS = new Color[]{Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.BLACK, Color.WHITE};
      patternImages = new HashMap();
      patternImages.put(GUI_Car.Pattern.DIAGONAL_DUAL_COLOR, Attrs.getImagePath("GUI_Car.Image.Pattern.Diagonal_Dual_Color"));
      patternImages.put(GUI_Car.Pattern.DOTTED, Attrs.getImagePath("GUI_Car.Image.Pattern.Dotted"));
      patternImages.put(GUI_Car.Pattern.CHECKERED, Attrs.getImagePath("GUI_Car.Image.Pattern.Checkered"));
      patternImages.put(GUI_Car.Pattern.ZEBRA, Attrs.getImagePath("GUI_Car.Image.Pattern.Zebra"));
   }

   public static enum Type {
      CAR(0, 15),
      TRACTOR(1, 11),
      RACECAR(2, 13),
      UFO(3, 10);

      private final int x;
      private final int h;
      private final int width = 41;
      public static final int size = values().length;

      private Type(int no, int h) {
         Objects.requireNonNull(this);
         this.x = no * 41;
         this.h = h;
      }

      public int x() {
         return this.x;
      }

      public int h() {
         return this.h;
      }

      public static Type getTypeFromString(String typeString) {
         Type[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Type type = var1[var3];
            if (type.toString().equals(typeString)) {
               return type;
            }
         }

         System.err.println("No such Type - choosing default : CAR");
         return CAR;
      }
   }

   public static enum Pattern {
      FILL,
      HORIZONTAL_GRADIANT,
      DIAGONAL_DUAL_COLOR,
      HORIZONTAL_DUAL_COLOR,
      HORIZONTAL_LINE,
      CHECKERED,
      DOTTED,
      ZEBRA;

      private Pattern() {
      }

      public static Pattern getPatternFromString(String patternString) {
         Pattern[] var1 = values();
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            Pattern pattern = var1[var3];
            if (pattern.toString().equals(patternString)) {
               return pattern;
            }
         }

         System.err.println("No such Pattern - choosing default: FILL");
         return FILL;
      }
   }

   public interface PositionChangedListener {
      void positionChanged(GUI_Car var1, GUI_Field var2, GUI_Field var3);
   }
}
