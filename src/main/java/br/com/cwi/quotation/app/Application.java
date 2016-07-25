package br.com.cwi.quotation.app;

import java.math.BigDecimal;

import br.com.cwi.quotation.service.CurrencyService;
import br.com.cwi.quotation.service.FileService;
import br.com.cwi.quotation.service.impl.CsvServiceImpl;
import br.com.cwi.quotation.service.impl.CurrencyServiceImpl;

/**
 * @author Rafael Camargo ( rafael.camargo.sp@gmail.com ) 
 *
 */
public class Application {
	
	private FileService fileService;
	private CurrencyService currencyService;
	
	public static void main(String[] args) throws Exception{
		Application app = new Application();
		
		String from = "USD";
		String to = "EUR";
		double money = 100.00;
		String date = "20/11/2014";
		
		app.run(from, to, money, date);
	}

	private void run(String from, String to, double money, String date) {
		
		fileService = new CsvServiceImpl();
		currencyService = new CurrencyServiceImpl(fileService);
		
		BigDecimal currencyQuotationValue = currencyService.currencyQuotation(from, to, money, date);
		System.out.println(currencyQuotationValue);
	}
}
