package servidor;

import entities.DataTransferDto;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;

import com.google.gson.*;

public class ClientListener extends Thread {
	private Gson gsonHelper;
	private Socket socket;
	private String ip;
	private ObjectInputStream input;
	private List<Chat> chats;
	private ObjectOutputStream output;

	private Class[] commandParameters;

	public ClientListener(Socket clientSocket, ObjectInputStream input, ObjectOutputStream output) {
		this.input = input;
		this.output = output;
		this.ip = clientSocket.getInetAddress().getHostAddress();
		this.socket = clientSocket;
		this.commandParameters = new Class[] { List.class, List.class };
	}

	@Override
	public void run() {
		while (true) {
			try {
				DataTransferDto response = gsonHelper.fromJson((String) input.readObject(), DataTransferDto.class);
				Class<Command> cls = (Class<Command>) Class.forName(response.getCommand()); // el getcommand es un
																				// "Exit" Exit.java Exit()
																				// Creo un objeto a partir
																				// del string que me llega.
				Method method;
				try {
					Object obj = cls.getDeclaredConstructor(commandParameters)
							.newInstance(new Object[] { Server.getConnections(), chats, response.getMessage() });
					method = cls.getDeclaredMethod("execute", new Class[] {}); // Busco un método de la clase creada
																				// (lo tengo que buscar así porque
																				// en tiempo de ejecución el
																				// programa no sabe qué metodos
																				// tiene).
					method.invoke(obj, null); // Invoco el método, en este caso el execute.
				} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | InstantiationException e) {
					e.printStackTrace();
				}

			} catch (IOException ex) {
				System.out.println("Error al ir al enviar la información: " + ex.getMessage());
				ex.printStackTrace();
				break;
			} catch (JsonSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
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
