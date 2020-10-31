package servidor;

import entities.DataTransferDto;
import entities.Message;

import java.io.IOException;
import java.util.List;

public class SendPrivate extends Command {

	private final List<Chat> chats;
	private final String userName;
	private final List<ClientListener> clients;
	private final ClientListener client;
	private final Message message;

	public SendPrivate(final List<ClientListener> clients,
					   final List<Chat> chats,
					   final String userName,
					   final ClientListener client,
					   final Message message) {
		this.chats = chats;
		this.clients = clients;
		this.userName = userName;
		this.client = client;
		this.message = message;
	}

	public  void execute() throws IOException {
		String clienteHacia = message.getChat();
		Message msg;
		ClientListener cliente = clients.stream()
				.filter(x -> x.getName().equals(clienteHacia))
				.findFirst().orElse(null);

		if(cliente != null){
			msg = new Message(client.getName(), message.getBodyMsg(), clienteHacia);
		} else{
			// en el caso de no poder entregar el mensaje le notifico al wachin
			msg = new Message(client.getName(), "No pudo se entregado tu mensaje a: " + clienteHacia, client.getName());
			cliente = client;
		}
		DataTransferDto dto = new DataTransferDto(msg);
		String json = gsonHelper.toJson(dto);
		cliente.getOutput().writeObject(json);
	}
}
