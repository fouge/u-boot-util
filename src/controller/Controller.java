package controller;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import view.View;



public class Controller {
	
	View view;
	String sudoPass = null;
	Config conf = new Config();
	
	public Controller(View view){
		this.view = view;
		
		/*
		 * Check if packages are installed
		 */
		this.tftpServerMissingPkg();
		
		/*
		 * Check if TFTP server is configured
		 */
		if(this.tftpServerIsConfigured())
		{
				/*
				 * Display settings
				 */
				this.displayTftpSettings();
		}
		else{ // an error occured
			// update view
	    	view.updateConfigureTftpLbls("Configuration file not found.", false);
			
		}
	}

	/*
	 * Install every missing packages needed to set up a TFTP server
	 * return true if every missing packages are installed
	 */
	public boolean installTftpServerPackages(){
		if(this.sudoPass==null){
			String pass = view.promptPassword("Sudo Password");
			if(pass != null){
				this.sudoPass = new String(pass);
			}
			else
				return false;
		}
		if(! this.conf.installMissingPkg(new String(this.sudoPass))){
			this.sudoPass = null; 
			return false;
		}
		return true;
	}
	
	/*
	 * Check if packages are missing
	 * return boolean : true : package(s) is/are missing
	 * 					false : every needed packages are installed
	 */
	public boolean tftpServerMissingPkg(){
		StringBuilder message = new StringBuilder();
		boolean ret = (this.conf.probePkgNeeded(message)>0);
		this.view.updatePackageLbls(message.toString(), ret);
		return ret;	
	}

	/*
	 * Check if TFTP server is configured and launched
	 * return true if configured
	 */
	public boolean tftpServerIsConfigured() {
		
		String tftpDirectory = new String();
		// Check if there is "tftp" file in /etc/xinetd.d
		try  
		{
			FileReader fstream = new FileReader("/etc/xinetd.d/tftp");

			/*
			 * If here, file exists and can be opened
			 */
		    tftpDirectory = this.displayTftpSettings();
		    if(tftpDirectory.length()>0)
		    	this.view.updateConfigureTftpLbls("TFTP server files are placed in : "+tftpDirectory, true);
		    else
		    	this.view.updateConfigureTftpLbls("Incorrect configuration file.", false);
		}
		catch (FileNotFoundException e)
		{
		    System.err.println(e.getClass().getSimpleName() + " : " + e.getMessage());
		    return false;
		}
		return true;
	}
	
	private String displayTftpSettings() {
	    BufferedReader in;
		try {
			in = new BufferedReader(new FileReader("/etc/xinetd.d/tftp"));
			
			/*
			 * TODO parser
			 */
			String line = in.readLine();
			while(line != null){
				if (line.contains("server_args")){
					System.out.println(line);
					String[] splitedLine = line.split(" ");
					return splitedLine[splitedLine.length-1];
				}
				line = in.readLine();
			}
			
			/* Matcher matcher = Pattern.compile("(?<=server_args =/).*").matcher(in.rea);
			matcher.find();
			System.out.println(matcher.group()); */
		    	
		} catch (FileNotFoundException e) { // open file exception
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public boolean configureTftpServer(){
		/*
		 * Ask for TFTP directory
		 */
		String path = this.view.promptDirectory("Directory where files will be available via TFTP");
		
		/*
		 * create server settings file
		 */
		// Ask sudo pass if not defined
		if(this.sudoPass == null){
			String pass = view.promptPassword("Sudo Password");
			if(pass == null) // cancelled
				return false;
			else
				this.sudoPass = new String(pass);
		}
		
		
		if(this.shcommand("echo "+this.sudoPass+" | sudo -S touch /etc/xinetd.d/tftp") && this.shcommand("echo "+this.sudoPass+" | sudo -S chmod 777 /etc/xinetd.d/tftp"))
		{
			String confFile = "service tftp \n{ \nprotocol = udp \nport = 69\nsocket_type = dgram\nwait = yes\nuser = nobody\nserver = /usr/sbin/in.tftpd\nserver_args  = "+path+"\ndisable = no \n}\n";
	
			
			PrintWriter writer;
			try {
				writer = new PrintWriter("/etc/xinetd.d/tftp");
				writer.println(confFile);
				writer.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return false;
			}
		}
		else{
			return false;
		}
		
		if(tftpServerIsConfigured()){
			return true;
		}
		else
			return false;
	}

	private boolean shcommand(String string) {
        Process p;
        int ret = 1;
        try {
        	String[] cmd = {
        			"/bin/sh",
        			"-c",
        			string
        			};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            ret = p.exitValue();
            System.out.println ("Command : "+string+"\nexit: " + ret);
            p.destroy();
        } catch (Exception e) {}
        
        	return (ret == 0);
	}
	
	public boolean connectTtyACM(){
		
		try {
			CommPortIdentifier portId = CommPortIdentifier.getPortIdentifier("/dev/ttyACM0");
		} catch (NoSuchPortException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
