package ctrl;

/**
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */

import gnu.io.CommPortIdentifier;

import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import view.View;



/**
 * @description Class that manage the app. Used as an interface between the view and the "mechanic" of the application.
 * 
 * @author Cyril Fougeray
 *
 */
public class Controller {

	private View view;
	
	private ThreadServer threadServer;
	
	/** Network interfaces, used to set server IP */
	Enumeration<NetworkInterface> interfaces;
	
	boolean isRoot = System.getProperty("user.name").equals("root");

	/**
	 * Construcor of the controller.
	 * 
	 * @param view : App class, can be used to modify some components of View.
	 * TODO Obsersable class ?
	 */
	public Controller(View view){
		this.view = view;
		
		try {
			this.interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	
	/**
	 * Start or interrupt the thread which manage TFTP server
	 * 
	 * @param port : port to start tftp server. port 69 is default but needs to have the application started as root.
	 * @return :
	 * 		false : server is interrupted
	 * 		true : server is running
	 */
	public boolean startTFTPServer(int port){
		
		if(this.threadServer == null)
		{
			this.threadServer = new ThreadServer(port);
			if(this.threadServer.getRunningState()){
				return true;
			}
		}
		else if(this.threadServer.getPort() != port){
			this.threadServer.terminate();
			this.threadServer = new ThreadServer(port);
			if(this.threadServer.getRunningState()){
				return true;
			}
		}
		else{
			if(this.threadServer.getRunningState()){
				this.threadServer.setState(false);
				this.threadServer.terminate();
				return false;
			}
			else{
				this.threadServer.setState(true);
				return true;
			}
		}
		return false;

		
	}
	
	
	/**
	 * Get the port of the TFTP server currently started
	 * 
	 * @return port of the tftp server started
	 * 			-1 if the server isn't started
	 */
	public int getCurrentPort(){
		if(this.threadServer==null)
			return -1;
		else
			return this.threadServer.getPort();
	}

	/**
	 * Get every connected interfaces
	 * 
	 * @return Return a vector of every connected interfaces (link with the system must be physically done for eth).
	 */
	public Vector<String> getInterfaces() {
		Vector<String> ret = new Vector<String>();
		try {
			this.interfaces = NetworkInterface.getNetworkInterfaces();
			
			for (Enumeration<NetworkInterface> e = this.interfaces; e.hasMoreElements();)
				ret.add(e.nextElement().getDisplayName());
			
		} catch (SocketException e) {
			e.printStackTrace();
		}
		if(ret.size()>0)
			return ret;
		else
			ret.add("No interface available");
		return ret;
	}

	/**
	 * Set IP of the selected network interface card. The sudo password is required to execute this task.
	 * 
	 * @param nic Network Interface Card
	 * @param ipServer IP address
	 * @param passwd Root password
	 * 
	 * TODO Use a more secure way to execute this function because password is stored clearly in memory.
	 */
	public void setIp(String nic, String ipServer, String passwd) {
		
		System.out.println("Modifying "+nic+", set IP address to "+ipServer);
		
		Process p;
		int ret = 1;
		try {
			String[] cmd = { "/bin/sh", "-c", "echo "+passwd+" | sudo -S ifconfig "+nic+" "+ipServer+" netmask 255.255.255.0 up"};		
			p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
			ret = p.exitValue();
			p.destroy();
		} catch (Exception e) {}
		
		if(ret == 0)
			System.out.println("@IP successfully changed.");
		else
			System.out.println("ret :"+ret+" Important : Unable to change @IP.");
	}
	
	
	
	/**
	 * Get name of every serial ports available
	 * 
	 * TODO this method don't list every serial ports
	 * 
	 * @return portList : Vector of 
	 */
	public Vector<String> getPortList() {
		Enumeration<CommPortIdentifier> portList;
		Vector<String> portVect = new Vector<String>();
		portList = CommPortIdentifier.getPortIdentifiers();

		CommPortIdentifier portId;
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				portVect.add(portId.getName());
			}
		}
		System.out.println(String.valueOf(portVect.size())+" serial ports");

		return portVect;
	}


	/**
	 * Get a list of every files in the tftp directory
	 * if 	the tftp directory doesn't exist, it is created (if root) 
	 * 		and set to be used as a tftp server directory
	 * else an empty vector is returned
	 * 
	 * @param directory TFTP server path
	 * @return A list in a vector of directory file name.
	 * 		if 'files.size()' == 0, 'directory' doesn't exist
	 * 		otherwise, 'directory' exists but 'files' can return
	 * 		"No file available"
	 * 
	 */
	public Vector<String> setAndLoadTftpDir(String directory) {
		Vector<String> files = new Vector<>();
		
	      File f = null;
	      File[] paths;
	      
	      try{      
	         // create new file
	         f = new File(directory);
	         if(!f.exists()){
	        	 /* if executed as root, we can create the tftp directory and set permissions to fit to a tftp server directory (all access)*/
	        	 if(this.isRoot){
	        		 f.mkdir();
	        		 f.setReadable(true, false);
	        		 f.setWritable(true, false);
	        	 }
	        	 else // in this case, files.size equals zero
	        		 return files;
	         }


	         // returns pathnames for files and directory
	         paths = f.listFiles();
	         
	         // for each pathname in pathname array
	         if(paths.length>0){
		         for(File path:paths)
		         {
		        	 files.add(path.getName());
		         }
	         }
	         else
	         {
	        	 files.add("No file available");
	         }

	      }catch(Exception e){
	         // if any error occurs
	         e.printStackTrace();
	      }
		
		return files;
		
	}
}
