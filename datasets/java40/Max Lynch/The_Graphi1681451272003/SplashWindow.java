package com.gs.ui;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;
import javax.swing.border.*;

import com.gs.paint.*;

public class SplashWindow extends JWindow {
	static final int WIDTH = 400;
	static final int HEIGHT = 300;
	
	Image gsImage;
	
	public SplashWindow()
	{
		int s_w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int s_h = Toolkit.getDefaultToolkit().getScreenSize().height;
		int s_w_2 = s_w/2;
		int s_h_2 = s_h/2;
		
		gsImage = Toolkit.getDefaultToolkit().getImage("hi.jpg");
		
		InnerWindow iw = new InnerWindow(gsImage);
		
		getContentPane().setLayout(new BorderLayout());
		
		getContentPane().add(iw, BorderLayout.CENTER);
		
		setBounds(s_w_2-WIDTH/2, s_h_2-HEIGHT/2, WIDTH, HEIGHT);
		
		show();
		
		
	}
	
	
	public void close()
	{
		dispose();
		setVisible(false);
	}
}

class InnerWindow extends JPanel {
	
	Image img;
	
	public InnerWindow(Image img)
	{
		this.img = img;
				
		setBorder(new EtchedBorder(Color.darkGray, Color.lightGray));
	}
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		GeneralPath gp = new GeneralPath();
		/*gp.moveTo(20, 100);
		gp.curveTo(100, 250, 10, 200, 100, 10);
		gp.lineTo(20, 200);
		gp.closePath();
		*/
		
		/*gp.moveTo(35, 50);
		
		
		gp.curveTo(35, 75, 75, 75, 75, 100); 
		
		gp.curveTo(75, 120, 50, 120, 50, 100); 
		
		gp.curveTo(50, 90, 65, 90, 65, 100);*/
		
		float r = 100;
		gp.moveTo((float)(r*Math.sin(0))+100, (float)(r*Math.cos(0))+100);
		for(int d = 0; d < 360; d++)
		{
			float x = (float)(r*Math.sin(d));
			float y = (float)(r*Math.cos(d));
			
			gp.lineTo((float) x+100, (float) y+100);
			if(r > 0)
				r-=.5;
		} 
		
		g2.setPaint(new Color(100, 100, 100));
		g2.setStroke(new BasicStroke(2f));
		g2.draw(gp);
		g2.setPaint(new RadialGradientPaint(new Rectangle2D.Double(300, 40, 100, 100), new Color(100, 100, 100), new Color(0, 40, 90)));
		g2.setFont(new Font("Sans-Serif", Font.BOLD, 140));
		g2.drawString("G", 300, 100);	
		g2.setPaint(new Color(100, 100, 100));
		g2.fill(new Rectangle2D.Double(40, 233, 300, 50));
		g2.setPaint(new Color(0, 20, 70));
		g2.setStroke(new BasicStroke(3f));
		g2.draw(new Rectangle2D.Double(40, 233, 300, 50));
		g2.setPaint(new GradientPaint(0, 0, new Color(0, 40, 100), 400, 300, new Color(0, 40, 100, 130)));
		g2.setFont(new Font("Times Roman", Font.BOLD, 24));
		g2.drawString("Graphics Studio v0.1", 50, 260);
		g2.setFont(new Font("Times Roman", Font.BOLD, 16));
		g2.drawString("Developed By: Max Lynch", 100, 275);

		
		
	}
}
		