package servidor;

import java.io.IOException;
import java.util.List;

import entities.DataTransferObject;
import entities.Message;

public class SendPrivate extends Command {

	private final List<ClientListener> clients;
	private final ClientListener client;
	private final Message message;

	public SendPrivate(List<ClientListener> clients,
					   List<Chat> chats,
					   List<Chat> roomsServer,
					   ClientListener client,
					   Message message) {
		this.clients = clients;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {
		String toClient = message.getChat();
		Message msg;
		ClientListener receiver = clients.stream().filter(x -> x.getName().equals(toClient)).findFirst().orElse(null);

		if (receiver != null) {
			msg = new Message(client.getName(), message.getBodyMsg(), toClient);
		} else {
			msg = new Message(client.getName(),
					"No pudo ser entregado tu mensaje porque el usuario no existe.",
					client.getName());
			receiver = client;
		}
		DataTransferObject dto = new DataTransferObject(msg);
		String json = gsonHelper.toJson(dto);
		receiver.getOutput().writeObject(json);
	}
}
