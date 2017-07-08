package gamePieces;

import java.io.File;
import java.util.Stack;

import board.Coordinate;

public class Peão extends Peça
{

	public Peão ( char cor )
	{
		super(cor);
		if (cor == 'b'){
			super.setSprite( new File("images/b_peao.gif") );
			super.setType(0);
		}
		else{
			super.setSprite(new File("images/p_peao.gif") );
			super.setType(6);
		}
	}
	
	public Peão ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[1];
		
		v[0] = new Stack<Coordinate>();
		
		if( this.getColor() == 'p')
		{
			v[0].push( new Coordinate(c) );
			if(Coordinate.bounded(c.getY()+1))
				v[0].push( new Coordinate(c).translate(0, 1) );
			
			if( !this.hasMoved && Coordinate.bounded(c.getY()+2) )
				v[0].push( new Coordinate(c).translate(0, 2) );
		}
		else
		{
			v[0].push( new Coordinate(c) );
			if(Coordinate.bounded(c.getY()-1) )
				v[0].push( new Coordinate(c).translate(0, -1) );
			
			if( !this.hasMoved && Coordinate.bounded(c.getY()-2))
				v[0].push( new Coordinate(c).translate(0, -2) );
		}
		
		return v;
	}
}
