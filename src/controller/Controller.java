package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import view.View;



public class Controller {
	
	View view;
	String sudoPass = new String();
	Config conf = new Config();
	
	public Controller(View view){
		this.view = view;
		
		/*
		 * Check if packages are installed
		 */
		if(!this.tftpServerMissingPkg())
		{
		/*
		 * Check if TFTP server is configured
		 */
			if(this.tftpServerIsConfigured())
			{
				/*
				 * TODO Display settings
				 */
				
			}
			else{
				/*
				 * TODO Prompt for the wanted settings
				 * 
				 * http://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
				 */
			}
				
		}
	}

	/*
	 * Install every missing packages needed to set up a TFTP server
	 */
	public void installTftpServerPackages(){
		this.sudoPass = view.promptPassword("Sudo Password");
		if(this.sudoPass != null)
			this.conf.installMissingPkg(new String(this.sudoPass));
	}
	
	/*
	 * Check if packages are missing
	 * return boolean : true : package(s) is/are missing
	 * 					false : every needed packages are installed
	 */
	public boolean tftpServerMissingPkg(){
		StringBuilder message = new StringBuilder();
		boolean ret = (this.conf.probePkgNeeded(message)>1);
			this.view.updatePackageLbls(message.toString(), ret );
		return ret;	
	}

	/*
	 * Check if TFTP server is configured and launched
	 */
	public boolean tftpServerIsConfigured() {
		// TODO Check if there is "tftp" file in /etc/xinetd.d
		try  
		{
			FileReader fstream = new FileReader("/etc/xinetd.d/tftp");
		    BufferedReader in = new BufferedReader(fstream);
		    
		    /*
		     * TODO read settings and pass these to Config
		     */
		    System.out.println(in.readLine());
		    System.out.println(in.readLine());
		    return true;
		}
		catch (Exception e)
		{
		    System.err.println(e.getClass().getSimpleName() + " : " + e.getMessage());
		    
		    if(e.getClass().getSimpleName().equals("FileNotFoundException")){

		    	view.updateConfigureTftpLbls("Configuration file not found.", false);
		
				return false;
		    }
		}
		return false;
	}
	
	public void configureTftpServer(){
		/*
		 * Ask for TFTP directory
		 */
		String path = this.view.promptDirectory("Directory where files will be available via TFTP");
		
		/*
		 * TODO create server settings file
		 */
		String confFile = "service tftp { \n protocol = udp \nport = 69\nsocket_type = dgram\nwait = yes\nuser = nobody\nserver = /usr/sbin/in.tftpd\nserver_args  = "+path+"\ndisable = no }";

		FileWriter fstream;
		try {
			fstream = new FileWriter("tftp", false);
		    BufferedWriter out = new BufferedWriter(fstream);
		    out.flush();
		    out.write("salut");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		tftpServerIsConfigured();
	}
}
