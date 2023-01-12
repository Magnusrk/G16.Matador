package G16.mguiPatched.gui_resources;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Attrs {
   private static final String BUNDLE_NAME = "gui_resources.attributes";
   private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle("gui_resources.attributes");

   private Attrs() {
   }

   public static String getString(String key, Object... args) {
      try {
         return String.format(RESOURCE_BUNDLE.getString(key), args);
      } catch (MissingResourceException var3) {
         throw new NullPointerException("Bad key [" + key + "]!");
      }
   }

   public static String getImagePath(String key) {
      try {
         String path = RESOURCE_BUNDLE.getString("Image.Path");
         String file = RESOURCE_BUNDLE.getString(key);
         return path + file;
      } catch (MissingResourceException var3) {
         throw new NullPointerException("Bad key [" + key + "]!");
      }
   }
}
