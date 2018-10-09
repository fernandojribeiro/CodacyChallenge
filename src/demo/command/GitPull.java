package demo.command;

/**
 * This class implement the git log command.
 * @author fernandojribeiro
 */
public class GitPull extends Command {
	private static final String COMMAND = "git pull"; 
	private static final String ARGUMENTS = "";

	public GitPull() {
		this.command = COMMAND;
		this.arguments = ARGUMENTS;
	}
}
