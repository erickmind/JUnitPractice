package br.ce.wcaquino.builders;

import br.ce.wcaquino.entidades.Filme;

public class FilmeBuilder {
	
	private Filme filme;
	
	private FilmeBuilder() {}
	
	public static FilmeBuilder criarFilme() {
		FilmeBuilder builder = new FilmeBuilder();
		builder.filme = new Filme();
		builder.filme.setNome("Filme");
		builder.filme.setEstoque(2);
		builder.filme.setPrecoLocacao(10.0);
		
		return builder;
	}
	
	public Filme agora() {
		return this.filme;
	}
	
	public FilmeBuilder zerarEstoque() {
		this.filme.setEstoque(0);
		return this;
	}
	
	public FilmeBuilder alterarValor(double i) {
		this.filme.setPrecoLocacao(i);
		return this;
	}

}
