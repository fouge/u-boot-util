package ctrl;

/**
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

import view.View;

public class Controller {

	private View view;
	
	private ThreadServer threadServer;
	
	Enumeration<NetworkInterface> interfaces;
	
	public Controller(View view){
		this.view = view;
		
		try {
			this.interfaces = NetworkInterface.getNetworkInterfaces();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/*
	 * Start or interrupt the thread which manage TFTP server
	 * return :
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
	
	public int getCurrentPort(){
		if(this.threadServer==null)
			return -1;
		else
			return this.threadServer.getPort();
	}

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
	 * 
	 * @param nic : Network Interface Card
	 * @param ipServer
	 * @param passwd 
	 */
	public void setIp(String nic, String ipServer, String passwd) {
		
		System.out.println("Modifying "+nic+", set IP address to "+ipServer);
		
		// TODO execute command ifconfig with sudo
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
}
