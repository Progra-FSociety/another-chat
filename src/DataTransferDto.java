import java.io.Serializable;

public class DataTransferDto implements Serializable {
	private static final long serialVersionUID = 1L; //Esto lo pide el eclipse por implementar serializable.
	private String message;
	private boolean isError;
	private String command;

	public DataTransferDto(String message, boolean isError, String command) {
		this.message = message;
		this.isError = isError;
		this.command = command;
	}

	public DataTransferDto(String message, String command) {
		this.message = message;
		this.command = command;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
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
