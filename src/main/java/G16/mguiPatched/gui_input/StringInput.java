package G16.mguiPatched.gui_input;

import G16.mguiPatched.gui_fields.GUI_Board;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class StringInput {
   private final JTextField inputField;
   private final EnterButton okButton;
   private final CountDownLatch latch;
   private final int minLength;
   private final int maxLength;
   private final boolean allowWhiteSpace;
   private String input = "";
   private boolean validInput;
   private boolean inputActive;

   public StringInput(GUI_Board board, String msg, int minLength, int maxLength, boolean allowWhiteSpace) {
      if (minLength < 0) {
         throw new IllegalArgumentException("Minimum input length must be zero or positive");
      } else if (maxLength <= 0) {
         throw new IllegalArgumentException("Maximum input length must be larger than 0");
      } else if (maxLength < minLength) {
         throw new IllegalArgumentException("Maximum input length cannot be less than  minimum input length");
      } else {
         this.allowWhiteSpace = allowWhiteSpace;
         this.minLength = minLength;
         this.maxLength = maxLength;
         this.validInput = minLength == 0;
         this.latch = new CountDownLatch(1);
         this.okButton = new EnterButton("OK", this::returnResult);
         this.okButton.setEnabled(this.validInput);
         this.inputField = new JTextField(20);
         this.inputField.setHorizontalAlignment(4);
         this.inputField.getDocument().addDocumentListener(new InputFieldValueListener());
         this.inputField.addKeyListener(new InputFieldKeyListener());
         this.inputActive = true;
         board.getUserInput(msg, this.inputField, this.okButton.getJButton());
         this.inputField.requestFocusInWindow();
      }
   }

   public String getResult() {
      try {
         this.latch.await();
      } catch (InterruptedException var2) {
         throw new RuntimeException("Was interrupted while waiting for input result - this shouldn't happen (contact developer)!");
      }

      return this.input;
   }

   public boolean validateInput(String input) {
      if (input.length() >= this.minLength && input.length() <= this.maxLength) {
         return this.allowWhiteSpace || input.length() <= 0 || !input.contains(" ") && !input.contains("\t") && !input.contains("\n") && !input.contains("\r");
      } else {
         return false;
      }
   }

   public void returnResult() {
      if (this.validInput && this.inputActive) {
         this.inputActive = false;
         this.latch.countDown();
      }

   }

   public void inputChanged() {
      if (this.inputActive) {
         this.validInput = this.validateInput(this.inputField.getText());
         if (this.validInput) {
            this.input = this.inputField.getText();
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
         StringInput.this.inputChanged();
      }

      public void removeUpdate(DocumentEvent e) {
         StringInput.this.inputChanged();
      }

      public void changedUpdate(DocumentEvent e) {
         StringInput.this.inputChanged();
      }
   }

   class InputFieldKeyListener implements KeyListener {
      InputFieldKeyListener() {
      }

      public void keyPressed(KeyEvent e) {
         if (e.getKeyCode() == 10) {
            StringInput.this.returnResult();
            e.consume();
         }

      }

      public void keyTyped(KeyEvent e) {
      }

      public void keyReleased(KeyEvent e) {
      }
   }
}
