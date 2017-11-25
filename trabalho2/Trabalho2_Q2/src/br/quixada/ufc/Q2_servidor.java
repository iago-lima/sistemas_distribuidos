package br.quixada.ufc;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Q2_servidor {
	public static void main(String args[]) {
		System.out.println("Servidor Rodando!");
		DatagramSocket socket = null;
		int portServ = 5555;
		try {
			socket = new DatagramSocket(portServ, InetAddress.getByName("0.0.0.0"));
			socket.setBroadcast(true);
			while (true) {
				byte[] receiveArray = new byte[1000];
				DatagramPacket receivePacket = new DatagramPacket(receiveArray, receiveArray.length);
				socket.receive(receivePacket);
				String msg = new String(receivePacket.getData(), 0, receivePacket.getLength());
				InetAddress ipCliente = receivePacket.getAddress();
				System.out.println("Mensagem recebida: " + msg + " de " + ipCliente);
				if(msg.equals("calc")) {
					String retorno = "Calculadora\n"
								   + " Digite uma operação (Ex: 2 + 2): ";
					enviar(receivePacket, socket, retorno);
					
					// Espera a chegada de uma msg (bloqueante)
					socket.receive(receivePacket);
					// Armazena a mensagem que chegou no formato String
					String operacao = new String(receivePacket.getData(), 0, receivePacket.getLength());
					//Separando a operação em varios pedaços
					String op[] = operacao.split(" ");
					
					if(op[1].equals("+")) {
						double resultado = Integer.parseInt(op[0]) + Integer.parseInt(op[2]);
						String env = "Resultado: " + Double.toString(resultado);
						enviar(receivePacket, socket, env);
					}
					if(op[1].equals("-")) {
						double resultado = Integer.parseInt(op[0]) - Integer.parseInt(op[2]);
						String env = "Resultado: " + Double.toString(resultado);
						enviar(receivePacket, socket, env);
					}
					if(op[1].equals("*")) {
						double resultado = Integer.parseInt(op[0]) * Integer.parseInt(op[2]);
						String env = "Resultado: " + Double.toString(resultado);
						enviar(receivePacket, socket, env);
					}
					if(op[1].equals("/")) {
						if(op[2].equals("0")) {
							String env = "Não é possível realizar divisão por zero!\n";
							enviar(receivePacket, socket, env);
						}else {
							double resultado = Integer.parseInt(op[0]) / Integer.parseInt(op[2]);
							String env = "Resultado: " + Double.toString(resultado);
							enviar(receivePacket, socket, env);
						}
					}	
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
		}
	}
	public static void enviar(DatagramPacket data, DatagramSocket socServ, String msg) {
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
