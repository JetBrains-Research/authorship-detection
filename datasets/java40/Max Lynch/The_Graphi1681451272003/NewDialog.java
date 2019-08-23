package com.gs.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class NewDialog extends JDialog implements ActionListener {
	Box buttonBox = new Box(BoxLayout.X_AXIS);
	
	JPanel mainPanel = new JPanel();
	
	JButton ok = new JButton("OK");
	JButton cancel = new JButton("Cancel");
	
	JLabel widthL = new JLabel("Width: ");
	JLabel heightL = new JLabel("Height: ");
	
	JTextField widthT = new JTextField();
	JTextField heightT = new JTextField();
	
	
	public NewDialog()
	{
		super(new JFrame(), "New Document", true);
		getContentPane().setLayout(new GridLayout(2, 1));
		
		getContentPane().add(mainPanel);
		getContentPane().add(buttonBox);
		
		mainPanel.setLayout(new GridLayout(2, 2));
		
		mainPanel.add(widthL);
		mainPanel.add(widthT);
		mainPanel.add(heightL);
		mainPanel.add(heightT);
		
		buttonBox.createHorizontalStrut(100);
		buttonBox.add(ok);
		buttonBox.add(cancel);
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		
		
		setSize(160, 120);
		
		
		
	}
	
	public int getWidth()
	{
		int width = Integer.parseInt(widthT.getText());
		return width;
	}
	
	public int getHeight()
	{
		int height = Integer.parseInt(heightT.getText());
		return height;
	}
	
	public void actionPerformed(ActionEvent e)
	{
		super.hide();
	}
}	