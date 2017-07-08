package client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class IOThread extends Observable implements Runnable{

	static Socket clientSocket;	
	String message;	
	boolean flag=false;
	String gameString;	
	static ArrayList<String> stringArrayList = new ArrayList<String>();
	boolean isInput;
	static PrintStream printStream;
	String nicknameString;	
	Scanner serverScan;
	
	public IOThread(Socket socket, String nickname, boolean type){
		
		clientSocket = socket;
		nicknameString = nickname;
		isInput = type;
		
		try {
			serverScan = new Scanner(clientSocket.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			printStream = new PrintStream(clientSocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	public static ArrayList<String> getStringArrayList(){
		return stringArrayList;		
	}
	
	public void loadFile(){
		File savedGame = new File("Saved_Game.txt");
		if(savedGame.exists()){
			savedGame.delete();
		}
		try {
			savedGame.createNewFile();
			FileWriter writer = new FileWriter(savedGame.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(gameString);
			bufferedWriter.close();
		
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void sendMessage(String msg){
		try {
			PrintStream s = new PrintStream(clientSocket.getOutputStream());
			s.println(msg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
public static void sendGameFile(){
		
		try {
			Scanner scanner = new Scanner(new File("Saved_Game.txt"));
			PrintStream s = new PrintStream(clientSocket.getOutputStream());
			s.println("update");
			while(scanner.hasNextLine()){
				s.print(scanner.nextLine());
			}
			s.println();
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


   
private void interpretGame(){
	   
	   while(serverScan.hasNextLine()){
			
			message = serverScan.nextLine();
			
			switch(message){
			
			case "update":
				
				gameString = serverScan.nextLine();
				loadFile();
				Game.getChess('x').carregaTab();
				break;
				
			case "clientReceiveListFlag": 
				
				stringArrayList.clear();
				flag = true;
				break;
			
			case "clientEndListFlag":
				
				flag = false;
				setChanged();
				notifyObservers("l");
				break;
			
			case "acceptedwhites":
				
				setChanged();
				notifyObservers("whites");
				break;
				
			case "acceptedblacks":
				
				setChanged();
				notifyObservers("blacks");
				break;
			
		
			case "END":
				
				Client.exit();
				break;
			
			default: 
				if(flag){
					stringArrayList.add(message);
				}
				else{
					System.out.println(message);
				}
				break;
			}
			
	}			
	
			
	serverScan.close();
	   
	   
   }

    
	
	public void run() {
		
		if(isInput){
//		
			interpretGame();

		}
		else{
			Scanner scannerInput = new Scanner(System.in);
			output(scannerInput, nicknameString);
		}
	
	}
	
	private void output(Scanner input, String nick){
		
		message = nick;
		while(true){
			printStream.println(message);
			message = input.nextLine();
			if(message.compareTo("###")==0){
				break;
			}
	}
	input.close();
	}
		
	

}
