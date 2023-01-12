package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_fields.GUI_Board;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;

import java.awt.Color;

public class GUI_Empty extends GUI_Field {
   public GUI_Empty() {
      this(GUI_Board.BASECOLOR, Color.BLACK, "", "", "");
   }

   public GUI_Empty(Color bgColor, Color fgColor, String title, String subText, String description) {
      super(bgColor, fgColor, subText, description, (String)null);
   }

   protected void displayOnCenter(GUI_Player[] playerList) {
   }

   public String toString() {
      return "GUI_Empty [bgColor=" + this.bgColor + ", fgColor=" + this.fgColor + ", title=" + this.title + ", subText=" + this.subText + ", description=" + this.description + "]";
   }
}
