package view;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controller.Controller;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;


public class View {

	private JFrame frame;
	JLabel lblMissingPkg;
	private JLabel lblTftpUpdate;
	JButton btnInstallPkg;
	JButton btnConfigure;
	
	JFileChooser chooser;

	Controller ctrl;
	private JLabel lblServerConfigured;
	private JLabel label_1;
	private JLabel label_2;
	
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("U-Boot Util");
		frame.setBounds(100, 100, 579, 418);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("U-Boot Util");
		lblNewLabel.setFont(new Font("Ubuntu Light", Font.BOLD, 23));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		lblTftpUpdate = new JLabel("TFTP Update ");
		lblTftpUpdate.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		
		lblMissingPkg = new JLabel("");
		lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		lblMissingPkg.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		
		btnInstallPkg = new JButton("Install");
		btnInstallPkg.setVisible(false);
		btnInstallPkg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInstallPkg.setVisible(false);
				System.out.println("Click from user to install packages");
				/*
				 * Install packages
				 */
				ctrl.installTftpServerPackages();
				/*
				 * Update view
				 */
				ctrl.tftpServerMissingPkg();
			}
		});
		
		JPanel panelServerConfiguration = new JPanel();
		panelServerConfiguration.setBorder(BorderFactory.createLineBorder(Color.gray));

		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(91)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTftpUpdate)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblMissingPkg, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnInstallPkg, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
							.addGap(40))))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelServerConfiguration, GroupLayout.PREFERRED_SIZE, 533, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblTftpUpdate)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMissingPkg)
						.addComponent(btnInstallPkg))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelServerConfiguration, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(145, Short.MAX_VALUE))
		);
		
		
		JLabel lblServerConf = new JLabel("Server configuration");
		lblServerConf.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		
		label_1 = new JLabel("");
		
		lblServerConfigured = new JLabel("");
		lblServerConfigured.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		lblServerConfigured.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		
		label_2 = new JLabel("");
		
		this.btnConfigure = new JButton("Configure");
		this.btnConfigure.setVisible(false);
		this.btnConfigure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnConfigure.setVisible(false);
				/*
				 * Configure TFTP server
				 */
				ctrl.configureTftpServer();
			}
		});
		GroupLayout gl_panelServerConfiguration = new GroupLayout(panelServerConfiguration);
		gl_panelServerConfiguration.setHorizontalGroup(
			gl_panelServerConfiguration.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelServerConfiguration.createSequentialGroup()
					.addGroup(gl_panelServerConfiguration.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelServerConfiguration.createSequentialGroup()
							.addGap(174)
							.addComponent(lblServerConf)
							.addGap(5)
							.addComponent(label_1))
						.addGroup(gl_panelServerConfiguration.createSequentialGroup()
							.addGap(31)
							.addComponent(lblServerConfigured)))
					.addContainerGap(217, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panelServerConfiguration.createSequentialGroup()
					.addContainerGap(402, Short.MAX_VALUE)
					.addComponent(btnConfigure, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelServerConfiguration.setVerticalGroup(
			gl_panelServerConfiguration.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelServerConfiguration.createSequentialGroup()
					.addGroup(gl_panelServerConfiguration.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelServerConfiguration.createSequentialGroup()
							.addGap(13)
							.addComponent(lblServerConf))
						.addGroup(gl_panelServerConfiguration.createSequentialGroup()
							.addGap(21)
							.addComponent(label_1)))
					.addGap(7)
					.addComponent(lblServerConfigured)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(btnConfigure)
					.addContainerGap())
		);
		panelServerConfiguration.setLayout(gl_panelServerConfiguration);
		frame.getContentPane().setLayout(groupLayout);
	
		
		/*
		 * Let the controller manage the app
		 */
		this.ctrl = new Controller(this);
	}
	
	public void updatePackageLbls(String message, boolean pkgMissing){
		if(!pkgMissing){
			lblMissingPkg.setIcon(new ImageIcon("icons/tick.png"));
		}
		else{
			btnInstallPkg.setVisible(true);
			lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		lblMissingPkg.setText(message.toString());
	}
	
	public void updateConfigureTftpLbls(String message, boolean serverIsConfigured){
		if(serverIsConfigured){
			lblServerConfigured.setIcon(new ImageIcon("icons/tick.png"));
		}
		else{
			btnConfigure.setVisible(true);
			lblServerConfigured.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		lblServerConfigured.setText(message.toString());
	}
	
	/*
	 * Input dialog to get 
	 * return : the password if any password is entered
	 * 			otherwise, a null pointer
	 */
	public String promptPassword(String dialogInputName){
		// prompt sudo password
		JPanel panel = new JPanel();
		JLabel label = new JLabel("Enter the password:");
		JPasswordField pass = new JPasswordField(10);
		panel.add(label);
		panel.add(pass);
		String[] options = new String[]{"OK", "Cancel"};
		int option = JOptionPane.showOptionDialog(null, panel, dialogInputName,
		                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
		                         null, options, options[1]);
		
		if(option == 0) // pressing OK button
		{
		    char[] password = pass.getPassword();
		    return new String(password);
		}
		else
			return null;
	}
	
	
	public String promptDirectory(String title){
		int result;
		JPanel panel = new JPanel();
	    chooser = new JFileChooser(); 
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle(title);
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    //
	    // disable the "All files" option.
	    //
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION) { 
	      System.out.println("getCurrentDirectory(): " 
	         +  chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " 
	         +  chooser.getSelectedFile());
			return chooser.getSelectedFile().toString();
	      }
	    else {
	      System.out.println("No Selection ");
	      return "No Selection";
	      }

	}
	
}
