package gamePieces;

import java.io.File;
import java.util.Stack;
import board.Coordinate;

public class Bispo extends Peça
{
	
	public Bispo ( char cor )
	{
		super(cor);
		if (cor == 'b'){
			super.setSprite( new File("images/b_bispo.gif") );
			super.setType(3);
		}
		else{
			super.setSprite(new File("images/p_bispo.gif") );
			super.setType(9);
		}
	}
	
	public Bispo ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[2];
		
		v[0] = mDiagonalP(c);
		v[1] = mDiagonalN(c);
		
		return v;
	}
}


