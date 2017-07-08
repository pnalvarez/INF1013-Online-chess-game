package exceptions;

@SuppressWarnings("serial")
public class WrongTurnException extends Exception
{
	public WrongTurnException(String message) 
	{
		super(message);
	}  
}
