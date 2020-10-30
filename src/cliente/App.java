package cliente;

import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		Client client = new Client("localhost", 8000);
		Scanner entradaTeclado = new Scanner(System.in);

		client.startLobby();

		while (true) {
			String literal;
			System.out.print("[" + client.getFormatedDate() + "] " + client.getUserName() + ":"); // Se va a escribir
																									// dos veces¿?
			literal = entradaTeclado.nextLine();
			client.analyzeInput(literal);
		}

	}
}
