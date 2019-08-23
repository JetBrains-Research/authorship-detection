package com.gs.ui;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;

import javax.swing.*;



public class About extends JDialog {
	
	AboutPanel aboutPanel = new AboutPanel();
	
	
	public About()
	{
		super(new JFrame(), "About Graphics Frame", true);
		setSize(500, 400);
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(aboutPanel, BorderLayout.CENTER);
		show();
		setResizable(false);
		
	}
	
	
	
	
	class AboutPanel extends JPanel implements Runnable, MouseMotionListener {
		Thread runner;
		int speed = 100;
		
		int x = 120;
		int y = 120;
		
		int incx = 5;
		int incy = 5;
		
		int r;
		
		Point mousexy;
		
		Ellipse2D.Double circle = new Ellipse2D.Double(120, 120, 100, 100);
		
		Graphics2D g2; 
		
		
		public AboutPanel()
		{
			setBackground(Color.red);
			
			addMouseMotionListener(this);
			runner = new Thread(this);
			runner.start();
		}
		

		
		public void run()
		{
			while(true)
			{
				
				
				x+=incx;
				y+=incy;
				
				if(x >= getWidth()-(int)circle.width || x <= 1)
				{	
					r = (int) Math.random()*15;
					incx*=-1+r;
				}
				if(y >= getHeight()-(int)circle.height || y <= 1)
				{	
					r = (int) Math.random()*15;
					incy*=-1+r;
				}
			
				if(mousexy != null)
				{
					if(circle.contains(mousexy))
					{
						//try {
	
							
							JOptionPane.showMessageDialog(this, "Game Over Silly ass speed increasing");
							if(speed-10 > 0)
								speed-=10;
							else if(speed-1 > 0)
								speed--;
							
						//} catch(InterruptedException ex) {}
					}
				}
					
				repaint();
				
				try { 
					runner.sleep(speed);
				} catch(InterruptedException e) {}
			}
			
		}
		
		public void mouseMoved(MouseEvent e)
		{
			mousexy = e.getPoint();
			System.out.println(mousexy);
		}
		
		public void paintComponent(Graphics g)
		{
			g2 = (Graphics2D) g;
			g2.clearRect(0, 0, getSize().width, getSize().height);
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			circle = new Ellipse2D.Double(x, y, 100, 100);
			g2.draw(circle);
			
		}
		
		public void mouseDragged(MouseEvent e) {}
		
	}
}
				