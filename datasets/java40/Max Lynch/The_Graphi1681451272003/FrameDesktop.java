package com.gs.ui;



/* Class that controls all the internal windows.  extends JDesktopPane.*/



import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

//Imports the main classes of GraphicsStudio.  Ohhh bizzoy you hot.  tEcHrolLas
import com.gs.main.*;


public class FrameDesktop extends JDesktopPane { 
	
	StudioFrame studioFrame;
	RenderSurface renderSurface;
	GraphicsFrame frame;
	
	GradientControls gradientControls;
	
	JInternalFrame colorPicker, strokeDialog, gradientExtras;
	

	public FrameDesktop(GraphicsFrame frame)
	{
		
		
		this.frame = frame;
		
		gradientControls = new GradientControls(GradientControls.LINEAR_CONTROLS);
		
		renderSurface = frame.getRenderSurface();
		
		studioFrame = new StudioFrame(500, 450, renderSurface);
		
		colorPicker = createInternalFrame("Color Picker", 540, 0, 200, 300, new ColorTabPanel(frame));
		strokeDialog = createInternalFrame("Stroke", 540, 300, 200, 150, new StrokeFramePanel(renderSurface));
		gradientExtras = createInternalFrame("Gradient Extras", 400, 250, 160, 120, gradientControls);
		
		gradientExtras.setVisible(false);
		//gradientExtras.putClientProperty("JInternalFrame.isPalette", Boolean.TRUE); 
		
		
		add(studioFrame);
		add(gradientExtras);
		add(colorPicker);
		add(strokeDialog);
		
		
		
	}
	
	
	public JInternalFrame createInternalFrame(String title, int x, int y, int w, int h, JComponent component)
	{
		JInternalFrame f = new JInternalFrame(title, false, false, false, false);
		f.setBounds(x, y, w, h);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(component, BorderLayout.CENTER);
		f.show();

		return f;
	}
	
	
	public void repositionWindows(Dimension newD)
	{
		int sw = newD.width;
		int sh = newD.height;
		
		System.out.println(sw);
		System.out.println(sh);
		
		colorPicker.setBounds(sw-250, 10, colorPicker.getWidth(), colorPicker.getHeight());
		strokeDialog.setBounds(sw-250, sh-270, strokeDialog.getWidth(), strokeDialog.getHeight());
	}

	
}