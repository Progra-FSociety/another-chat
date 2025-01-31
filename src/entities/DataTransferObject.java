package entities;

import java.io.Serializable;

public class DataTransferObject implements Serializable, Cloneable {
	private static final long serialVersionUID = 1L; // Esto lo pide el eclipse por implementar serializable.
	private Message message;
	private String command;
	private byte[] chatHistory = null;

	public DataTransferObject() {

	}

	public DataTransferObject(String command) {
		this.command = command;
	}

	public DataTransferObject(Message msg, byte[] history) {
		this.message = msg;
		this.chatHistory = history;
	}

	/**
	 * To send a command and a message associated.
	 * 
	 * @param message
	 * @param command
	 */
	public DataTransferObject(Message message, String command) {
		this.message = message;
		this.command = command;
	}

	public DataTransferObject(Message message) {
		this.message = message;
	}

	public String getFormatedMessage() {
		return this.message.getMessageToChat();
	}

	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public void setFile(byte[] file) {
		this.chatHistory = file;
	}

	public byte[] getFile() {
		return this.chatHistory;
	}

	public boolean hasFile() {
		return this.chatHistory != null;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public Object clone() {
		Object obj = null;
		try {
			obj = super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}
}
