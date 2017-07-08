package client;

import java.io.IOException;


public class UserInterfaceManager   {
	static Client c;
	static UserInterfaceManager g;
	public static String nick;
//	
	public static UserInterfaceManager getGG(){
		if(g==null){
			g = new UserInterfaceManager();
		}
		return g;
	}
	
	public static Client getClient(){
		if(c==null){
			try {
				c = new Client(nick);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return c;

	}
	
	
	
}