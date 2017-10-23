package br.quixada.ufc.client;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class chatTCPClient {
	@SuppressWarnings("resource")
	public static void main (String args[]) {
		// arguments supply message and hostname
		
		
		try{
			int serverPort = 1222;
			Scanner leituraTeclado = new Scanner(System.in);
			final Socket soc = new Socket(args[0], serverPort);
			new Thread() {
				/**
				 * 
				 */
				@Override
				public void run() {
					try {
						DataInputStream inFromServer = new DataInputStream(soc.getInputStream());
						
						while(true) {
							String msg = inFromServer.readUTF();
							System.out.println(msg);
						}
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}.start();;	
			
			try {
				DataOutputStream outToServer = new DataOutputStream(soc.getOutputStream());
				System.out.print("Comece a enviar mensagens: \n");
				while(true) {
					String msgCli = leituraTeclado.nextLine();
					outToServer.writeUTF(msgCli);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}catch (UnknownHostException e){
			System.out.println("Socket:"+e.getMessage());
		}catch (EOFException e){
			System.out.println("EOF:"+e.getMessage());
		}catch (IOException e){
			System.out.println("readline:"+e.getMessage());
		}
	}
}