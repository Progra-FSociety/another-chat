package cliente;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import entities.DataTransferObject;
import entities.Message;

public class Client extends Thread {
	private String userName;
	private final String GUIDE_COMMAND = "GET-GUIDE";
	private List<String> chats; // La lista de los chats a los q est� unido.
	private Socket connection;
	DataInputStream input;
	private DataTransferObject request;
	private Read reader;
	private Write writer;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();

	private final static LinkedList<String> COMMANDS = new LinkedList<String>(
			List.of("Create", "Exit", "Join", "SendPrivate", "SendChat", "ExitAll", "GetAllChats"));
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

	public DataTransferObject getRequest() {
		return request;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String name) {
		this.userName = name;
	}

	public String getFormatedDate() {
		return dateFormat.format(new Date());
	}

	public void startLobby() {
		showGuide(); // Muestro la gu�a.
		this.startConnection();
	}

	public void showGuide() {
		System.out.println("Para salir de alg�n chat que elijas, escrib� \"exit {nombre del chat}\"");
		System.out.println("Para crear un chat en cualquier momento, escrib� \"create {nombre del chat}\"");
		System.out.println("Para unirte a un chat en cualquier momento, escrib� \"join {nombre del chat}\"");
		System.out.println("Cuando escribas en un chat, hacelo de esta manera: \"sendchat {nombre chat} {mensaje}.\"");
		System.out.println(
				"Para escribirle a alguien por privado, hacelo de esta manera: \"sendprivate {nombre usuario} {mensaje}.\"");
		System.out.println("Para volver a ver los chats del lobby, escrib� \"getallchats\"");
		System.out.println("Para salir del lobby, escrib� \"exitall\"");
		System.out.println("Para volver a ver la ayuda, escrib� \"get-guide\"");
	}

	public void analyzeInput(String input) {
		String[] words = input.split(" ");
		String command = words[0];
		String nameChat = words[1];
		boolean findIt = false;

		if (words[0].toUpperCase() == this.GUIDE_COMMAND) {
			showGuide();

		} else {
			for (String item : Client.COMMANDS) {
				if (item.toUpperCase().equals(command.toUpperCase())) {
					command = item;
					findIt = true;
					break;
				}
			}
			if (findIt) {
				Message msg = new Message(this.userName,
						input.substring(command.length() + 1 + nameChat.length()).trim(), nameChat);
				this.request = new DataTransferObject(msg, command);
				synchronized(this) {
					   this.notify();
					}
			} else {
				System.out.println("Ups. No es un comando v�lido.");
			}
		}
	}

	public void showChats() {
		this.request = new DataTransferObject(COMMANDS.getLast());
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

	public String getExitAllCommand() {
		return "EXITALL";
	}
	
	private static final String EMPTY_STRING = "";

	public static void main(String[] args) throws IOException {
		Client client = new Client("localhost", 8000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		String text = EMPTY_STRING;
		System.out.println("Hola! Bienvienido al lobby de FSociety");
		System.out.println("�C�mo quer�s que te llamemos?");
		System.out.println("Nombre de usuario: _");
		client.setUserName(reader.readLine());
		client.startLobby();
       
		do {			
			text = reader.readLine();
			client.analyzeInput(text);
		} while (true);
	}

}
