package servidor;

import java.util.*;
import java.io.*;
import java.nio.charset.Charset;

public class Chat {
	private List<String> users;
	private String chatName;
	private List<String> history;

	public Chat(String chatName) {
		this.chatName = chatName;
		this.users = new LinkedList<>();
		this.history = new LinkedList<String>();
	}

	public void addUser(String userName) {
		this.users.add(userName);
	}

	public String getName() {
		return this.chatName;
	}

	public List<String> getUsers() {
		return this.users;
	}

	public void addToHistory(String comments) {
		this.history.add(comments);
	}

	public byte[] printChat() throws IOException {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);		
		Writer writer = new OutputStreamWriter(baos, Charset.forName("UTF-8"));
		
		for (String comment : this.history) {
			try {
				writer.write(comment);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		writer.close();
		out.close();
		return baos.toByteArray();
		

	}
}
