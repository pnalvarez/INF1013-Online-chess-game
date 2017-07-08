package client;

import java.util.Observable;
import java.util.Observer;

import board.Board;
import gameUserInterface.NicknameActionListener;


public class Game extends Observable implements Observer{

	public static NicknameActionListener mainActionListener = null;
	static Board board;
	static Game game;
	
		public static NicknameActionListener getMainActionListener (){
	      return mainActionListener;
	}

	public static Game getGame(){
		if(game==null){
			game = new Game();
		}
		return game;
	}
	
	public static Board getChess(char c){
		if(board==null){
			board = new Board(c);
			board.getFrame().setVisible(true);
		}
		return board;
		
	}

	@Override
	public void update(Observable o, Object arg) {
		
		IOThread.sendGameFile();
		
	}
	
	
}
