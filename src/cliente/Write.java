package cliente;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.google.gson.*;

public class Write extends Thread {
	private Gson gsonHelper;
	private Client lobby;
	private ObjectOutputStream output;

	public Write(Socket socket, Client lobby) {
		this.lobby = lobby;

		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			gsonHelper = new Gson();
		} catch (IOException ex) {
			System.out.println("Error getting input stream: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				output.writeObject(gsonHelper.toJson(lobby.getRequest()));
				wait(); // Lo hago esperar hasta que necesita enviar algún request nuevo.
			} catch (IOException ex) {
				System.out.println("Error al ir al enviar la información: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
