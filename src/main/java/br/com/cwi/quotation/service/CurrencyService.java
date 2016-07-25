package br.com.cwi.quotation.service;

import java.math.BigDecimal;

public interface CurrencyService {
	
	public BigDecimal currencyQuotation(String from, String to, Number value, String quotationDate);

	public String getValidBusinessDate(String date, String datePattern);

	public BigDecimal calculateTotalTargetCurrency(BigDecimal fromTotalBrl, BigDecimal toQuotValue);

	public BigDecimal calculateTotalAmountQuotation(BigDecimal fromQuotValue, BigDecimal value);

}
