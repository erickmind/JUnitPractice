package br.ce.waquino.daos;

import java.util.List;

import br.ce.wcaquino.entidades.Locacao;

public class LocacaoDaoFake implements LocacaoDao{
	
	public void salvar(Locacao locacao) {
		
	}

	@Override
	public List<Locacao> obterLocacoesPendentes() {
		return null;
	}

}
