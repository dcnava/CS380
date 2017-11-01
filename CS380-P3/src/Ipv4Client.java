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
		byte version = 0b0100; //version 4 
		byte hLen = 0b0101; // header length 
		byte tos = 0b0; // type of service -don't implement 
		byte length =0b10100 ;//header + data
		byte ident = 0b0; //don't implement		
		byte flags = 0b010;//no fragmentation
		byte offset = 0b0;//don't implement
		byte ttl = 0b00110010;//assume time to live of 50
		byte protocol = 0b0110;//TCP protocol 6
		byte sourceAddr[] = new byte[4];//IP address of choice
				sourceAddr[0] =(byte) 11000000;//192
				sourceAddr[1] =(byte) 10101000;//168
				sourceAddr[2] =(byte) 0001;//1
				sourceAddr[3] =(byte) 1011011;//91
				
		byte destAddr[] = new byte[4];//IP address of server
				destAddr[0]= (byte) 1010;//10	00010010;18
				destAddr[1]= (byte) 1101110;//110	11011101;221
				destAddr[2]= (byte) 1111;//15	01100110;102
				destAddr[3]= (byte) 10100;//20	10110110;182
				
		Socket socket = new Socket("18.221.102.182",38003);
		
		InputStream is = socket.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		OutputStream os = socket.getOutputStream();
		BufferedReader br = new BufferedReader(isr);
		ByteBuffer bt = ByteBuffer.allocate(2);
		

		//making the packets
		for(int counter = 1; counter < 13; counter++){
			short sizeOfData = 2;
			byte[] packet = new byte[20 + sizeOfData];
			byte[] data = new byte[sizeOfData];
			//contains version and header length together
			packet[0]= (byte) ((version << 4)|hLen );
			
			//tos not implemented
			packet[1]=tos;
			
			//length broken up into two bytes
			byte L1 = (byte) (length >>8);
			byte L2 = length;
			packet[2]=L1;
			packet[3]=L2;
			
			//identity
			packet[4]= ident;
			packet[5]= ident;
			
			//flags and upper part of offset
			packet[6]=(byte) ((flags<<5) | offset);
			
			//second half of the offset
			packet[7]= offset;
			
			//ttl
			packet[8]=ttl;
			
			//protocol
			packet[9]=protocol;
			
			//checksum
			packet[10]= 0;
			packet[11]= 0;
			
			//source IP 
			for(int i = 12; i<16; i++)
				for(int j = 0; j<sourceAddr.length;j++ )
					packet[i]=sourceAddr[j];
			
			//destination IP
			for(int i = 16; i<20; i++)
				for(int j = 0; j<destAddr.length;j++ )
					packet[i]=destAddr[j];
			
			//copy the data onto the packet
			for(int i = 20; i<data.length; i++)
				for(int j = 0; j<destAddr.length;j++ )
					packet[i]=data[j];
			
			os.write(packet);
			System.out.println("data length: " + (counter * sizeOfData));
		}
		
		
}
	
	//checksum method
	public static short checksum(short[] packet){
		int[] header = new int[10];
		int sum=0;
		for(int i=0; i<10; i++){
			int first = ((int)packet[2*i]<<8)&0xFF00;
			int sec = packet[2*i+1]& 0xFF;
			header[i]= first + sec;
			sum +=header[i];
		}
		sum = ~((sum & 0xFFFF));
		
		return (short) sum;
	}

}	
