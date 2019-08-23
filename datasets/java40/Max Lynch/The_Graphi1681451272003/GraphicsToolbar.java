package com.gs.ui;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import com.gs.ui.*;
import com.gs.shape.*;

public class TestToolbar extends JToolBar implements ActionListener {
	ToolbarPanel tbp;
	RenderSurface renderSurface;
	GraphicsFrame frame;
	JButton rectButton = new JButton(new ImageIcon("rect.gif"));
	JButton circleButton = new JButton(new ImageIcon("circle.gif"));
	JButton lineButton = new JButton(new ImageIcon("line.gif"));
	JButton freeButton = new JButton("Free");
	JButton swirlButton = new JButton("Swirl");	
	public TestToolbar(GraphicsFrame frame)
	{
		super("Tools");
		
		this.frame = frame;
		//tbp = new ToolbarPanel();
		
		//setLayout(new BorderLayout());
		//add(tbp, BorderLayout.CENTER);
		add(rectButton);
		add(circleButton);
		add(lineButton);
		//add(freeButton);
		//add(swirlButton);
		
		rectButton.addActionListener(this);
		circleButton.addActionListener(this);
		lineButton.addActionListener(this);
		freeButton.addActionListener(this);
		swirlButton.addActionListener(this);
		
		

	}


		
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == rectButton)
				frame.addShape(new ShapeLayerModel('r'));
			 else if(e.getSource() == circleButton)			
				frame.addShape(new ShapeLayerModel('o'));
			 else if(e.getSource() == lineButton)			
				frame.addShape(new ShapeLayerModel('l'));
			 else if(e.getSource() == freeButton)
				frame.addShape(new ShapeLayerModel('f'));
			 else if(e.getSource() == swirlButton)
			 	frame.addShape(new ShapeLayerModel('s'));
			
		}
	
}