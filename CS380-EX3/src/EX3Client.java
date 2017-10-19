import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class EX3Client {

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("18.221.102.182",38103);
		System.out.println("Connected to server.");
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		//read the incoming bytes from the server
		int numOfBytes = is.read();
		
		//declaring an array that will store the bytes
		int[] bytesRecieved = new int[numOfBytes];
		
		//store the bytes into an array
		for(int index = 0; index < numOfBytes; index++){
			bytesRecieved[index] = is.read();
		}
		
	}
	//checksum algorithm
	public static short checksum(byte[] b){
		long sum = 0;
		int count = 0;
		
		while(count < b.length){
			
			short sixteenBitNum=0;
			sum += sixteenBitNum;
			if((sum & 0xFFFF0000) == 0){
				//carry occurred. so wrap around
				sum &= 0xFFFF;
				sum++;
			}
		}
		return (short) ~(sum & 0xFFFF);
	}

}
