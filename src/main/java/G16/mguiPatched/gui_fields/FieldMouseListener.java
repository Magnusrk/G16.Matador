package G16.mguiPatched.gui_fields;

import G16.mguiPatched.gui_codebehind.GUI_Center;
import G16.mguiPatched.gui_fields.GUI_Field;
import G16.mguiPatched.gui_fields.GUI_Player;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Timer;
import java.util.TimerTask;

public class FieldMouseListener implements MouseListener {
   private static final boolean PRINT_COUNTER = false;
   private G16.mguiPatched.gui_fields.GUI_Field field;
   private G16.mguiPatched.gui_fields.GUI_Player[] playerList;
   private boolean show = true;
   private static Timer timer = null;
   private static Timer counter = null;

   public FieldMouseListener(GUI_Field field, GUI_Player[] playerList) {
      this.field = field;
      this.playerList = playerList;
   }

   public void mouseEntered(MouseEvent e) {
   }

   public void mouseExited(MouseEvent e) {
   }

   public void mouseClicked(MouseEvent e) {
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
      if (this.show) {
         this.field.displayOnCenter(this.playerList);
         this.show = false;
         if (timer != null) {
            timer.cancel();
         }

         if (counter != null) {
            counter.cancel();
         }

         int delay = 3000;
         timer = new Timer();
         timer.schedule(new TimerTask() {
            public void run() {
               GUI_Center.getInstance().displayDefault();
               FieldMouseListener.this.show = true;
               if (FieldMouseListener.counter != null) {
                  FieldMouseListener.counter.cancel();
               }

            }
         }, (long)(delay + 5));
      } else {
         GUI_Center.getInstance().displayDefault();
         this.show = true;
         if (counter != null) {
            counter.cancel();
         }

         if (timer != null) {
            timer.cancel();
         }
      }

   }
}
