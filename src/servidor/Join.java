package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import entities.DataTransferDto;
import entities.Message;

public class Join extends Command {
	private List<Chat> chatClients;
	private List<Chat> roomsServer;
	private Message message;
	private ClientListener client;

	public Join(List<ClientListener> clients, List<Chat> chatClients, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.chatClients = chatClients;
		this.roomsServer = roomsServer;
		this.message = message;
		this.client = client;
	}

	public void execute() throws IOException {
		for (Chat chat : this.roomsServer) {
			if (this.message.getChat() == chat.getName()) { // Si el nombre de chat que eligio para unirse es igual al
				if (this.chatClients.size() < 3) { // nombre del chat que se encuentra en el servidor, lo agregamos a su
													// lista.
					chat.users.add(this.message.getNick());
					this.chatClients.add(chat);
				} else {
					Message msg = new Message(client.getName(),
							"No se pudo agregar al chat: " + chat.getName() + ", ya se encuentra en 3 salas de chat.");
					DataTransferDto dto = new DataTransferDto(msg);
					String json = gsonHelper.toJson(dto);
					client.getOutput().writeObject(json);
				}
			}
		}
	}
}
