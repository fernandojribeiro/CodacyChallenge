package demo;

import demo.api.RestInterface;
import demo.command.chain.CommitHistory;

/**
 * This class triggers the program.
 * @author fernandojribeiro
 */
public class Main {

	public static void main(String[] args) {
		try {
			Common common = new Common();
			CommitHistory commitHistory = new CommitHistory(common);
			new RestInterface().initCommitAPI(commitHistory, common);
		} catch (Exception e) {
			Common.getLogger().error("There was a problem loading the program: " + e.getMessage(), e);
		}

	}

}
