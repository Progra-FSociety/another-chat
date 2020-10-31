package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;

public class GetAllChats extends Command {

	private final List<Chat> roomsServer;
	static List<ClientListener> clients;
	private final ClientListener client;

	public GetAllChats(List<ClientListener> clients,
					   List<Chat> chats,
					   List<Chat> roomsServer,
					   ClientListener client,
					   Message message) {
		this.roomsServer = roomsServer;
		this.client = client;
	}

	public void execute() throws IOException {
		for (Chat chat : this.roomsServer) {
			Message msg = new Message("", Integer.toString(chat.getUsers().size()), chat.getName());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);
			this.client.getOutput().writeObject(json);
		}
		
	}
}
