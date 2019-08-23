package com.gs.shape;


import java.awt.*;

import java.awt.geom.*;

import com.gs.ui.*;

public class ImageLayer extends ShapeLayer {
	
	ShapeLayerModel model;
	
	RenderSurface renderSurface;
	
	Image img;
	
	public ImageLayer(ShapeLayerModel model, RenderSurface renderSurface)
	{
		super(model, renderSurface);
		this.model = model;
		this.renderSurface = renderSurface;
		this.img = img;
	}


	public void paintShape(Graphics2D g)
	{
		g.setRenderingHint(antialiasing, antialiasingOn);
		
        Color old = g.getColor();
        
        g.setStroke(model.getStroke());     
       
        if(model.getImage() != null)
        {	
        	System.out.println("Inside imager");
        	g.drawImage(model.getImage(), model.getLocX()-getX(),model.getLocY()-getY(),model.getWidth(),model.getHeight(), old, null);
        }
        if(model.getGradientStroke() != null)
       		g.setPaint(model.getGradientStroke());
       	else if(model.getRadialStroke() != null)
       		g.setPaint(model.getRadialStroke());
       	else
       	    g.setColor(model.getStrokeColor());
       	    
       	g.draw(new Rectangle2D.Double(model.getLocX()-getX(),model.getLocY()-getY(),model.getWidth(),model.getHeight()));   
    	
    	g.setColor(old);
    }
}
  