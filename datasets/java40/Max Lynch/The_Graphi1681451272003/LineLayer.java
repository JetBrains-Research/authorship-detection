package com.gs.shape;


import java.awt.*;

import java.awt.geom.*;

import com.gs.ui.*;

public class LineLayer extends ShapeLayer {
	
	ShapeLayerModel model;
	
	RenderSurface renderSurface;
	
	
	public LineLayer(ShapeLayerModel model, RenderSurface renderSurface)
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
       
        
       	if(model.getGradientStroke() != null)
       		g.setPaint(model.getGradientStroke());
       	else if(model.getRadialStroke() != null)
       		g.setPaint(model.getRadialStroke());
       	else
       	    g.setColor(model.getStrokeColor());      
        
        
       
        g.draw(new Line2D.Double(model.getX1()-this.getX(),model.getY1()-this.getY(),model.getX2()-this.getX(),model.getY2()-this.getY()));
        	
        g.setColor(old);
        
    }
}
  