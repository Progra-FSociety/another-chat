package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ExitAll extends Command {

	private final List<Chat> chats;
	private final List<ClientListener> clients;
	private final ClientListener client;
	private final Message message;

	public ExitAll(List<ClientListener> clients,
				   List<Chat> chats,
				   List<Chat> roomsServer,
				   ClientListener client,
				   Message message) {
		this.chats = client.getChats();
		this.clients = clients;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {
		String clientNick = message.getNick();

		for (Chat chat : this.chats) {
			Message msg = new Message(clientNick,
					"Se ha desconectado.", chat.getName());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);

			List<ClientListener> clnt = clients.stream()
					.filter(x -> x.getChats().contains(chat))
					.collect(Collectors.toList());

			for (ClientListener item : clnt)
				item.getOutput().writeObject(json);
		}

		for (Chat chat : this.chats) {
			chat.getUsers().remove(clientNick);
			this.chats.remove(chat);
		}

		// Hay que matar el hilo del usuario???????????
		client.stop();
	}
}
