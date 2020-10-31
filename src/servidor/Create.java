package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;

public class Create extends Command {
	private final List<Chat> roomsServer;
	private final ClientListener client;
	private final Message message;

	public Create(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.client = client;
		this.message = message;
		this.roomsServer = roomsServer;
	}

	public void execute() throws IOException {
		Message msg;
		if (client.getChats().size() < 3
				&& this.roomsServer.stream().noneMatch(x -> x.chatName.equals(this.message.getChat()))) {
			Chat chat = new Chat(this.message.getChat());
			chat.users.add(client.getName());
			this.roomsServer.add(chat);
			this.client.getChats().add(chat);
			msg = new Message(client.getName(), "La sala se creo con exito.", chat.getName());
		} else {
			msg = new Message(client.getName(), "No se pudo crear la sala correctamente.", message.getChat());
		}
		DataTransferObject dto = new DataTransferObject(msg);
		String json = gsonHelper.toJson(dto);
		client.getOutput().writeObject(json);
	}
}
