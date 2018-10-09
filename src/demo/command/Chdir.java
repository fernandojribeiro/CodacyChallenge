package demo.command;

/**
 * This class implement the changedir (cd) command.
 * @author fernandojribeiro
 */
public class Chdir extends Command {
	// This is to be OS agnostic
	private static final String WINDOWS_COMMAND = "cmd /c cd"; 
	private static final String LINUX_COMMAND = "cd"; 

	public Chdir(String path) {
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")) {
			this.command = WINDOWS_COMMAND;
		} else {
			this.command = LINUX_COMMAND;
		}
		this.arguments = path;
	}
}
