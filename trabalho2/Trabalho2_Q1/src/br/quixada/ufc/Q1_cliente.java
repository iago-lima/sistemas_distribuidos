package br.quixada.ufc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

import br.ufc.quixada.calc.Calculadora.Reply;
import br.ufc.quixada.calc.Calculadora.Request;

public class Q1_cliente {
	private static Scanner teclado;

	public static Request RCalc(){
		Request.Builder calculadora = Request.newBuilder();
		
		teclado = new Scanner(System.in);
		
		System.out.print("Digite o ID: \n");
	    calculadora.setId(Integer.valueOf(teclado.next()));
	    System.out.print("Digite o 1º número: \n");
	    calculadora.setN1(Integer.valueOf(teclado.next()));
	    System.out.print("Digite a operação.\n 1 +\n 2 -\n 3 *\n 4 /\n: ");
	    int op = teclado.nextInt();
	    System.out.print("Digite o 2º número: \n");
	    calculadora.setN2(Integer.valueOf(teclado.next()));
	    
	    
	    if(op == 1) {
	    	calculadora.setOp(Request.Operacao.SOM);
	    }else if(op == 2){
	    	calculadora.setOp(Request.Operacao.SUB);
	    }else if(op == 3){
	    	calculadora.setOp(Request.Operacao.MUL);
	    }else if(op == 4){
	    	double num2 = calculadora.getN2();
	    	if(num2 == 0) {
	    		System.out.println("Erro! Divisão por ZERO!");
	    		System.exit(0);
	    	}else {
	    		calculadora.setOp(Request.Operacao.DIV);
	    	}
	    }else {
	    	System.out.println("Operação incorreta\n");
	    	System.exit(0);
	    }    
	    return calculadora.build();
	}
	
	public static void main(String[] args) throws Exception {
		DatagramSocket sockCliente = null;
		Request requestCalc = null;
		try {
			requestCalc = RCalc();
			sockCliente = new DatagramSocket();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			requestCalc.writeDelimitedTo(out);
			byte op[] = out.toByteArray();
			InetAddress ipCliente = InetAddress.getByName("localhost");
			int port = 6789;
			DatagramPacket sendPack = new DatagramPacket(op, op.length, ipCliente, port);
			sockCliente.send(sendPack);
		
			
			byte[] receiveOp = new byte[1024];
			DatagramPacket reciveServer = new DatagramPacket(receiveOp, receiveOp.length);
			sockCliente.receive(reciveServer);
			ByteArrayInputStream in = new ByteArrayInputStream(receiveOp);
			Reply respCalc = Reply.parseDelimitedFrom(in);
			System.out.println("Resultado: " + respCalc.getRes());
			System.out.println();
		} catch (Exception e) {
		}
	}
	
}
