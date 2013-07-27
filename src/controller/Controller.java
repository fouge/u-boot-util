package controller;

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
		this.tftpServerMissingPkg();
		
		/*
		 * Check if TFTP server is configured
		 */
		this.tftpServerIsConfigured();
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
	public void tftpServerIsConfigured() {
		// TODO Check if there is "tftp" file in /etc/xinetd.d
		
		
		// TODO If server is configured, display settings, else, configure it
	}
}
