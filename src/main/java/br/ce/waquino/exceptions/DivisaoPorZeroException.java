package br.ce.waquino.exceptions;

public class DivisaoPorZeroException extends Exception {

	private static final long serialVersionUID = 5696856209451046532L;
	
	public DivisaoPorZeroException() {
		System.out.println("Divisão por zero");
	}
	
}
