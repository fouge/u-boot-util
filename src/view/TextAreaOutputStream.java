package view;


import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

/**
 * Class that let the out stream printed to a component of the view. It must extend OutputStream.
 * 				Methods need to be override to write the log in the visual component.
 * 
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */
public class TextAreaOutputStream extends OutputStream {

   private final JTextArea textArea;
   private final StringBuilder sb = new StringBuilder();
   private String title;

   /**
    * 
    * @param textArea Visual component to write into
    * @param title 
    */
   public TextAreaOutputStream(final JTextArea textArea, String title) {
      this.textArea = textArea;
      this.title = title;
   }

   public void flush() {
   }

   public void close() {
   }

   /**
    * write one characted in the visual component
    * @param b Character to write
    */
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
               textArea.append(text);
            }
         });
         sb.setLength(0);

         return;
      }

      sb.append((char)b);
   }
}
