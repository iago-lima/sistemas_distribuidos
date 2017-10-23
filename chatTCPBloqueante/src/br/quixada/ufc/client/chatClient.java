package br.quixada.ufc.client;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class chatClient {
	public static void main (String args[]) {
		// arguments supply message and hostname
		Socket s = null;
		@SuppressWarnings("resource")
		Scanner leituraTeclado = new Scanner(System.in);
		try{
			int serverPort = 1222;
			while(true){
				s = new Socket(args[0], serverPort);   
				DataInputStream in = new DataInputStream( s.getInputStream());
				DataOutputStream out =new DataOutputStream( s.getOutputStream());
				System.out.print("#");
				String msg = leituraTeclado.nextLine();
				out.writeUTF(msg);      	// UTF is a string encoding see Sn. 4.4
				String data = in.readUTF();	    // read a line of data from the stream
				System.out.println(s.getLocalPort() + "# "+ data) ;
			}
		}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){System.out.println("readline:"+e.getMessage());
		}finally {if(s!=null) try {s.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
     }
}