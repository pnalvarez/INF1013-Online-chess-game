package board;

import gameButtons.Load;
import gameButtons.Restart;
import gameButtons.Save;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board extends Observable 
{	
	public BoardMatrix boardMatrix = new BoardMatrix();

	static private int tileWidth = 50, tileHeight = 50;
	
	private ClickCount counter = new ClickCount(this);
	private ClickObserver watcher = new ClickObserver();
	
	private BoardMovement boardMovement = new BoardMovement(boardMatrix);
	private char turn = 'b';
	private char mine;
	private int line = 1;
		
	public JFrame frame;
	public JPanel mainPanel;
	private JPanel buttonPanel;
	private JPanel boardPanel;
	public DrawBoard draw;
	
	private Save saveButton;
	private Load loadButton;
	private Restart restartButton;
	
	public JFrame getFrame()
	{
		return frame;
	}
	
	public Board(char myColor)
	{
		counter.addObserver(watcher);
		
		mine = myColor;
		System.out.println(line + " - Brancas Come�am!");
		line++;
		
		frame = new JFrame("Chess");
		boardPanel = new JPanel();
		draw = new DrawBoard(boardMatrix);
		boardPanel = draw.panel;
		boardPanel.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mousePressed(MouseEvent e)
			{	
				if(turn==mine){
				if(counter.isEven())
				{
					if( boardMovement.moveFrom(new Coordinate(e.getX()/tileWidth, e.getY()/tileHeight), line, turn) )
					{
						counter.decrement();
					}
				}
				else
				{
					if( !boardMovement.moveTo(new Coordinate(e.getX()/tileWidth, e.getY()/tileHeight), line, turn) )
					{
						nextTurn();
						saveButton.saveGame(boardMatrix, turn);
						setChanged();
						notifyObservers();
					}
					else
					{
						line++;
					}
				}
				counter.increment();
			}
			}
		});
		mainPanel = new JPanel(new BorderLayout());
		buttonPanel = new JPanel();
		
		saveButton = new Save("Save");
		loadButton = new Load("Load");
		restartButton = new Restart("Restart");
		
		saveButton.save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveButton.saveGame(boardMatrix, turn);
			}
		});
		
		loadButton.load.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadButton.loadGame(boardMatrix, boardPanel);
				turn = loadButton.getTurn();
			}
		});
		
		restartButton.restart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				restartButton.restartGame(boardMatrix, boardPanel);
				turn = 'b';
			}
		});
		
		buttonPanel.add(saveButton.save);
		buttonPanel.add(loadButton.load);
		buttonPanel.add(restartButton.restart);
		
		mainPanel.add(boardPanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		frame.add(mainPanel);
		
		/* + 15 e + 40 s�o valores arbitrariamente escolhidos */
		frame.setBounds(100, 100, (tileWidth * 8) + 15, (tileHeight * 8) + 80 );
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}		
	public Board getBoard(){
		return this;
	}
	public BoardMatrix getCurrentBoard() {
		return this.boardMatrix;
		
	}
	private void nextTurn ()
	{
		if( turn == 'b')
		{
			turn = 'p';
			System.out.println(line + " - Turno das Pe�as Pretas!");
		}
		else
		{	
			turn = 'b';
			System.out.println(line + " - Turno das Pe�as Brancas!");
		}
		line++;
	}

	public void carregaTab(){
		loadButton.loadGame(boardMatrix, boardPanel);
		loadButton.loadGame(boardMatrix, boardPanel);
		loadButton.doClick();
	}
	
}