import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class PhysLayerClient {
	
    public static int decode(int fiveBit)
    {
        switch (fiveBit)
        {
            case 0b11110:
                return 0b0000;
            case 0b01001:
                return 0b0001;
            case 0b10100:
                return 0b0010;
            case 0b10101:
                return 0b0011;
            case 0b01010:
                return 0b0100;
            case 0b01011:
                return 0b0101;
            case 0b01110:
                return 0b0110;
            case 0b01111:
                return 0b0111;
            case 0b10010:
                return 0b1000;
            case 0b10011:
                return 0b1001;
            case 0b10110:
                return 0b1010;
            case 0b10111:
                return 0b1011;
            case 0b11010:
                return 0b1100;
            case 0b11011:
                return 0b1101;
            case 0b11100:
                return 0b1110;
            case 0b11101:
                return 0b1111;
            default:
                throw new RuntimeException("Bad signal received.");
        }
}

	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("18.221.102.182", 38002);
		
		System.out.println("Connected to server.");

		InputStream is = socket.getInputStream();
        double baseline = 0;

        //server sending preamble 64 signals and read
        for(int i=0;i<63;i++) {
            baseline+=is.read();
        }
        int lastSignal = is.read();
        baseline+=lastSignal;
        baseline/=64.0;
        System.out.println("Baseline established from preamble: "+baseline);

        byte[] MessageReturn = new byte[32];

        int[] signal = new int[320];
        for(int i=0;i<320;i++)
        {
          int thisSignal = is.read();
          if(thisSignal<baseline)
              signal[i]=0;
          else
              signal[i]=1;
        }

        int[] Data = processSignal(signal,lastSignal);

        for(int i=0;i<Data.length/10;i++)
        {
            int firstHalfByte = 0;
            for(int j=0;j<5;j++)
            {
                firstHalfByte = (firstHalfByte<<1)+Data[i*10+j];
            }

            int secondHalfByte = 0;
            for(int j=5;j<10;j++)
            {
                secondHalfByte = (secondHalfByte<<1)+Data[i*10+j];
            }

            firstHalfByte = decode(firstHalfByte);
            secondHalfByte = decode(secondHalfByte);

            MessageReturn[i] = (byte)((firstHalfByte<<4)+secondHalfByte);
        }
        System.out.print("Received 32 bytes: ");
        for(int i=0;i<MessageReturn.length;i++)
        {
            System.out.print(String.format("%h",MessageReturn[i]).toUpperCase());
        }
        socket.getOutputStream().write(MessageReturn);
        int respond = is.read();

        System.out.println();
        if(respond==1)
            System.out.println("Respond good.");

        else
            System.out.println("Respond error.");

        System.out.println("Disconnected from server.");

    }



   private static int[] processSignal(int[] signal,int lastSignal)
    {
        int[] Data = new int[signal.length];

        for(int i=0;i<signal.length;i++)
        {
            if(signal[i]==lastSignal)
                Data[i]=0;
            else
                Data[i]=1;
            lastSignal=signal[i];
        }
        return Data;
    }



	}


