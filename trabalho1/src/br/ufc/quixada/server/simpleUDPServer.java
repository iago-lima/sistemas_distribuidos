package br.ufc.quixada.server;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

class ServerUDP{
	public static void main(String args[]) {
		long inicio = System.currentTimeMillis();
		
		DatagramSocket serverSocket = null;
		try {
			serverSocket = new DatagramSocket(5003);
			System.out.println("Servidor em execução!");
			byte[] receiveData = new byte[1024];
			int id = 0;
			while (true) {
				id++;				
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);

				serverSocket.receive(request);

				String sentence = new String(request.getData(), 0, request.getLength());

				System.out.println("Cliente: " + request.getAddress().getHostAddress() + " - Porta: " + request.getPort());

				if(sentence.equals("\\UPTIME")) {
					long fim  = System.currentTimeMillis();
					sendPacket(request, serverSocket, new SimpleDateFormat("mm:ss").format(new Date(fim - inicio)));
				}else if(sentence.equals("\\REQNUM")) {
					sendPacket(request, serverSocket, Integer.toString(id));					
				}else {
					sendPacket(request, serverSocket, "");
				}
			}
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Fecha o socket 
			if (serverSocket != null) serverSocket.close();
		}
	}

	public static void sendPacket(DatagramPacket request, DatagramSocket serverSocket, String msg) {
		InetAddress ipAdress = request.getAddress();
		int port = request.getPort();

		byte[] send = msg.getBytes();

		DatagramPacket sendPacket = new DatagramPacket(send, send.length, ipAdress, port);

		try {
			serverSocket.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Enviado\n");
	}

}