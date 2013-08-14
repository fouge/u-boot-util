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
import java.io.IOException;
import java.io.PrintStream;
import javax.swing.JList;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JMenuBar;
import javax.swing.JMenu;

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
		initialize();
		this.ctrl = new Controller(this);
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
		 * Pannels
		 */
		JPanel panel = new JPanel();
		panel.setBounds(0, 12, 567, 35);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 59, 545, 169);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
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
		lblPort.setBounds(26, 56, 29, 16);
		panel_1.add(lblPort);
		lblPort.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		
		this.btnStartTftpServer = new JButton("Start TFTP server");
		btnStartTftpServer.setBounds(377, 132, 156, 25);
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
					System.out.println("Warning : you need to be root to start the server on port 69.");
				}
				if(spinnerValue==70)
					spinner.setValue((Integer)1025);
				if(spinnerValue>71 && spinnerValue<1024)
					spinner.setValue((Integer)1025);
			}
		});
		spinner.setBounds(112, 56, 69, 17);
		panel_1.add(spinner);
		
		JLabel lblDirectory = new JLabel("Directory");
		lblDirectory.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblDirectory.setBounds(26, 84, 70, 15);
		panel_1.add(lblDirectory);
		
		JLabel lblPathServer = new JLabel("/tftpboot");
		lblPathServer.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		lblPathServer.setBounds(112, 84, 70, 15);
		panel_1.add(lblPathServer);
		
	}
}
