package org.vaisham.perf.dto;

/**
 * 
 * @author KH1868
 *
 */
public class ConsolidatedReportsMetaDTO  implements java.io.Serializable {

	private static final long serialVersionUID = 7192004521680489791L;
	private int year;
	private String month;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
}
