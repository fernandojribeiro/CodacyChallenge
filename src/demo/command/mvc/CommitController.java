package demo.command.mvc;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import demo.Common;
import demo.api.rest.CommitsAPI;
import demo.command.chain.CommandOutput;
import demo.command.chain.CommitHistory;

/**
 * The controller for the commits history
 * @author fernandojribeiro
 */
public class CommitController implements CommandController {
	ArrayList<Commit> commits;
	ArrayList<String> errors;
	CommitView view;
	Common common;

	public CommitController(Common common) {
		this.common = common;
		this.commits = new ArrayList<Commit>();
		this.view = new CommitView();
	}

	/**
	 * This method orchestrate the logic to obtain the commits list and the fallback 
	 * @param commitHistory to execute fallback command
	 * @param commitsAPI to obtain the commits through the public API
	 * @param untilDate the start date to obtain the commits
	 * @param sinceDate the end date to obtain the commits
	 * @return the JSON object into string
	 */
	public String execute(CommitHistory commitHistory, CommitsAPI commitsAPI, String untilDate, String sinceDate) {
		String jsonString = "";
		resetModel();
		ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
		
		try {
			if (Common.isEmpty(sinceDate) ||
				Common.isEmpty(untilDate) ||
				!Common.isDateValid(sinceDate, common.getProperty(Common.API_DATE_FORMAT)) ||
				!Common.isDateValid(untilDate, common.getProperty(Common.API_DATE_FORMAT))) {
				JSONObject json = new JSONObject();
				json.put("ErrorCode", 400);
				json.put("ErrorMessage", "Since and Until date is mandatory (format: " + common.getProperty(Common.API_DATE_FORMAT));
				Common.getLogger().error("Something went wrong (api)!");
				jsonString = json.toString();
			} else {
				JSONArray jsonArray = commitsAPI.readJsonContent(sinceDate, untilDate);
				parseOutput(jsonArray);
				updateView();
				jsonString = jsonMapper.writeValueAsString(getModel());
				Common.getLogger().info("Great Job (api)!");
			}
		} catch (Exception e1) {
			Common.getLogger().error("Fallback will be executed, Public API error: " + e1.getMessage());
			try {
				commitHistory.untilDate(untilDate);
				commitHistory.sinceDate(sinceDate);
				CommandOutput output = commitHistory.execute();
				if (output.isSuccess()) {
					parseOutput(output);
					updateView();
					jsonString = jsonMapper.writeValueAsString(getModel());
					Common.getLogger().info("Good Job (fallback)!");
				} else {
					jsonString = jsonMapper.writeValueAsString(output.getErrorMessages());
					Common.getLogger().error("Something went wrong (fallback)!");
				}
			} catch(Exception e2) {
				Common.getLogger().error("Fallback error: " + e2.getMessage());
				JSONObject json = new JSONObject();
				json.put("ErrorCode", 520);
				json.put("ErrorMessage", e2.getMessage());
				jsonString = json.toString();
				Common.getLogger().error("Something went wrong (fallback)!");
			}
		}
		return jsonString;
	}

	/**
	 * The method is responsible for parse the information gathered by the public commit API and define it in the model
	 * @author fernandojribeiro
	 * @param jsonArray the json object to parse
	 */
	public void parseOutput(JSONArray jsonArray) {
		Iterator<Object> iterator = jsonArray.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			if(object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)object;
				JSONObject commitObject = (JSONObject)jsonObject.get("commit");
				JSONObject authorObject = (JSONObject)(commitObject).get("author");
				commits.add(new Commit(jsonObject.getString("sha"),       // short hash
						authorObject.getString("name"),    // author name
						authorObject.getString("email"),   // author email
						authorObject.getString("date"),    // author date
						Common.COMMIT_EMPTY,               // author relative date doesn't exist in API
						commitObject.getString("message"))); // subject
			}
		}
	}

	/**
	 * The method is responsible for parse the information gathered by the git log commit and define it in the model
	 * @author fernandojribeiro
	 */
	public void parseOutput(CommandOutput output) {
		for(String outputLine : output.getOutputMessages()) {
			String[] commit = outputLine.split(Common.COMMIT_SEPARATOR);

			commits.add(new Commit((commit.length >=1)?commit[0]:Common.COMMIT_EMPTY,   // short hash
								   (commit.length >=2)?commit[1]:Common.COMMIT_EMPTY,   // author name
								   (commit.length >=3)?commit[2]:Common.COMMIT_EMPTY,   // author email
								   (commit.length >=4)?commit[3]:Common.COMMIT_EMPTY,   // author date
								   (commit.length >=5)?commit[4]:Common.COMMIT_EMPTY,   // author relative date
								   (commit.length >=6)?commit[5]:Common.COMMIT_EMPTY)); // subject
		}

		for(String error : output.getErrorMessages()) {
			errors.add(error);
		}
	}

	/**
	 * The method update the view (print the commits to the output)
	 * @author fernandojribeiro
	 */
	public void updateView() {
		Common.getLogger().debug("View: " + view.printCommits(commits));
	}

	/**
	 * This method return the object model
	 * @return the model
	 */
	public Object getModel() {
		return commits;
	}
	
    /**
     * This method return the error messages
     * @return the error messages
     */
	public Object getErrors() {
		return errors;
	}
	
	/**
	 * This method clear the model info
	 */
	public void resetModel() {
		this.commits = new ArrayList<Commit>();
	}
}
