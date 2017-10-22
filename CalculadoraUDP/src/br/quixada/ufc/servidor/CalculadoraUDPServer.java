package br.quixada.ufc.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class CalculadoraUDPServer {
	public static void main(String[] args) {
		DatagramSocket serverSocket = null;
		
		try {
			// Instancia socker UDP (define que ele deve usar a porta 6789)
			serverSocket = new DatagramSocket(9876);
			System.out.println("Servidor em execucão!");
			
			// Cria array de bytes recebe o pacote do cliente
			byte[] receiveData = new byte[1024];
			
			while (true) {
				// Cria pacote para receber a mensagem UDP
				DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
				
				// Espera a chegada de uma msg (bloqueante)
				serverSocket.receive(request);
				// Armazena a mensagem que chegou no formato String
				String msgCliente = new String(request.getData(), 0, request.getLength());
				
				//Separando a operação em varios pedaços
				String operacao[] = msgCliente.split(" ");
				int calculo; 
				String msgServer;
				
				if(operacao[1].equals("+")) {
					calculo = Integer.parseInt(operacao[0]) + Integer.parseInt(operacao[2]);
					msgServer = "Resultado: " + Integer.toString(calculo);
					send(request, serverSocket, msgServer);
				}
				if(operacao[1].equals("-")) {
					calculo = Integer.parseInt(operacao[0]) - Integer.parseInt(operacao[2]);
					msgServer = "Resultado: " + Integer.toString(calculo);
					send(request, serverSocket, msgServer);
				}
				if(operacao[1].equals("*")) {
					calculo = Integer.parseInt(operacao[0]) * Integer.parseInt(operacao[2]);
					msgServer = "Resultado: " + Integer.toString(calculo);
					send(request, serverSocket, msgServer);
				}
				if(operacao[1].equals("/")) {
					if(operacao[2].equals("0")) {
						msgServer = "Não é possível realizar divisão por zero!\n";
						send(request, serverSocket, msgServer);
					}
					calculo = Integer.parseInt(operacao[0]) / Integer.parseInt(operacao[2]);
					msgServer = "Resultado: " + Integer.toString(calculo);
					send(request, serverSocket, msgServer);
				}
				
			}
		} catch (Exception e) {}
	}
	public static void send(DatagramPacket data, DatagramSocket socServ, String msg) {
		InetAddress ip;
		ip = data.getAddress();
		int porta = data.getPort();
		byte[] send = msg.getBytes();
		
		DatagramPacket sendPacket = new DatagramPacket(send, send.length, ip, porta);
		try {
			socServ.send(sendPacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Enviado!");
	}
}