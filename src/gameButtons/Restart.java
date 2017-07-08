package gameButtons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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

public class Restart extends JButton{
	public JButton restart;
	public Restart(String string){
		restart = new JButton("Restart");
	}
	public void restartGame(BoardMatrix boardMatrix, JPanel panel){
		File fresh_Game = new File("Fresh_Game.txt");	
		String jogoNovo = "b 7 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 1 1337 8 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 2 1337 9 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 3 1337 10 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 4 1337 11 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 5 1337 9 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 3 1337 8 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 2 1337 7 1337 6 1337 12 55 12 55 12 55 12 55 0 1337 1 1337";
		if(!fresh_Game.exists()){
			try {
				fresh_Game.createNewFile();
				FileWriter writer = new FileWriter(fresh_Game.getAbsoluteFile());
				BufferedWriter bufferedWriter = new BufferedWriter(writer);
				bufferedWriter.write(jogoNovo);
				bufferedWriter.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			;
		}
		try {
				Scanner scanner = new Scanner(fresh_Game);
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
}
