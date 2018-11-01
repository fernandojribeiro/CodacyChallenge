package demo.command.mvc;

import demo.command.chain.CommandOutput;

/**
 * The interface for the controler
 * @author fernandojribeiro
 */
public interface CommandControler {
	void parseOutput(CommandOutput output);	
	
	void updateView();
	
	Object getModel();
}
