package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferDto;
import entities.Message;

public class ExitAll extends Command {

	private List<Chat> chats;
	private String userName;
	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;

	public ExitAll(List<ClientListener> clients, List<Chat> chats, String userName, ClientListener client,
			Message message) {
		this.chats = client.getChats();
		this.clients = clients;
		this.userName = userName;
		this.client = client;
		this.message = message;
	}

	public void execute() throws IOException {

		for (Chat chat : this.chats) {
			Message msg = new Message(client.getName(),
					"Me voy a desconectar de: " + chat.getName() + " me dio ansieda", chat.getName());
			DataTransferDto dto = new DataTransferDto(msg);
			String json = gsonHelper.toJson(dto);
			List<ClientListener> clnt = clients.stream().filter(x -> x.getChats().contains(chat))
					.collect(Collectors.toList());
			for (ClientListener item : clnt)
				item.getOutput().writeObject(json);
		}
		
		//Borrar el nombre del usuario de los chats, y tambien borramos todos los chats a los que este usuario pertenecia.
		for (Chat chat : this.chats) {
			chat.users.remove(this.userName);
			this.chats.remove(chat);
		}
		
		//Hay que matar el hilo del usuario?
//		client.stop();
	}
}
