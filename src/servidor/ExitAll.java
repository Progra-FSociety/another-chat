package servidor;

import java.util.Iterator;
import java.util.List;

public class ExitAll extends Command {

	private List<Chat> chats;
	static List<ClientListener> clients;
	public ExitAll(List<ClientListener> clients, List<Chat> chats) {
		this.chats = chats;
		this.clients = clients;
	}

	public  void execute() {
		for (Chat chat : chats) {
			
		}
	}
}
