package cliente;

import java.io.ObjectInputStream;
import java.io.OutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.google.gson.*;

import entities.DataTransferObject;

public class Read extends Thread {
	private Gson data;
	private ObjectInputStream input;

	public Read(Socket socket, Client lobby) {
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
				
				if(response.hasFile()) {
					OutputStream os = new FileOutputStream(new File(response.getMessage().getChat()));
					os.write(response.getFile());
					os.close();
				}
					
				System.out.println(response.getFormatedMessage());
			} catch (IOException ex) {
				System.out.println("Error al ir a buscar la informacion: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				break;
			}

		}
	}
}
