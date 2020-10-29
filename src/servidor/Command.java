package servidor;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import com.google.gson.*;

public abstract class Command {
	//Todas las salas con sus respectivos clientes (sockets de cada cliente para poder hablarles).

	public abstract void execute();
}
