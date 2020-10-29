package cliente;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import com.google.gson.*;

public class Client {
	private String userName;
	private String ip;
	private int port;
	private List<String> chats; // La lista de los chats a los q está unido.
	private Socket connection;
	DataInputStream input;
	private DataTransferDto request;
	private Read reader;
	private Write writer; 

	/*
	 * inicia el servidor - levanta el lobby salas de chats - <- se une cliente
	 * 
	 */

	public Client(String ip, int port) {
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
		this.userName = System.console().readLine();
		System.out.println("Hola " + this.userName + ".");
		showGuide();
		this.showChats();
	}

	public void showGuide() {
		System.out.println("Para salir de algún chat que elijas, escribí \"exit {nombre del chat}\"");
		System.out.println("Para crear un chat en cualquier momento, escribí \"create {nombre del chat}\"");
		System.out.println("Para unirte a un chat en cualquier momento, escribí \"join {nombre del chat}\"");
		System.out.println("Cuando escribas en un chat, hacelo de esta manera: \"{nombre chat} {mensaje}.\"");
		System.out.println(
				"Para escribirle a alguien por privado, hacelo de esta manera: \"{nombre usuario} {mensaje}.\"");
		System.out.println("Para volver a ver la ayuda, escribí \"help-commands-chat\"");
	}
	/*
	 * [sala1,sala2] command: {Sala 1} hola gente
	 */

	public void showChats() {

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
		this.reader = new Read(this.connection, this);
		reader.start();
		this.writer = new Write(this.connection, this);
		writer.start();
	}

	@SuppressWarnings("deprecation")
	private void stopConenction() {
		reader.suspend();
		reader.stop();
		writer.suspend();
		writer.stop();
		//
	}

	public void analyzeInput(String input) {
		String command; // Lo que desea realizar..
		String message; // Lo que desea realizar..
		// Acá debemos analizar si le llega algo de todo esto:
		/*
		 * "exit {nombre del chat}" "create {nombre del chat}" "join {nombre del chat}"
		 * "{nombre chat} {mensaje}" "{nombre usuario} {mensaje}" "help-commands-chat"
		 */
		if (input.startsWith("help-")) {
			// Blabla
		}
		// Este if es re contra mejorable, solo lo dejo de ejemplo para orientar.
		if (input.startsWith(chats.get(0)) || input.startsWith(chats.get(1)) || input.startsWith(chats.get(2))) {
			// blabla
		}
		// if...
		// if..
		// podría ser un swtich case tmb.
		createRequest(command, message);
	}

	//PARA MANDARLE MENSAJES AL SERVIDOR, SE USA WRITER.NOTIFY() PORQUE EL NOTIFY LE AVISA AL HILO QUE EMPIECE A EJECUTARSE DE NUEVO.
	//
	private void createRequest(String command, String message) {
		this.request = new DataTransferDto(message, command);
		writer.notify(); // Así hacemos que deje de esperar y le mande el comando al server...
	}

	public DataTransferDto getRequest() {
		return request;
	}
}
