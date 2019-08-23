
package UI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

public class modelViewer extends JFrame
    implements ComponentListener
{
/**
  The model being viewed
*/
  private Model model;

  /** a JPanel for viewing the model */
  private modelViewPanel display;

    public modelViewer()
    {
        super("Model Viewer");
        display = new modelViewPanel(600, 600);
        ModelViewerJMenuBar menu = new ModelViewerJMenuBar(this);
        setJMenuBar(menu);
        addKeyListener(menu);
        Container c = getContentPane();
        c.setLayout(new BorderLayout());
        c.add(display,BorderLayout.CENTER);

        setSize(400,400);
        setVisible(true);
        addComponentListener(this);
        menu.openFile();
    }

    public void setModel(Model m)
    {
        display.setModel(m);
    }

    public Model getModel()
    {
        return display.getModel();
    }

    public void scaleVectors(Vector3D v)
    {
        display.scaleVectors(v);
    }

    public void setRenderingMode(int dm)
    {
        display.setRenderingMode(dm);
    }

    public int countTriangles()
    {
        return display.countTriangles();
    }

    public BufferedImage getDisplayImage()
    {
        return display.getDisplayImage();
    }

    public JPanel getDisplayPanel()
    {
        return display;
    }

    public void zoomIn()
    {
        display.zoomIn();
    }

    public void zoomOut()
    {
        display.zoomOut();
    }

   public void componentHidden(ComponentEvent e)
   {}

   public void componentMoved(ComponentEvent e)
   {}

   public void componentResized(ComponentEvent e)
   {
       display.setDimensions(getWidth(),getHeight());
   }
    public void componentShown(ComponentEvent e) { } 
}








