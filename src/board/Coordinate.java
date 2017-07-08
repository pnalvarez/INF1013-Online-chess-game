package board;

public class Coordinate 
{
	private int x, y;
	
	public Coordinate (int x, int y)
	{
		if( bounded(x, y) )
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public Coordinate (Coordinate c)
	{
		if( bounded(c) )
		{
			this.x = c.getX();
			this.y = c.getY();
		}
	}
	
		
	public int getX()
	{
		return x;
	}

	public int getY() 
	{
		return y;
	}
	
	public void setX(int x) 
	{
		if( bounded(x) )
			this.x = x;
	}
	
	public void setY(int y) 
	{
		if( bounded(y) )
			this.y = y;
	}
	
	public void set(int x, int y)
	{
		if( bounded(x, y) )
		{
			this.x = x;
			this.y = y;
		}
	}
	
	public void set(Coordinate c)
	{
		if( bounded(c) )
		{
			this.x = c.getX();
			this.y = c.getY();
		}
	}
	
	public Coordinate translate(int dx, int dy)
	{
		if( bounded(x, y) )
		{
			this.x = this.x + dx;
			this.y = this.y + dy;
			return this;
		}
		return this;
	}
	
	public static boolean equals(Coordinate a, Coordinate b)
	{
		if((a!=null) && (b!=null))
			return ( a.getX() == b.getX()) && (a.getY() == b.getY() );
		
		return false;
	}
	
	public static boolean bounded(Coordinate c)
	{
		if ( (c.getX() >= 0) && (c.getX() <= 7) && (c.getY() >= 0) && (c.getY() <= 7) )
			return true;
		
		return false;
	}
	
	public static boolean bounded(int x)
	{
		if ( (x >= 0) && (x <= 7) )
			return true;
		
		return false;
	}
	
	public static boolean bounded(int x, int y)
	{
		if ( (x >= 0) && (x <= 7) && (y >= 0) && (y <= 7) )
			return true;
		
		return false;
	}
}
