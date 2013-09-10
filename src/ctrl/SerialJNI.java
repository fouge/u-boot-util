package ctrl;


/**
 * 
 * 
 * @author fouge
 * @version 0.5 (Linux only)
 */
public class SerialJNI {
	
	/*
	 * Load shared library 
	 * 
	 * v0.5 Linux
	 */
	static{
		System.loadLibrary("serial");
	}

	public SerialJNI (){
		
	}
	
	private native int open_port(String file);
	
	private native int write(char data);
	
	private native char read_char();
	
	private native int close();
	
	
	public void serial_write(String port, String str){
		/*
		 * Open port
		 */
		System.out.println(System.getProperty("os.name").toLowerCase());
		
		if(System.getProperty("os.name").toLowerCase().equals("linux")){
			open_port("/dev/"+port);
		}
		
		/*
		 * Send data
		 */
		int error=0;
		for(int i=0; i<str.length(); i++){
			error += write(str.charAt(i));
		}
		if(error!=str.length()){
			System.err.println("SERIAL : error while communicating with serial device");
		}
		
		
		/*
		 * Close port
		 */
		System.out.println("close : "+String.valueOf(close()));
	}
	
	
}