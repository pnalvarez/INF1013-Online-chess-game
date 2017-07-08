package gamePieces;

import java.io.File;
import java.util.Stack;

import board.Coordinate;

public class Torre extends Peça
{

	public Torre ( char cor )
	{
		super(cor);
		
		if (cor == 'b'){
			super.setSprite( new File("images/b_torre.gif") );
			super.setType(1);
		}
		else{
			super.setSprite(new File("images/p_torre.gif") );
			super.setType(7);
		}
	}
	
	public Torre ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[2];
		
		v[0] = mHorizontal(c);
		v[1] = mVertical(c);
		
		return v;
	}
}
