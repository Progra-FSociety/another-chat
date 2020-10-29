package servidor;
import java.util.List;

public class Chat {
	
	List<String> users;
	String chatName;
	
	public Chat(String chatName) {
		this.chatName = chatName;
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
