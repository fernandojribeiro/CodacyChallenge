package demo.command;

/**
 * This class implement the git clone command.
 * @author fernandojribeiro
 */
public class GitClone extends Command {
	private static final String COMMAND = "git clone"; 
	
	public GitClone(String repo) {
		this.command = COMMAND;
		this.arguments = repo;
	}
}
