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
		Message msg;
		ClientListener receiver;

		/**
		 * It's the client which will receive the message.
		 */
		String toClient = message.getChat();
		String clientNick = message.getNick();

		receiver = clients.stream()
				.filter(x -> x.getNickname().equals(toClient))
				.findFirst().orElse(null);

		if (receiver != null) {
			boolean hasScope = receiver.getChats().stream()
					.anyMatch(chat -> chat.getUsers().contains(clientNick));

			if (hasScope){
				msg = new Message(clientNick, message.getBodyMsg(), toClient);
			} else {
				msg = new Message(clientNick,
						"No pudo ser entregado tu mensaje porque no compartis un chat con el usuario: " + receiver.getNickname(),
						clientNick);
				receiver = client;
			}
		} else {
			msg = new Message(clientNick,
					"No pudo ser entregado tu mensaje porque el usuario no existe.",
					clientNick);
			receiver = client;
		}
		DataTransferObject dto = new DataTransferObject(msg);
		String json = gsonHelper.toJson(dto);
		receiver.getOutput().writeObject(json);
		client.getOutput().writeObject(json);
	}
}
