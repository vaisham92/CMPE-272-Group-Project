package org.vaisham.perf.manage.exceptions;

/**
 * UnParsableException.java KH1868 Vaishampayan Reddy
 */

public class UnParsableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5797667561262228605L;

	public Object object;

	public UnParsableException(Object object) {
		super();
		this.object = object;
	}

	@Override
	public String getMessage() {
		return "Exception: The object: " + object.toString()
				+ " cannot be parsed";
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

}
