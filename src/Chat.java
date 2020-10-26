import java.net.Socket;

public class Chat {
	private Socket connection;
	private String chatName;
	public Chat(String chatName, Socket socket) {
		this.connection = socket;
	}
	
	/**
	 * Initialize reader and writer of chat.
	 */
	public void start() {
		var reader = new Read(this.connection,this).start();
		var writer = new Write(this.connection,this).start();
	}
	
	public String getChatName() {
		return this.chatName;
	}
}
