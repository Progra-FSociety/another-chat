package cliente;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private int id;
	
	private String nick;
	private String sendDate;
	private String bodyMsg;
	private SimpleDateFormat dateFormat = new SimpleDateFormat();
	
	/*
	 * 
	 * 
	 */
	public Message(String nick, String bodymsg) {
		this.nick = nick;
		this.sendDate = dateFormat.format(new Date());
		this.bodyMsg = bodymsg;
	}
	
	public String getMessage() {
		return "[" + this.sendDate + "] " + this.nick + ": " + this.bodyMsg;
	}	
}
