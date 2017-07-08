package gameButtons;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JPanel;

import board.BoardMatrix;
import board.Coordinate;
import gamePieces.Bispo;
import gamePieces.Cavalo;
import gamePieces.Dama;
import gamePieces.Peão;
import gamePieces.Rei;
import gamePieces.Torre;

public class Load extends JButton{
	public JButton load;
	public Load(String string){
		load = new JButton(string);
	}
	
	public void loadGame(BoardMatrix boardMatrix, JPanel panel){
		try {
			Scanner scanner = new Scanner(new File("Saved_Game.txt"));
			scanner.next(); //pula o char
			for( int i = 0; i < 8; i++ ){
				for( int j = 0; j < 8; j++){
					Coordinate c = new Coordinate(i, j);
					boardMatrix.setPiece(c, null);
					int type = scanner.nextInt();
					switch(type){
						case 0:
							boardMatrix.setPiece(c, new Peão('b'));
								break;
						case 1:
							boardMatrix.setPiece(c, new Torre('b'));
								break;
						case 2:
							boardMatrix.setPiece(c, new Cavalo('b'));
								break;
						case 3:
							boardMatrix.setPiece(c, new Bispo('b'));
								break;
						case 4:
							boardMatrix.setPiece(c, new Dama('b'));
								break;
						case 5:
							boardMatrix.setPiece(c, new Rei('b'));
								break;
						case 6:
							boardMatrix.setPiece(c, new Peão('p'));
								break;
						case 7:
							boardMatrix.setPiece(c, new Torre('p'));
								break;
						case 8:
							boardMatrix.setPiece(c, new Cavalo('p'));
								break;
						case 9:
							boardMatrix.setPiece(c, new Bispo('p'));
								break;
						case 10:
							boardMatrix.setPiece(c, new Dama('p'));
								break;
						case 11:
							boardMatrix.setPiece(c, new Rei('p'));
								break;
						default:
								break;
								
					}
					int moved = scanner.nextInt();
					if( moved == 42){
						boardMatrix.getPiece(i, j).hasMoved = true;
					}else if( moved == 1337 ){
						boardMatrix.getPiece(i, j).hasMoved = false;
					}							
				panel.repaint();	 
				}
			}
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.out.println("NENHUM JOGO SALVO");
			System.exit(1);
			e1.printStackTrace();
		}
	}
	public char getTurn(){
		char turn = 0;
		try {
			Scanner scanner = new Scanner(new File("Saved_Game.txt"));
			turn = scanner.next().charAt(0);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return turn;
	}

}
