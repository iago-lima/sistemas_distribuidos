package br.quixada.ufc.cliente;
import java.io.*;
import java.net.*;

class SimpleTCPClient {
	public static void main(String argv[]) {
		// Declara as mensagens da aplicação
		String requisicao;
		String resposta;

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		// Declara o socket
		Socket clientSocket;
		try {
			// Instancia o socket
			clientSocket = new Socket("localhost", 6789);
			// Instancia objeto que escreve no buffer de saída
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			// Instancia objeto que lê buffer de entrada
			DataInputStream inFromServer = new DataInputStream(clientSocket.getInputStream());
			System.out.println("Escreva MSG a ser enviada:");
			// Ler entrada do teclado
			requisicao = inFromUser.readLine();
			// Enviar mensagem digitada para o servidor
			outToServer.writeUTF(requisicao + '\n');
			// Esperar resposta do servidor
			resposta = inFromServer.readUTF();
			System.out.println("FROM SERVER: " + resposta);
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
