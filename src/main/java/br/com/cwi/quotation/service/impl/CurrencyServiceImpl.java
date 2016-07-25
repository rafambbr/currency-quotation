package br.com.cwi.quotation.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.cwi.quotation.exception.ServiceException;
import br.com.cwi.quotation.service.CurrencyService;
import br.com.cwi.quotation.service.FileService;
import br.com.cwi.quotation.util.Util;

public class CurrencyServiceImpl implements CurrencyService {
	
	private FileService fileService;
	
	public CurrencyServiceImpl(){
		//No-op
	}
	
	public CurrencyServiceImpl(FileService fileService){
		this.fileService = fileService;
	}
	
	public BigDecimal currencyQuotation(String from, String to, Number value, String quotationDate){
		
		if(from == null || from.trim().length() != 3 ||
				to == null || to.trim().length() != 3){
			throw new ServiceException("Moeda informada inválida");
		}else if(value.doubleValue() < 0){
			throw new ServiceException("O valor informado é inválido");
		}
		
		String validDate = getValidBusinessDate(quotationDate, Util.DATE_PATTERN_DD_MM_YYYY);
		
		BigDecimal fromQuotationValue = getQuotation(from, validDate);
		BigDecimal toQuotationValue = getQuotation(to, validDate);
		
		BigDecimal bdValue = new BigDecimal(value.doubleValue());
		
		BigDecimal fromTotalAmount = calculateTotalAmountQuotation(fromQuotationValue, bdValue);
		BigDecimal toTotalTargetCurrency = calculateTotalTargetCurrency(fromTotalAmount, toQuotationValue);
		
		return toTotalTargetCurrency;
	}
	
	public String getValidBusinessDate(String date, String datePattern){
		String businessDate = null;
		
		try{
			Calendar validDate = Util.convertStringToCalendar(date, datePattern);
			if(validDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				validDate.add(Calendar.DATE, -2);
			}else if(validDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				validDate.add(Calendar.DATE, -1);
			}
			
			SimpleDateFormat sdFormat = new SimpleDateFormat(Util.DATE_PATTERN_DD_MM_YYYY);
			businessDate = sdFormat.format(validDate.getTime());
			
		}catch(Exception e){
			throw new ServiceException("Erro ao obter uma data válida");
		}
		
		return businessDate;
	}
	
	public BigDecimal getQuotation(String name, String quotationDate){
		InputStream fileInputStream = fileService.getFileInputStream(quotationDate);
		BigDecimal quotation = fileService.findQuotationValueByNameInFile(name, fileInputStream);
		return quotation;
	}

	public BigDecimal calculateTotalTargetCurrency(BigDecimal fromTotalAmount, BigDecimal toQuotationValue) {
		return fromTotalAmount.divide(toQuotationValue, RoundingMode.CEILING).setScale(2, RoundingMode.CEILING);
	}

	public BigDecimal calculateTotalAmountQuotation(BigDecimal fromQuotationValue, BigDecimal value) {
		return fromQuotationValue.multiply(value);
	}

}
