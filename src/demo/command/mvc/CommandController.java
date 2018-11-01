package demo.command.mvc;

import demo.command.chain.CommandOutput;

/**
 * The interface for the controller
 * @author fernandojribeiro
 */
public interface CommandController {
	void parseOutput(CommandOutput output);	
	
	void updateView();
	
	Object getModel();
}
