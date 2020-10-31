package servidor;

import java.util.Iterator;
import java.util.List;

public class GetAllChats extends Command {

	private List<Chat> chats;
	static List<ClientListener> clients;
	public GetAllChats(List<ClientListener> clients, List<Chat> chats) {
		this.chats = chats;
		this.clients = clients;
	}

	public  void execute() {
		for (Chat chat : chats) {

		}
		
		//Acá uso los atributos para hacer la magia.
	}
}
