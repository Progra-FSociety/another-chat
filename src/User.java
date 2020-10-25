import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	private String ip;
	private int port;
	private ConsoleChat chat;
	private Socket userConection;
	DataInputStream input;

	public User(String ip, int port, ConsoleChat chat) {
		this.chat = chat;
		try {
			this.userConection = new Socket(ip, port);
			this.input = new DataInputStream(userConection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Receives messages.
	 */
	public void getMessages() {
		boolean connected = true;

		while (connected) {
			try {
				chat.showMessage(this.input.readUTF() + System.lineSeparator());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
