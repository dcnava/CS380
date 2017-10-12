import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;



public class EX2Client {

	public static void main(String[] args) throws IOException {
		//Socket which contains the port and ip address being connected to
		Socket socket = new Socket("18.221.102.182",38102);
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		//array which will receive 100 bytes 
		byte[] bytesReceived = new byte[100];
		
		System.out.println("Connected to server.\n" +
							"Recieved bytes: ");
		
		System.out.println("Generated CRC32:" );

		System.out.println("Disconnected from server.");
		
	}

}
