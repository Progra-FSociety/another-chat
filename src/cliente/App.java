package cliente;

import java.util.Scanner;

public class App {
	private static final String EMPTY_STRING = "";
	public static void main(String[] args) {
		Client client = new Client("localhost", 8000);
		Scanner input = new Scanner(System.in);

		client.startLobby();
		String literal = EMPTY_STRING;
		while (!literal.toUpperCase().contains(client.getExitAllCommand())) {
			
			System.out.print("[" + client.getFormatedDate() + "] " + client.getUserName() + ": "); // Se va a escribir
																									// dos veces¿?
			literal = input.nextLine();
			client.analyzeInput(literal);
		}
		input.close();
	}
}
