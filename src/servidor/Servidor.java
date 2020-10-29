package servidor;

import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;

import cliente.Read;
import cliente.Write;

import java.text.*;

public class Servidor {
	private ServerSocket server;
	private final int PORT = 8000;
	private static Vector<Socket> clientSockets;
	
	public Servidor() {
		try {
			server = new ServerSocket(this.PORT);
			this.clientSockets = new Vector<Socket>();
			
			SimpleDateFormat dateformat2 = new SimpleDateFormat();
			System.out.println("Se inicio el servidor a las " + dateformat2.format(new Date()));
			System.out.println();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
