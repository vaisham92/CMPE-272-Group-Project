package org.vaisham.perf.core;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.vaisham.perf.utils.GsonHelper;

public class TestVaisham {
	public static void main(String[] args) {
		GsonHelper gsonHelper = new GsonHelper();
		Map<String, JSONArray> graph_data = new HashMap<String, JSONArray>();
		JSONObject abcd = new JSONObject();
		abcd.put(System.currentTimeMillis(), 999);
		JSONArray xyz = new JSONArray();
		graph_data.put("sairam", xyz);
		JSONObject mnb = new JSONObject();
		mnb.put("909090", "909090");
		graph_data.get("sairam").add(abcd);
		graph_data.get("sairam").add(mnb);
		System.out.println(graph_data.toString());
		JSONObject qwery = new JSONObject();
		qwery.put("graph_data", graph_data);
		System.out.println(gsonHelper.convertToJson(qwery));
		System.out.println(qwery.toJSONString());
	}
}
