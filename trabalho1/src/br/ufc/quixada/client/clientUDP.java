package br.ufc.quixada.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

class ClientUDP  {

	public static void main(String args[]) {			

		DatagramSocket clientSocket = null;
		try {
			clientSocket = new DatagramSocket();

			String ip;
			int port = 5002;
			ip = JOptionPane.showInputDialog(null,"Digite o endereço IP:");
			port = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite a porta:"));

			String msg;

			byte[] sendArray;

			while(true) {
				
				 msg = JOptionPane.showInputDialog(null,"Digite um comando: \\UPTIME, \\REQNUM, \\CLOSE");
				 msg = msg.toUpperCase();
				
				if(msg.equals("\\UPTIME") || 
						msg.equals("\\REQNUM")) {
					sendArray = msg.getBytes();
					break;
				}
				else if(msg.equals("\\CLOSE")) 
					System.exit(0);
				else {
					JOptionPane.showMessageDialog(null, "Comando Errado");
				}
			}


			InetAddress IpServidor = InetAddress.getByName(ip);

			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, port);
			clientSocket.send(sendPacket);
			System.out.println("Pacote enviado!");

			byte[] receiveData = new byte[1024];

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			clientSocket.receive(receivePacket);

			String recebido = new String(receivePacket.getData()); 

			System.out.println("Pacote UDP recebido...");
			
			System.out.println(recebido);

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Fecha o socket 
			if (clientSocket != null) clientSocket.close();
		}
	}
}