package br.ce.wcaquino.servicos;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.ce.waquino.exceptions.DivisaoPorZeroException;

public class CalculadoraTest {
	
	private Calculadora calc;
	
	public static StringBuffer ordem = new StringBuffer();
	
	@Before
	public void setup() {
		calc = new Calculadora();
		System.out.println("Iniciando 2");
		ordem.append("2");
	}
	
	@After
	public void tearDown() {
		System.out.println("Finalizando 2");
	}
	
	@AfterClass
	public static void tearDownClass() {
		System.out.println(ordem.toString());
	}

	@Test
	public void deveSomar() {
		int a = 5;
		int b = 3;
		
		int soma = calc.somar(a, b);
		Assert.assertEquals(8, soma);
	}
	
	@Test
	public void deveSubtrair() {
		int a = 8;
		int b = 5;
				
		int subt = calc.subtrair(a, b);
		Assert.assertEquals(3, subt);
	}
	
	@Test
	public void deveMultiplicar() {
		int a = 2;
		int b = 5;
				
		int mult = calc.multiplicar(a, b);
		Assert.assertEquals(10, mult);
	}
	
	@Test
	public void deveDividir() throws DivisaoPorZeroException {
		int a = 10;
		int b = 2;
				
		int div = calc.dividir(a, b);
		Assert.assertEquals(5, div);
	}
	
	@Test(expected = DivisaoPorZeroException.class)
	public void dividirPorZeroException() throws DivisaoPorZeroException {
		int a = 10;
		int b = 0;
				
		calc.dividir(a, b);
	}
}
