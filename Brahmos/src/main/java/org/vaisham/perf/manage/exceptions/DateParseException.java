package org.vaisham.perf.manage.exceptions;

/**
 * DateParseException.java KH1868 Vaishampayan Reddy
 */

public class DateParseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1671533540704520152L;
	private String dateFormat;
	private String dateString;

	public DateParseException(String dateFormat, String dateString) {
		super();
		this.dateFormat = dateFormat;
		this.dateString = dateString;
	}

	@Override
	public String getMessage() {
		return "ERROR: unable to parse the date: dateformat: "
				+ this.dateFormat + " : dateString: " + this.dateString;
	}

	public String getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(String dateFormat) {
		this.dateFormat = dateFormat;
	}

	public String getDateString() {
		return dateString;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

}
