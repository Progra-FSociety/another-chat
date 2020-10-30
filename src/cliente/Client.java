package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Client {
	private String userName;
	private final String guideCommand = "GET-GUIDE";
	private List<String> chats; // La lista de los chats a los q está unido.
	private Socket connection;
	DataInputStream input;
	private DataTransferDto request;
	private Read reader;
	private Write writer;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();

	private final static LinkedList<String> commands = new LinkedList<String>(
			List.of("CREATE", "EXIT", "JOIN", "SENDPRIVATE", "SENDCHAT", "GETALLCHATS"));
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

	public List<String> getChats() {
		return this.chats;
	}

	public DataTransferDto getRequest() {
		return request;
	}

	public String getUserName() {
		return this.userName;
	}

	public String getFormatedDate() {
		return dateFormat.format(new Date());
	}

	public void startLobby() {
		System.out.println("Hola! Bienvienido al lobby de FSociety");
		System.out.println("¿Cómo querés que te llamemos?");
		System.out.println("Nombre de usuario: _");
		Scanner scn = new Scanner(System.in);
		this.userName = scn.next();
		System.out.println("Hola " + this.userName + ".");
		scn.nextLine(); // Lo limpio.
		scn.close(); // Lo cerramos.
		showGuide(); // Muestro la guía.
		this.startConnection();
	}

	public void showGuide() {
		System.out.println("Para salir de algún chat que elijas, escribí \"exit {nombre del chat}\"");
		System.out.println("Para crear un chat en cualquier momento, escribí \"create {nombre del chat}\"");
		System.out.println("Para unirte a un chat en cualquier momento, escribí \"join {nombre del chat}\"");
		System.out.println("Cuando escribas en un chat, hacelo de esta manera: \"sendchat {nombre chat} {mensaje}.\"");
		System.out.println(
				"Para escribirle a alguien por privado, hacelo de esta manera: \"sendprivate {nombre usuario} {mensaje}.\"");
		System.out.println("Para volver a ver la ayuda, escribí \"guide-commands-chat\"");
	}

	public void analyzeInput(String input) {
		String[] words = input.split(" ");
		String command = words[0];

		if (words[0].toUpperCase() == this.guideCommand) {
			showGuide();
		} else if (commands.contains(command.toUpperCase())) { // Preguntamos si está pidiendo usar un comando
																// pre-definido.
			Message msg = new Message(this.userName, input.substring(command.length() + 1));
			this.request = new DataTransferDto(msg, command);
			writer.notify();
		}
	}

	public void showChats() {
		this.request = new DataTransferDto(commands.getLast());
		writer.notify();
	}

	/**
	 * Initialize reader and writer of chat.
	 */
	private void startConnection() {
		this.writer = new Write(this.connection, this);
		writer.start();
		this.reader = new Read(this.connection, this);
		reader.start();
	}

}
