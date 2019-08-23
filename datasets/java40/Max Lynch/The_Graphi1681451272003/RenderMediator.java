package com.gs.ui;


import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import com.gs.shape.*;
import com.gs.paint.*;

public class RenderMediator {
	
	
	private RenderSurface renderSurface;
	
	public RenderMediator(GraphicsFrame frame){
		renderSurface = frame.getRenderSurface();
	
	
	}
	
		
	public void setBackground(Color color) {
		renderSurface.setBackground(color);
	}
	
	public void setBackground(GradientPaint gp) {
		renderSurface.setBackground(gp);
	}
	
	public void setBackground(RadialGradientPaint rgp) {
		renderSurface.setBackground(rgp);
	}
	
	public void moveShapeToFront() {
		renderSurface.moveShapeToFront();
	}
	
	public void moveShapeToBack() {
		renderSurface.moveShapeToBack();
	}
	
	public void removeAll() {
		renderSurface.removeAll();
		renderSurface.repaint();
	}
	
	public void addShape(ShapeLayerModel model)
	{
		renderSurface.addShape(model);
	}
	
	public int getSurfaceWidth()
	{
		return renderSurface.getWidth();
	}
	
	public int getSurfaceHeight()
	{
		return renderSurface.getHeight();
	}
	
}