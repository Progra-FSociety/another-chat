package servidor;

import java.util.Iterator;
import java.util.List;

public class SendChat extends Command {

	private List<Chat> chats;
	static List<ClientListener> clients;
	public SendChat(List<ClientListener> clients, List<Chat> chats) {
		this.chats = chats;
		this.clients = clients;
	}

	public  void execute() {
		for (Chat chat : chats) {
			//if(chat.getUsers())
		}
		
		//Ac� uso los atributos para hacer la magia.
	}
}
