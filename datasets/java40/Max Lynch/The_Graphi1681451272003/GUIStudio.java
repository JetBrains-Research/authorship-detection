package com.gs.ui;


import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import com.gs.shape.*;
import com.gs.paint.*;

public class GUIStudio {
	
	private static GUIStudio _instance = null;
	
	
	
	public final static JFrame FRAME = new JFrame();
	
	
	private final GraphicsFrame MAIN_FRAME = new GraphicsFrame(FRAME);	
	
	
	private final RenderMediator renderMediator = MAIN_FRAME.getRenderMediator();
	

	
	
	
	
	
	private GUIStudio()
	{ 

	}
	
	public static synchronized GUIStudio instance()
	{
		if(_instance == null)
		{
			_instance = new GUIStudio();
		}
		return _instance;
	}
	
	
	public GraphicsFrame getGraphicsFrame()
	{
		return MAIN_FRAME;
	}
	
	
	public void removeAll()
	{
		renderMediator.removeAll();
		
	}
	
	public void moveShapeToFront()
	{
		renderMediator.moveShapeToFront();
	}
	 
	 
	public void setBackground(Color color) {
		renderMediator.setBackground(color);
	}
	
	public void setBackground(GradientPaint gp) {
		renderMediator.setBackground(gp);
	}
	
	public void setBackground(RadialGradientPaint rgp) {
		renderMediator.setBackground(rgp);
	}

}
	
	