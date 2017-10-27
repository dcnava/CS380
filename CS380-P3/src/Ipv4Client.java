import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Ipv4 {

	public static void main(String[] args) throws IOException{
		//packets in Ipv4
		byte version = 0100; //version 4 
		byte hLen = 0101; // header length 
		byte tos = 0; // type of service -don't implement 
		byte length =(byte) 10100 ;//header + data
		byte ident = 0; //don't implement		
		byte flags = 010;//no fragmentation
		byte offset = 0;//don't implement
		byte ttl = (byte) 00110010;//assume time to live of 50
		byte protcol = 0110;//TCP protocol 6
		byte sourceAddr[] = new byte[4];//IP address of choice
				sourceAddr[0] =0;
				sourceAddr[1] =0;
				sourceAddr[2] =0;
				sourceAddr[3] =0;
				
		byte destAddr[] = new byte[4];//IP address of server
				destAddr[0]= (byte) 10010;//18
				destAddr[1]= (byte) 11011101;//221
				destAddr[2]= (byte) 1100110;//102
				destAddr[3]= (byte) 10110110;//182
				
		Socket socket = new Socket("18.221.102.182",38003);
		
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		OutputStream os = socket.getOutputStream();
		BufferedReader br = new BufferedReader(isr);
		ByteBuffer bt = ByteBuffer.allocate(2);
		
		short sizeOfData = 2;
		byte[] packet = new byte[sizeOfData];
	}
	
		
	//checksum method
	public static byte[] checksum(byte[] packet){
		int[] header = new int[10];
		int sum=0;
		for(int i=0; i<10; i++){
			int first = ((int)packet[2*i]<<8)&0xFF00;
			int sec = packet[2*i+1]& 0xFF;
			header[i]= first + sec;
			sum +=header[i];
		}
		sum = ~((sum & 0xFFFF)+(sum>>16));
		
		return intToBytes(sum);
	}

	//converts the integers from checksum to bytes
	private static byte[] intToBytes(int integer) {
		byte[] bytes = new byte[4];
		for(int i=0; i<4; i++){
			bytes[i]=(byte) (integer >> (8*i));
		}
		return bytes;
	}

}
