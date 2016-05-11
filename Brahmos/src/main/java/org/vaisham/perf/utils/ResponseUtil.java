package org.vaisham.perf.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *	ResponseUtil.java
 *	KH1868 Vaishampayan Reddy
 */

public class ResponseUtil implements IResponseUtil {

	public JSONParser parser;
	public Object obj;
	
	public String getSuccessResponse(String data) {
		return data;
	}

	@SuppressWarnings("unchecked")
	public String getFailureResponse(int httpCode,String message, String errMsg) {
		JSONObject object = new JSONObject();
		object.put("httpCode", httpCode);
		object.put("message", message);
		object.put("errMsg", errMsg);
		return object.toString();
	}

}
