package gameUserInterface;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.*;

import client.UserInterfaceManager;


public class NicknameActionListener extends JFrame  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	String nicknameString;

    private final int SIZE = 500;
    private JTextField nicknameJtextField = new JTextField();
	
	public NicknameActionListener(){
		
		
		JLabel nicknameJLabel = new JLabel("Nickname:");
		JButton start = new JButton("Start");
		JPanel panel = new JPanel();
		
		add(panel);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(SIZE, SIZE);
		setLocationRelativeTo(null);
		panel.setLayout(null);
		panel.add(nicknameJLabel);
		nicknameJLabel.setBounds(200, 30, 200, 20);
		panel.add(nicknameJtextField);
		nicknameJtextField.setBounds(100, 100, 300, 30);
		nicknameJtextField.grabFocus();
		panel.add(start);
		start.setBounds(150, 350, 200, 50);
		
		start.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				nicknameString = nicknameJtextField.getText();
				if(nicknameString.length()>=8 && nicknameString.length()<=12){
					setVisible(false);
					
					UserInterfaceManager.nick = nicknameString;
					UserInterfaceManager.getClient();
					
				}
				else{
					JOptionPane.showMessageDialog(null, "Your nickname must have between 8 and 12 characters.");
				}
			}
			
			
			
		});
	}
		
	}

	

