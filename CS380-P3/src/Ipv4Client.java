import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Ipv4Client{

	public static void main(String[] args)  throws IOException{
		//packets in Ipv4
		byte version = 0b0100; //version 4 
		byte hLen = 0b0101; // header length 
		byte tos = 0b0; // type of service -don't implement 
		byte length =0b10100 ;//header + data
		byte ident = 0b0; //don't implement		
		byte flags = 0b011;//no fragmentation
		byte offset = 0b0;//don't implement
		byte ttl = 0b00110010;//assume time to live of 50
		byte protcol = 0b0110;//TCP protocol 6
		byte checksum ;//checksum for header
		byte sourceAddr;//IP address of choice
		byte destAddr;//IP address of server
		byte data;//
		
		Socket socket = new Socket("18.221.102.182", 38003);
		
		InputStream is = socket.getInputStream();
		OutputStream os = socket.getOutputStream();
		
		

	}

}
