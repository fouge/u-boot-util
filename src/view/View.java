package view;



import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

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
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ListSelectionModel;

/**
 * View of the main window. 
 * 3 panels used : TFTP configuration, Network configuration and log.
 * 
 * @author Cyril Fougeray (cyril.fougeray@gmail.com)
 * @version 0.1
 * 
 */
public class View {
	
	private Controller ctrl;
	
	private JFrame frame;
	private HelpFrame popupHelpFrame;
	
	/*
	 * Buttons
	 */
	JButton btnStartTftpServer;
	JSpinner spinner;
	
	/*
	 * Text areas
	 */
	JTextArea log;
	
	boolean isRoot = false;
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
		System.out.println(System.getProperty("java.library.path"));
		this.isRoot = System.getProperty("user.name").equals("root");
		this.ctrl = new Controller(this);
		initialize();
		// TODO remove this line :
		this.ctrl.getPortList();
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
		 * Set UI like GTK
		 */
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		JPanel panel = new JPanel();
		panel.setBounds(97, 12, 371, 35);
		frame.getContentPane().add(panel);
		
		JPanel panel_1 = new JPanel();;
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_1.setBounds(12, 59, 543, 166);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_2.setBounds(12, 237, 543, 136);
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
		 * Components main panel
		 */
		JScrollPane scrollPane = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
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
		btnStartTftpServer.setBounds(375, 129, 156, 25);
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
		if(!this.isRoot)
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

					if(!isRoot)
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
		lblDirectory.setBounds(22, 102, 70, 15);
		panel_1.add(lblDirectory);
		
		JLabel lblPathServer = new JLabel("/tftpboot/");
		lblPathServer.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		lblPathServer.setBounds(113, 102, 156, 15);
		panel_1.add(lblPathServer);
		
		
		Vector<String> data = this.ctrl.setAndLoadTftpDir("/tftpboot/");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(350, 39, 181, 78);
		panel_1.add(scrollPane_1);
		
		if(data.size()==0){
			lblPathServer.setText("/tftpboot/ doesn't exist.");
			// TODO a button : mkdir /tftpboot
		}
		else
		{
			JList list = new JList(data);
			scrollPane_1.setViewportView(list);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			list.setFocusable(false);
		}
		
		JLabel lblFilesAvailable = new JLabel("Files available");
		lblFilesAvailable.setBounds(250, 41, 88, 13);
		lblFilesAvailable.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		panel_1.add(lblFilesAvailable);
		
		
		/*
		 * Components panel 2
		 */
		/* TODO: add a "reload" button */
		JLabel lblNetworkConfiguration = new JLabel("Network configuration");
		lblNetworkConfiguration.setFont(new Font("Ubuntu Light", Font.PLAIN, 14));
		lblNetworkConfiguration.setBounds(12, 12, 177, 15);
		panel_2.add(lblNetworkConfiguration);
		
		final JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBackground(SystemColor.text);
		comboBox.setForeground(Color.BLACK);
		comboBox.setFont(new Font("Ubuntu Light", Font.PLAIN, 12));
		comboBox.setBounds(84, 39, 65, 20);
		Vector<String> interfaces = null;
		interfaces = (Vector<String>) this.ctrl.getInterfaces();
		comboBox.setModel(new DefaultComboBoxModel<>(interfaces));
		panel_2.add(comboBox);
		
			/*
			 * Server IP
			 */
		JLabel lblIpServer = new JLabel("Server IP");
		lblIpServer.setFont(new Font("Ubuntu", Font.PLAIN, 13));
		lblIpServer.setBounds(22, 69, 77, 15);
		panel_2.add(lblIpServer);
		
		final JLabel lblIpServerValue = new JLabel("192.168.");
		lblIpServerValue.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpServerValue.setBounds(91, 69, 58, 15);
		panel_2.add(lblIpServerValue);
		
		final JSpinner spinner_ipServer1 = new JSpinner();
		spinner_ipServer1.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipServer1.setModel(new SpinnerNumberModel(200, 1, 254, 1));
		spinner_ipServer1.setBounds(142, 67, 46, 20);
		panel_2.add(spinner_ipServer1);
		
		final JSpinner spinner_ipServer2 = new JSpinner();
		spinner_ipServer2.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipServer2.setModel(new SpinnerNumberModel(101, 1, 254, 1));
		spinner_ipServer2.setBounds(192, 67, 46, 20);
		panel_2.add(spinner_ipServer2);
			
			/*
			 * Target IP
			 */
		JLabel lblIpTarget = new JLabel("Target IP");
		lblIpTarget.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpTarget.setBounds(343, 69, 70, 15);
		panel_2.add(lblIpTarget);
		
		final JLabel lblIpTargetValue = new JLabel(lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+".");
		lblIpTargetValue.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblIpTargetValue.setBounds(411, 69, 82, 15);
		panel_2.add(lblIpTargetValue);
		
		final JSpinner spinner_ipTarget = new JSpinner();
		spinner_ipTarget.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		spinner_ipTarget.setModel(new SpinnerNumberModel(102, 1, 254, 1));
		spinner_ipTarget.setBounds(485, 66, 46, 20);
		panel_2.add(spinner_ipTarget);
		
		spinner_ipServer1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				lblIpTargetValue.setText(lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+".");
			}
		});
		
		JButton btnSet = new JButton("Set");
		btnSet.setBackground(SystemColor.menu);
		btnSet.setFont(new Font("Ubuntu Light", Font.BOLD, 12));
		btnSet.setBounds(437, 99, 94, 25);
		panel_2.add(btnSet);
		
		JLabel lblInterfaceipv = new JLabel("Interface");
		lblInterfaceipv.setFont(new Font("Ubuntu Light", Font.PLAIN, 13));
		lblInterfaceipv.setBounds(22, 42, 58, 15);
		panel_2.add(lblInterfaceipv);

		btnSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(spinner_ipServer2.getValue().equals(spinner_ipTarget.getValue()))
					System.out.println("Important : IPs must be different.");
				else {
					/* Set server IP */
					String passwd = View.this.promptPassword("Sudo password");
					if(passwd != null)
						View.this.ctrl.setIp(comboBox.getSelectedItem().toString(), lblIpServerValue.getText()+String.valueOf(spinner_ipServer1.getValue())+"."+String.valueOf(spinner_ipServer2.getValue()), passwd);
					
					/* Set target IP */
					// TODO method to set target IP
					System.out.println("Set target IP : not implemented yet. Use minicom to communicate with U-Boot:\nU-Boot> setenv ipaddr "+lblIpTargetValue.getText()+spinner_ipTarget.getValue());
				}
			}
		});
	
		
		
		/*
		 * Help panel
		 */
		JLabel lblHelp = new JLabel("?");
		lblHelp.setFont(new Font("Ubuntu", Font.PLAIN, 14));
		lblHelp.setBounds(547, 0, 20, 23);
		frame.getContentPane().add(lblHelp);
		lblHelp.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if(View.this.popupHelpFrame == null){
					View.this.popupHelpFrame = new HelpFrame(View.this.frame.getX()+View.this.frame.getWidth(), View.this.frame.getY());
				}

				View.this.popupHelpFrame.setVisible(true);
			
			}
		});
		
		
	}
	
	
	/**
	 * 
	 * @param dialogInputName : Name of the window
	 * @return password : String
	 */
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
