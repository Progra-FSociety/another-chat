package cliente;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Message {
	private int id;
	
	private String nick;
	private String fechaEnvio;
	private String bodymsg;
	private SimpleDateFormat formatEnvio = new SimpleDateFormat();
	
	public Message(String nick, String bodymsg) {
		this.nick = nick;
		this.fechaEnvio = formatEnvio.format(new Date());
		this.bodymsg = bodymsg;
	}
	
	public String getMessage() {
		return "[" + this.fechaEnvio + "] " + this.nick + ": " + this.bodymsg;
	}

	//[sala 1] 
	//[/{sala 1} message]
	
}
