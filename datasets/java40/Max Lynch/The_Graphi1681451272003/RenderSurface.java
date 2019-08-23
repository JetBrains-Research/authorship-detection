package com.gs.ui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.*;

import com.gs.main.*;
import com.gs.ui.*;
import com.gs.shape.*;
import com.gs.paint.*;


public class RenderSurface extends JPanel implements MouseListener, MouseMotionListener {
	
	
	GraphicsStudio studio;
	MenuMan menus;
	
	public ShapeLayer selected;
	
	int s_x1, s_y1, s_x2, s_y2, s_w, s_h;
	
	Point down, current;
	
	int addTime = 0;
	
	boolean added = false;
	boolean freeDrawing = false;
	
	Paint paint;
	
	Rectangle selectionRectangle;
	
	GraphicsFrame frame;
	

	FreeLineLayer freeShape;

	
	ShapeLayerModel rectModel = new ShapeLayerModel('r');

	public RenderSurface(GraphicsFrame frame)
	{
		super();
		
		this.frame = frame;
		menus = frame.getMenuMan();
		
		
		
		
		
		setLayout(null);
		setBackground(Color.white);
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e)
			{
				System.out.println("Key Pressed");
				if(e.getKeyCode() == KeyEvent.VK_DELETE)
				{
					if(getSelected() != null)
					{
						remove(selected);
						repaint();
					}
				}
				
			}
		} );
		

		 
	}
	
	public MenuMan getMenuMan()
	{
		return menus;
	}
	
	

	
    public void addShape(ShapeLayerModel model) {
    	ShapeLayer temp = null;
    
	    if(model.getType() == 'r') temp = new RectangleLayer(model, this);
	    if(model.getType() == 'o') temp = new EllipseLayer(model, this);
	    if(model.getType() == 'l') temp = new LineLayer(model, this);
	    if(model.getType() == 's') temp = new SwirlLayer(model, this);
	    if(model.getType() == 'i') temp = new ImageLayer(model, this);
	    /*if(model.getType() == 'f') 
	    {
	    	freeShape = new FreeLineLayer(model, this);
	    	
	    	freeDrawing = true;
	    } */
	    
    	if(!freeDrawing)
    		add(temp);
    	else 
    		add(freeShape); 
    	System.out.println("Shape Added");
    
    	repaint();
	}
	
	
	

	
	
	
	
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		
		if(paint != null)
		{
			g2.setPaint(paint);
			g2.fill(new Rectangle2D.Double(0, 0, getSize().width, getSize().height));
		}
		
		if(current != null)
		{
			g2.draw(selectionRectangle);
		}
		
	}
	
	
	
	
	
	
	
	
	
	

	public void moveShapeToFront()
	{
		  if(selected != null)
		  {
		  
		  	remove(selected);
          	add(selected, 0);//getComponentCount();
          	selected.repaint();
          }
          
			
		
	}
	
	
	public void moveShapeToBack()
	{
		if(selected != null)
		{
				
			remove(selected);
	        add(selected, -1);
	        selected.repaint();
	 	}
	        
		
		
	}
	
	
	public void setBackground(GradientPaint paint)
	{
		this.paint = paint;
		repaint();
	}
	
	
	public void setBackground(RadialGradientPaint paint)
	{
		this.paint = paint;
		repaint();
	}
	
	public void setBackground(Color color)
	{
		if(paint != null)
			paint = null;
		super.setBackground(color);
		repaint();
	}
	
	public void setSelected(ShapeLayer selected)
	{
		this.selected = selected;
		
		
		repaint();
	}
	
	public ShapeLayer getSelected()
	{
		return selected;
	}
	
	public ShapeLayerModel getSelectedModel()
	{
		return selected.getModel();
	} 



	public void mousePressed(MouseEvent e) {
		int m_x = e.getX();
		int m_y = e.getY();
		
		s_x1 = m_x;
		s_y1 = m_y;
		
		down = new Point(s_x1, s_y1);
		
		if(getSelected() != null)
			selected = null;
			
		requestFocus();
	}

	
	
	public void mouseClicked(MouseEvent e) {
		selected = null;
		repaint();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(selectionRectangle != null)
		{
			
			Component[] components = getComponents();
			for(int i = 0; i < components.length; i++)
			{
				if(components[i].getBounds().intersects(selectionRectangle))
				{
					((ShapeLayer) components[i]).select();
				}
			}
			current = null;
			repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mouseDragged(MouseEvent e) {
		int m_x = e.getX();
		int m_y = e.getY();
		
		if(getSelected() == null && !freeDrawing)
		{
			
			current = new Point(m_x, m_y);
		
			s_w = current.x - down.x;
			s_h = current.y - down.y;
			
			selectionRectangle = new Rectangle(down.x, down.y, s_w, s_h);
			
			
			repaint();
		} /*else if(freeDrawing)
		{
			if(freeShape != null)
			{
				freeShape.updatePath(m_x, m_y);
				repaint();
				System.out.println("drawing");
			}
		}*/
			
		
	
	}

	public void mouseMoved(MouseEvent e) {
	}
	

									 
									 	
										
	
}			