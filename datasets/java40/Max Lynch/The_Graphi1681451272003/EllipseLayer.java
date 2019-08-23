package com.gs.shape;


import java.awt.*;

import java.awt.geom.*;

import com.gs.ui.*;

public class EllipseLayer extends ShapeLayer {
	
	ShapeLayerModel model;
	
	RenderSurface renderSurface;
	
	
	public EllipseLayer(ShapeLayerModel model, RenderSurface renderSurface)
	{
		super(model, renderSurface);
		this.model = model;
		this.renderSurface = renderSurface;
	}


	public void paintShape(Graphics2D g)
	{
		g.setRenderingHint(antialiasing, antialiasingOn);
		Ellipse2D.Double ellipse = new Ellipse2D.Double(model.getLocX()-getX(),model.getLocY()-getY(),model.getWidth(),model.getHeight());
		
        Color old = g.getColor();
        
        

       	if(model.getGradientFill() != null)
       		g.setPaint(model.getGradientFill());
       	else if(model.getRadialFill() != null)
       		g.setPaint(model.getRadialFill());
       	else
       	    g.setColor(model.getColor());
        
        g.fill(ellipse);

       	if(model.getGradientStroke() != null)
       		g.setPaint(model.getGradientStroke());
       	else if(model.getRadialStroke() != null)
       		g.setPaint(model.getRadialStroke());
       	else
       	    g.setColor(model.getStrokeColor());
      
        g.setStroke(model.getStroke());
        
       
        g.draw(ellipse);
        	
        g.setColor(old);
        
    }
}
  