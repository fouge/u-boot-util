package controller;


public class Config {
	
	public Config(){
		
	}

	public boolean pkgIsInstalled(String name){
		
        String s;
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
}
