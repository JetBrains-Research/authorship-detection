
package UI;

import model.*;
import java.awt.event.*;
import java.awt.*;


public class ModelViewerMouseListener
    implements MouseListener,MouseMotionListener
{
 private Point prevPoint;
 private modelViewPanel panel;
 private double sensetivity = 5;

    public ModelViewerMouseListener(modelViewPanel panel)
    {
        this.panel = panel;
    }

  public void mouseClicked(MouseEvent e)
  {}

  public void mouseEntered(MouseEvent e)
  {}

  public void mouseExited(MouseEvent e)
  {}

  public void mousePressed(MouseEvent e)
  {
      prevPoint = new Point(e.getX(),e.getY());
  }

  public void mouseReleased(MouseEvent e)
  {}

    public void mouseDragged(MouseEvent e)
    {
        if (prevPoint != null)
        {
            Vector3D v = panel.getViewPoint();
            double dx = e.getX() - prevPoint.x, dy = e.getY() - prevPoint.y;
            double vx = Vector3D.getX(v);
            double vy = Vector3D.getY(v);
            double vz = Vector3D.getZ(v);

            if (e.isControlDown())
            {
                vx += sensetivity*dx;
                vz += sensetivity*dy; 
            }
            else if (e.isAltDown())
            { 
             Vector3D rotationVector;
                // if 'z' is down, 
             // rotationVector = Vector3D.getFromCoordinates(0,dx*0.01,dy*0.01);
                // else
                rotationVector = Vector3D.getFromCoordinates(dx*0.01,dy*0.01,0);
                panel.rotateModel(rotationVector);
            }
            else
            {
                vx += sensetivity*dx;
                vy += sensetivity*dy;
            }
            panel.setViewPoint(Vector3D.getFromCoordinates(vx,vy,vz));
        }
        prevPoint = new Point(e.getX(),e.getY());

    }

   public void mouseMoved(MouseEvent e)
   {}



}

