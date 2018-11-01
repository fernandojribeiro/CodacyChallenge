package demo.command;

/**
 * This class implement the git log command.
 * @author fernandojribeiro
 */
public class GitLog extends Command {
	private static final String COMMAND = "git log"; 
	private static final String ARGUMENTS = "--pretty=format:\"%h;%an;%ae;%ad;%ar;%s\"";

	public GitLog() {
		this.command = COMMAND;
		this.arguments = ARGUMENTS;
	}
	
	public void sinceDate(String date) {
		this.arguments += " --since=" + date;
	}
	
	public void untilDate(String date) {
		this.arguments += " --until=" + date;
	}
}
