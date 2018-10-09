/**
 * 
 */
package demo.command.chain;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import demo.Common;

/**
 * This abstract class define the command chains.
 * 
 * @author fernandojribeiro
 */
public abstract class CommandChain {
	protected String commandSeparator;

	/**
	 * Abstract method to define the command chain to execute.
	 * 
	 * @author fernandojribeiro
	 * @return the return status of the executed command chain
	 */
	public abstract CommandOutput execute();

	/**
	 * Execute the command chain.
	 * 
	 * @author fernandojribeiro
	 * @return the command line output of the executed command chain
	 */
	protected CommandOutput executeCommands(String[] commands) {
		String commandPromptInput = "";
		CommandOutput output = new CommandOutput();

		try {
			for(int i = 0; i < commands.length; i++) {
				commandPromptInput += commands[i] + ((i < commands.length - 1)?" " + commandSeparator + " ":"");  
				Common.getLogger().info("Command to execute [" + (i + 1) + "]: " + commands[i]);
			}
			
			Common.getLogger().debug("Command prompt input: " + commandPromptInput);
			Process process = Runtime.getRuntime().exec(commandPromptInput);
		    BufferedReader commandOutputReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
		    BufferedReader commandErrorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));

		    String commandOutputLine;
		    while ((commandOutputLine = commandOutputReader.readLine()) != null) {
	            output.AddOutputMessage(commandOutputLine);
				output.setSuccess(true);
				Common.getLogger().debug("Command Output: " + commandOutputLine);
	        }
		    
		    String commandErrorLine;
		    while ((commandErrorLine = commandErrorReader.readLine()) != null) {
			    //TODO: Some commands sent to sterr some messages that are not errors, like git clone for example with message "Cloning into 'CommitViewer'...".
		    	//TODO: This is a hammered solution to address this problem.
				if(commandErrorLine.contains("error") || commandErrorLine.contains("fatal")) {
					output.AddErrorMessage(commandErrorLine);
					output.setSuccess(false);
				}
				Common.getLogger().error("Command Error: " + commandErrorLine);
	        }
		} catch (Exception e) {
			Common.getLogger().error("Error during command execution: " + e.getMessage(), e);
			output.AddErrorMessage(e.getMessage());
			output.setSuccess(false);
		}
		return output;
	}
}
