package br.quixada.ufc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

class Q2_cliente {
	public static void main(String args[]) {
		DatagramSocket socket = null;
		String msg = null;
		int port = 5555;

		try {
			// Digitar mensagem usando o teclado
			BufferedReader entradaDoUsuario = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Digite o serviço desejado: ");
			msg = entradaDoUsuario.readLine();

			socket = new DatagramSocket();
			socket.setBroadcast(true);
			byte[] sendArray = msg.getBytes();
			InetAddress broadcast = InetAddress.getByName("255.255.255.255");

			DatagramPacket sendPacket = new DatagramPacket(sendArray, sendArray.length, broadcast, port);
			socket.send(sendPacket);

			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();

				if (networkInterface.isLoopback() || !networkInterface.isUp()) {
					continue;
				}
				for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
					broadcast = interfaceAddress.getBroadcast();
					if (broadcast == null) {
						continue;
					}

					try {
						sendPacket = new DatagramPacket(sendArray, sendArray.length, broadcast, 8888);
						socket.send(sendPacket);
						//Criando comunicação com o servidor			
						if(msg.equals("calc")) {
							byte[] receiveData = new byte[1024];
							DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
							
							//Reconhecimento em rede
							socket.receive(request);
							String resposta = new String(request.getData(), 0, request.getLength());
							InetAddress ipServ = request.getAddress();
							String respostaIp = ipServ.getHostAddress();
							int respostaPorta = request.getPort();
							System.out.println("Ip do servidor: " + respostaIp + ", porta: " + respostaPorta);
							System.out.print(resposta);

							//Lendo operação
							String operacao = entradaDoUsuario.readLine();
							byte[] sendOperacao = operacao.getBytes();
							
							//InetAddress IpServidor = InetAddress.getByName(respostaIp);                //Se nao funcionar MUDAR ipServ
							DatagramPacket sendPacketUniCast = new DatagramPacket(sendOperacao, sendOperacao.length, ipServ, respostaPorta);
							socket.send(sendPacketUniCast);
							socket.receive(request);
							String respostaOperacao = new String(request.getData(), 0, request.getLength());
							System.out.println(respostaOperacao);
						}
					} catch (Exception e) {
					}
				}
			}

		} catch (SocketException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null)
				socket.close();
		}
	}
}