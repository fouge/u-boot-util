package controller;

import java.util.ArrayList;


public class Config {
	
	ArrayList<String> pkgNeeded = new ArrayList<String>();
	
	public Config(){
		
	}

	public int probePkgNeeded(StringBuilder message){
		int needPkg = 0;
		if(!this.pkgIsInstalled("tftp")){
			message.append("tftp");
			this.pkgNeeded.add("tftp");
			needPkg ++;
		}
		if(!this.pkgIsInstalled("tftpd")){
			if(needPkg>0)
				message.append(", ");
			message.append("tftpd");
			this.pkgNeeded.add("tftpd");
			needPkg ++;
			}
		if(!this.pkgIsInstalled("xinetd")){
			if(needPkg > 0)
				message.append(" and xinetd");
			else			
				message.append("xinetd");
			pkgNeeded.add("xinetd");
			needPkg ++;
			}
		
		if(needPkg == 0){
			message.append("Every needed packages are installed.");
		}

		else {
			if (needPkg == 1)
				message.append(" package is not installed.");
			else
				message.append(" packages are not installed.");
		}

		return needPkg;
	}
	
	
	public boolean pkgIsInstalled(String name){
		
        Process p;
        int ret = 1;
        try {
        	String[] cmd = {
        			"/bin/sh",
        			"-c",
        			"dpkg-query -l " +name+" | grep ii"
        			};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            ret = p.exitValue();
            System.out.println ("dpkg-query -l " +name+" | grep ii\nexit: " + ret);
            p.destroy();
        } catch (Exception e) {}
        	return (ret == 0);
	}
	
	
	
	public boolean installPkg(String name, String sudoPasswd){
		
        Process p;   
        int ret = 1;
        
        try {
        	String[] cmd = {
        			"/bin/sh",
        			"-c",
        			"echo "+sudoPasswd+" | sudo -S apt-get install "+ name+" -y"
        			};
            p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
            ret = p.exitValue();
            System.out.println ("exit: " + ret);
            p.destroy();
        } catch (Exception e) {
        	e.printStackTrace();
        }
        	return (ret == 0);
	}

	public void installMissingPkg(String passwd) {
		for(String s : this.pkgNeeded){
			this.installPkg(s, passwd);
		}
	}
}
