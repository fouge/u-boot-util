package controller;

import view.View;

public class Controller {
	
	View view;
	String sudoPass = new String();
	Config conf = new Config();
	
	public Controller(View view){
		this.view = view;
	}

	public void installTftpServerPackages(){
		this.sudoPass = view.promptPassword("Sudo Password");
		if(this.sudoPass != null)
			this.conf.installMissingPkg(new String(this.sudoPass));
	}
	
	public int tftpServerMissingPkg(StringBuilder message){
		int ret = this.conf.probePkgNeeded(message);
		System.out.println(message+"d");
		return ret;
	}

	
}
