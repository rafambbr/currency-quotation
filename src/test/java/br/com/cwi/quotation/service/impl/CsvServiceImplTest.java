package br.com.cwi.quotation.service.impl;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Test;

import br.com.cwi.quotation.exception.ServiceException;
import br.com.cwi.quotation.service.impl.CsvServiceImpl;

public class CsvServiceImplTest {

	private CsvServiceImpl csvService = new CsvServiceImpl();
	
	@Test
	public void buildFileUrlByDate(){
		
		String date01 = "22/07/2016";
		String date02 = "01/10/2016";
		
		String csvUrl01 = csvService.buildFileUrlByDate(date01);
		String csvUrl02 = csvService.buildFileUrlByDate(date02);
		
		String expectedUrl01 = CsvServiceImpl.FILE_BASE_URL + "20160722" + CsvServiceImpl.FILE_EXTENSION;
		String expectedUrl02 = CsvServiceImpl.FILE_BASE_URL + "20161001" + CsvServiceImpl.FILE_EXTENSION;
		
		assertEquals(expectedUrl01, csvUrl01);
		assertEquals(expectedUrl02, csvUrl02);
	}
	
	@Test(expected=ServiceException.class)
	public void faildOnBuildFileUrlByDate(){
		csvService.buildFileUrlByDate(null);
	}
	
	@Test
	public void findQuotationValueByNameInFile() throws FileNotFoundException {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File initialFile01 = new File(classLoader.getResource("20160722.csv").getFile());
	    InputStream csvFile01 = new FileInputStream(initialFile01);
	    BigDecimal usdQuotation = csvService.findQuotationValueByNameInFile("USD", csvFile01);
	    BigDecimal expectedUsdQuotation = new BigDecimal(3.2848).setScale(3, RoundingMode.CEILING);
	    assertEquals(expectedUsdQuotation, usdQuotation.setScale(3, RoundingMode.CEILING) );
	    
	    File initialFile02 = new File(classLoader.getResource("20160722.csv").getFile());
	    InputStream csvFile02 = new FileInputStream(initialFile02);
	    BigDecimal jpyQuotation = csvService.findQuotationValueByNameInFile("JPY", csvFile02);
	    BigDecimal expectedJpyQuotation = new BigDecimal(0.03091).setScale(3, RoundingMode.CEILING);
	    assertEquals(expectedJpyQuotation, jpyQuotation.setScale(3, RoundingMode.CEILING) );
	}
	
	@Test(expected=ServiceException.class)
	public void failOnFindQuotationValueByNameInFile() throws FileNotFoundException {
		
		ClassLoader classLoader = getClass().getClassLoader();
		File initialFile01 = new File(classLoader.getResource("20160722.csv").getFile());
	    InputStream csvFile01 = new FileInputStream(initialFile01);
	    csvService.findQuotationValueByNameInFile("RAF", csvFile01);
	    
	}
	
	@Test(expected=ServiceException.class)
	public void failOnGetFileInputStream(){
		csvService.getFileInputStream(null);
	}
}
