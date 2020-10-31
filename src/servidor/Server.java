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
	List<Chat> rooms;
	int clientCount = 0;

	public Server() throws IOException {
		this.socket = new ServerSocket(8000);
		SimpleDateFormat dateformat = new SimpleDateFormat();
		
		System.out.println("Se inicio el servidor a las " + dateformat.format(new Date()));		
	}

	public static List<ClientListener> getConnections() {
		return clients;
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