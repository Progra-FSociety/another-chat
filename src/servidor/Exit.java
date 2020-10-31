package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Exit extends Command {

	private final List<ClientListener> clients;
	private final ClientListener client;
	private final Message message;
	private List<Chat> chats;

	public Exit(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.clients = clients;
		this.client = client;
		this.message = message;
		this.chats = chats;
	}

	public void execute() throws IOException {
		String clientNick = message.getNick();
		Message msg;

		Chat chat = client.getChats().stream()
				.filter(x -> x.getName().toUpperCase().equals(message.getChat().toUpperCase())).findFirst()
				.orElse(null);

		if (chat != null) {

			List<ClientListener> clnt = clients.stream().filter(x -> x.getChats().contains(chat))
					.collect(Collectors.toList());

			msg = new Message(clientNick, "Ha salido del chat.", chat.getName());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);

			for (ClientListener item : clnt)
				item.getOutput().writeObject(json);

			chat.getUsers().remove(clientNick);
			client.getChats().remove(chat);
		} else {
			msg = new Message(clientNick, "La sala de la que se quiere ir no existe.", this.message.getChat());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);
			client.getOutput().writeObject(json);
		}
	}
}
