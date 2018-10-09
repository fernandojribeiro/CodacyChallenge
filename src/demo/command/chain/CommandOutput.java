package demo.command.chain;

import java.util.ArrayList;

/**
 * The class that define the command output 
 * @author fernandojribeiro
 */
public class CommandOutput {
	
	private boolean isSuccess;
	private ArrayList<String> outputMessages;
	private ArrayList<String> errorMessages;
	
	public CommandOutput() {
		outputMessages = new ArrayList<String>();
		errorMessages = new ArrayList<String>();
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	public ArrayList<String> getOutputMessages() {
		return outputMessages;
	}
	
	public void setOutputMessages(ArrayList<String> returnMessages) {
		this.outputMessages = returnMessages;
	}
	
	public void AddOutputMessage(String message) {
		this.outputMessages.add(message);
	}

	public ArrayList<String> getErrorMessages() {
		return errorMessages;
	}
	
	public void setErrorMessages(ArrayList<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
	
	public void AddErrorMessage(String message) {
		this.errorMessages.add(message);
	}
}
