package br.com.cwi.quotation.util;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Locale;

import org.junit.Test;

import br.com.cwi.quotation.util.Util;

public class UtilTest {

	@Test
	public void parseStringToBigDecimal(){
		String value01 = "20";
		String value02 = "20,456";
		String value03 = "998877,12";
		
		Locale locale = new Locale("pt","BR");
		
		BigDecimal expectedTotal01 = new BigDecimal(20).setScale(0, RoundingMode.CEILING);
		BigDecimal expectedTotal02 = new BigDecimal(20.456).setScale(3, RoundingMode.CEILING);
		BigDecimal expectedTotal03 = new BigDecimal(998877.12).setScale(2, RoundingMode.CEILING);
		
		BigDecimal bdValue01 = Util.parseStringToBigDecimal(value01, locale);
		BigDecimal bdValue02 = Util.parseStringToBigDecimal(value02, locale);
		BigDecimal bdValue03 = Util.parseStringToBigDecimal(value03, locale);
		
		assertEquals(expectedTotal01, bdValue01.setScale(0, RoundingMode.CEILING));
		assertEquals(expectedTotal02, bdValue02.setScale(3, RoundingMode.CEILING));
		assertEquals(expectedTotal03, bdValue03.setScale(2, RoundingMode.CEILING));
	}
	
	@Test
	public void convertStringToCalendar() throws ParseException{
		
		String strDate01 = "25/07/2016";
		Calendar cal01 = Util.convertStringToCalendar(strDate01, Util.DATE_PATTERN_DD_MM_YYYY);
		
		int day01 = cal01.get(Calendar.DAY_OF_MONTH);
		int month01 = cal01.get(Calendar.MONTH)+1;
		int year01 = cal01.get(Calendar.YEAR);
		
		assertEquals(25, day01);
		assertEquals(7, month01);
		assertEquals(2016, year01);
	}
	
	@Test(expected=ParseException.class)
	public void failOnConvertStringToCalendar() throws ParseException{
		String strDate01 = "DATA INVALIDA";
		Util.convertStringToCalendar(strDate01, Util.DATE_PATTERN_DD_MM_YYYY);
	}
}
