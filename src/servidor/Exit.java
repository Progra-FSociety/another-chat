package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferObject;
import entities.Message;

public class Exit extends Command {

	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;

	public Exit(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.clients = clients;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {
		Chat chat = client.getChats().stream().filter(x -> x.chatName.equals(message.getChat())).findFirst().get();

		List<ClientListener> clnt = clients.stream().filter(x -> x.getChats().contains(chat))
				.collect(Collectors.toList());

		Message msg = new Message(client.getName(), "Ha salido del chat.", chat.getName());
		DataTransferObject dto = new DataTransferObject(msg);
		String json = gsonHelper.toJson(dto);
		for (ClientListener item : clnt)
			item.getOutput().writeObject(json);

		client.getChats().remove(chat);
	}
}
