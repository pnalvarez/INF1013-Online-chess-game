package server;

import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Player {
		
	String nickname;
	Player opponent;
	Thread playerThread;
	Socket playerSocket;	
	boolean gameStatus = false;
	
	
	public Player(Socket socket) {
		playerSocket = socket;
	}
	
	public Socket getsocket(){
		return playerSocket;
	}
	public String getNickname(){
		return nickname;
	}
	public Thread getPlayerThread(){
		return playerThread;
	}
	public Player getOpponent(){
		return opponent;
	}
	
	public boolean getGameStatus(){
		return gameStatus;
	}
	
	public void setNickname(String nick){
		nickname = nick;
	}
	public void setPlayerThread(Thread thread){
		playerThread = thread;
	}
	public void setOpponent(Player player){
		opponent = player;
	}
	
	public void setGameStatus(Boolean status){
		gameStatus = status;
	}
	
	
}
