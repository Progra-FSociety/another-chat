package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class GetAllChats extends Command {

	private List<Chat> chats;
	private List<Chat> roomsServer;
	static List<ClientListener> clients;
	private ClientListener client;

	public GetAllChats(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client) {
		this.roomsServer = roomsServer;
		this.chats = chats;
		this.clients = clients;
		this.client = client;
	}

	public void execute() throws IOException {
		for (Chat chat : this.roomsServer) {
			this.client.getOutput().writeObject(chat.getName());
		}
	}
}
