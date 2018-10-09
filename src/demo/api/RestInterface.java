package demo.api;

import static spark.Spark.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import demo.Common;
import demo.command.chain.CommandOutput;
import demo.command.chain.CommitHistory;
import demo.command.mvc.CommandControler;
import demo.command.mvc.CommitControler;
/**
 * This class define the behavior of the REST APIs
 * @author fernandojribeiro
 *
 */
public class RestInterface {
	
	/**
	 * Initiate the commit REST API
	 * @author fernandojribeiro
	 * @param commitHistory the object to return
	 * @param common the common functionalities of the program
	 */
	public void initCommitAPI(CommitHistory commitHistory, Common common) {
		try {
			ObjectMapper jsonMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

			// Define the listening port
			port(Integer.parseInt(common.getProperty(Common.PORT)));
			
			// Define the timeout and thread limitation to handle performance problems
			threadPool(Integer.parseInt(common.getProperty(Common.MAX_THREADS)),
					   Integer.parseInt(common.getProperty(Common.MIN_THREADS)),
					   Integer.parseInt(common.getProperty(Common.TIMEOUT)));
			
			Common.getLogger().info("Commit REST API listening [Port: " + common.getProperty(Common.PORT) + ", " + 
																  "Min Threads: " + common.getProperty(Common.MIN_THREADS) + ", " +
																  "Max Threads: " + common.getProperty(Common.MAX_THREADS) + ", " +
																  "Request Timeout: " + common.getProperty(Common.TIMEOUT) + "]...");

			// Spark lib to expose REST APIs
			get("/commits", (request, response) -> {
				Common.getLogger().debug("Rest API Request: " + request.url() + "?" + request.queryString());
				String beforeDate = request.queryParams("beforeDate");
				String maxCount = request.queryParams("maxCount");

				if (!beforeDate.isEmpty()) {
					commitHistory.beforeDate(beforeDate);
				}
				
				if (!maxCount.isEmpty() && maxCount.chars().allMatch(Character::isDigit)) {
					commitHistory.maxCount(Integer.parseInt(maxCount));
				}

				CommandOutput output = commitHistory.execute();

				String jsonString;
				if (output.isSuccess()) {
					CommandControler commitControler = new CommitControler();
					commitControler.parseOutput(output);
					commitControler.updateView();

					jsonString = jsonMapper.writeValueAsString(commitControler.getModel());
					Common.getLogger().debug("Rest API Response: " + jsonString);
					Common.getLogger().info("Great Job!");
				} else {
					jsonString = jsonMapper.writeValueAsString(output.getErrorMessages());
					Common.getLogger().error("Something went wrong!");
				}
				return jsonString;

			});
			
			// Wait for the spark listener to initialize
			awaitInitialization();

		} catch (Exception e) {
			Common.getLogger().error("There was a problem loading the program: " + e.getMessage(), e);
		}
	}
}