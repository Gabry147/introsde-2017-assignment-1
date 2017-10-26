package it.gabry147.XMLformatter;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class DateFormatter {
	
	/**
	 * Convert a Date in javax.xml.datatype.XMLGregorianCalendar
	 * @param d The Date to convert
	 * @return The same timing converted in javax.xml.datatype.XMLGregorianCalendar
	 */
	public static XMLGregorianCalendar fromDateToXMLGregorianCalendar(Date d) {
		//create instance of GregorianCalendar
		GregorianCalendar gc = new GregorianCalendar();
		//set the time to the Date value
		gc.setTime(d);
		XMLGregorianCalendar xmlCalendar = null;
		try {
			//convert GregorianCalendar to XMLGregorianCalendar
			xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xmlCalendar;
	}
	
	/**
	 * Convert a XMLGregorianCalendar in a java.util.Date
	 * @param xmlCalendar The XMLGregorianCalendar to convert
	 * @return The same timing converted in java.util.Date
	 */
	public static Date fromXMLGregorianCalendarToDate(XMLGregorianCalendar xmlCalendar) {
		Date date = xmlCalendar.toGregorianCalendar().getTime();
		return date;
	}

}
