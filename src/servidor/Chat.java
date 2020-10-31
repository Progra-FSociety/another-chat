package servidor;
import java.util.*;

public class Chat {
	private List<String> users;
	private String chatName;
	
	public Chat(String chatName) {
		this.chatName = chatName;
		this.users = new LinkedList<>();
	}
	
	public void addUser(String userName) {
		users.add(userName);
	}
	
	public String getName() {
		return this.chatName;
	}

	public List<String> getUsers() {
		return this.users;
	}
	
	public String toString() {
		return "";
	}
}
