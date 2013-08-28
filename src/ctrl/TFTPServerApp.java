package ctrl;

import java.io.ByteArrayOutputStream;
import java.io.File;

import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import org.apache.commons.net.tftp.*;

/**
 * @description Class that handle a TFTP server. Used to send (not receive) file 
 * 				to an U-Boot-equipped system. Port used is configurable, if the
 * 				remote system can handle it.
 * 
 * <br>
 * <br>
 * NOTE: This class requires the Apache Jakarta Commons Net TFTP library to
 * work.
 * 
 * @author Jennifer Hodgdon, Poplar ProductivityWare, www.poplarware.com in
 *         conjunction with an article at:
 *         http://www.poplarware.com/udpjava.html
 * @author Cyril Fougeray
 * 			updated to fit with U-Boot Util.
 * 
 */
public class TFTPServerApp {

    /** Port to use for TFTP connection. */
    private int intPort = -1;
    
    /**
     * Size of bogus file to generate -- suggest greater than 512 bytes, and not
     * an even multiple, for optimal testing purposes.
     */
    private final static int GENERATE_BUF_SIZE = 1000;

    /** Buffer for use in input stream. */
    private byte[] inputBuf;

    /** TFTP object to use for send/receive. */
    private TFTP tftp;

    /** Output stream to use for receiving files. */
    private ByteArrayOutputStream outputStream;
    
    /** Path to server directory (default "/tftpboot") */
    private String path = new String("/tftpboot/"); 
    
    
    /**
     * Default constructor: sets up for TFTP, using default port (2269).
     */
    public TFTPServerApp(){
        this(69); // UDP port by default
    }

    /**
     * Constructor: sets up for TFTP, overriding default port.
     * 
     * @param port Port for TFTP
     */
    public TFTPServerApp( int port ){

        try {
            // Read args
            intPort = port;

            // set up TFTP
            tftp = new TFTP();

            tftp.open( intPort );
          

            // set up output stream
            outputStream = new ByteArrayOutputStream();

        } catch( Exception e ) {
        	this.intPort = -1;
            System.out.println( "Important : Exception in opening TFTP connection: "
                    + e.getLocalizedMessage());
        }

    }

    /**
     * Sends a bogus file to the TFTP client, and prints a message on the
     * screen.
     * 
     * @param addr Address to send to
     * @param port Port to send to
     */
    private void sendFile( InetAddress addr, int port, String file ) {
        try {

            int bytesSent = 0;
            int blockNum = 1;
            
            System.out.println("File "+file+" in "+this.path+" is about to be sent.");
            System.out.println(this.path+file);
            File f = new File(this.path+file);
            System.out.println("File size : "+f.length()+"B.");

            this.inputBuf = new byte[(int) f.length()];
            Path p = Paths.get(this.path+file);
            inputBuf = Files.readAllBytes(p);
            
            
            while( bytesSent < inputBuf.length ) {
                /*
                 * Note: this loop assumes that the buffer does not end on an
                 * exact 512 byte boundary! If it did, you would need to send an
                 * extra zero-length data packet at the end. As it is, the
                 * less-than-512 packet signifies that it is the end.
                 */

                // send a data packet with the next bytes from our "file"
                int toSend = 512;
                if( bytesSent + 512 > inputBuf.length ) {
                    toSend = inputBuf.length - bytesSent;
                }

                TFTPDataPacket dPack = new TFTPDataPacket( addr, port,
                        blockNum, inputBuf, bytesSent, toSend );
                tftp.send( dPack );

                blockNum++;
                bytesSent += toSend;

                // get acknowledgement packet
                TFTPPacket pack = tftp.receive();

                /*
                 * Verify it is a acknowledge packet -- Note: should check some
                 * other things, like it being the correct block number.
                 */
                if( pack.getType() != TFTPPacket.ACKNOWLEDGEMENT ) {
                    printPacketError( pack, "read file request" );
                    return;
                }
            } // end of while loop sending file

            System.out.println( "Info : File sent successfully" );
        } catch( Exception e ) {
            System.out.println( "Important : Exception in sending file: " + e.getMessage() );
        }
    }

    
    
    /**
     * Receives a bogus file from the TFTP client, prints the received file on
     * the screen, and resets the output stream.
     * 
     * @param addr Address to send to
     * @param port Port to send to
     */
/*   private void receiveFile( InetAddress addr, int port ) {
        try {

            boolean lastAck = false;
            int blockNum = 0;
            TFTPAckPacket ackPack = new TFTPAckPacket( addr, port, blockNum );
            while( true ) {
                // send acknowledge packet
                tftp.send( ackPack );

                // check to see if we are done
                if( lastAck ) {
                    break;
                }

                // increment block number for next time
                blockNum++;
                ackPack.setBlockNumber( blockNum );

                // get next data packet
                TFTPPacket pack = tftp.receive();

                //Verify it is a data packet -- Note: should check some other things, like it being the correct block number.
                if( pack.getType() != TFTPPacket.DATA ) {
                    printPacketError( pack, "write file request" );
                    return;
                }

                // copy the data into our output stream object

                TFTPDataPacket dPack = (TFTPDataPacket) pack;

                if( dPack.getDataLength() < 1 ) {
                    // empty data packet signifies end
                    break;
                }

                outputStream.write( dPack.getData(), dPack.getDataOffset(),
                        dPack.getDataLength() );

                // see if we are done -- the signal is a too-small data packet

                if( dPack.getDataLength() < TFTPDataPacket.MAX_DATA_LENGTH ) {
                    lastAck = true;
                }
            } // end of while loop

            // When we get here, we received a complete file
            System.out.println( "File received successfully: " );
            System.out.println( outputStream.toString() );
            outputStream.reset();
        } catch( Exception e ) {
            System.out.println( "Exception in receiving file: "
                    + e.getMessage() );
        }

    }
*/

    /**
     * Prints an error message saying a bad packet type has been received, with
     * some other information.
     * 
     * @param pack Packet that is of a bad type
     * @param msg Message saying where error occured
     */
    private void printPacketError( TFTPPacket pack, String msg ) {
        System.out.print( "Unexpected packet received in " + msg + ": ");
        switch( pack.getType() ) {
            case TFTPPacket.ACKNOWLEDGEMENT:
                System.out.println( "ACK packet received" );
                break;
            case TFTPPacket.DATA:
                System.out.println( "Data packet received" );
                break;
            case TFTPPacket.ERROR:
                TFTPErrorPacket ePack = (TFTPErrorPacket) pack;
                System.out.println( "Error packet received: "
                        + ePack.getMessage() );
                break;
            case TFTPPacket.READ_REQUEST:
                System.out.println( "Read Request packet received" );
                break;
            case TFTPPacket.WRITE_REQUEST:
                System.out.println( "Write Request received" );
                break;

        }
    }

    /**
     * Checks to see if there is an incoming TFTP request. Processes it if it is
     * a read or write; prints message on the screen if not; returns if timeout
     * occurs.
     * 
     */
    public void checkForRequests() {
        TFTPPacket pack = null;

        try {
            pack = tftp.receive();
        } catch( Exception e ) {
            /*
             * Normally this should mean a timeout occurred -- do nothing, and
             * return. In a real application, should check the exception type!
             */
            return;
        }

        // check packet type and send/receive file

        switch( pack.getType() ) {
            case TFTPPacket.READ_REQUEST:
                TFTPRequestPacket rPack = (TFTPRequestPacket) pack;
                System.out.println( "Read request received for file "
                        + rPack.getFilename() + " from address "
                        + rPack.getPort() );
                sendFile( pack.getAddress(), pack.getPort(), rPack.getFilename());
                break;

            case TFTPPacket.WRITE_REQUEST:
                TFTPRequestPacket wPack = (TFTPRequestPacket) pack;
                System.out.println( "Write request received for file "
                        + wPack.getFilename() + " from address "
                        + wPack.getPort() );
                System.out.println("Important : Sorry but U-Boot util can't receive files yet.");
               // receiveFile( pack.getAddress(), pack.getPort() );
                break;

            default:
                printPacketError( pack, "checking for requests" );
        }
    }
    
    
    
    
    /**
     * Verify if the server achieved to get the port open
     * 
     * @return true if port is open
     * 			false otherwise
     */
    public boolean portIsOpen(){
		return tftp.isOpen();
	}
    
    /**
     * Get the num of the port
     * 
     * @return num of port in use (-1 if port isn't open)
     */
	public int getPort(){
		return this.intPort;
	}
	
	/**
	 * Get the path of the server
	 * 
	 * @return path of the server in a string (absolute path)
	 */
	public String getServerPath(){
		return this.path;
	}
    
	/**
	 * Close the port of the server (if open)
	 */
    public void close(){
    	if(tftp.isOpen())
		{
			tftp.close();
			System.out.println("Port "+getPort()+" closed.");
			this.intPort = -1;
		}
    	
    }

}
