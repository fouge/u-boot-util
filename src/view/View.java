package view;


import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controller.Controller;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class View {

	private JFrame frame;
	JLabel lblMissingPkg;
	private JLabel lblTftpUpdate;
	JButton btnInstallPkg;
	JButton btnConfigure;

	Controller ctrl;
	private JLabel lblServerConfigured;

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
		
		lblServerConfigured = new JLabel("");
		lblServerConfigured.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/question.png")));
		lblServerConfigured.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		
		this.btnConfigure = new JButton("Configure");
		this.btnConfigure.setVisible(false);
		this.btnConfigure.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				btnConfigure.setVisible(false);
				
				// TODO configure server
			}
		});
		
		JPanel panelServerConfiguration = new JPanel();
		panelServerConfiguration.setBorder(new Border() {
			
			@Override
			public void paintBorder(Component arg0, Graphics arg1, int arg2, int arg3,
					int arg4, int arg5) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isBorderOpaque() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Insets getBorderInsets(Component arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(91)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMissingPkg, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblServerConfigured, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConfigure, 0, 0, Short.MAX_VALUE)
						.addComponent(btnInstallPkg, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
					.addGap(40))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(248, Short.MAX_VALUE)
					.addComponent(lblTftpUpdate)
					.addGap(240))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelServerConfiguration, GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTftpUpdate)
					.addGap(4)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMissingPkg)
						.addComponent(btnInstallPkg))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnConfigure)
						.addComponent(lblServerConfigured, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelServerConfiguration, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(107, Short.MAX_VALUE))
		);
		
		
		JLabel lblServerConf = new JLabel("Server configuration");
		lblServerConf.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		panelServerConfiguration.add(lblServerConf);
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
}
