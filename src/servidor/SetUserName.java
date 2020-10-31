package servidor;

import entities.Message;

import java.io.IOException;
import java.util.List;

public class SetUserName extends Command{

    private final ClientListener client;
    private final Message message;

    public SetUserName(List<ClientListener> clients,
                List<Chat> chats,
                List<Chat> roomsServer,
                ClientListener client,
                Message message) {
        this.client = client;
        this.message = message;
    }

    public void execute() throws IOException {
        String clientNick = message.getNick();
        client.setNickname(clientNick);
    }
}
