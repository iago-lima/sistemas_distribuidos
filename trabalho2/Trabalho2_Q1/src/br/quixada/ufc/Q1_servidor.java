package br.quixada.ufc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Request;

public class Q1_servidor {
	public static Reply resp(Request req) {
		double respOp = 0;
		if(req.getOp().equals(Request.Operacao.SOM)) {
			respOp = req.getN1() + req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.SUB)) {
			respOp = req.getN1() - req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.MUL)) {
			respOp = req.getN1() * req.getN2();
		}
		if(req.getOp().equals(Request.Operacao.DIV)) {
			respOp = req.getN1() / req.getN2();
		}
		Reply.Builder build = Reply.newBuilder();
		build.setId(req.getId());
		build.setRes(respOp);
		return build.build();
	}
	
	public static void main(String[] args) {
		DatagramSocket socketServ = null;
		System.out.println("Servidor Rodando!");
		try {
			socketServ = new DatagramSocket(6789);
			byte[] reciveCliente = new byte[1024];
			
			while(true) {	
			DatagramPacket receivePack = new DatagramPacket(reciveCliente, reciveCliente.length);
			socketServ.receive(receivePack);
			InetAddress ipCli = receivePack.getAddress();
			int portCli = receivePack.getPort();
			
			ByteArrayInputStream inCliente = new ByteArrayInputStream(reciveCliente);
			ByteArrayOutputStream outCliente = new ByteArrayOutputStream();
			
			//Protocol Buffer
			Request request = Request.parseDelimitedFrom(inCliente);
			Reply replyCli = resp(request);
			replyCli.writeDelimitedTo(outCliente);
			byte sendData[] = outCliente.toByteArray();
			DatagramPacket sendPack = new DatagramPacket(sendData, sendData.length,ipCli,portCli);
			socketServ.send(sendPack);
			}
		} catch (Exception e) {
				
		}
	}
}
