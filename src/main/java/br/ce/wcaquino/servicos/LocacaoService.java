package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.waquino.daos.LocacaoDao;
import br.ce.waquino.exceptions.FilmeSemEstoqueException;
import br.ce.waquino.exceptions.LocadoraException;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {
	
	private LocacaoDao dao;
	
	private SpcService spc;
	
	private EmailService emailService;
	
	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) throws Exception {
		
		if(usuario == null)
			throw new LocadoraException("Usuario vazio");
		
		if(spc.possuiNegativacao(usuario))
			throw new LocadoraException("Usuario negativado");
		
		if(filmes == null || filmes.isEmpty())
			throw new LocadoraException("Filme vazio");
		
		for(Filme filme : filmes) {
			if(filme.getEstoque() <= 0)
				throw new FilmeSemEstoqueException();
		}
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		Double valor = 0.0;
		
		int index = 1;
		for(Filme filme : filmes) {
			switch(index){
				case(3): valor += filme.getPrecoLocacao() * 0.75; index++; break;
				case(4): valor += filme.getPrecoLocacao() * 0.5; index++; break;
				case(5): valor += filme.getPrecoLocacao() * 0.25; index++; break;
				case(6): index++; break;
				default: valor += filme.getPrecoLocacao(); index++;
			}
		}
		locacao.setValor(valor);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		
		//Salvando a locacao...	
		dao.salvar(locacao);
		
		return locacao;
	}
	
	public void notificarAtrasos() {
		List<Locacao> locacoes = dao.obterLocacoesPendentes();
		for(Locacao locacao: locacoes) {
			emailService.notificarAtraso(locacao.getUsuario());
		}
	}
}