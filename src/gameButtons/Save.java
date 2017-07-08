package gameButtons;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;

import board.BoardMatrix;


public class Save extends JButton{
	public JButton save;

	public Save(String string){
		save = new JButton(string);
	}
	public void saveGame(BoardMatrix boardMatrix, char turn){
		File savedGame = new File("Saved_Game.txt");
		if(savedGame.exists()){
			savedGame.delete();
		}
		try {
			savedGame.createNewFile();
			FileWriter writer = new FileWriter(savedGame.getAbsoluteFile());
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			bufferedWriter.write(turn);
			bufferedWriter.write(" ");
			for( int i = 0; i < 8; i++ ){
				for( int j = 0; j < 8; j++){
					if(boardMatrix.getPiece(i,j) != null){
						bufferedWriter.write( String.valueOf(boardMatrix.getPiece(i, j).getType()));
						if(boardMatrix.getPiece(i,j).hasMoved){
							bufferedWriter.write(" ");
							bufferedWriter.write("42");
						}else{
							bufferedWriter.write(" ");
							bufferedWriter.write("1337");
						}
					}else{
						bufferedWriter.write(String.valueOf(12));
						bufferedWriter.write(" ");
						bufferedWriter.write("55");
					}
					
					bufferedWriter.write(" ");
				}
			}
			bufferedWriter.close();
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
