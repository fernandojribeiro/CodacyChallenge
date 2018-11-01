package demo.api.rest;

import java.io.IOException;
import java.nio.charset.MalformedInputException;

import org.json.JSONArray;

import demo.Common;

/**
 * This class invoke the public commits api from the repository
 * @author fernandojribeiro
 */
public class CommitsAPI {
	Common common;

	public CommitsAPI(Common common) {
		this.common = common;
	}
	
	public JSONArray readJsonContent(String sinceDate, String untilDate) throws IOException, MalformedInputException {
		String url = common.getProperty(Common.COMMITS_URL).
				replace(common.getProperty(Common.COMMITS_SINCE_DATE_TAG), sinceDate).
				replace(common.getProperty(Common.COMMITS_UNTIL_DATE_TAG), untilDate);
		
		return common.invokeJsonAPI(url);
	}
}