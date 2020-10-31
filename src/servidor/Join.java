package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferObject;
import entities.Message;

public class Join extends Command {
	private final List<Chat> chats;
	private final List<Chat> roomsServer;
	private final Message message;
	private final ClientListener client;
	private final List<ClientListener> clients;

	public Join(List<ClientListener> clients,
				List<Chat> chats,
				List<Chat> roomsServer,
				ClientListener client,
				Message message) {
		this.chats = chats;
		this.roomsServer = roomsServer;
		this.message = message;
		this.client = client;
		this.clients = clients;
	}

	public void execute() throws IOException {
		String clientNick = message.getNick();
		Message msg = null;

		if (this.chats.size() >= 3) {
			msg = new Message(clientNick,
					"No se pudo agregar al chat, ya se encuentra en 3 salas de chat.", message.getChat());
		} else {
			Chat chat = this.roomsServer.stream()
					.filter(room -> room.getName().equals(this.message.getChat()))
					.findFirst().orElse(null);
			boolean isAlreadyInTheChat = client.getChats().contains(chat);

			if (chat == null) {
				msg = new Message(clientNick,
						"No existe el chat.", message.getChat());
			} else if(isAlreadyInTheChat){
				msg = new Message(clientNick,
						"Ya estas en el chat.", message.getChat());
			} else{
				chat.getUsers().add(this.message.getNick());
				this.chats.add(chat);
				List<ClientListener> clientsListeners = clients.stream().filter(cls -> cls.getChats().contains(chat))
						.collect(Collectors.toList());

				for (ClientListener clients : clientsListeners) {
					msg = new Message(clientNick,"Se ha unido al chat ", chat.getName());
				}
			}
		}

		DataTransferObject dto = new DataTransferObject(msg);
		String json = gsonHelper.toJson(dto);
		client.getOutput().writeObject(json);
	}
}
