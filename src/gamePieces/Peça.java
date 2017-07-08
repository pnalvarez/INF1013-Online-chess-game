package gamePieces;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Stack;

import javax.imageio.ImageIO;

import board.Coordinate;


public abstract class Peça
{

	private char color; // b e p
	private int type;
	
	private Image sprite;
	
	public boolean hasMoved = false;
	
	public abstract Stack<Coordinate>[] move(Coordinate c);
	
	
	public Peça(char color)
	{
		this.color = color;
	}
	
	public Peça(char color, File sprite)
	{
		this.color = color;
		try
		{
			this.sprite = ImageIO.read(sprite);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}

	}
	
	public Image getSprite()
	{
		return this.sprite;
	}
	
	public void setSprite(File sprite)
	{
		try
		{
			this.sprite = ImageIO.read(sprite);
		}
		catch (IOException e) 
		{
			System.out.println(e.getMessage());
			System.exit(1);
		}
	}

	public char getColor() 
	{
		return color;
	}
		
	public static Stack<Coordinate> mDiagonalP (Coordinate c)
	{
		int soma = c.getX() + c.getY();
			
		Stack<Coordinate> s = new Stack<Coordinate>();
		
		if ( soma > 7 )
		{
	
			for( int x = 7 ,  i = Math.abs(soma - 14) ; i >= 0 ; i-- , x--)
			{
				s.push( new Coordinate (x, soma - x) );
			}
			
		}
		
		else
		{
			for( int y = 0 ,  i = soma; i >= 0 ; i-- , y++)
			{
				s.push( new Coordinate (soma - y, y) );
			}
		}
		
		return s;		
	}
	
	public static Stack<Coordinate> mDiagonalN (Coordinate c)
	{
		Stack<Coordinate> s = new Stack<Coordinate>();
		
		int sub = c.getX() - c.getY();

		if ( sub > 0 )
		{
	
			for( int y = 0 ,  i = Math.abs(sub - 7) ; i >= 0 ; i-- , y++)
			{
				s.push( new Coordinate (sub + y, y) );
			}
			
		}
		
		else
		{
			for( int x = 0 ,  i = sub + 7 ; i >= 0 ; i-- , x++)
			{
				s.push( new Coordinate (x, x - sub) );
			}
			
		}
		
		return s;
	}
	
	
	public static Stack<Coordinate> mVertical (Coordinate c)
	{
		Stack<Coordinate> s = new Stack<Coordinate>();
		
	
		for(int x = c.getX() , y = 0; y <= 7 ; y ++)
		{
			s.push( new Coordinate (x, y) );
		}

		return s;
	}
	
	
	public static Stack<Coordinate> mHorizontal (Coordinate c)
	{
		Stack<Coordinate> s = new Stack<Coordinate>();
		
	
		for(int x = 0 , y = c.getY(); x <= 7 ; x ++)
		{
			s.push( new Coordinate (x, y) );
		}

		return s;
	}


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

}
