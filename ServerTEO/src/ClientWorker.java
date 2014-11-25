import java.net.Socket;

import Shared.Encryption;

/**
 * This class impliments the Runnable class to be used as a thread.
 * @author Esben
 *
 */
public class ClientWorker implements  Runnable{
	private Socket connectionSocketConected;
	private Encryption encryption = new Encryption();
	private String incommingJson;
	private GiantSwitch GS = new GiantSwitch();
	
	
	/**
	 * The constructor instantiates the Socket to be used.
	 * @param connectionSocket
	 */
	public ClientWorker(Socket connectionSocket){
		this.connectionSocketConected = connectionSocket;
	}
	
	/**
	 * The run method uses the instantiated Socket to receive data from the client in the form of a byte array.
	 * The byte array is decrypted to a String using an object of the Encryption class.
	 * The String is used in an object of the GiantSwitch to return a String.
	 * The new String is encrypted to a byte array and sent back to the client.
	 */
	public void run(){
		try{
			byte[] incommingByteArray = new byte[500000];
			int count = connectionSocketConected.getInputStream().read(incommingByteArray);
//			ByteArrayInputStream bais = new ByteArrayInputStream(incommingByteArray);
//			DataInputStream inFromClient = new DataInputStream(connectionSocketConected.getInputStream());		
			//Creates an object of the data which is to be send back to the client, via the connectionSocket
//			DataOutputStream outToClient = new DataOutputStream(connectionSocketConected.getOutputStream());
			//Sets client sentence equals input from client
//			incommingJson = inFromClient.toString();		
			
			incommingJson = encryption.decrypt(incommingByteArray);
			
			
			System.out.println("Besked modtaget!");
			System.out.println("Received: " + incommingJson);
			String returnAnswer = GS.GiantSwitchMethod(incommingJson);
			byte[] returnAnswerCrypted = encryption.encrypt(returnAnswer);
//			//Sends the capitalized message back to client!!
			connectionSocketConected.getOutputStream().write(returnAnswerCrypted);
			//BufferedWriter writer = new BufferedWriter(arg0)
		}catch(Exception exception){
			System.err.print(exception);
		}
	}
}
