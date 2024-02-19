package br.ce.wcaquino.servicos;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

public class CalculadoraMockTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		System.out.println("Iniciando 3");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando 3");
	}

	@Test
	public void deveMostrarDiferencaEntreMockSpy() {
		when(calcMock.somar(1, 2)).thenReturn(5);
		when(calcMock.somar(1, 2)).thenCallRealMethod();
		doReturn(5).when(calcSpy).somar(1, 2);
		doNothing().when(calcSpy).imprime();
		
		System.out.println("Mock:" + calcMock.somar(1, 2));
		System.out.println("Spy:" + calcSpy.somar(1, 2));
	}
	
}
