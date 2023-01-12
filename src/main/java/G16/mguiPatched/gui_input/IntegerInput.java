package G16.mguiPatched.gui_input;

import G16.mguiPatched.gui_fields.GUI_Board;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IntegerInput {
   private final JTextField inputField;
   private final EnterButton okButton;
   private final CountDownLatch latch;
   private final int minValue;
   private final int maxValue;
   private int inputValue = 0;
   private boolean validInput = false;
   private boolean inputActive = false;

   public IntegerInput(GUI_Board board, String msg, int minValue, int maxValue) {
      if (maxValue < minValue) {
         throw new IllegalArgumentException("Maximum value must be larger than minimum");
      } else {
         this.minValue = minValue;
         this.maxValue = maxValue;
         this.latch = new CountDownLatch(1);
         this.okButton = new EnterButton("OK", this::returnResult);
         this.okButton.setEnabled(false);
         this.inputField = new JTextField(20);
         this.inputField.setHorizontalAlignment(4);
         this.inputField.getDocument().addDocumentListener(new InputFieldValueListener());
         this.inputField.addKeyListener(new InputFieldKeyListener());
         this.inputActive = true;
         board.getUserInput(msg, this.inputField, this.okButton.getJButton());
         this.inputField.requestFocusInWindow();
      }
   }

   public int getResult() {
      try {
         this.latch.await();
      } catch (InterruptedException var2) {
         throw new RuntimeException("Was interrupted while waiting for input result - this shouldn't happen (contact developer)!");
      }

      return this.inputValue;
   }

   private boolean validateInput(String input) {

      int value;
      try {
         value = Integer.parseInt(input);
      } catch (NumberFormatException var4) {
         return false;
      }

      return value >= this.minValue && value <= this.maxValue;
   }

   private void returnResult() {
      if (this.validInput && this.inputActive) {
         this.inputActive = false;
         this.latch.countDown();
      }

   }

   private void inputChanged() {
      if (this.inputActive) {
         this.validInput = this.validateInput(this.inputField.getText());
         if (this.validInput) {
            this.inputValue = Integer.parseInt(this.inputField.getText());
            this.inputField.setForeground(Color.BLACK);
         } else {
            this.inputField.setForeground(Color.RED);
         }

         this.okButton.setEnabled(this.validInput);
      }
   }

   class InputFieldValueListener implements DocumentListener {
      InputFieldValueListener() {
      }

      public void insertUpdate(DocumentEvent e) {
         IntegerInput.this.inputChanged();
      }

      public void removeUpdate(DocumentEvent e) {
         IntegerInput.this.inputChanged();
      }

      public void changedUpdate(DocumentEvent e) {
         IntegerInput.this.inputChanged();
      }
   }

   class InputFieldKeyListener implements KeyListener {
      InputFieldKeyListener() {
      }

      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == 10) {
            IntegerInput.this.returnResult();
            e.consume();
         }

      }

      public void keyTyped(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
      }
   }
}
