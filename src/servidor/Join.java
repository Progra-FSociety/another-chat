package servidor;

import java.util.Iterator;
import java.util.List;

public class Join extends Command {

	private List<Chat> chats;
	static List<ClientListener> clients;
	public Join(List<ClientListener> clients, List<Chat> chats) {
		this.chats = chats;
		this.clients = clients;
	}

	public  void execute() {
		for (Chat chat : chats) {
			//if(chat.getUsers())
		}
		
		//Acá uso los atributos para hacer la magia.
	}
}
