package gamePieces;

import java.io.File;
import java.util.Stack;

import board.Coordinate;

public class Cavalo extends Peça
{
	public Cavalo ( char cor )
	{
		super(cor);
		if (cor == 'b'){
			super.setSprite( new File("images/b_cavalo.gif") );
			super.setType(2);
		}
		else{
			super.setSprite(new File("images/p_cavalo.gif") );
			super.setType(8);
		}
	}
	
	public Cavalo ( char cor, File sprite )
	{
		super(cor, sprite);
	}

	
	@SuppressWarnings("unchecked")
	public Stack<Coordinate>[] move(Coordinate c)
	{
		Stack<Coordinate>[] v = new Stack[4];		
			
		v[0] = new Stack<Coordinate>();
		if( Coordinate.bounded(c.getY() + 2) )
		{
			if( Coordinate.bounded(c.getX() - 1) ) 
				v[0].push( new Coordinate(c).translate(-1, 2) );
			
			v[0].push(c);
			
			if( Coordinate.bounded(c.getX() + 1) )
				v[0].push( new Coordinate(c).translate(1, 2) );
		}	
		
		v[1] = new Stack<Coordinate>();		
		if( Coordinate.bounded(c.getY() - 2) )
		{
			if( Coordinate.bounded(c.getX() - 1) ) 
				v[1].push( new Coordinate(c).translate(-1, -2) );
			
			v[1].push(c);
			
			if( Coordinate.bounded(c.getX() + 1) )
				v[1].push( new Coordinate(c).translate(1, -2) );
		}	
		
		v[2] = new Stack<Coordinate>(); 
		if( Coordinate.bounded(c.getX() + 2) )
		{
			if( Coordinate.bounded(c.getY() - 1) ) 
				v[2].push( new Coordinate(c).translate(2, -1) );
			
			v[2].push(c);
			
			if( Coordinate.bounded(c.getY() + 1) )
				v[2].push( new Coordinate(c).translate(2, 1) );
		}	
		
		v[3] = new Stack<Coordinate>(); 
		if( Coordinate.bounded(c.getX() - 2) )
		{
			if( Coordinate.bounded(c.getY() - 1) ) 
				v[3].push( new Coordinate(c).translate(-2, -1) );
			
			v[3].push(c);
			
			if(Coordinate.bounded(c.getY() + 1) )
				v[3].push( new Coordinate(c).translate(-2, 1) );
		}	
		
		return v;
	}
}
