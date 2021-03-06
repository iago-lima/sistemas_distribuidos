package br.quixada.ufc.server;
import java.net.*;
import java.util.Scanner;
import java.io.*;
public class chatServer {
	@SuppressWarnings("resource")
	public static void main (String args[]) {
		try{
			int serverPort = 1222; // the server port
			ServerSocket listenSocket = new ServerSocket(serverPort);
			while(true) {
				Socket clientSocket = listenSocket.accept();
				@SuppressWarnings("unused")
				Connection c = new Connection(clientSocket);
			}
		} catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
	}
}
class Connection extends Thread {
	DataInputStream in;
	DataOutputStream out;
	Socket clientSocket;
	public Connection (Socket aClientSocket) {
		try {
			clientSocket = aClientSocket;
			in = new DataInputStream( clientSocket.getInputStream());
			out =new DataOutputStream( clientSocket.getOutputStream());
			this.start();
		} catch(IOException e) {System.out.println("Connection:"+e.getMessage());}
	}
	public void run(){
		try {			                 // an echo server

			String data = in.readUTF();	                  // read a line of data from the stream
			System.out.println(clientSocket.getLocalPort() + " #" + data);
			@SuppressWarnings("resource")
			Scanner leituraTeclado = new Scanner(System.in);
			System.out.print("# ");
			String msg;
			msg = leituraTeclado.nextLine();
			out.writeUTF(msg);
			
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
		

	}
}