import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

public class Lobby {
	private String userName;
	private String ip;
	private int port;
	private List<String> chats;
	private Socket connection;
	DataInputStream input;

	public Lobby(String ip, int port) {
		try {
			this.connection = new Socket(ip, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startLobby() {
		System.out.println("Hola! Bienvienido al lobby de FSociety");
		System.out.println("¿Cómo querés que te llamemos?");
		System.out.println("Nombre de usuario: _");
		
	}

	public void showChats() {
		//Creamos una conexión para mostrar los chats en el lobby.
	}

	public void createOrSelectChats() {
		startConnection();
	}

	public List<String> getChats() {
		return this.chats;
	}

	/**
	 * Initialize reader and writer of chat.
	 */
	private void startConnection() {
		var reader = new Read(this.connection,this).start();
		var writer = new Write(this.connection,this).start();
	}
	
	private void stopConenction() {
		//
	}
	
	public void backToMenu() {
		this.startLobby();
		this.showChats();
		this.createOrSelectChats();
	}
	
}
