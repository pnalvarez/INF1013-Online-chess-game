package gamePieces;

import java.io.File;
import java.util.Stack;

import board.Coordinate;

public class Rei extends Peça
{
	public Rei ( char cor )
	{
		super(cor);
		
		if (cor == 'b'){
			super.setSprite( new File("images/b_rei.gif") );
			super.setType(5);
		}
		else{
			super.setSprite(new File("images/p_rei.gif") );
			super.setType(11);
		}
	}
	
	public Rei ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[4];
		
		v[0] = new Stack<Coordinate>();
		if( Coordinate.bounded(c.getX()+1) )
			v[0].push(new Coordinate(c).translate(1, 0) );
		
		v[0].push(c);
		
		if( Coordinate.bounded(c.getX()-1) )
			v[0].push(new Coordinate(c).translate(-1, 0) );

		
		/****************/
		v[1] = new Stack<Coordinate>();		
		if( Coordinate.bounded(c.getY()+1) )
			v[1].push(new Coordinate(c).translate(0, 1) );
		
		v[1].push(c);
		
		if( Coordinate.bounded(c.getY()-1) )
			v[1].push(new Coordinate(c).translate(0, -1) );
		
		
		/****************/
		v[2] = new Stack<Coordinate>();		
		if( Coordinate.bounded( (c.getX()+1), (c.getY()-1) ))
			v[2].push(new Coordinate(c).translate(1, -1) );
		
		v[2].push(c);
		
		if( Coordinate.bounded( (c.getX()-1), (c.getY()+1) ))
			v[2].push(new Coordinate(c).translate(-1, 1) );
		
		
		/****************/
		v[3] = new Stack<Coordinate>();		
		if( Coordinate.bounded( (c.getX()-1), (c.getY()-1) ))
			v[3].push(new Coordinate(c).translate(-1, -1) );
		
		v[3].push(c);		
		if( Coordinate.bounded( (c.getX()+1), (c.getY()+1)) )
			v[3].push(new Coordinate(c).translate(1, 1) );

		
		return v;
	}
}
