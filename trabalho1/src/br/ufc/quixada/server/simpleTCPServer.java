package br.ufc.quixada.server;

import java.io.*;
import java.net.*;
import java.sql.Date;
import java.text.SimpleDateFormat;

class ServerTCP {
	public static void main(String argv[]) {
		long inicio = System.currentTimeMillis();
		ServerSocket listenSocket;

		String msgRequisicao;
		try {

			listenSocket = new ServerSocket(5002);
			System.out.println("Esperando conexões");
			int id = 0;
			while (true) {
				id++;
				Socket connectionSocket = listenSocket.accept();

				DataInputStream inFromClient = new DataInputStream(connectionSocket.getInputStream());
				DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

				msgRequisicao = inFromClient.readUTF();

				if(msgRequisicao.equals("\\UPTIME")) {
					long fim  = System.currentTimeMillis();
					outToClient.writeUTF(new SimpleDateFormat("mm:ss").format(new Date(fim - inicio)));
				}else if(msgRequisicao.equals("\\REQNUM")) {
					outToClient.writeUTF(Integer.toString(id));									
				}else {
					outToClient.writeUTF("Error!");
				}				

				connectionSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}