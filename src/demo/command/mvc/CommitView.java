package demo.command.mvc;

import java.util.ArrayList;

/**
 * The view in the MVC design pattern
 * @author fernandojribeiro
 */
public class CommitView {

	/**
	 * Print the Commits
	 * @author fernandojribeiro
	 * @return return the message to print
	 * @param commits the object to print
	 */
	public String printCommits(ArrayList<Commit> commits) {
		String message = "\nList of Commits:\n";
		for(Commit commit : commits) {
			message += "\tShort Hash: " + commit.getShortHash() + "\n";
			message += "\tAuthor Name: " + commit.getAuthorName() + "\n";
			message += "\tAuthor Email: " + commit.getAuthorEmail() + "\n";
			message += "\tAuthor Date: " + commit.getAuthorDate() + "\n";
			message += "\tAuthor Relative Date: " + commit.getAuthorRelativeDate() + "\n";
			message += "\tSubject: " + commit.getSubject() + "\n\n";
		}
		return message;
	}
}
