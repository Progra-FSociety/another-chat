import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import com.google.gson.*;

public class Read extends Thread{
	private Gson data;
	private Socket socket;
	private Lobby lobby;
	private ObjectInputStream input;

	public Read(Socket socket, Lobby lobby) {
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

	public void run()  {
		while (true) {
			try {
				String text = (String) input.readObject();
				DataTransferDto response = data.fromJson(text, DataTransferDto.class);

				if (response.isError())
					System.out.print("Ups. Ocurri� un error: " + response.getMessage());
				else {
					System.out.print(response.getMessage());
				}
				wait(); //Este m�todo sirve para dejarlo esperando hasta que le llegue un "read.notify()" de la clase lobby.
			} catch (IOException ex) {
				System.out.println("Error al ir a buscar la informaci�n: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}
	}
}
