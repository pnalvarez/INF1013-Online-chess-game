package gameUserInterface;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import board.Board;
import client.Game;
import client.IOThread;
 

public class OpponentListActionListener extends JFrame implements Observer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<String> nicknameList = new ArrayList<>();
	JList <String> nicknameJlist;
	DefaultListModel<String> listModel;
	Board board;
	private final int SIZE = 1000;
	
    public OpponentListActionListener() {    	
        listModel = new DefaultListModel<>();              
        nicknameJlist = new JList<>(listModel);
        //nicknameJlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        nicknameJlist.addListSelectionListener(new ListSelectionListener() {			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				IOThread.sendMessage("select:"+nicknameJlist.getSelectedValue());
				
			}
		});
        add(new JScrollPane(nicknameJlist));        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(SIZE, SIZE);
        setLocationRelativeTo(null);
        setVisible(true);        
        nicknameJlist.setVisible(true);
    }    
    
    public void beginChessGame(Object o){
    	String color=o.toString();
    	if(color.equals("whites")){
    		board = Game.getChess('b');
    	}else{
    		board = Game.getChess('p');
    	}
    	
		Game.getChess('x').addObserver(Game.getGame());
    }

	@Override
	public void update(Observable arg0, Object arg1) {
		nicknameList = IOThread.getStringArrayList();
		if(((String) arg1).compareTo("l")==0){
			listModel.clear();
		for(int i=0; i<nicknameList.size();i++){
			if(nicknameList.get(i).compareTo(Game.getMainActionListener().nicknameString)!=0)
			listModel.addElement(nicknameList.get(i));
		}
		}
		else if(((String) arg1).compareTo("whites")==0||((String) arg1).compareTo("blacks")==0){
			beginChessGame(arg1);
			setVisible(false);
			
		}
	}
}
