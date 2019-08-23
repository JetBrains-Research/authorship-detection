/* GraphicsFrame.java : You could refer to this class as the backbone 
 * of the GraphicsStudio.  Its got all the things a little 
 * boy could ever dream of.  And then some. */



package com.gs.ui;


import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;

import com.gs.shape.*;
import com.gs.paint.*;

public class GraphicsFrame {
	
	private static GraphicsFrame _instance = null;
	
	
	//private final MenuToolbar menuToolbar = new MenuToolbar(this);
	
	private final RenderSurface renderSurface = new RenderSurface(this);
	
	private final GraphicsToolbar toolbar = new GraphicsToolbar(this);

	private final FrameDesktop frameDesktop = new FrameDesktop(this);
	
	private final MenuMan menuMan = new MenuMan(this);
	
	private final RenderMediator renderMediator = new RenderMediator(this);
	
	private final StatusBar statusBar = new StatusBar(this);
	
	private final JFrame FRAME;
	
	
	public GraphicsFrame()
	{
		FRAME = new JFrame(); //Main Frame in application
	
		
		Container c = FRAME.getContentPane(); //ContentPane of Frame, to add components, etc.
		
		
		c.setLayout(new BorderLayout()); //BorderLayout Please, thank you
		
		statusBar.setPreferredSize(new Dimension(FRAME.WIDTH, 20)); //Make the status bar small enough
		
		/* Add the Three main components to the frame:
		 * The Toolbar, the DesktopPane and
		 * The staus bar */
		 
		c.add(toolbar, BorderLayout.NORTH);
		//c.add(testbar, BorderLayout.NORTH);
		c.add(frameDesktop, BorderLayout.CENTER);
		c.add(statusBar, BorderLayout.SOUTH);	
		
		
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //Scree
		
		int sw = screenSize.width;
		int sh = screenSize.height;
		Image icon = Toolkit.getDefaultToolkit().getImage("gs.gif");
		FRAME.setIconImage(icon);
		FRAME.setJMenuBar(menuMan);
		FRAME.pack();
		FRAME.setSize(sw-100, sh-100);
		frameDesktop.repositionWindows(FRAME.getSize());
		FRAME.setTitle("Graphics Studio v0.1");
		FRAME.show();
		
		FRAME.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//The Following Commented out section will reposition the internal frames
		//When the main frame is resized
		
		/*FRAME.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				frameDesktop.repositionWindows(FRAME.getSize());
			}
		} );*/		
		
	}


	public static synchronized GraphicsFrame instance()
	{
		if(_instance == null)
		{
			_instance = new GraphicsFrame();
		}
		return _instance;
	}
	
	
	public GraphicsFrame getGraphicsFrame()
	{
		return this;
	}
	
	
	public void removeAll()
	{
		renderMediator.removeAll();
		
	}
	
	public void moveShapeToFront()
	{
		renderMediator.moveShapeToFront();
	}
	 
	 
	public void setBackground(Color color) {
		renderMediator.setBackground(color);
	}
	
	public void setBackground(GradientPaint gp) {
		renderMediator.setBackground(gp);
	}
	
	public void setBackground(RadialGradientPaint rgp) {
		renderMediator.setBackground(rgp);
	}
	
	public StatusBar getStatusBar() {
		return (statusBar);
	}
		
	public RenderSurface getRenderSurface() {
		return (renderSurface); 
	}

	public GraphicsToolbar getToolbar() {
		return (toolbar); 
	}

	public FrameDesktop getFrameDesktop() {
		return (frameDesktop); 
	}

	public MenuMan getMenuMan() {
		return (menuMan); 
	}
	
	public RenderMediator getRenderMediator() {
		return (renderMediator); 
	}

	public JFrame getFrame() {
		return (FRAME); 
	}
	
	
	
	public void addShape(ShapeLayerModel model)
	{
		renderMediator.addShape(model);
	}
	
	public void addImageShape(Image img, ShapeLayerModel model)
	{
		model.setImage(img);
		renderSurface.addShape(model);
	}

	
}
		