import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;


public class ClientWorker implements  Runnable{
	private Socket connectionSocketConected;
	private Encryption encryption = new Encryption();
	private String incomingJson;
	
	ClientWorker(Socket connectionSocket){
		this.connectionSocketConected = connectionSocket;
	}
	
	public void run(){
		try{
			byte[] incommingByteArray = new byte[500000];
			int count = connectionSocketConected.getInputStream().read(incommingByteArray);
//			ByteArrayInputStream bais = new ByteArrayInputStream(incommingByteArray);
			DataInputStream inFromClient = new DataInputStream(connectionSocketConected.getInputStream());		
			//Creates an object of the data which is to be send back to the client, via the connectionSocket
			DataOutputStream outToClient = new DataOutputStream(connectionSocketConected.getOutputStream());
			//Sets client sentence equals input from client
			//incomingJson = inFromClient.readLine();			
			
			String ny = encryption.decrypt(incommingByteArray);
			
			
			System.out.println("Besked modtaget!");
			System.out.println("Received: " + ny);
			String returnAnswer = GS.GiantSwitchMethod(ny);
			byte[] returnAnswerCrypted = encryption.encrypt(returnAnswer);
//			//Sends the capitalized message back to client!!
			outToClient.writeBytes(returnAnswerCrypted + "\n");
			//BufferedWriter writer = new BufferedWriter(arg0)
		}catch(Exception exception){
			System.err.print(exception);
		}
	}
}
