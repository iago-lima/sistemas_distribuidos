package br.quixada.ufc.server;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class chatTCPServer {
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
	String msg;
	static final ArrayList<Connection> clientes = new ArrayList<Connection>();
	
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
			clientes.add(this);
			
			while(true){
				msg = in.readUTF();
				if(msg.equalsIgnoreCase(":HORA")) {
					SimpleDateFormat hora = new SimpleDateFormat("dd/MM/yyyy hh:mm");
					msg = hora.format(new Date());
					for(Connection cli: clientes) {
							cli.out.writeUTF(clientSocket.getLocalSocketAddress() + ":" + msg);
					}
					msg = "";
				}else {
					for(Connection cli: clientes) {
						cli.out.writeUTF(clientSocket.getLocalSocketAddress() + ":" + msg);
					}
					msg = "";
					
				}
				
			}
			
			
		}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
		} catch(IOException e) {System.out.println("readline:"+e.getMessage());
		} finally{ try {clientSocket.close();}catch (IOException e){/*close failed*/}}
		

	}
}