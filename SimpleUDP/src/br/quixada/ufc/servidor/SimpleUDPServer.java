package br.quixada.ufc.servidor;

import java.io.*;
import java.net.*;

class SimpleUDPServer {
	public static void main(String args[]) {
		// Declara socket UDP
		DatagramSocket serverSocket = null;
		try {
			// Instancia socker UDP (define que ele deve usar a porta 6789)
			serverSocket = new DatagramSocket(6789);
			System.out.println("Servidor em execução!");
			// Cria array de bytes que será enviado para o servidor
			byte[] receiveData = new byte[1024];
			int id = 0;
			// Cria loop para receber mais de uma msg
			while (true) {
				id++;
				System.out.println("Esperando Msg " + id + " ...");
				// Cria pacote para receber a mensagem UDP
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
				// Espera a chegada de uma msg (bloqueante)
				serverSocket.receive(request);
				// Armazena a mensagem que chegou no formato String
				String sentence = new String(request.getData(), 0, request.getLength());
				// Mostra informações do cliente
				System.out.println("Cliente: " + request.getAddress().getHostAddress() + " - Porta: " + request.getPort());
				System.out.println("Msg: " + sentence);
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
}