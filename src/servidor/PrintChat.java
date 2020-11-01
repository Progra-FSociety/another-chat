package servidor;

import entities.DataTransferObject;
import entities.Message;

import java.io.IOException;
import java.util.List;

public class PrintChat extends Command {
	
	private final ClientListener client;
	private final Message message;

	public PrintChat(List<ClientListener> clients, List<Chat> chats, List<Chat> roomsServer, ClientListener client,
			Message message) {
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {
		String clientNick = this.message.getNick();
		Message msg;

		Chat chat = client.getChats().stream()
				.filter(x -> x.getName().toUpperCase().equals(message.getChat().toUpperCase())).findFirst()
				.orElse(null);
		DataTransferObject dto = null;
		if (chat != null) {
			byte[] history = chat.printChat();
			msg = new Message(clientNick, "Te env�o el historial.", this.message.getChat());
			dto = new DataTransferObject(msg, history);
		} else {
			msg = new Message(clientNick, "No estas conectado al chat que queres imprimir.", this.message.getChat());
			dto = new DataTransferObject(msg);
		}		

		String json = gsonHelper.toJson(dto);
		client.getOutput().writeObject(json);
	}
}
