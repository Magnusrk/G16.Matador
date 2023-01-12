package G16.mguiPatched.gui_input;

import java.awt.event.KeyEvent;
import javax.swing.JButton;
import org.jetbrains.annotations.NotNull;

public class EnterButton {
   private final ActionCallback callback;
   private final JButton button;

   public EnterButton(@NotNull String label, @NotNull ActionCallback callback) {
      this.callback = callback;
      this.button = new JButton(label);
      this.button.addActionListener((e) -> {
         this.doAction();
      });
      this.button.addKeyListener(new KeyListener());
   }

   public void setEnabled(boolean enabled) {
      this.button.setEnabled(enabled);
   }

   public JButton getJButton() {
      return this.button;
   }

   public void doAction() {
      this.callback.run();
   }

   public void focus() {
      this.button.requestFocusInWindow();
   }

   public interface ActionCallback {
      void run();
   }

   private class KeyListener implements java.awt.event.KeyListener {
      private KeyListener() {
      }

      public void keyPressed(KeyEvent e) {
         if (e.getKeyChar() == '\n') {
            EnterButton.this.doAction();
            e.consume();
         }

      }

      public void keyTyped(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
      }
   }
}
