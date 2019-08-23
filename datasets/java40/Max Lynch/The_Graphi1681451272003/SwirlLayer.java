package com.gs.shape;

import java.awt.*;
import java.awt.geom.*;

import com.gs.ui.*;

public class SwirlLayer extends ShapeLayer {
	GeneralPath swirl = new GeneralPath();
	public SwirlLayer(ShapeLayerModel model, RenderSurface renderSurface)
	{
		super(model, renderSurface);
		this.model = model;
		this.renderSurface = renderSurface;
	}
	
	
	public void paintShape(Graphics2D g)
	{
		g.setRenderingHint(antialiasing, antialiasingOn);
		Color old = g.getColor();
		
		swirl = new GeneralPath();
        
        AffineTransform af = new AffineTransform();

        
        float x1 = model.getLocX()-getX()+model.getWidth()/2;
        float y1 = model.getLocY()-getY()+model.getHeight()/2;
        float heighty = model.getHeight()-model.getLocY()-getY();
        float widthx = model.getWidth()-model.getLocX()-getX();
        float width = model.getWidth();
        float height = model.getHeight();
        
        
        	
        g.setStroke(model.getStroke());
        
        g.setPaint(model.getColor());
        
  		swirl.moveTo(x1, y1);
		
		swirl.curveTo(x1, heighty, width-40, heighty, width-20, y1); //up
		
		swirl.curveTo(x1+70, y1+40, x1+20, y1+40, x1+20, y1); //down
		
		swirl.curveTo(x1+20, y1-20, x1+50, y1-20, x1+50, y1); //up
		
		swirl.curveTo(x1+50, y1+20, x1+40, y1+20, x1+30, y1); //down
 		
 		
 		/* Better swirl for now
 		 *swirl.moveTo(x1, y1);
		
		swirl.curveTo(x1, y1, x1+70, y1-40, x1+70, y1); //up
		
		swirl.curveTo(x1+70, y1+40, x1+20, y1+40, x1+20, y1); //down
		
		swirl.curveTo(x1+20, y1-20, x1+50, y1-20, x1+50, y1); //up
		
		swirl.curveTo(x1+50, y1+20, x1+40, y1+20, x1+30, y1); //down
		
		
		//swirl.curveTo(50, 90, 65, 90, 65, y1); //up
		
		/*       
		
		swirl.moveTo(20, 100);
		
		swirl.curveTo(20, 60, 90, 60, 90, 100); //up
		
		swirl.curveTo(90, 140, 35, 140, 35, 100); //down
		
		swirl.curveTo(35, 75, 75, 75, 75, 100); //up
		
		swirl.curveTo(75, 120, 50, 120, 50, 100); //down
		
		swirl.curveTo(50, 90, 65, 90, 65, 100); //up */
	
		g.draw(swirl);

		swirl = null;
		
		g.setTransform(new AffineTransform());
		
	}
	
}