
public class App {
	public static void main(String[] args) {
		Lobby lobby = new Lobby("localhost",10000);
		//posibilidad de crear un lobby.starMenu();
		
		
		lobby.startLobby(); //Le va a dar la bienvenida y le va a pedir su nombre de usuario.
		//y le explica como volver al lobby luego de elegir el/los chats
		lobby.showChats(); //Le muestro los chats para seleccionar alguno.
		lobby.createOrSelectChats(); //Este le pide que elija uno o cree y 
		//le explica con qu� formato escribir en la consola
		
		
	}
}




//Hace esto.
//Chats:
// - Nombre chat - Usuarios que lo conforman
// - Nombre chat - Usuarios que lo conforman
// - Nombre chat - Usuarios que lo conforman
// - Nombre chat - Usuarios que lo conforman
// - Nombre chat - Usuarios que lo conforman
// - Nombre chat - Usuarios que lo conforman

//Qu� desea hacer?
//c --> create chat --> Qu� nombre quiere ponerle al chat? --> Chat creado Refresco

//a --> unirme a chats. --> a qu� chats te quer�s unir? (Escribo la lista de chats)
//Escrb� los chats con el formato {chat 1} | {chat 2} | {chat n} (m�ximo 3 chats)
//s --> salir.

//VENTANA DE UNIDO A CHATS.
//Conversaci�n
//Para escribir en x 