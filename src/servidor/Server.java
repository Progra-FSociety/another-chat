package servidor;

import java.io.IOException;
import java.io.*;
import java.util.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.*;

public class Server extends Thread {
	ServerSocket socket;
	static List<ClientListener> clients = new LinkedList<ClientListener>();
	static List<Chat> rooms = new LinkedList<Chat>();

	public Server() throws IOException {
		this.socket = new ServerSocket(8000);
		SimpleDateFormat dateformat = new SimpleDateFormat();
		
		System.out.println("Se inicio el servidor a las " + dateformat.format(new Date()));		
		
		Server.rooms.add(new Chat("Fsociety1"));
		Server.rooms.add(new Chat("Fsociety2"));
		Server.rooms.add(new Chat("Fsociety3"));
		Server.rooms.add(new Chat("Fsociety4"));
	}

	public static List<ClientListener> getConnections() {
		return clients;
	}
	
	public static List<Chat> getRooms() {
		return rooms;
	}
	
	@Override
	public void run() {
		try {
			while (true) {
				Socket clientSocket = this.socket.accept(); // Se queda esperando para aceptar conexiones de clientes.
				ObjectOutputStream output = new ObjectOutputStream(clientSocket.getOutputStream()); // Abrimos stream de
				output.flush();
				ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
				
				ClientListener attention = new ClientListener(clientSocket, input, output);
				clients.add(attention);
				attention.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}
}