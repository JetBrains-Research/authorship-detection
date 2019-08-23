package com.gs.ui;

/* Class defines all the functionality of the stroke chooser panel */

import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;




public class StrokeFramePanel extends JPanel {
	RenderSurface renderSurface;



	public StrokeFramePanel(RenderSurface renderSurface)
	{
		setLayout(new BorderLayout());
		add(new StrokeControls(renderSurface), BorderLayout.CENTER);
	}
	
	
}




//This is the class that does it all

class StrokeControls extends JPanel implements ActionListener, ChangeListener {
	RenderSurface renderSurface;
	
	
	JPanel choicePanel = new JPanel();
	JPanel bottomPanel = new JPanel();
	
	private DrawPanel drawPanel = new DrawPanel();
	
	JLabel joinL = new JLabel("Join: ");
	JLabel capL = new JLabel("Cap: ");
	JLabel styleL = new JLabel("Style: ");
	JLabel widthL = new JLabel("Width: ");

	JComboBox cap, join, style;
	
	JButton ok, cancel;
	
	JSpinner width = new JSpinner();
	
	String[] caps = { "Cap Butt", "Cap Round", "Cap Square" };
	String[] joins = { "Join Bevel", "Join Miter", "Join Round" };
	String[] styles = { "Solid", "Dashed" };
	
	
	float[] dash = {10f};
	
	
	Stroke stroke;

	
	public StrokeControls(RenderSurface renderSurface)
	{
		
		
		this.renderSurface = renderSurface;
		
		choicePanel.setLayout(new GridLayout(4, 2));
		
		stroke = new BasicStroke(1f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL);
		
		
		setLayout(new BorderLayout());
		
		width.setModel(new SpinnerNumberModel(0.5, 0.25, 100, .25));
		width.addChangeListener(this);
		
		cap = new JComboBox(caps);
		join = new JComboBox(joins);
		style = new JComboBox(styles);
		
		ok = new JButton("Ok");
		cancel = new JButton("Cancel");
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
		cap.addActionListener(this);
		join.addActionListener(this);
		style.addActionListener(this);
		
		choicePanel.add(widthL);
		choicePanel.add(width);
		choicePanel.add(capL);
		choicePanel.add(cap);
		choicePanel.add(joinL);
		choicePanel.add(join);
		choicePanel.add(styleL);
		choicePanel.add(style);
		
		
		
		add(choicePanel, BorderLayout.NORTH);
		add(drawPanel, BorderLayout.CENTER);
	
		
		
		
	}
	

	
			
	
	
	public void stateChanged(ChangeEvent e)
	{
		String value = width.getValue().toString();
		float size = Float.parseFloat(value);
		
		String dashOrSolid = style.getSelectedItem().toString();
		
		if(dashOrSolid == "Dashed")
			stroke = new BasicStroke(size, getCap(), getJoin(), 10.0f, dash, 0.0f);
		else if(dashOrSolid == "Solid")
			stroke = new BasicStroke(size, getCap(), getJoin());
		
		drawPanel.repaint();
		
		if(renderSurface.getSelected() != null)
			renderSurface.getSelected().getModel().setStroke(stroke, size);
		renderSurface.repaint();
		
	}
	
	public void actionPerformed(ActionEvent e)
	{
		String in = width.getValue().toString();
		float size = Float.parseFloat(in);
	
	
	
		String dashOrSolid = style.getSelectedItem().toString();
	
		if(dashOrSolid == "Dashed")
			stroke = new BasicStroke(size, getCap(), getJoin(), 10.0f, dash, 0.0f); 
		else if(dashOrSolid == "Solid")
			stroke = new BasicStroke(size, getCap(), getJoin());
	
		drawPanel.repaint();
		
		if(renderSurface.getSelected() != null)
			renderSurface.getSelected().getModel().setStroke(stroke, size);
		renderSurface.repaint();
	}
	
	
	
	
	
	
	public int getCap()
	{
		if(cap.getSelectedItem().toString() == "Cap Butt")
			return BasicStroke.CAP_BUTT;
		else if(cap.getSelectedItem().toString() == "Cap Round")
			return BasicStroke.CAP_ROUND;
		else if(cap.getSelectedItem().toString() == "Cap Square")
			return BasicStroke.CAP_SQUARE;
		else 
			return BasicStroke.CAP_SQUARE;
	}
	
	
	
	
	
	
	
	
	
	public int getJoin()
	{
		if(join.getSelectedItem().toString() == "Join Bevel")
			return BasicStroke.JOIN_BEVEL;	
		else if(join.getSelectedItem().toString() == "Join Miter")
			return BasicStroke.JOIN_MITER;
		else if(join.getSelectedItem().toString() == "Join Round")
			return BasicStroke.JOIN_ROUND;
		else 
			return BasicStroke.JOIN_MITER;
	}

	
	
	
	class DrawPanel extends JPanel {
		Graphics2D g2;
		
		int w, h;
	
		public DrawPanel()
		{
			super();

	
		}
	
		public void paintComponent(Graphics g)
		{
			super.paintComponent(g);
			
			w = getSize().width;
			h = getSize().height;
			
			g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if(stroke != null)
				g2.setStroke(stroke);
			
			g2.draw(new Line2D.Double(w/2-50, h/2, w/2+50, h/2));
			
			//if(shape instanceof Rectangle2D.Double)
				//g2.draw(new Rectangle2D.Double(w/2-50, h/2-50, 100, 100));
			/*else if(shape instanceof Ellipse2D.Double)
				g2.draw(new Ellipse2D.Double(w/2-50, h/2-50, 100, 100));
			else if(shape instanceof Line2D.Double)
				
			else if(shape == null)
				g2.draw(new Rectangle2D.Double(w/2-50, h/2-50, 100, 100));
				*/
		
		}
	}
	
	
}