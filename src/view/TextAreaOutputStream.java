package view;

/**
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */

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


public class TextAreaOutputStream extends OutputStream {

   private final JTextArea textArea;
   private final StringBuilder sb = new StringBuilder();
   private String title;

   public TextAreaOutputStream(final JTextArea textArea, String title) {
      this.textArea = textArea;
      this.title = title;
      
      
      DefaultStyledDocument doc = new DefaultStyledDocument();
      textArea.setDocument(doc);

      StyleContext context = new StyleContext();
      // build a style
      javax.swing.text.Style style = context.addStyle("test", null);
      // set some style properties
      StyleConstants.setForeground(style, Color.BLUE);
      // add some data to the document
      try {
		doc.insertString(0, "Hello", style);
	} catch (BadLocationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }

   @Override
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
