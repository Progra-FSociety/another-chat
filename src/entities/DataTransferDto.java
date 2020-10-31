package entities;

import java.io.Serializable;

public class DataTransferDto implements Serializable {
	private static final long serialVersionUID = 1L; // Esto lo pide el eclipse por implementar serializable.
	private Message message;
	private String command;

	/**
	 * Just to send a command.
	 * 
	 * @param command
	 */
	public DataTransferDto(String command) {
		this.command = command;
	}

	/**
	 * To send a command and a message associated.
	 * 
	 * @param message
	 * @param command
	 */
	public DataTransferDto(Message message, String command) {
		this.message = message;
		this.command = command;
	}
	
	public DataTransferDto(Message message) {
		this.message = message;
	}

	public String getFormatedMessage() {
		return this.message.getMessage();
	}
	
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
