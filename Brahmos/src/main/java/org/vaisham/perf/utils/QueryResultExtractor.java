package org.vaisham.perf.utils;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.vaisham.perf.enums.LogLevel;
import org.vaisham.perf.manage.exceptions.UnParsableException;
import org.vaisham.perf.utils.factory.ILogger;
import org.vaisham.perf.utils.factory.LoggerManager;

/**
 * QueryResultExtractor.java KH1868 Vaishampayan Reddy
 */

public class QueryResultExtractor {

	private GsonHelper gsonHelper = new GsonHelper();
	private JSONParser jsonParser = new JSONParser();
	
	private static ILogger logger = LoggerManager.getLoggerFactory().getLogger(QueryResultExtractor.class.getName());
	/**
	 * 
	 * @param <T>
	 * @param queryResponse
	 * @return
	 * @throws UnParsableException 
	 */
	@SuppressWarnings("unchecked")
	public <T> String getContentFromList(List<T> queryResponse) throws UnParsableException {
		
		JSONArray objectArray = new JSONArray();
		int size = queryResponse.size();
		for (int i = 0; i < size; i++) {
			Object object = queryResponse.get(i);
			String objectJson = gsonHelper.convertToJson(object);
			try {
				objectArray.add((JSONObject) jsonParser.parse(new StringReader(objectJson)));
			} catch (IOException e) {
				logger.logException(LogLevel.ERROR, e);
				throw new UnParsableException(objectJson);
			} catch (ParseException e) {
				throw new UnParsableException(objectJson);
			}
		}
		return objectArray.toString();
	}

}
