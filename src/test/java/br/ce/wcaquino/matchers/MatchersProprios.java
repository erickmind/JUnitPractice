package br.ce.wcaquino.matchers;

import java.util.Calendar;
import java.util.Date;

import org.hamcrest.Matcher;

public class MatchersProprios {
	
	public static ValidacaoDataMatcher caiEm(int diaSemana) {
		return new ValidacaoDataMatcher(diaSemana);
	}

	public static ValidacaoDataMatcher caiNaSegunda() {
		return new ValidacaoDataMatcher(Calendar.MONDAY);
	}

	public static Matcher<Date> ehHojeComDiferencaDias(int i) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		return new ValidacaoDataMatcher(cal.get(Calendar.DAY_OF_WEEK) + i > 7 ? cal.get(Calendar.DAY_OF_WEEK) + i - 6 : cal.get(Calendar.DAY_OF_WEEK) + i);
	}

	public static Matcher<Date> ehHoje() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		
		return new ValidacaoDataMatcher(cal.get(Calendar.DAY_OF_WEEK));
	}
}
