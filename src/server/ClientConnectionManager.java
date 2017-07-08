package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;

public class ClientConnectionManager extends Observable implements Runnable{
	
	ServerSocket serverSocket;
	Socket clientSocket;
	
	public ClientConnectionManager(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}
	
	
	@Override
	public void run() {
		while(true){
		try {
			this.clientSocket = serverSocket.accept();			
			Player player = new Player(clientSocket);
			setChanged();
			notifyObservers(player);
			System.out.println("New player connected:" + clientSocket.getInetAddress().getHostAddress());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}

}
