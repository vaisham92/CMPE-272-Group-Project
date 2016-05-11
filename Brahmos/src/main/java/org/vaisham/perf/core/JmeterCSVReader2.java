package org.vaisham.perf.core;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.opencsv.CSVReader;

public class JmeterCSVReader2 implements IJmeterCSVReader {

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
		Map<String, TreeMap<Long, Integer>> graph_data_sorted = new HashMap<String, TreeMap<Long, Integer>>();
		try {
			while ((nextLine = reader.readNext()) != null) {
				Long timestamp = Long.parseLong(nextLine[0]);
				int responseTime = Integer.parseInt(nextLine[1]);
				String apiName = nextLine[2];
				String statusCode = nextLine[3];
				String message = nextLine[4];
				boolean isSuccess = Boolean.parseBoolean(nextLine[7]);
				if (isSuccess) {
					if(graph_data_sorted.containsKey(apiName)) {
						TreeMap<Long, Integer> apiData = graph_data_sorted.get(apiName);
						apiData.put(timestamp, responseTime);
						graph_data_sorted.put(apiName, apiData);
					}
					else {
						TreeMap<Long, Integer> apiData = new TreeMap<Long, Integer>();
						apiData.put(timestamp, responseTime);
						graph_data_sorted.put(apiName, apiData);
					}
					
					
					/*
					JSONObject xYpair = new JSONObject();
					xYpair.put("date", dateFormatter.format(new Date(timestamp)).toString());
					xYpair.put("response", responseTime);
					if(graph_data.containsKey(apiName))
						graph_data.get(apiName).add(xYpair);
					else {
						JSONArray xYValuePairs = new JSONArray();
						xYValuePairs.add(xYpair);
						graph_data.put(apiName, xYValuePairs);
					}*/
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
			graph_data = modifyApidataIntoGraphDataFormat(graph_data_sorted);
			buildData.put("graph_data", graph_data);
			buildData.put("error_data", error_data);
			return buildData.toJSONString();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, JSONArray> modifyApidataIntoGraphDataFormat(Map<String, TreeMap<Long, Integer>> graph_data_sorted) {
		Set<String> apiNames = graph_data_sorted.keySet();
		Map<String, JSONArray> result = new HashMap<String, JSONArray>();
		for(String api : apiNames) {
			JSONArray graphValues = new JSONArray();
			TreeMap<Long, Integer> xYpairs = graph_data_sorted.get(api);
			for(Map.Entry<Long, Integer> entry: xYpairs.entrySet()) {
				JSONObject xYpair = new JSONObject();
				xYpair.put("date", dateFormatter.format(new Date(entry.getKey())).toString());
				xYpair.put("response", entry.getValue());
				graphValues.add(xYpair);
			}
			result.put(api, graphValues);
		}
		return result;
	}
}
