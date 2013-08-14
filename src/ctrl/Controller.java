package ctrl;

/**
 * @author Cyril Fougeray
 * @version 0.1
 * 
 */

import view.View;

public class Controller {

	private View view;
	
	private ThreadServer threadServer;
	
	public Controller(View view){
		this.view = view;
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
}
