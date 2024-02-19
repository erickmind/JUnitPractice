package br.ce.wcaquino.servicos;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.Mockito;

import br.ce.waquino.daos.LocacaoDao;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

@RunWith(Parameterized.class)
public class CalculadoraDescontosTest {
	
	private LocacaoService service;

	@Parameter
	public List<Filme> filmes;
	@Parameter(value = 1)
	public double valorLocacao;
	@Parameter(value = 2)
	public String nomeTeste;
	
	@Before
	public void setup() {
		service = new LocacaoService();
		LocacaoDao dao = Mockito.mock(LocacaoDao.class);
		service.setLocacaoDAO(dao);
	}
	
	private static Filme filme1 = new Filme("Kill Bill", 5, 10.0);
	private static Filme filme2 = new Filme("Django Livre", 2, 10.0);
	private static Filme filme3 = new Filme("Cinderela", 3, 10.0);
	private static Filme filme4 = new Filme("Inception", 3, 10.0);
	private static Filme filme5 = new Filme("Chucky", 3, 10.0);
	private static Filme filme6 = new Filme("Carga explosiva", 3, 10.0);
	private static Filme filme7 = new Filme("Panico", 3, 10.0);
	
	@Parameters(name = "{2}")
	public static List<Object[]> getParametros() {
		return Arrays.asList(new Object[][] {
				{Arrays.asList(filme1, filme2), 20.0, "2 filmes, Sem desconto"},
				{Arrays.asList(filme1, filme2, filme3), 27.5, "3 filmes, 25%"},
				{Arrays.asList(filme1, filme2, filme3, filme4), 32.5, "4 filmes, 50%"},
				{Arrays.asList(filme1, filme2, filme3, filme4, filme5), 35.0, "5 filmes, 75%"},
				{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 35.0, "6 filmes, 100%"},
				{Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6, filme7), 45.0, "7 filmes, Sem desconto"}
		});
	}
	
	@Test
	public void deveCalcularDescontosDeAcordoComNumeroFilmes() throws Exception {
		
		Usuario usuario = new Usuario();
		
		Locacao retorno = service.alugarFilme(usuario, filmes);
		
		Assert.assertEquals(valorLocacao, retorno.getValor(), 0.1);
	}
}
