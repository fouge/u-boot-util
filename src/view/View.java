package view;


import java.awt.EventQueue;
import java.awt.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import controller.Config;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Button;
import java.util.ArrayList;

import javax.swing.JProgressBar;

public class View {

	private JFrame frame;
	Config conf = new Config();
	JLabel lblMissingPkg;
	private JLabel lblTftpUpdate;
	JButton btnInstallPkg;
	private JProgressBar progressBar;
	ArrayList<String> pkgNeeded = new ArrayList<String>();

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
		
		progressBar = new JProgressBar();
		progressBar.setVisible(false);
		
		btnInstallPkg = new JButton("Install");
		btnInstallPkg.setVisible(false);
		btnInstallPkg.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				btnInstallPkg.setVisible(false);
				System.out.println("Installing packages...");
				
				// prompt sudo password
				JPanel panel = new JPanel();
				JLabel label = new JLabel("Enter a password:");
				JPasswordField pass = new JPasswordField(10);
				panel.add(label);
				panel.add(pass);
				String[] options = new String[]{"OK", "Cancel"};
				int option = JOptionPane.showOptionDialog(null, panel, "Sudo Password",
				                         JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE,
				                         null, options, options[1]);
				
				if(option == 0) // pressing OK button
				{
				    char[] password = pass.getPassword();
					progressBar.setVisible(true);
					progressBar.setStringPainted(true);
					int cursor = 0;
					progressBar.setValue(cursor);
					int step = 100 / pkgNeeded.size();
					System.out.println(String.valueOf(step));
					for(String s : pkgNeeded){
						System.out.println(s);
						if(conf.installPkg(s, new String(password))){
							cursor += step;
							progressBar.setValue(cursor);
						}

					}
					updatePackageLbls();
				}
				
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(45)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(86)
									.addComponent(lblMissingPkg, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnInstallPkg))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblTftpUpdate)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(24)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(39, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTftpUpdate)
						.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnInstallPkg)
						.addComponent(lblMissingPkg))
					.addGap(185))
		);
		frame.getContentPane().setLayout(groupLayout);
	
		updatePackageLbls();
	}
	
	private void updatePackageLbls(){
		int needPkg = 0;
		String pkgToInstall = "";
		if(!conf.pkgIsInstalled("tftp")){
			pkgToInstall = "tftp";
			pkgNeeded.add("tftp");
			needPkg ++;
			lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		if(!conf.pkgIsInstalled("tftpd")){
			if(needPkg>0)
				pkgToInstall += ", ";
			pkgToInstall += "tftpd";
			pkgNeeded.add("tftpd");
			needPkg ++;
			lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		if(!conf.pkgIsInstalled("xinetd")){
			if(needPkg > 0)
				pkgToInstall += " and xinetd";
			else
				pkgToInstall += ", xinetd";
			pkgNeeded.add("xinetd");
			needPkg ++;
			lblMissingPkg.setIcon(new ImageIcon(View.class.getResource("/javax/swing/plaf/metal/icons/ocean/error.png")));
		}
		
		if(needPkg == 0){
			pkgToInstall = "Every needed packages are installed.";
			ImageIcon iconOk = new ImageIcon("icons/tick.png");
			lblMissingPkg.setIcon(new ImageIcon("icons/tick.png"));
		}

		else {
			if (needPkg == 1)
				pkgToInstall += " package is not installed.";
			else
				pkgToInstall += " packages are not installed.";
			
			btnInstallPkg.setVisible(true);
		}

		
		
		lblMissingPkg.setText(pkgToInstall);
	}
}
