package view;


/**
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */


import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTabbedPane;
import javax.swing.JButton;

import ctrl.Controller;
import javax.swing.JTextArea;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTree;
import javax.swing.text.MaskFormatter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.JFormattedTextField;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import java.awt.Choice;
import javax.swing.JComboBox;
import javax.swing.JTextPane;

public class View {
	
	private Controller ctrl;
	
	private JFrame frame;
	
	/*
	 * Buttons
	 */
	JButton btnStartTftpServer;
	JSpinner spinner;
	
	/*
	 * Text areas
	 */
	JTextArea log;
	
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
		this.ctrl = new Controller(this);
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("U-Boot Utilitary for Linux");
		frame.setResizable(false);
		frame.setBounds(100, 100, 569, 530);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		
		/*
		 * 
		 */
		UIManager.put("OptionPane.background", SystemColor.window);
		UIManager.put("OptionPane.buttonFont", new Font("Ubuntu Light", Font.BOLD, 12));
		UIManager.put("OptionPane.font", new Font("Ubuntu Light", Font.BOLD, 12));
		UIManager.put("JButton.background", SystemColor.window);
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		/*
		 * Pannels
		 */
		JPanel panel = new JPanel();
		panel.setBounds(0, 12, 567, 35);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();;
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(12, 59, 543, 136);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(12, 207, 543, 113);
		frame.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		
		/*
		 * Titles
		 */
		JLabel lblUbootUtil = new JLabel("U-Boot Util");
		lblUbootUtil.setFont(new Font("Ubuntu Light", Font.PLAIN, 20));
		panel.add(lblUbootUtil);
		
		JLabel lblTftpServerConfiguration = new JLabel("TFTP server configuration");
		lblTftpServerConfiguration.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblTftpServerConfiguration.setBounds(12, 12, 209, 15);
		panel_1.add(lblTftpServerConfiguration);
		
		/*
		 * Components main pannel
		 */
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(12, 385, 543, 108);
		frame.getContentPane().add(scrollPane);
		
		
		this.log = new JTextArea();
		scrollPane.setViewportView(log);
		this.log.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 12));
		this.log.setForeground(Color.WHITE);
		this.log.setBackground(Color.BLACK);
		
		/** Output stream is redirected to JTextArea */
		TextAreaOutputStream systemOut = new TextAreaOutputStream(this.log, "Log");
		System.setOut(new PrintStream(systemOut));
		
		/*
		 * Components panel_1
		 */
		JLabel lblPort = new JLabel("Port ");
		lblPort.setBounds(22, 39, 29, 16);
		panel_1.add(lblPort);
		lblPort.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		
		this.btnStartTftpServer = new JButton("Start TFTP server");
		btnStartTftpServer.setBackground(SystemColor.menu);
		btnStartTftpServer.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		btnStartTftpServer.setBounds(375, 99, 156, 25);
		panel_1.add(btnStartTftpServer);
		this.btnStartTftpServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Start/stop TFTP server
				if(ctrl.startTFTPServer((Integer)spinner.getValue()))
					btnStartTftpServer.setText("Stop TFTP server");
				else
					btnStartTftpServer.setText("Start TFTP server");
			}
		});

		
		this.spinner = new JSpinner();
		spinner.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		spinner.setModel(new SpinnerNumberModel(69, 69, 65535, 1));
		// TODO if not root, display warning message
		System.out.println("Warning : you need to be root to start the server on port 69.");
		spinner.addChangeListener(new ChangeListener() {
			
			@Override
			public void stateChanged(ChangeEvent e) {
				/** Value of the spinner */
				int spinnerValue = (Integer)spinner.getValue();
				
				/* Set text on button depending on spinner value */
				if(ctrl.getCurrentPort()==spinnerValue)
					btnStartTftpServer.setText("Stop TFTP server");
				else
					btnStartTftpServer.setText("Start TFTP server");
				
				
				if(spinnerValue==1024){
					spinner.setValue((Integer)69);

					// TODO if not root, display warning message
					System.out.println("Warning : you need to be root to start the server on port 69.");
				}
				if(spinnerValue==70)
					spinner.setValue((Integer)1025);
				if(spinnerValue>71 && spinnerValue<1024)
					spinner.setValue((Integer)1025);
			}
		});
		spinner.setBounds(108, 39, 65, 20);
		panel_1.add(spinner);
		
		JLabel lblDirectory = new JLabel("Directory");
		lblDirectory.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblDirectory.setBounds(22, 67, 70, 15);
		panel_1.add(lblDirectory);
		
		JLabel lblPathServer = new JLabel("/tftpboot/");
		lblPathServer.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		lblPathServer.setBounds(108, 67, 70, 15);
		panel_1.add(lblPathServer);
		
		
		/*
		 * Components pannel 2
		 */
		/* TODO: add a "reload" button */
		JLabel lblNetworkConfiguration = new JLabel("Network configuration");
		lblNetworkConfiguration.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblNetworkConfiguration.setBounds(12, 12, 177, 15);
		panel_2.add(lblNetworkConfiguration);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBackground(SystemColor.menu);
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		comboBox.setBounds(173, 7, 65, 20);
		Vector<String> interfaces = null;
		interfaces = (Vector<String>) this.ctrl.getInterfaces();
		comboBox.setModel(new DefaultComboBoxModel<>(interfaces));
		panel_2.add(comboBox);
		
			/*
			 * Server IP
			 */
		JLabel lblIpServer = new JLabel("Server IP");
		lblIpServer.setFont(new Font("Ubuntu", Font.PLAIN, 13));
		lblIpServer.setBounds(22, 42, 77, 15);
		panel_2.add(lblIpServer);
		
		final JLabel lblIpServerValue = new JLabel("192.168.");
		lblIpServerValue.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpServerValue.setBounds(91, 42, 58, 15);
		panel_2.add(lblIpServerValue);
		
		final JSpinner spinner_ipServer1 = new JSpinner();
		spinner_ipServer1.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipServer1.setModel(new SpinnerNumberModel(200, 1, 254, 1));
		spinner_ipServer1.setBounds(142, 40, 46, 20);
		panel_2.add(spinner_ipServer1);
		
		final JSpinner spinner_ipServer2 = new JSpinner();
		spinner_ipServer2.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipServer2.setModel(new SpinnerNumberModel(101, 1, 254, 1));
		spinner_ipServer2.setBounds(192, 40, 46, 20);
		panel_2.add(spinner_ipServer2);
			
			/*
			 * Target IP
			 */
		JLabel lblIpTarget = new JLabel("Target IP");
		lblIpTarget.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpTarget.setBounds(343, 42, 70, 15);
		panel_2.add(lblIpTarget);
		
		final JLabel lblIpTargetValue = new JLabel(lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+".");
		lblIpTargetValue.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpTargetValue.setBounds(411, 42, 82, 15);
		panel_2.add(lblIpTargetValue);
		
		JSpinner spinner_ipTarget = new JSpinner();
		spinner_ipTarget.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipTarget.setModel(new SpinnerNumberModel(102, 1, 254, 1));
		spinner_ipTarget.setBounds(485, 39, 46, 20);
		panel_2.add(spinner_ipTarget);
		
		spinner_ipServer1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblIpTargetValue.setText(lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+".");
			}
		});
		
		JButton btnSet = new JButton("Set");
		btnSet.setBackground(SystemColor.menu);
		btnSet.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		btnSet.setBounds(437, 76, 94, 25);
		panel_2.add(btnSet);
		
		btnSet.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String passwd = View.this.promptPassword("Sudo password");
				if(passwd != null)
					View.this.ctrl.setIp(comboBox.getSelectedItem().toString(), lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+"."+String.valueOf(spinner_ipServer2.getValue()));
			}
		});
		
	
	}
	
	
	
	public String promptPassword(String dialogInputName){
		// prompt sudo password
		/* Panel */
		JPanel panel = new JPanel();
		/* Label */
		JLabel label = new JLabel("Enter the password:");
		/* Passwd field */
		final JPasswordField pass = new JPasswordField(10);
		
		panel.add(label);
		panel.add(pass);
		
		String[] options = {"Cancel", "Ok"};
		int option = JOptionPane.showOptionDialog(null, panel, dialogInputName,JOptionPane.NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);
		if(option==1){
			return new String(pass.getPassword());
		}
		else
			return null;
	}
}
