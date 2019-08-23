package com.gs.ui;



/* GradientControls.java
 * Class that controls all actions and layout for the gradient size controls
 * you see when choosing between linear or radial gradients */ 



import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import com.gs.ui.*;

public class GradientControls extends JPanel implements ChangeListener {
	
	public static final int LINEAR_CONTROLS = 0;
	public static final int RADIAL_CONTROLS = 1;
	
	GraphicsFrame frame;
	
	ColorFramePanelGradient gradientPanel;
	
	JSlider xSlider = new JSlider(0, 100);
	JSlider ySlider = new JSlider(0, 100);
	
	JSlider wSlider = new JSlider(0, 100);
	JSlider hSlider = new JSlider(0, 100);
	
	JLabel xLabel = new JLabel("x:");
	JLabel yLabel = new JLabel("y:");
	JLabel wLabel = new JLabel("w:");
	JLabel hLabel = new JLabel("h:");
	
	int g_x, g_y, g_w, g_h, type;
	
	public GradientControls(int type)
	{
		
		

		this.type = type;
		
		if(type == LINEAR_CONTROLS)
		{
			showLinearDialog();
		} else if(type == RADIAL_CONTROLS)
		{
			showRadialDialog();
		} else {
			showLinearDialog();
		}
		
		xSlider.addChangeListener(this);
		ySlider.addChangeListener(this);
		wSlider.addChangeListener(this);
		hSlider.addChangeListener(this);
		
	}
	
	public void showLinearDialog()
	{
		 removeAll();

		 setLayout(new GridLayout(2, 2));
		 add(xLabel);
		 add(xSlider);
		 add(yLabel);
		 add(ySlider);
		
	}
	
	public void showRadialDialog()
	{
		 removeAll();


		 setLayout(new GridLayout(2, 4));
		 add(xLabel);
		 add(xSlider);
		 add(yLabel);
		 add(ySlider);
		 add(wLabel);
		 add(wSlider);
		 add(hLabel);
		 add(hSlider);
		 
	}
	
	public void stateChanged(ChangeEvent e)
	{
		if(e.getSource() == xSlider)
		{
			g_x = xSlider.getValue();
		} else if(e.getSource() == ySlider)
		{
			g_y = ySlider.getValue();
		} else if(e.getSource() == wSlider)
		{
			g_w = wSlider.getValue();
		} else if(e.getSource() == hSlider)
		{
			g_h = hSlider.getValue();
		}
	
		update();
	}
	
	
	public void update()
	{
		if(gradientPanel != null)
		{	
			gradientPanel.setGradientSize(g_x, g_y, g_w, g_h);
			System.out.println("asdfaf");
		}
	}
	
	public void registerForChange(ColorFramePanelGradient gradientPanel)
	{
		this.gradientPanel = gradientPanel;
	}
}
		