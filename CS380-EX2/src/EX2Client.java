import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.zip.CRC32; 


public class EX2Client {

	public static void main(String[] args) throws IOException {
		//Socket which contains the port and ip address being connected to
		Socket socket = new Socket("18.221.102.182",38102);
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		//array which will receive 100 bytes 
		byte[] bytesReceived = new byte[100];
		
		//reading only half of the bytes and then the other half
		for(int index =0;index <100; index++ ){
			int firstByte = is.read();
			int secondByte = is.read();
			firstByte = firstByte << 4;
			bytesReceived[index]= (byte)(firstByte | secondByte); 
		}
		
		System.out.println("Connected to server.\n" +
							"Recieved bytes: ");
		for(int index =0;index <100; index++ ){
			System.out.print(String.format("%x",bytesReceived[index]).toUpperCase());
			if(index%10 == 9)
				System.out.println();
			
		}
		

		//Create the CRC32 which will detect an error from the data
		CRC32 crc32 = new CRC32();
		crc32.update(bytesReceived);
		int value =(int)crc32.getValue();
		ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
		buffer.putInt(value);
		os.write(buffer.array());
		
		System.out.println("\nGenerated CRC32:" + String.format("%x", value).toUpperCase());
		
		/*When the server constructs the same CRC32 code then the response 
		 * is good which is a byte value 1*/
		int byteResponse = is.read();
		if(byteResponse == 1)
			System.out.println("Response good.");
		else
			System.out.println("Response bad.");

		System.out.println("Disconnected from server.");
		
		
	}
}
