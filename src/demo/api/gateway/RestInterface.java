package demo.api.gateway;

import static spark.Spark.*;

import demo.Common;
import demo.api.rest.CommitsAPI;
import demo.command.chain.CommitHistory;
import demo.command.mvc.CommitController;
/**
 * This class define the behavior of the REST APIs
 * @author fernandojribeiro
 */
public class RestInterface {
	Common common;
	
	public RestInterface(Common common) {
		this.common = common;
	}
	
	/**
	 * Initiate the commit REST API
	 * @author fernandojribeiro
	 * @param commitController the execution controller
	 * @param commitHistory the fallback object
	 * @param commitsAPI the api object
	 */
	public void initCommitAPI(CommitController commitController, CommitHistory commitHistory, CommitsAPI commitsAPI) {
		try {
			// Define the listening port
			port(Integer.parseInt(common.getProperty(Common.API_PORT)));
			
			// Define the timeout and thread limitation to handle performance problems
			threadPool(Integer.parseInt(common.getProperty(Common.API_MAX_THREADS)),
					   Integer.parseInt(common.getProperty(Common.API_MIN_THREADS)),
					   Integer.parseInt(common.getProperty(Common.API_TIMEOUT)));
			
			Common.getLogger().info("Commit REST API listening [Port: " + common.getProperty(Common.API_PORT) + ", " + 
																  "Min Threads: " + common.getProperty(Common.API_MIN_THREADS) + ", " +
																  "Max Threads: " + common.getProperty(Common.API_MAX_THREADS) + ", " +
																  "Request Timeout: " + common.getProperty(Common.API_TIMEOUT) + "]...");

			// Spark lib to expose REST APIs
			get("/commits", (request, response) -> {
				Common.getLogger().info("Rest API Request: " + request.url() + (!Common.isEmpty(request.queryString())? "?" + request.queryString() : ""));
				String postMessage = commitController.execute(commitHistory, commitsAPI, request.queryParams("untilDate"), request.queryParams("sinceDate"));
        		Common.getLogger().debug("Rest API Response: " + postMessage);
				return postMessage;
			});

			// Wait for the spark listener to initialize
			awaitInitialization();
		} catch (Exception e) {
			Common.getLogger().error("There was a problem loading the program: " + e.getMessage(), e);
		}
	}
}