package demo.command.mvc;

import java.util.ArrayList;

import demo.Common;
import demo.command.chain.CommandOutput;

/**
 * The controler for the commits history
 * @author fernandojribeiro
 */
public class CommitControler implements CommandControler {
	ArrayList<Commit> commits;
	ArrayList<String> errors;
	CommitView view;

	public CommitControler() {
		this.commits = new ArrayList<Commit>();
		view = new CommitView();
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
		Common.getLogger().debug(view.printCommits(commits));
	}
	
	public Object getModel() {
		return commits;
	}
	
	public Object getErrors() {
		return errors;
	}
}
