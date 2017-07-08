package server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;
import java.util.*;

public  class ClientThreadObservable extends Observable implements Runnable {
	
	Player player;
	HashMap<String, Player> playerHashMap;
	Scanner scanner;
	String nickname;
	ArrayList<String> nicknameList = new ArrayList<>();
	Scanner outputSocket;
	Scanner input;
	Socket c;
	String playerNick;
	
	private void flush(ArrayList<String> str){
		str.clear();
	}
	
	public ClientThreadObservable(Player player, HashMap<String, Player> playerHashMap){
		this.player = player;
		this.playerHashMap = playerHashMap;
		try {
			scanner = new Scanner(this.player.getsocket().getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.nickname = scanner.nextLine();
		this.player.setNickname(this.nickname);
		this.playerHashMap.put(this.nickname, this.player);
		
		
	}
	

	
	public boolean checkNickname(String nickname){
		if(player.getNickname().compareTo(nickname)!=0){
			if(playerHashMap.get(nickname)!=null && !playerHashMap.get(nickname).getGameStatus()){
				return true;
			}
		}
		return false;
	}
	
	
	public void send(String msg){
		for (String key : playerHashMap.keySet()) {
			
            Player otherPlayer = playerHashMap.get(key);
            try {
            	
				PrintStream printStream = new PrintStream(otherPlayer.getsocket().getOutputStream());
				if(msg!=null && player.getGameStatus()==false){
					if(otherPlayer.equals(playerHashMap.get(nickname))==false){
						printStream.println(msg);
					}
				}
				else if(msg==null && otherPlayer.getGameStatus()==false){
					flush(nicknameList);
					
					for (String key2 : playerHashMap.keySet()) {
						
			            Player otherplayer = playerHashMap.get(key2);
			            	if(otherplayer.getGameStatus()==false){
			            		System.out.println(otherplayer.getNickname());
			            		nicknameList.add(otherplayer.getNickname());
			            	}
					}
					
				printStream.println("clientReceiveListFlag");
				for(int i=0; i<nicknameList.size();i++){
					printStream.println(nicknameList.get(i));
				}
				printStream.println("clientEndListFlag");
				}
				else{
					if(msg==null){						
					}
					
					else if(msg.compareTo("accepted")==0){
						if(otherPlayer.equals(player.getOpponent())){
							printStream.println(msg + "blacks");
						}
						else if(otherPlayer.equals(playerHashMap.get(nickname))==true){
							printStream.println(msg + "whites");
						}
					}
					else{
					if(otherPlayer.equals(player.getOpponent())){
						printStream.println(msg);
					}
					}
				}
				
            	
			} catch (IOException e) {
				e.printStackTrace();
			}
        
		}
	}
	
	public void acceptPlayer(){
		
		playerHashMap.get(this.nickname).setGameStatus(true);
		playerHashMap.get(this.nickname).setOpponent(playerHashMap.get(playerNick));
		playerHashMap.get(playerNick).setGameStatus(true);
		playerHashMap.get(playerNick).setOpponent(playerHashMap.get(this.nickname));
		
		send("accepted");
		send(null);
	}
	
	private void broadcast(String line){
		
		String broadcast = nickname +"  -  "+ line;
		System.out.println(line);
		send(broadcast);
	}
	
	@Override
	public void run() {
		send(null);
		while(scanner.hasNext() && player.getsocket().isConnected()){
			String line = scanner.nextLine();
			if(line.compareTo("###")!=0){
				if(line.length()>=7){
				if(line.substring(0, 7).compareTo("select:")==0){
					
					this.playerNick = line.substring(7);
					
					if(checkNickname(playerNick))
							acceptPlayer();					
					
				}
				else if(line.compareTo("update")==0){
					send(line);
					send(scanner.nextLine());
					
				}
				else
					broadcast(line);
				
				}
			}
				
			else{
				playerHashMap.remove(nickname);
				break;
			}
		}
		playerHashMap.remove(nickname);
	}

}
