package br.ce.wcaquino.suites;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runners.Suite.SuiteClasses;

import br.ce.wcaquino.servicos.CalculadoraDescontosTest;
import br.ce.wcaquino.servicos.CalculadoraTest;
import br.ce.wcaquino.servicos.LocacaoServiceTest;

//@RunWith(Suite.class)
@SuiteClasses({
		CalculadoraDescontosTest.class,
		CalculadoraTest.class,
		LocacaoServiceTest.class
		})
public class SuiteExecucao {

	@BeforeClass
	public static void before() {
		System.out.println("Before");
	}
	
	@AfterClass
	public static void after() {
		System.out.println("After");
	}
}
