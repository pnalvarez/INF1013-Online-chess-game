package client;

import java.net.*;


import gameUserInterface.NicknameActionListener;
import gameUserInterface.OpponentListActionListener;

import java.io.*;

public class Client  {
	
	IOThread inputThread;
	IOThread outputThread;
	UserInterfaceManager ui = UserInterfaceManager.getGG();
	
	public Client(String nickname) throws IOException {
		Socket clientSocket = new Socket(InetAddress.getLocalHost().getHostAddress(), 12345);

		 inputThread = new IOThread(clientSocket,nickname,true);
		 outputThread = new IOThread(clientSocket, nickname,false);

		addJList();
		manageThreads();
        
	}
	private  void addJList(){
		
		OpponentListActionListener opponentActionListener = new OpponentListActionListener();
		inputThread.addObserver(opponentActionListener);
	}
	
	private void manageThreads(){
		
		Thread threadInputThread = new Thread(inputThread);
		Thread threadOutputThread = new Thread(outputThread);
		threadInputThread.start();
		threadOutputThread.start();
	}

	public static void exit() {

		System.exit(0);
	}
	public static void initializeGame(){
		Game.getGame();		
		Game.mainActionListener = new NicknameActionListener();
	}
	public static void main(String[] args) {

		initializeGame();
	}

}