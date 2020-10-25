import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class User {
	private String ip;
	private int port;
	private Socket userConection;
	DataInputStream input;

	public User (String ip, int port) {
			try {
				this.userConection = new Socket(ip,port);
				this.input = new DataInputStream(userConection.getInputStream());
			}
			catch(IOException e) {
				e.printStackTrace();
			}
	}
	public void getMessages() {
		boolean connected = true;
		
		while(connected) {
			try {
				
				this.input.readUTF();
			} catch (Exception e) {
				// TODO: handle exception
			}y
		}
	}
}
