package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class SendChat extends Command {

	private final List<ClientListener> clients;
	private final ClientListener client;
	private final Message message;

	public SendChat(List<ClientListener> clients,
					List<Chat> chats,
					List<Chat> roomsServer,
					ClientListener client,
					Message message) {
		this.clients = clients;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {

		// Busco el chat al cual quiero mandar el msj
		Message msg;
		String clientNick = message.getNick();

		Chat chat = client.getChats().stream()
				.filter(x -> x.chatName.toUpperCase().equals(message.getChat().toUpperCase()))
				.findFirst().orElse(null);

		// Busco los clientes adheridos a ese chat

		List<ClientListener> clnt = clients.stream().filter(x -> x.getChats().contains(chat))
				.collect(Collectors.toList());


		if (chat != null) {
			msg = new Message(clientNick, this.message.getBodyMsg(), chat.getName());
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);
			for (ClientListener item : clnt)
				item.getOutput().writeObject(json);
		} else {
			msg = new Message(clientNick,
					"No pudo ser entregado tu mensaje porque el chat no existe.",
					clientNick);
			DataTransferObject dto = new DataTransferObject(msg);
			String json = gsonHelper.toJson(dto);
			client.getOutput().writeObject(json);
		}
	}
}
