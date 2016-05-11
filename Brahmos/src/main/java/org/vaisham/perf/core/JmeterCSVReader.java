package org.vaisham.perf.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opencsv.CSVReader;

public class JmeterCSVReader implements IJmeterCSVReader {

	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	/**
	 * timestamp 
	 * response time in milliseconds 
	 * api call -- httprequest label
	 * statuscode 
	 * message 
	 * threadData 
	 * textformat 
	 * success 
	 * bytes 
	 * latency
	 * 
	 * 
	 */
	@SuppressWarnings("unchecked")
	public String extractBuildDataFromFile(String filepath)
			throws FileNotFoundException {
		CSVReader reader = new CSVReader(new FileReader(filepath));
		String[] nextLine;
		Map<String, JSONArray> graph_data = new HashMap<String, JSONArray>();
		Map<String, JSONObject> error_data = new HashMap<String, JSONObject>();
		try {
			while ((nextLine = reader.readNext()) != null) {
				Long timestamp = Long.parseLong(nextLine[0]);
				int responseTime = Integer.parseInt(nextLine[1]);
				String apiName = nextLine[2];
				String statusCode = nextLine[3];
				String message = nextLine[4];
				boolean isSuccess = Boolean.parseBoolean(nextLine[7]);
				if (isSuccess) {
					JSONObject xYpair = new JSONObject();
					xYpair.put("date", dateFormatter.format(new Date(timestamp)).toString());
					xYpair.put("response", responseTime);
					if(graph_data.containsKey(apiName))
						graph_data.get(apiName).add(xYpair);
					else {
						JSONArray xYValuePairs = new JSONArray();
						xYValuePairs.add(xYpair);
						graph_data.put(apiName, xYValuePairs);
					}
				}
				else {
					JSONObject apiError;
					JSONObject errValue;
					if((apiError = error_data.get(apiName)) != null) {
						if((errValue = (JSONObject) apiError.get(statusCode)) != null) {
							int count = (Integer) errValue.get("count");
							errValue.put("count", ++count);
							apiError.put(statusCode, errValue);
							error_data.put(apiName, apiError);
						}
						else {
							errValue = new JSONObject();
							errValue.put("errmsg", message);
							errValue.put("count", 1);
							apiError.put(statusCode, errValue);
							error_data.put(apiName, apiError);
						}
					}
					else {
						apiError = new JSONObject();
						errValue = new JSONObject();
						errValue.put("errmsg", message);
						errValue.put("count", 1);
						apiError.put(statusCode, errValue);
						error_data.put(apiName, apiError);
					}
				}
			}
			reader.close();
			JSONObject buildData = new JSONObject();
			buildData.put("graph_data", graph_data);
			buildData.put("error_data", error_data);
			return buildData.toJSONString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
