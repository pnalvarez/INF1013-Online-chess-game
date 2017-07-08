package board;

import java.util.Observable;
import java.util.Observer;

public class ClickObserver implements Observer 
{
	 public void update(Observable obj, Object board) 
	 {
		((Board)board).mainPanel.repaint();
	 }
}
