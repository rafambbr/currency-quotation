package br.com.cwi.quotation.exception;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = -372142113889733843L;

	public ServiceException(String erroMsg) {
		super(erroMsg);
	}
	
	public ServiceException(String erroMsg, Exception e) {
		super(erroMsg, e);
	}
}
