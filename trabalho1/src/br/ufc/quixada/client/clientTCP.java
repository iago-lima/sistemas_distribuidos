package br.ufc.quixada.client;

import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

class ClientTCP {
	public static void main(String argv[]) {
		String resposta;
		
		Socket clientSocket;
		try {

			String ip;
			int port = 5002;
			ip = JOptionPane.showInputDialog(null,"Digite o endereço IP:");
			port = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite a porta:"));

			clientSocket = new Socket(ip, port);
			
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());

			String msg;

			while(true) {

				msg = JOptionPane.showInputDialog(null,"Digite um comando: \\UPTIME, \\REQNUM, \\CLOSE");
				msg = msg.toUpperCase();

				if(msg.equals("\\UPTIME") || 
				   msg.equals("\\REQNUM")) 					
					break;

				else if(msg.equals("\\CLOSE")) 
					System.exit(0);

				else 
					JOptionPane.showMessageDialog(null, "Comando Errado");

			}

			outToServer.writeUTF(msg);
			resposta = inFromServer.readUTF();
			System.out.println("FROM SERVER: " + resposta);
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}