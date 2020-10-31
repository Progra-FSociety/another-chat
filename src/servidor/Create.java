package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import entities.DataTransferDto;
import entities.Message;

public class Create extends Command {
	private List<Chat> chats;
	private String userName;
	private List<Chat> roomsServer;
	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;

	public Create(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, String userName,
			ClientListener client, Message message) {
		this.chats = chats;
		this.clients = clients;
		this.userName = userName;
		this.client = client;
		this.message = message;
		this.roomsServer = roomsServer;
	}

	public void execute() throws IOException {
		Message msg;
		if (client.getChats().size() < 3
				&& !this.roomsServer.stream().anyMatch(x -> x.chatName == this.message.getChat())) {
			Chat chat = new Chat(this.message.getChat());
			this.roomsServer.add(chat);
			this.client.getChats().add(chat);
			chat.users.add(client.getName());
			
			msg = new Message(client.getName(),"Has creado la sala de chat: " + chat.getName(),chat.getName());
			
		} else {
			msg = new Message(client.getName(),"No se pudo crear la sala correctamente.");
		}
		DataTransferDto dto = new DataTransferDto(msg);
		String json = gsonHelper.toJson(dto);
		
		client.getOutput().writeObject(json);
		
	}
}
