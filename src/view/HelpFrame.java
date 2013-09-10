package view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.JTextArea;
import javax.swing.DropMode;



/**
 * Second window used to display some help.
 * Plus, the "About" section.
 * 
 * @author Cyril Fougeray
 * @version 0.1
 *
 */
public class HelpFrame extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame.
	 */
	public HelpFrame(int x, int y) {
		setResizable(false);
		this.setTitle("Help");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(x, y, 317, 530);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHelpAndMore = new JLabel("About");
		lblHelpAndMore.setBounds(12, 435, 70, 25);
		lblHelpAndMore.setFont(new Font("Ubuntu Light", Font.PLAIN, 18));
		lblHelpAndMore.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblHelpAndMore);
		
		JLabel lblTwitter = new JLabel("");
		lblTwitter.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblTwitter.setForeground(new Color(0, 191, 255));
		lblTwitter.setIcon(new ImageIcon("icons/twitter.png"));
		lblTwitter.setBounds(252, 472, 50, 24);
		contentPane.add(lblTwitter);
		lblTwitter.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		lblTwitter.addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {
			}
			public void mousePressed(MouseEvent arg0) {			
			}
			public void mouseExited(MouseEvent arg0) {
			}
			public void mouseEntered(MouseEvent arg0) {
			}
			public void mouseClicked(MouseEvent arg0) {
				try {
					openWebPage(new URL("https://twitter.com/cyrilfougeray"));
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		JLabel lblAndMore = new JLabel("Cyril Fougeray");
		lblAndMore.setHorizontalAlignment(SwingConstants.LEFT);
		lblAndMore.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblAndMore.setBounds(22, 472, 106, 24);
		lblAndMore.setCursor(new Cursor(Cursor.HAND_CURSOR));
		lblAndMore.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				try {
					openWebPage(new URL("http://about.me/cyril.fougeray"));
				} catch (MalformedURLException e1) {
					e1.printStackTrace();
				}
				
			}
			public void mouseEntered(MouseEvent e) {	
			}
			public void mouseExited(MouseEvent e) {			
			}
			public void mousePressed(MouseEvent e) {			
			}
			public void mouseReleased(MouseEvent e) {			
			}
		});
		contentPane.add(lblAndMore);
		
		JLabel lblNetwork = new JLabel("Network");
		lblNetwork.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblNetwork.setBounds(12, 252, 94, 15);
		contentPane.add(lblNetwork);
		
		JTextArea txtrIfEthernet = new JTextArea();
		txtrIfEthernet.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		txtrIfEthernet.setBackground(SystemColor.window);
		txtrIfEthernet.setEditable(false);
		txtrIfEthernet.setText("If ethernet : link must be physically\nconnected to get the interface (ethN).");
		txtrIfEthernet.setBounds(22, 279, 280, 50);
		contentPane.add(txtrIfEthernet);
		
		JLabel lblUbootutilV = new JLabel("U-Boot-util v0.5 (GPL)");
		lblUbootutilV.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblUbootutilV.setBounds(106, 442, 171, 15);
		contentPane.add(lblUbootutilV);
		
		JLabel lblTftp = new JLabel("TFTP");
		lblTftp.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblTftp.setBounds(12, 66, 70, 15);
		contentPane.add(lblTftp);
		
		JTextArea txtTftp = new JTextArea();
		txtTftp.setText("Port number 69 is used for TFTP by default, \nbut reserved and used by the system. You \nneed to launch U-Boot-Util as root to use \nthis port.");
		txtTftp.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		txtTftp.setEditable(false);
		txtTftp.setBackground(SystemColor.window);
		txtTftp.setBounds(22, 93, 280, 71);
		contentPane.add(txtTftp);
		
		JTextArea txtrFilesAvailableOn = new JTextArea();
		txtrFilesAvailableOn.setText("Files available on the server are placed in\n/tftpboot/ (v0.5). They are listed in this panel.");
		txtrFilesAvailableOn.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		txtrFilesAvailableOn.setEditable(false);
		txtrFilesAvailableOn.setBackground(SystemColor.window);
		txtrFilesAvailableOn.setBounds(22, 176, 280, 50);
		contentPane.add(txtrFilesAvailableOn);
		
	}
	
	
	private void openWebPage(URL url){
	    Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
	    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
	        try {
	            desktop.browse(url.toURI());
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}
}
