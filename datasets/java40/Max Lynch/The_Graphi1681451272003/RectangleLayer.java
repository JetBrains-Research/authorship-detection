package com.gs.shape;


import java.awt.*;

import java.awt.geom.*;

import com.gs.ui.*;

public class RectangleLayer extends ShapeLayer {
	
	ShapeLayerModel model;
	
	RenderSurface renderSurface;
	
	
	public RectangleLayer(ShapeLayerModel model, RenderSurface renderSurface)
	{
		super(model, renderSurface);
		this.model = model;
		this.renderSurface = renderSurface;
	}


	public void paintShape(Graphics2D g)
	{
		g.setRenderingHint(antialiasing, antialiasingOn);
		
        Color old = g.getColor();
        
        Rectangle2D.Double rect = new Rectangle2D.Double(model.getLocX()-getX(),model.getLocY()-getY(),model.getWidth(),model.getHeight());
        
        
        g.setStroke(model.getStroke());
       	
       	if(model.getGradientFill() != null)
       		g.setPaint(model.getGradientFill());
       	else if(model.getRadialFill() != null)
       		g.setPaint(model.getRadialFill());
       	else
       	    g.setColor(model.getColor());
        
        g.fill(rect);

       	if(model.getGradientStroke() != null)
       		g.setPaint(model.getGradientStroke());
       	else if(model.getRadialStroke() != null)
       		g.setPaint(model.getRadialStroke());
       	else
       	    g.setColor(model.getStrokeColor());

     
        g.draw(rect);
     	
        g.setColor(old);
        
    }
}
        
