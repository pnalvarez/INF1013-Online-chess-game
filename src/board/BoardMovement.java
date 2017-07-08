package board;

import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

import exceptions.IllegalMoveException;
import exceptions.KingThreatException;
import exceptions.WrongTurnException;
import gamePieces.Dama;
import gamePieces.Peão;
import gamePieces.Peça;
import gamePieces.Rei;
import gamePieces.Torre;

public class BoardMovement
{
	
	private Coordinate from, to;
	
	private ArrayList<Coordinate> legalList;
	
	public JFrame frame;
	
	private Coordinate[] towerCastling = new Coordinate[2];
	
	private Coordinate whiteKing, blackKing;
	
	private Peça keep;

	private BoardMatrix boardMatrix;
		
	public BoardMovement(BoardMatrix boardMatrix)
	{
		this.boardMatrix = boardMatrix;
	}
	
	public boolean moveFrom(Coordinate From, int line, char turn)
	{
		this.from = From;
		if ( boardMatrix.isEmpty(from) )
		{
			return true;
		}
		try
		{	
			if( boardMatrix.getPieceColor(from) != turn )
			{
				throw new WrongTurnException("\tN�o � o seu turno!");
			}
		
			movement(from);					
			boardMatrix.highlight(legalList);
		}
		catch (WrongTurnException exception)
		{
			System.out.println(exception.getMessage());
			return true;
		}
		return false;
	}
		
	public boolean moveTo(Coordinate To, int line, char turn)
	{
		this.to = To;
		boolean ret = false;
		try
		{
			if( !isLegal(to) )
			{
				throw new IllegalMoveException(line + " - Movemento Inv�lido!");
			}

			keep = boardMatrix.swap(to, from);
			
			if(check() == turn)
			{
				throw new KingThreatException(line + " - Movemento Inv�lido, Rei Est� em Cheque!");
			}

			if(boardMatrix.getPiece(to) instanceof Rei)
				moveCastling(to);
			
			if(boardMatrix.getPiece(to) instanceof Peão)
			{	
				if( (to.getY() == 0) && (boardMatrix.getPieceColor(to) == 'b') )
				{
					promote(to);
				}
				else if( (to.getY() == 7) && (boardMatrix.getPieceColor(to) == 'p'))
				{
					promote(to);
				}							
			}
			
			if( check() != 'n' ) 
			{
				System.out.printf("CHEQUE");
				if( mate(turn) )
				{
					System.out.printf(" MATE");
					
				}
				System.out.printf("!\n");
			}
		}
		catch (IllegalMoveException exception)
		{
			System.out.println(exception.getMessage());
			ret = true;
		}
		catch(KingThreatException exception)
		{
			System.out.println(exception.getMessage());
			boardMatrix.swap(from, to);
			boardMatrix.setPiece(to, keep);
			ret = true;
		}
		finally
		{
			boardMatrix.unHighlightAll();
			towerCastling[0] = null;
			towerCastling[1] = null;
		}

		return ret;
	}
	
	// Determina as Movementa��es poss�veis da peça na coordenada peça
	private void movement(Coordinate peça)
	{
		if( boardMatrix.isEmpty(peça) )
			return ;
		
		legalList = new ArrayList<Coordinate>();
		
		Stack<Coordinate>[] v = boardMatrix.getPiece(peça).move(peça);
		
		if( (boardMatrix.getPiece(peça)) instanceof Peão )
			pawnCapture(peça);
		
		if( ((boardMatrix.getPiece(peça)) instanceof Rei) && (!boardMatrix.hasMoved(peça)))
			Castling(peça);
		
		for( int i = 0 ; i < v.length ; i++)
		{
			Stack<Coordinate> aux = new Stack<Coordinate>();

			Coordinate current;	
			
			while( !v[i].isEmpty() ) // Empilha em aux as Movementacoes possiveis
			{
				current = v[i].pop();	
				
				if( Coordinate.equals(current, peça) ) // Se encontrou a Peça a ser movida
				{
					while( !v[i].isEmpty() ) //Empilha at� acabar OU at� encontrar outra peça
					{					
						current = v[i].pop();
						
						if( !boardMatrix.isEmpty(current) )
						{
							if( boardMatrix.getPieceColor(current) != boardMatrix.getPieceColor(peça) ) 
								aux.push(current);
							break;
						}						
						aux.push(current);
					}					
					break;
				}
				
				// Se a coordenada c estiver vazia
				if( boardMatrix.isEmpty(current) )
					aux.push(current);
				else
				{
					aux = new Stack<Coordinate>();					
					if(boardMatrix.getPieceColor(current) != boardMatrix.getPieceColor(peça) && ( !(boardMatrix.getPiece(peça) instanceof Peão) )) 
						aux.push(current);
				}
			}
			while( !aux.isEmpty() ) // Copia conteudo de aux para legalList
				legalList.add(aux.pop());
		}
	}
	private void movement(int x, int y)
	{
		movement(new Coordinate(x, y));
	}

	
	private boolean isLegal(Coordinate toMove)
	{
		for(int i = 0; i < legalList.size(); i++)
		{			
			if( Coordinate.equals(legalList.get(i), toMove) )
				return true;
		}
		return false;
	}

	private void pawnCapture(Coordinate peça)
	{
		if( (boardMatrix.getPiece(peça)) instanceof Peão ) // Movemento comida Peão
		{
			if(boardMatrix.getPieceColor(peça) == 'p')
			{
				Coordinate aux = new Coordinate(peça);
				aux.translate(-1, 1);
				if((Coordinate.bounded(aux)) && (!boardMatrix.isEmpty(aux)) && (boardMatrix.getPieceColor(aux) == 'b'))
					legalList.add(new Coordinate(aux));
				
				aux = new Coordinate(peça);
				aux.translate(1, 1);
				if((Coordinate.bounded(aux)) && (!boardMatrix.isEmpty(aux)) && (boardMatrix.getPieceColor(aux) == 'b'))
					legalList.add(new Coordinate(aux));
			}
			else
			{
				Coordinate aux = new Coordinate(peça);
				aux.translate(-1, -1);
				if((Coordinate.bounded(aux)) && (!boardMatrix.isEmpty(aux)) && (boardMatrix.getPieceColor(aux) == 'p'))
					legalList.add(new Coordinate(aux));
				
				aux = new Coordinate(peça);
				aux.translate(1, -1);
				if((Coordinate.bounded(aux)) && (!boardMatrix.isEmpty(aux)) && (boardMatrix.getPieceColor(aux) == 'p'))
					legalList.add(new Coordinate(aux));
			}
		}
	}
	
	private void Castling(Coordinate peça)
	{

		if( boardMatrix.getPieceColor(peça) == 'b')
		{
			if( (!boardMatrix.isEmpty(0, 7)) && (boardMatrix.getPieceColor(0, 7) == 'b') && (boardMatrix.getPiece(0, 7) instanceof Torre) && (!boardMatrix.hasMoved(0, 7))
				&& boardMatrix.isEmpty(1, 7) && boardMatrix.isEmpty(2, 7) && boardMatrix.isEmpty(3, 7))
			{
				legalList.add(new Coordinate(1, 7));
				towerCastling[0] = new Coordinate(0, 7);
			}
			if( (!boardMatrix.isEmpty(7, 7)) && (boardMatrix.getPieceColor(7, 7) == 'b') && (boardMatrix.getPiece(7, 7) instanceof Torre) && (!boardMatrix.hasMoved(7, 7))
					&& boardMatrix.isEmpty(6, 7) && boardMatrix.isEmpty(5, 7))
			{
				legalList.add(new Coordinate(6, 7));
				towerCastling[1] = new Coordinate(7, 7);
			}			
		}
		else
		{
			if( (!boardMatrix.isEmpty(0, 0)) && (boardMatrix.getPieceColor(0, 0) == 'p') && (boardMatrix.getPiece(0, 0) instanceof Torre) && (!boardMatrix.hasMoved(0, 0))
					&& boardMatrix.isEmpty(1, 0) && boardMatrix.isEmpty(2, 0) && boardMatrix.isEmpty(3, 0))
			{
				legalList.add(new Coordinate(1, 0));
				towerCastling[0] = new Coordinate(0, 0);
			}
			if( (!boardMatrix.isEmpty(7, 0)) && (boardMatrix.getPieceColor(7, 0) == 'p') && (boardMatrix.getPiece(7, 0) instanceof Torre) && (!boardMatrix.hasMoved(7, 0))
					&& boardMatrix.isEmpty(6, 0) && boardMatrix.isEmpty(5, 0))
			{
				legalList.add(new Coordinate(6, 0));
				towerCastling[1] = new Coordinate(7, 0);
			}
		}
	}
	
	private void moveCastling(Coordinate to)
	{
		if( (towerCastling[0] != null) && (Coordinate.equals( new Coordinate(towerCastling[0].getX()+1, towerCastling[0].getY()), to) ))
			boardMatrix.swap( towerCastling[0].getX()+2, towerCastling[0].getY(), towerCastling[0].getX(), towerCastling[0].getY() );
		else if( (towerCastling[1] != null) && Coordinate.equals( new Coordinate(towerCastling[1].getX()-1, towerCastling[1].getY()), to) )
			boardMatrix.swap( towerCastling[1].getX()-2, towerCastling[1].getY(),  towerCastling[1].getX(), towerCastling[1].getY() );
	}
	
	private void promote(Coordinate peça)
	{
		Peça p = new Dama( boardMatrix.getPieceColor(peça) );
		boardMatrix.setPiece(peça, p);
		
		System.out.println("\n Peão Promovido!\n");
	}
	
	private char check()
	{
		for(int count=0, i=0; i < 8; i++)
		{
			for(int j=0; j < 8; j++)
			{
				Peça aux = boardMatrix.getPiece(i, j);
				
				if(aux instanceof Rei)
				{
					if(aux.getColor() == 'b')
					{
						whiteKing = new Coordinate(i, j);
						count++;
					}
					else if(aux.getColor() == 'p')
					{
						blackKing = new Coordinate(i, j);
						count++;
					}
					if(count == 2)
						break;
				}
			}
			if(count == 2)
				break;
		}
		
		for(int i=0; i < 8; i++)
		{
			for(int j=0; j < 8; j++)
			{
				if(!boardMatrix.isEmpty(i, j) && !( Coordinate.equals(new Coordinate(i, j), whiteKing) || Coordinate.equals(new Coordinate(i, j), blackKing)) )
				{
					movement(i, j);
					if((boardMatrix.getPieceColor(i, j) == 'p') && isLegal(whiteKing))
					{
						return 'b';
					}
					else if((boardMatrix.getPieceColor(i, j) == 'b') && isLegal(blackKing))
					{
						return 'p';
					}
				}				
			}
		}
		
		return 'n';
	}
	private boolean mate(char turn)
	{
		Coordinate king;
		
		if(turn == 'b')
			king = blackKing;
		else
			king = whiteKing;
		
		movement(king);

		for(int i=0; i < 8; i++)
		{
			for(int j=0; j < 8; j++)
			{
				Coordinate p = new Coordinate (i, j);
				movement(p);
				if( !boardMatrix.isEmpty(i, j) && (boardMatrix.getPieceColor(i, j) != turn) ) 
				{
					if( !legalList.isEmpty() )
					{
						for(int q = 0; q < legalList.size(); q++)
						{
							Coordinate c = legalList.get(q);
							
							keep = boardMatrix.swap(c, p);
							
							if(check() == 'n')
							{
								boardMatrix.swap(p, c);
								boardMatrix.setPiece(c, keep);
								return false;
							}
							
							boardMatrix.swap(p, c);
							boardMatrix.setPiece(c, keep);
							movement(p);
						}
					}
				}				
			}
		}
		
		movement(king);

		if( !legalList.isEmpty() )
		{
			for(int i = 0; i < legalList.size(); i++)
			{
				Coordinate c =legalList.get(i);
				keep = boardMatrix.swap(c, king);
				
				if(check() == 'n')
				{
					boardMatrix.swap(king, c);
					boardMatrix.setPiece(c, keep);
					return false;
				}
				
				boardMatrix.swap(king, c);
				boardMatrix.setPiece(c, keep);
				movement(king);
			}
		}
		
		return true;
	}
}
