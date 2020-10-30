package cliente;

import java.io.Serializable;

public class DataTransferDto implements Serializable {
	private static final long serialVersionUID = 1L; // Esto lo pide el eclipse por implementar serializable.
	private Message message;
	private boolean isError;
	private String command;

	/**
	 * Just to send a command.
	 * @param command
	 */
	public DataTransferDto(String command) {
		this.command = command;
	}
	/**
	 * To send a command and a message associated.
	 * @param message
	 * @param command
	 */
	public DataTransferDto(Message message, String command) {
		this.message = message;
		this.command = command;
	}
	/**
	 * Not used.
	 * @param message
	 * @param isError
	 * @param command
	 */
	
	public DataTransferDto(Message message, boolean isError, String command) {
		this.message = message;
		this.isError = isError;
		this.command = command;
	}

	public String getMessage() {
		return this.message.getMessage();
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isError() {
		return isError;
	}

	public void setError(boolean isError) {
		this.isError = isError;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
