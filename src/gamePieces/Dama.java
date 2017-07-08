package gamePieces;

import java.io.File;
import java.util.Stack;

import board.Coordinate;

public class Dama extends Peça
{
	
	public Dama ( char cor )
	{
		super(cor);
		if (cor == 'b'){
			super.setSprite( new File("images/b_dama.gif") );
			super.setType(4);
		}
		else{
			super.setSprite(new File("images/p_dama.gif") );
			super.setType(10);
		}
	}
	
	public Dama ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[4];
		
		v[0] = mDiagonalP(c);
		v[1] = mDiagonalN(c);
		v[2] = mHorizontal(c);
		v[3] = mVertical(c);
		
		return v;
	}
}
