package cliente;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.net.Socket;
import com.google.gson.*;

import entities.DataTransferObject;

public class Read extends Thread {
	private Gson data;
	private Socket socket;
	private Client lobby;
	private ObjectInputStream input;

	public Read(Socket socket, Client lobby) {
		this.socket = socket;
		this.lobby = lobby;

		try {
			input = new ObjectInputStream(socket.getInputStream());
			data = new Gson();
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				String text = (String) input.readObject();
				DataTransferObject response = data.fromJson(text, DataTransferObject.class);
				System.out.print(response.getFormatedMessage());
			} catch (IOException ex) {
				System.out.println("Error al ir a buscar la información: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}
//			catch (InterruptedException e) {
//				e.printStackTrace();
//				break;
//			}
		}
	}
}
