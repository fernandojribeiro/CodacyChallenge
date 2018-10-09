package demo.command;

/**
 * This abstract class define the command primitives.
 * @author fernandojribeiro
 */
public abstract class Command {
	protected String command = ""; 
	protected String arguments = "";

	/**
	 * Method to set extra arguments
	 * @author fernandojribeiro
	 * @param arguments the extra arguments to add into the command
	 */
	public void setArgument(String arguments) {
		this.arguments = arguments;
	}

	/**
	 * This interface return the command to execute.
	 * @author fernandojribeiro
	 * @return the command to execute
	 */
	public String getCommand() {
		return command + " " + arguments;
	}
}
