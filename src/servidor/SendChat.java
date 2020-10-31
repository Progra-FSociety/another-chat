package servidor;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import entities.DataTransferDto;
import entities.Message;

public class SendChat extends Command {

	private List<Chat> chats;
	private String userName;
	private List<ClientListener> clients;
	private ClientListener client;
	private Message message;
	public SendChat(List<ClientListener> clients, List<Chat> chats, String userName, ClientListener client,
			Message message) {
		this.chats = chats;
		this.clients = clients;
		this.userName = userName;
		this.client = client;
		this.message = message;
	}

	public  void execute() throws IOException {
		
		//Busco el chat al cual quiero mandar el msj
		
		Chat chat = client.getChats().stream()
	            .filter(x -> x.chatName == message.getChat())
	            .findFirst()
	            .get();
		
		//Busco los clientes adheridos a ese chat
		
		List<ClientListener> clnt = clients.stream()
	            .filter(x -> x.getChats().contains(chat))
	            .collect(Collectors.toList());
		
		//Envio el mensaje?
		
		Message msg = new Message(client.getName(),this.message.getMessage(),chat.getName());
		
		//Esto parece que lo envia pero la verdad no tengo idea
		
		DataTransferDto dto = new DataTransferDto(msg);
		String json = gsonHelper.toJson(dto);
		for (ClientListener item : clnt) 
			item.getOutput().writeObject(json);
		
		//Acá uso los atributos para hacer la magia.
	}
}
