package br.com.cwi.quotation.service.impl;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;

import br.com.cwi.quotation.exception.ServiceException;
import br.com.cwi.quotation.service.FileService;
import br.com.cwi.quotation.util.Util;

public class CurrencyServiceImplTest {
	
	private CurrencyServiceImpl currencyService;
	
	@Before
	public void setup(){
		FileService fileService = new CsvServiceImpl();
		currencyService = new CurrencyServiceImpl(fileService);
	}
	
	@Test
	public void failOnCurrencyQuotationInvalidFrom(){
		try{
			currencyService.currencyQuotation(null, "USD", 100.0, "23/07/2016");
			fail();
		}catch(ServiceException exp){
			assertTrue(true);
		}
		
		try{
			currencyService.currencyQuotation("USD", null, 100.0, "23/07/2016");
			fail();
		}catch(ServiceException exp){
			assertTrue(true);
		}
		
		try{
			currencyService.currencyQuotation("INVALID", "USD", 100.0, "23/07/2016");
			fail();
		}catch(ServiceException exp){
			assertTrue(true);
		}
		
		try{
			currencyService.currencyQuotation("USD", "INVALID", 100.0, "23/07/2016");
			fail();
		}catch(ServiceException exp){
			assertTrue(true);
		}
		
		try{
			currencyService.currencyQuotation("USD", "EUR", -1, "23/07/2016");
			fail();
		}catch(ServiceException exp){
			assertTrue(true);
		}
	}

	@Test
	public void getValidBusinessDateReturnSameDate(){
		
		String monday = "18/07/2016";
		String tuesday = "19/07/2016";
		String wednesday = "20/07/2016";
		String thursday = "21/07/2016";
		String friday = "22/07/2016";
		
		String validDateMonday = currencyService.getValidBusinessDate(monday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateTuesday = currencyService.getValidBusinessDate(tuesday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateWednesday = currencyService.getValidBusinessDate(wednesday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateThursday = currencyService.getValidBusinessDate(thursday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateFriday = currencyService.getValidBusinessDate(friday, Util.DATE_PATTERN_DD_MM_YYYY);
		
		assertEquals(monday, validDateMonday);
		assertEquals(tuesday, validDateTuesday);
		assertEquals(wednesday, validDateWednesday);
		assertEquals(thursday, validDateThursday);
		assertEquals(friday, validDateFriday);
	}

	@Test
	public void getValidBusinessDateNeedReturnFridayDate(){
		String friday = "22/07/2016";
		String saturday = "23/07/2016";
		String sunday = "24/07/2016";
		
		String validDateFriday = currencyService.getValidBusinessDate(friday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateSaturday = currencyService.getValidBusinessDate(saturday, Util.DATE_PATTERN_DD_MM_YYYY);
		String validDateSunday = currencyService.getValidBusinessDate(sunday, Util.DATE_PATTERN_DD_MM_YYYY);
		
		assertEquals(friday, validDateFriday);
		assertEquals(friday, validDateSaturday);
		assertEquals(friday, validDateSunday);
	}
	
	@Test(expected=ServiceException.class)
	public void failOnGetValidBusinessDate(){
		currencyService.getValidBusinessDate("DATA INVALIDA", Util.DATE_PATTERN_DD_MM_YYYY);
	}
	
	@Test(expected=ServiceException.class)
	public void failOnGetQuotationWithInvalidName(){
		currencyService.getQuotation(null, "24/07/2016");
	}
	
	@Test(expected=ServiceException.class)
	public void failOnGetQuotationWithInvalidDate(){
		currencyService.getQuotation("USD", "DATA INVALIDA");
	}
	
	@Test
	public void calculateTotalTargetCurrency(){
		BigDecimal fromTotalAmount01 = new BigDecimal(100);
		BigDecimal fromTotalAmount02 = new BigDecimal(100);
		BigDecimal fromTotalAmount03 = new BigDecimal(300);
		
		BigDecimal toQuotationValue01 = new BigDecimal(2);
		BigDecimal toQuotationValue02 = new BigDecimal(0.5);
		BigDecimal toQuotationValue03 = new BigDecimal(1);
		
		BigDecimal expectedTotal01 = new BigDecimal(50).setScale(2, RoundingMode.CEILING);
		BigDecimal expectedTotal02 = new BigDecimal(200).setScale(2, RoundingMode.CEILING);
		BigDecimal expectedTotal03 = new BigDecimal(300).setScale(2, RoundingMode.CEILING);
		
		BigDecimal total01 = currencyService.calculateTotalTargetCurrency(fromTotalAmount01, toQuotationValue01);
		BigDecimal total02 = currencyService.calculateTotalTargetCurrency(fromTotalAmount02, toQuotationValue02);
		BigDecimal total03 = currencyService.calculateTotalTargetCurrency(fromTotalAmount03, toQuotationValue03);
		
		assertEquals(expectedTotal01, total01);
		assertEquals(expectedTotal02, total02);
		assertEquals(expectedTotal03, total03);
	}
	
	@Test
	public void calculateTotalAmountQuotation(){
		BigDecimal fromQuotationValue01 = new BigDecimal(2.0);
		BigDecimal fromQuotationValue02 = new BigDecimal(0.5);
		BigDecimal fromQuotationValue03 = new BigDecimal(1);

		BigDecimal value01 = new BigDecimal(100.0);
		BigDecimal value02 = new BigDecimal(100.0);
		BigDecimal value03 = new BigDecimal(300.0);
		
		BigDecimal expectedTotal01 = new BigDecimal(200).setScale(2, RoundingMode.CEILING);
		BigDecimal expectedTotal02 = new BigDecimal(50).setScale(2, RoundingMode.CEILING);
		BigDecimal expectedTotal03 = new BigDecimal(300).setScale(2, RoundingMode.CEILING);
		
		BigDecimal total01 = currencyService.calculateTotalAmountQuotation(fromQuotationValue01, value01);
		BigDecimal total02 = currencyService.calculateTotalAmountQuotation(fromQuotationValue02, value02);
		BigDecimal total03 = currencyService.calculateTotalAmountQuotation(fromQuotationValue03, value03);
		
		assertEquals(expectedTotal01, total01.setScale(2, RoundingMode.CEILING));
		assertEquals(expectedTotal02, total02.setScale(2, RoundingMode.CEILING));
		assertEquals(expectedTotal03, total03.setScale(2, RoundingMode.CEILING));
	}

}
