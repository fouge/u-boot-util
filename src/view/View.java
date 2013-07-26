package view;


import java.awt.EventQueue;

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
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class View {

	private JFrame frame;
	JLabel lblMissingPkg;
	private JLabel lblTftpUpdate;
	JButton btnInstallPkg;

	Controller ctrl = new Controller(this);

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
		frame.setBounds(100, 100, 579, 300);
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
				ctrl.installTftpServerPackages();
				updatePackageLbls();
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(86)
									.addComponent(lblMissingPkg, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnInstallPkg))
								.addComponent(lblTftpUpdate)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(lblTftpUpdate)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInstallPkg)
						.addComponent(lblMissingPkg))
					.addGap(185))
		);
		frame.getContentPane().setLayout(groupLayout);
	
		updatePackageLbls();
	}
	
	public void updatePackageLbls(){
		StringBuilder message = new StringBuilder ();
		if(ctrl.tftpServerMissingPkg(message)>0){
			btnInstallPkg.setVisible(true);
			lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		else{
			lblMissingPkg.setIcon(new ImageIcon("icons/tick.png"));
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
