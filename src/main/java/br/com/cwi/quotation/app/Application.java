package br.com.cwi.quotation.app;

import java.math.BigDecimal;

import br.com.cwi.quotation.service.CurrencyService;
import br.com.cwi.quotation.service.impl.CurrencyServiceImpl;

/**
 * @author Rafael Camargo ( rafael.camargo.sp@gmail.com ) 
 *
 */
public class Application {
	
	public static void main(String[] args) throws Exception{
		
		String from = "USD";
		String to = "EUR";
		double money = 100.0;
		String date = "24/07/2016";
		
		CurrencyService currencyService = new CurrencyServiceImpl();
		BigDecimal currencyQuotationValue = currencyService.currencyQuotation(from, to, money, date);
		System.out.println(currencyQuotationValue);
	}
}
