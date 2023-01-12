package G16.mguiPatched.gui_codebehind;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class CenterMouseListener implements MouseListener {
   private GUI_Center center;

   public CenterMouseListener(GUI_Center center) {
      this.center = center;
   }

   public void mouseClicked(MouseEvent e) {
      GUI_Center.getInstance().displayChanceCard();
   }

   public void mouseExited(MouseEvent e) {
      this.center.displayDefault();
   }

   public void mousePressed(MouseEvent e) {
   }

   public void mouseReleased(MouseEvent e) {
   }

   public void mouseEntered(MouseEvent e) {
   }
}
