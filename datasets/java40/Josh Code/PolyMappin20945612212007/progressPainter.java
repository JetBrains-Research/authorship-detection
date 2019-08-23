package UI;

import fileIO.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** Something to help the user see the progress while loading large files
 */
public class progressPainter implements loadProgressListener,ComponentListener
{
 private double progress=0;
 private double progressAtLastPaint=-1;
 private String msg;
 private int width, height;
 private JComponent c;
 private boolean completed;

    public progressPainter(JComponent c)
    {
        this.c = c;
        progress = 0.0;
        msg = null;
        width = 100;
        height = 100;
        completed = false;
        c.addComponentListener(this);
        setDimensions(c.getWidth(),c.getHeight());
    }

    public void loadStatusUpdate(double progress, String msg)
    {
        if (progress>=0)
          this.progress = progress;

        if (msg!=null)
           this.msg = msg;

        completed=false;

        if (progress-progressAtLastPaint>0.01 || msg!=null)
           repaint();
    }

    public void loadComplete()
    {
        completed = true;
        progress = 1.0;
        repaint();
    }

    public void setDimensions(int width, int height)
    {
        this.width = width;
        this.height = height;
        repaint();
    }

    public void repaint()
    {
        if (c!=null)
          paint(c.getGraphics());
    }

    public void paint(Graphics g)
    {
        if (g == null) return;
        if (progress < 0 && msg == null)
            return;

        progressAtLastPaint = progress;
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        int top=height/2-10;
        if (completed)
            msg = "Completely loaded!";
        if (msg!=null)
        {
           Font font = g.getFont();
           FontMetrics f = g.getFontMetrics(font);
           int size = f.stringWidth(msg);
           int left = (width-size)/2;
             g.drawString(msg,left,top);
             top += 20;
        }
        if (width>20 && !completed && progress>0)
        {
          int sidePadding = (int)((width-20)*(1-progress)/2);
            g.drawRect(10, top, width - 20, 20);
            g.fillRect(10 + sidePadding, top, width - 20 - sidePadding, 20);
        }
        if (completed)
        {
            msg = null;
            progress = -1;
        }
    }

   public void componentHidden(ComponentEvent e)
   {}

   public void componentMoved(ComponentEvent e)
   {}

   public void componentResized(ComponentEvent e)
   {
      setDimensions(e.getComponent().getWidth(),e.getComponent().getHeight());
   }

   public void componentShown(ComponentEvent e) 
   {}
}


