package server;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server implements Observer{
	
	static HashMap<String, Player> playersHashMap = new HashMap<String, Player>();
	ArrayList<Thread> threadsList = new ArrayList<Thread>();
	ServerSocket serverSocket;
	ClientConnectionManager clientConnectionManager;

	public Server() {
		try {
			serverSocket = new ServerSocket(12345);
		} catch (IOException e) {
			e.printStackTrace();
		}
		clientConnectionManager = new ClientConnectionManager(serverSocket);
		clientConnectionManager.addObserver(this);

		Thread clientConnectionManagerThread = new Thread(clientConnectionManager);
		clientConnectionManagerThread.start();



		while(clientConnectionManagerThread.isAlive()){}

		try {
			serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public void update(Observable e, Object player) {

		ClientThreadObservable clientThreadObservable = new ClientThreadObservable((Player)player, playersHashMap);		
		clientThreadObservable.addObserver(this);
		Thread guestThread = new Thread(clientThreadObservable);
		guestThread.start();
		((Player) player).setPlayerThread(guestThread);

	}

	public static void main(String[] args){
		new Server();
		
	}

}	



