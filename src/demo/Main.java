package demo;

import demo.api.gateway.RestInterface;
import demo.api.rest.CommitsAPI;
import demo.command.chain.CommitHistory;
import demo.command.mvc.CommitController;

/**
 * This class triggers the program.
 * @author fernandojribeiro
 */
public class Main {

	public static void main(String[] args) {
		try {
			Common common = new Common();
			CommitController commitController = new CommitController(common);
			CommitHistory commitHistory = new CommitHistory(common);
			CommitsAPI commitsAPI = new CommitsAPI(common);
			new RestInterface(common).initCommitAPI(commitController, commitHistory, commitsAPI);
		} catch (Exception e) {
			Common.getLogger().error("There was a problem loading the program: " + e.getMessage(), e);
		}

	}

}
