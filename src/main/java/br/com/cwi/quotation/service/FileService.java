package br.com.cwi.quotation.service;

import java.io.InputStream;
import java.math.BigDecimal;

public interface FileService {
	
	public BigDecimal findQuotationValueByNameInFile(String name, InputStream csvInputStream);

	public InputStream getFileInputStream(String quotationDate);

	public String buildFileUrlByDate(String quotationDate);
}
