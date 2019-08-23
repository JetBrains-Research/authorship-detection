package com.gs.shape;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import com.gs.ui.*;

public class FreeLineLayer extends ShapeLayer {
	
	ShapeLayerModel model;
	
	RenderSurface renderSurface;
	
	GeneralPath linePath = new GeneralPath();
	
	boolean first = true;
	
	
	public FreeLineLayer(ShapeLayerModel model, RenderSurface renderSurface)
	{
		super(model, renderSurface);
		this.model = model;
		this.renderSurface = renderSurface;
	}


	public void paintShape(Graphics2D g)
	{
		g.setRenderingHint(antialiasing, antialiasingOn);
		
        Color old = g.getColor();
        
        
        
        g.setStroke(model.getStroke());
       
        
        //g.setColor(model.getColor().darker().darker());
        
       	//g.draw(new Rectangle2D.Double(model.getLocX()-getX(),model.getLocY()-getY(),model.getWidth(),model.getHeight()));

		g.setColor(model.getColor());
		
		
		g.draw(linePath);
        	
        g.setColor(old);
        
    }
    
    public void updatePath(float x, float y)
    {
    	if(first)
    	{
    		linePath.moveTo(x, y);
    		first = false;
    	} else 
    	{
    		linePath.lineTo(x, y);
    	}
    	repaint();
    }
    
}
    	

