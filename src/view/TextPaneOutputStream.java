package view;


import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.jws.soap.SOAPBinding.Style;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyleContext.NamedStyle;
import javax.swing.text.StyledDocument;

/**
 * Class that let the out stream printed to a component of the view. It must extend OutputStream.
 * 				Methods need to be override to write the log in the visual component.
 * 
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */
public class TextPaneOutputStream extends OutputStream {

   private final JTextPane textPane;
   private final StringBuilder sb = new StringBuilder();
   private String title;
   private StyledDocument doc;
   
   /* Styles */
   private SimpleAttributeSet styleSerialMessage;
   private SimpleAttributeSet styleWarningMessage;
   private SimpleAttributeSet styleImportantMessage;
   private SimpleAttributeSet styleNormalMessage;

   /**
    * 
    * @param textArea Visual component to write into
    * @param title 
    */
   public TextPaneOutputStream(final JTextPane textPane, String title) {
      this.textPane = textPane;
      this.title = title;
      this.doc = this.textPane.getStyledDocument();
      
      
      /*
       * Styles
       * there is two ways to modify (set attributes) of a style :
       * 	StyleConstants.setFontSize(this.styleWarningMessage, 13);
       * 	this.styleWarningMessage.addAttribute(StyleConstants.Size, 13);
       */

      this.styleWarningMessage = new SimpleAttributeSet();
      StyleConstants.setFontFamily(this.styleWarningMessage, "DejaVu Sans Light");
      StyleConstants.setFontSize(this.styleWarningMessage, 13);
      StyleConstants.setBold(this.styleWarningMessage, false);
      StyleConstants.setItalic(this.styleWarningMessage, false);
      this.styleWarningMessage.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.ORANGE);
      
      this.styleSerialMessage = new SimpleAttributeSet();
      this.styleSerialMessage.addAttribute(StyleConstants.Size, 13);
      this.styleSerialMessage.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.FALSE);
      this.styleSerialMessage.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.FALSE);
      this.styleSerialMessage.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.GREEN);
      
      this.styleImportantMessage = new SimpleAttributeSet();
      StyleConstants.setFontFamily(this.styleImportantMessage, "DejaVu Sans Light");
      StyleConstants.setFontSize(this.styleImportantMessage, 13);
      StyleConstants.setBold(this.styleImportantMessage, false);
      StyleConstants.setItalic(this.styleImportantMessage, false);
      this.styleImportantMessage.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.RED);
      
      this.styleNormalMessage = new SimpleAttributeSet();
      StyleConstants.setFontFamily(this.styleNormalMessage, "DejaVu Sans Light");
      StyleConstants.setFontSize(this.styleNormalMessage, 13);
      StyleConstants.setBold(this.styleNormalMessage, false);
      StyleConstants.setItalic(this.styleNormalMessage, false);
      this.styleNormalMessage.addAttribute(StyleConstants.CharacterConstants.Foreground, Color.WHITE);
      
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
               try {
            	   if(text.toLowerCase().contains("warning"))
            		   TextPaneOutputStream.this.doc.insertString(doc.getLength(), text, TextPaneOutputStream. this.styleWarningMessage);
            	   else if(text.toLowerCase().contains("important"))
            		   TextPaneOutputStream.this.doc.insertString(doc.getLength(), text, TextPaneOutputStream. this.styleImportantMessage);
            	   else if(text.toLowerCase().contains("serial"))
            		   TextPaneOutputStream.this.doc.insertString(doc.getLength(), text, TextPaneOutputStream. this.styleSerialMessage);
            	   else
            		   TextPaneOutputStream.this.doc.insertString(doc.getLength(), text, TextPaneOutputStream. this.styleNormalMessage);
				
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
            }
         });
         sb.setLength(0);

         return;
      }

      sb.append((char)b);
   }
}
