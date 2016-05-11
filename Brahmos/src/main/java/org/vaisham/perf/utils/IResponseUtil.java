package org.vaisham.perf.utils;

/**
 *	ResponseUtils.java
 *	KH1868 Vaishampayan Reddy
 */

public interface IResponseUtil {
	
	public String getSuccessResponse(String data);
	
	public String getFailureResponse(int httpCode,String message, String errMsg);

}
