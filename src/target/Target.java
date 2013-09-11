package target;

public class Target {

	private SerialJNI serIface;
	
	private String port;
	
	/**
	 * Constructor, default port set to ACM0
	 */
	public Target(){
		this("ttyACM0");
	}

	
	public Target(String port){
		this.port = port;
		this.serIface = new SerialJNI();
	}
	
	
	public void setIP(String ip) {
		
		// TODO stop reading
		
		
		/* send data to the target */
		this.serIface.serial_write(port, "\r\nsetenv ipaddr "+ip+" \r\n");
	}

}
