package br.com.cwi.quotation.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Util {

	public static final String DATE_PATTERN_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DATE_PATTERN_DDMMYYYY = "yyyyMMdd";
	
	public static BigDecimal parseStringToBigDecimal(String currencyValue, Locale locale) {
        DecimalFormat decimalFormat = (DecimalFormat)NumberFormat.getInstance(locale);
        decimalFormat.setParseBigDecimal(true);
        BigDecimal bdValue = (BigDecimal)decimalFormat.parse(currencyValue, new ParsePosition(0));
        return bdValue;
	}

	public static Calendar convertStringToCalendar(String fromData, String datePattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(datePattern);
		Calendar date = Calendar.getInstance();
		date.setTime(sdf.parse(fromData));
		return date;
	}
}
