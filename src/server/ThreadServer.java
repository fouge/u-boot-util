package server;

/**
 * Thread of the TFTP server
 * 
 * @author Cyril Fougeray (cyril.fougeray@gmail.com)
 * @version 0.1
 */
public class ThreadServer extends Thread {
	private TFTPServerApp server;
	private boolean serverRunning;
	
	/**
	 * 
	 * @param port : port used to start the server
	 */
	public ThreadServer(int port){
		System.out.println("Starting new thread for TFTP server (port "+String.valueOf(port)+").");
		this.server = new TFTPServerApp(port);
		if(this.server.portIsOpen())
			this.serverRunning = true;
		else
			this.serverRunning = false;
		
		this.start();
	}
	
	public void run() {
		    while(this.serverRunning){
				this.server.checkForRequests();
		    }
	}
	  
	public void setState(boolean run){
		this.serverRunning = run;
	}
	public boolean getRunningState(){
		return this.serverRunning;
	}
	
	public synchronized void terminate(){
		this.server.close();
	}
	
	public int getPort(){
		return this.server.getPort();
	}
}
