package entities;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private int id;
	
	private String nick;
	private String sendDate;
	private String bodyMsg;
	private String chatName;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	/*
	 * 
	 * 
	 */
	public Message(String nick, String bodymsg, String chatName) {
		this.nick = nick;
		this.sendDate = dateFormat.format(new Date());
		this.bodyMsg = bodymsg;
		this.chatName = chatName;
	}

	public Message(String nick, String bodymsg) {
		this.nick = nick;
		this.sendDate = dateFormat.format(new Date());
		this.bodyMsg = bodymsg;
	}
	
	public String getMessageToChat() {
		return "[" + this.sendDate + "] [" + this.chatName + "] " + this.nick + ": " + this.bodyMsg;
	}
	
	public String getMessage() {
		return "[" + this.sendDate + "] " + this.nick + ": " + this.bodyMsg;
	}
	
	public String getChat() {
		return this.chatName;
	}
	
	public String getNick() {
		return nick;
	}
	
	public String getBodyMsg() {
		return bodyMsg;
	}
}
