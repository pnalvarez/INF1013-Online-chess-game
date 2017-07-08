package board;

import java.util.Observable;

public class ClickCount extends Observable
{
	private int count = 0;
	private Board board;
	
	public ClickCount()
	{
		
	}
	
	public ClickCount(Board board)
	{
		this.board = board;
	}
	
	public void increment()
	{
		count++;
        setChanged();
        notifyObservers(board);
	}
	
	public void decrement()
	{
		count--;
        setChanged();
        notifyObservers(board);
	}
	
	public boolean isEven()
	{
		if(count % 2 == 0)
			return true;
		
		return false;
	}

}
