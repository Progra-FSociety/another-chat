package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.google.gson.*;

public class ClientListener extends Thread {
	private final Gson gsonHelper;
	private final Socket socket;
	private final String ip;
	private final ObjectInputStream input;
	private List<Chat> chats;
	private final ObjectOutputStream output;

	private final Class[] commandParameters;

	public ClientListener(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output) {
		this.gsonHelper = new Gson();
		this.input = input;
		this.output = output;
		this.ip = clientSocket.getInetAddress().getHostAddress();
		this.socket = clientSocket;
		this.commandParameters = new Class[] { List.class, List.class, List.class, ClientListener.class,
				Message.class };
	}

	@Override
	public void run() {
		while (true) {
			try {
				String json = (String) input.readObject();
				DataTransferObject response = gsonHelper.fromJson(json,
						DataTransferObject.class);
				Class<Command> cls = (Class<Command>) Class.forName(response.getCommand());
				Method method;
				try {
					Object obj = cls.getDeclaredConstructor(commandParameters).newInstance(Server.getConnections(), chats, Server.getRooms(), this, response.getMessage());
					method = cls.getDeclaredMethod("execute");
					method.invoke(obj, (Object) null);
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | InstantiationException e) {
					e.printStackTrace();
				}

			} catch (IOException ex) {
				System.out.println("Error al ir al enviar la informacion: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (JsonSyntaxException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public List<Chat> getChats() {
		return this.chats;
	}

	public ObjectOutputStream getOutput() {
		return this.output;
	}
}
