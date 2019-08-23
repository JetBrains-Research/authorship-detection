package com.gs.shape;


import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;

import java.io.*;

import com.gs.paint.*;
import com.gs.shape.event.*;


public class ShapeLayerModel extends Object implements Serializable {

	
	private char type;
	private int x1, y1;
	private int x2, y2;
	private int x3, y3;
	private int x4, y4;
	private int oldx1, oldx2;
	private int oldy1, oldy2;
	private float size;
	private Color fillColor;
	private Color strokeColor;
	private GradientPaint fillColorGradient;
	private GradientPaint strokeColorGradient;
	private RadialGradientPaint fillColorRadial;
	private RadialGradientPaint strokeColorRadial;	
	private Stroke stroke;
	private Image image;
	private boolean fill;
	private boolean mouse3or4 = false;
	
	
	public ShapeLayerModel(char type) {
		this(type, 50, 50, 89, 89, Color.gray);
	}
	
	
	public ShapeLayerModel(char type, int x1, int y1, int x2, int y2, Color fillColor, Stroke stroke, boolean fill) {
		this.type = type;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.fillColor = fillColor;
		this.stroke = stroke;
		this.fill = fill;
		strokeColor = Color.black;
		oldx1 = 0;
		oldx2 = 0;
		oldy1 = 0;
		oldy2 = 0;
		listeners = null;
	}
	
	public ShapeLayerModel(char type, int x1, int y1, int x2, int y2, Color fillColor) {
		this(type, x1, y1, x2, y2, fillColor, new BasicStroke(1f), true);
	}
	
	public char getType() { return(type); }
	
	public int getX1() { return(x1); }
	public int getY1() { return(y1); }
	public int getX2() { return(x2); }
	public int getY2() { return(y2); }
	public int getX3() { return(x2); }
	public int getY3() { return(y1); }
	public int getX4() { return(x1); }
	public int getY4() { return(y2); }
	
	
	
	
	public void applyDeltas(int dx1, int dy1, int dx2, int dy2, int dx3, int dy3, int dx4, int dy4, boolean m3or4) {
            x1+=dx1;
            y1+=dy1;
            x2+=dx2;
            y2+=dy2;
            x3+=dx3;
            y3+=dy3;
            x4+=dx4;
            y4+=dy4;
            mouse3or4 = m3or4;
            notifyListeners();
	}
	
	int getWidth() {
            int w = Math.abs(x1-x2);
            if(mouse3or4)
            	w = Math.abs(x4-x3);
       
            if(w != 0) return w;
            return 1;
	}
	
	int getHeight() {
            int h = Math.abs(y1-y2);
            if(mouse3or4)
            	h = Math.abs(y4-y3);
            	
         
            if(h != 0) return h;
            return 1;
	}
	
	int getLocX() {
            return Math.min(x1,x2);
	}
	
	int getLocY() {
            return Math.min(y1,y2);
	}
	
	float getStrokeWidth()
	{
		return size;
	}
	
	void setStrokeWidth(float size)
	{
		this.size = size;
	}
	
	void setBounds(Rectangle bounds)
	{
		x1 = (int) bounds.getX();
		y1 = (int) bounds.getY();
		x2 = (int) (bounds.getX() + bounds.getWidth());
		y2 = (int) (bounds.getY() + bounds.getHeight());
	}
	
	public Color getColor() { return(fillColor); }
	public Color getStrokeColor() { return(strokeColor); }
	public Color getStrokeColor1() {
		if(strokeColorGradient != null) 
			return strokeColorGradient.getColor1();
		else 
			return strokeColor;
		}
	public Color getStrokeColor2() {
		if(strokeColorGradient != null)
			return strokeColorGradient.getColor2();
		else 
			return strokeColor;
		}
	public Color getFillColor1() {
		if(fillColorGradient != null)
			return fillColorGradient.getColor2();
		else 
			return fillColor;
		}
	public Color getFillColor2() {
		if(fillColorGradient != null)
			return fillColorGradient.getColor2();
		else 
			return fillColor;
		}
	public GradientPaint getGradientFill()
	{
		return fillColorGradient;
	}
	public RadialGradientPaint getRadialFill()
	{
		return fillColorRadial;
	}
	public GradientPaint getGradientStroke()
	{
		return strokeColorGradient;
	}
	public RadialGradientPaint getRadialStroke()
	{
		return strokeColorRadial;
	}
	public Stroke getStroke() { return stroke; }
	public boolean getFill() { return fill; }
	
	public void setColor(Color color) {
            fillColor = color;
            fillColorGradient = null;
            fillColorRadial = null;
            notifyListeners();
	}
	public void setStrokeColor(Color color)
	{
		strokeColor = color;
		strokeColorGradient = null;
		strokeColorRadial = null;
		notifyListeners();
	}
	public void setGradientStroke(GradientPaint g)
	{
		strokeColorGradient = g;
		strokeColor = null;
		strokeColorRadial = null;
		notifyListeners();
	}
	public void setGradientStroke(RadialGradientPaint g)
	{
		strokeColorGradient = null;
		strokeColor = null;
		strokeColorRadial = g;
		notifyListeners();
	}

	public void setGradientFill(GradientPaint g)
	{
		fillColorGradient = g;
		fillColor = null;
		fillColorRadial = null;
		notifyListeners();
	}
	
	public void setGradientFill(RadialGradientPaint g)
	{
		fillColorGradient = null;
		fillColor = null;
		fillColorRadial = g;
		notifyListeners();
	}
		
	public void setStroke(Stroke stroke, float size)
	{
		//this.size = size;
		this.stroke = stroke;
		this.size = size;
	}
	
	public void setFill(boolean fill)
	{
		this.fill = fill;
	}
	
	public void setImage(Image i)
	{
		if(type == 'i')
			image = i;
	}
	
	public Image getImage()
	{
		if(image != null && type == 'i')
			return image;
		else
			return null;
	}
	transient private ArrayList listeners;
	
	
	
	public void addListener(ShapeModelListener listener) {
            if(listeners == null) {
                listeners = new ArrayList();
            }
            listeners.add(listener);
	}

	public void removeListener(ShapeModelListener listener) {
            if(listeners == null || listener == null) return;
            if(listeners.contains(listener)) {
                listeners.remove(listener);
            }
	}
        
        protected void notifyListeners() {
            if(listeners == null) return;
            Iterator it = listeners.iterator();
            while(it.hasNext()) {
                ShapeModelListener elem = (ShapeModelListener)it.next();
                elem.shapeChanged(this);
            }
        }
	

}
