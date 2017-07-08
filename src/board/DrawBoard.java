package board;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.geom.Rectangle2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

public class DrawBoard extends JPanel implements Observer{
	JPanel panel;
	private int tileWidth = 50, tileHeight = 50; 
	
	public DrawBoard(final BoardMatrix boardMatrix)
	{
		panel = new JPanel(new GridLayout(30, 30))
		{
			public void paintComponent( Graphics g )
			{				  
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                Rectangle2D rekt = new Rectangle2D.Double(0, 0, tileWidth, tileHeight);
                
                for( int i = 0; i < 8 ; i++)
                {
                	for( int j = 0; j < 8; j++)
                	{
                		rekt.setRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
              		
                		if( (i + j) % 2 == 0)
                			g2.setPaint(Color.WHITE);
                		else
                			g2.setPaint(Color.BLACK);
              		  
                		g2.fill(rekt);
                		
                		if( !boardMatrix.isEmpty(i, j) )
                			g2.drawImage(boardMatrix.getSprite(i,j), ( i * tileWidth) + 10, (j * tileHeight) + 10, null);
                		
                		if( boardMatrix.isHighlighted(i, j) )
              		  	{
                			if( boardMatrix.isEmpty(i, j))
                				g2.setPaint(Color.GREEN);
              			  	else
              			  		g2.setPaint(Color.RED);
              			  
                			g2.drawRect(i*tileWidth, j*tileHeight, tileWidth, tileHeight);
              		  	}
                	}        	  
                }
			}
		};
		
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}


}
