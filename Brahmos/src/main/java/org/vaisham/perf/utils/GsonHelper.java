package org.vaisham.perf.utils;

import com.google.gson.Gson;

/**
 *	GsonHelper.java
 *	KH1868 Vaishampayan Reddy
 */

/**
 * @author KH1868 Vaishampayan Reddy
 * This is a Gson Helper class created to help creating a java object from Json
 * and a Json Object from Java Object.
 * 
 */
public class GsonHelper {

	public String convertToJson(Object object) {
		
		Gson gson = new Gson();
		return gson.toJson(object);
	}
	
	public <T> T convertFromJson(String json,Class<T> typeOfClass) {
		
		Gson gson = new Gson();
		return gson.fromJson(json, typeOfClass);
	}
	
}
