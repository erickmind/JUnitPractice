package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.matchers.MatchersProprios.caiNaSegunda;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHoje;
import static br.ce.wcaquino.matchers.MatchersProprios.ehHojeComDiferencaDias;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import br.ce.waquino.daos.LocacaoDao;
import br.ce.waquino.exceptions.FilmeSemEstoqueException;
import br.ce.waquino.exceptions.LocadoraException;
import br.ce.wcaquino.builders.FilmeBuilder;
import br.ce.wcaquino.builders.UsuarioBuilder;
import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;

public class LocacaoServiceTest {
	
	@Rule	
	public ErrorCollector erro = new ErrorCollector();
	
	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	private LocacaoService service;
	
	private LocacaoDao dao;
	
	private SpcService spc;
	
	private EmailService emailService;
	
	@Before
	public void setup() {
		service = new LocacaoService();	
		dao = Mockito.mock(LocacaoDao.class);
		service.setLocacaoDAO(dao);
		spc = Mockito.mock(SpcService.class);
		service.setSpc(spc);
		emailService = Mockito.mock(EmailService.class);
		service.setEmailService(emailService);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
		
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().alterarValor(2.0).agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		Locacao locacao = service.alugarFilme(usuario, filmesAlugados);
		
		erro.checkThat(locacao.getValor(), is(2.0));
		erro.checkThat(locacao.getDataLocacao(), ehHoje());
		erro.checkThat(locacao.getDataRetorno(), ehHojeComDiferencaDias(1));
	}
	
	@Test(expected=FilmeSemEstoqueException.class)
	public void testeLocacao_FilmeSemEstoque() throws Exception {
		
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().zerarEstoque().agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);

		service.alugarFilme(usuario, filmesAlugados);
	}
	
	@Test
	public void testeLocacao_UsuarioVazio() throws LocadoraException{
		
		Filme filme = FilmeBuilder.criarFilme().agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);

		
		try {
			service.alugarFilme(null, filmesAlugados);
			
			Assert.fail();
		} catch (Exception e){
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}
	
	@Test
	public void testeLocacao_FilmeVazio() throws Exception{
		
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme vazio");
		
		service.alugarFilme(usuario, null);
	}
	
	@Test
	public void testeLocacao_AlugarDoisFilmes() throws Exception{
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().alterarValor(5.0).agora();
		Filme filme2 = FilmeBuilder.criarFilme().alterarValor(5.0).agora();
		
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		filmesAlugados.add(filme2);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertNotNull(retorno);
		Assert.assertEquals(10.0, retorno.getValor(), 0.1);
	}
	
	@Test
	public void testeDesconto_3Filme() throws Exception {
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		Filme filme2 = FilmeBuilder.criarFilme().agora();
		Filme filme3 = FilmeBuilder.criarFilme().agora();
		
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		filmesAlugados.add(filme2);
		filmesAlugados.add(filme3);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertNotNull(retorno);
		Assert.assertEquals(27.5, retorno.getValor(), 0.1);
	}
	
	@Test
	public void testeDesconto_4Filme() throws Exception {
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		Filme filme2 = FilmeBuilder.criarFilme().agora();
		Filme filme3 = FilmeBuilder.criarFilme().agora();
		Filme filme4 = FilmeBuilder.criarFilme().agora();
		
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		filmesAlugados.add(filme2);
		filmesAlugados.add(filme3);
		filmesAlugados.add(filme4);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertNotNull(retorno);
		Assert.assertEquals(32.5, retorno.getValor(), 0.1);
	}
	
	@Test
	public void testeDesconto_5Filme() throws Exception {
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		Filme filme2 = FilmeBuilder.criarFilme().agora();
		Filme filme3 = FilmeBuilder.criarFilme().agora();
		Filme filme4 = FilmeBuilder.criarFilme().agora();
		Filme filme5 = FilmeBuilder.criarFilme().agora();
		
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		filmesAlugados.add(filme2);
		filmesAlugados.add(filme3);
		filmesAlugados.add(filme4);
		filmesAlugados.add(filme5);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertNotNull(retorno);
		Assert.assertEquals(35.0, retorno.getValor(), 0.1);
	}
	
	@Test
	public void testeDesconto_6Filme() throws Exception {
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		Filme filme2 = FilmeBuilder.criarFilme().agora();
		Filme filme3 = FilmeBuilder.criarFilme().agora();
		Filme filme4 = FilmeBuilder.criarFilme().agora();
		Filme filme5 = FilmeBuilder.criarFilme().agora();
		Filme filme6 = FilmeBuilder.criarFilme().agora();
		
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		filmesAlugados.add(filme2);
		filmesAlugados.add(filme3);
		filmesAlugados.add(filme4);
		filmesAlugados.add(filme5);
		filmesAlugados.add(filme6);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertNotNull(retorno);
		Assert.assertEquals(35.0, retorno.getValor(), 0.1);
	}
	
	@Test
	@Ignore
	public void naoDeveDevolverFilmeDomingo() throws Exception {
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		
		Locacao retorno = service.alugarFilme(usuario, filmesAlugados);
		Assert.assertThat(retorno.getDataLocacao(), caiNaSegunda());
				
	}
	
	@Test
	public void possuiUsuarioNegativado() throws Exception {
		
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		
		when(spc.possuiNegativacao(usuario)).thenReturn(true);
		
		exception.expect(LocadoraException.class);
		exception.expectMessage("Usuario negativado");
		
		service.alugarFilme(usuario, filmesAlugados);	
	}
	
	@Test
	public void deveEnviarEmailParaAtrasados() {
		
		Usuario usuario = UsuarioBuilder.criarUsuario().agora();
		Filme filme = FilmeBuilder.criarFilme().agora();
		List<Filme> filmesAlugados = new ArrayList<>();
		filmesAlugados.add(filme);
		
		Locacao locacao = new Locacao();
		locacao.setFilme(filmesAlugados);
		locacao.setUsuario(usuario);
		
		List<Locacao> locacoes = Arrays.asList(locacao);
		
		when(dao.obterLocacoesPendentes()).thenReturn(locacoes);
		
		service.notificarAtrasos();
		
		verify(emailService).notificarAtraso(usuario);
	}
}
