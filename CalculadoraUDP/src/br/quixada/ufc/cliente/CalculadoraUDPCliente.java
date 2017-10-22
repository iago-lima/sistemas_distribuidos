package br.quixada.ufc.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javax.swing.JOptionPane;

class CalculadoraUDPCliente {
	public static void main(String[] args) {
		DatagramSocket clientSocket = null;
		
		try {
			System.out.print("Digite um número a operação e um número");
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			String msgUsuario = inFromUser.readLine();
			
			// Cria o socker UDP
			clientSocket = new DatagramSocket();

			// Cria array de bytes que será enviado para o servidor
			byte[] sendArray = msgUsuario.getBytes();
			byte[] receiveData = new byte[1024];
			
			// Ip e porta do servidor
			String ip = JOptionPane.showInputDialog("Digite o IP do Servidor");
			InetAddress IpServidor = InetAddress.getByName(ip);
			int portaServidor = Integer.parseInt(JOptionPane.showInputDialog("Digite a porta do servidor"));
			
			// Cria pacote UDP (array, tamanho do array, ip, porta)
			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, IpServidor, portaServidor);
			
			//Enviando pacote
			clientSocket.send(sendPacket);
			
			//Criando pacote de recebimento
			DatagramPacket msgServidor = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(msgServidor);
			
			//Convertendo o que chegou do servidor em String
			msgUsuario = new String(msgServidor.getData(), 0, msgServidor.getLength());
			System.out.println(msgUsuario);
			clientSocket.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}