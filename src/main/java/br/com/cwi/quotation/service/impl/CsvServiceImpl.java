package br.com.cwi.quotation.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;

import br.com.cwi.quotation.exception.ServiceException;
import br.com.cwi.quotation.service.FileService;
import br.com.cwi.quotation.util.Util;

public class CsvServiceImpl implements FileService{
	
	public static final String FILE_EXTENSION = ".csv";
	public static final String FILE_BASE_URL = "http://www4.bcb.gov.br/Download/fechamento/";
	
	public BigDecimal findQuotationValueByNameInFile(String name, InputStream csvInputStream) {
		BigDecimal quotationValue = null;
		Scanner csv = new Scanner(csvInputStream);
		Locale locale = new Locale("pt","BR");
		
		while(csv.hasNext()){
			String linha = csv.nextLine();
			String[] valores = linha.split(";");
			String currencyName = valores[3].trim();
			String currencyValue = valores[4].trim();

			if(name.equals(currencyName)){
				quotationValue = Util.parseStringToBigDecimal(currencyValue, locale);
			}
		}
		csv.close();
		
		if(quotationValue == null){
			throw new ServiceException("Moeda informada não foi encontrada");
		}
		
		return quotationValue;
	}

	public InputStream getFileInputStream(String quotationDate) {
		InputStream csvInputStream = null;
		try{
			String csvUrl = buildFileUrlByDate(quotationDate);
			URLConnection connection = new URL(csvUrl).openConnection();
			csvInputStream = connection.getInputStream();
		}catch(Exception e){
			throw new ServiceException("Não foi possível acessar o Banco Central para a consulta", e);
		}
		
		return csvInputStream;
	}

	public String buildFileUrlByDate(String quotationDate) {
		
		String url = null;
		try{
			SimpleDateFormat sdFormatFrom = new SimpleDateFormat(Util.DATE_PATTERN_DD_MM_YYYY);
			SimpleDateFormat sdFormatTo = new SimpleDateFormat(Util.DATE_PATTERN_DDMMYYYY);
			Date date = sdFormatFrom.parse(quotationDate);
			
			String fileDate = sdFormatTo.format(date);
			StringBuilder csvUrl = new StringBuilder(FILE_BASE_URL)
					.append(fileDate)
					.append(FILE_EXTENSION);
			url = csvUrl.toString();
		
		}catch(Exception e){
			throw new ServiceException("Erro ao gerar a URL do Banco Central", e);
		}
		
		return url;
	}
	
}
