package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferObject;
import entities.Message;

public class ExitAll extends Command {

	private List<Chat> chats;
	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;

	public ExitAll(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.chats = client.getChats();
		this.clients = clients;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {

		for (Chat chat : this.chats) {
			Message msg = new Message(client.getName(),
					"Se ha desconectado.", chat.getName());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);
			List<ClientListener> clnt = clients.stream().filter(x -> x.getChats().contains(chat))
					.collect(Collectors.toList());
			for (ClientListener item : clnt)
				item.getOutput().writeObject(json);
		}

		for (Chat chat : this.chats) {
			chat.users.remove(this.message.getNick());
			this.chats.remove(chat);
		}

		// Hay que matar el hilo del usuario???????????
//		client.stop();
	}
}
