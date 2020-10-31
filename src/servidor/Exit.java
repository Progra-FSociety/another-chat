package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferDto;
import entities.Message;

public class Exit extends Command {

	private List<Chat> chats;
	private String userName;
	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;
	public Exit(List<ClientListener> clients, List<Chat> chats, String userName, ClientListener client,
			Message message) {
		this.chats = chats;
		this.clients = clients;
		this.userName = userName;
		this.client = client;
		this.message = message;
	}

	public  void execute() throws IOException {
		Chat chat = client.getChats().stream()
	            .filter(x -> x.chatName == message.getChat())
	            .findFirst()
	            .get();		
		
		List<ClientListener> clnt = clients.stream()
	            .filter(x -> x.getChats().contains(chat))
	            .collect(Collectors.toList());
		
		Message msg = new Message(client.getName(),"He salido del chat: " + chat.getName(),chat.getName());
		DataTransferDto dto = new DataTransferDto(msg);
		String json = gsonHelper.toJson(dto);
		for (ClientListener item : clnt) 
			item.getOutput().writeObject(json);
		
		client.getChats().remove(chat); //Siempre lo borra porque no va a llegar este comando con un chat en el que no está asociado
		//el usuario.
	}
}
