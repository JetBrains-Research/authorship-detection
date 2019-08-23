
package UI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class modelViewPanel extends JPanel
{
  private static final int WIRE_FRAME = 0;
  private static final int TEXTURE_MAPPED = 1;
    private static final int WHITE_VERTICES = 2;
    private static final RenderingMode[] renderingModes = getRenderingModes();

  private int displaymode;
 /**
   The model being viewed
 */
 private Model m;
 private RenderingBuffer renderer;


    public static RenderingMode[] getRenderingModes()
    {
        if (renderingModes != null)
            return renderingModes;
        else
        {
            return new RenderingMode[] 
            { new RenderingMode("White Vertices",WHITE_VERTICES),
                 new RenderingMode("Wire",WIRE_FRAME),
              new RenderingMode("Solid Surfaces",TEXTURE_MAPPED)
            };
        }
    }

    public modelViewPanel(int width,int height)
    {
        this(new RenderingBuffer(width,height));
    }

    public modelViewPanel(RenderingBuffer renderer)
    {
        this.renderer = renderer;
        displaymode = WIRE_FRAME;
        updateDimensionsToRenderers();
        addListeners();
    }

    public void setRenderingMode(int dm)
    {
        displaymode = dm;
        render();
    }

    public void scaleVectors(Vector3D v)
    {
        m.scaleVectors(v);
        render();
    }

    private void addListeners()
    {
        ModelViewerMouseListener ml = new ModelViewerMouseListener(this);
        addMouseListener(ml);
        addMouseMotionListener(ml);
    }

    public void setModel(Model m)
    {
        if (m == null)
        {
            System.out.println("modelViewPanel can't set to null model");
            return;
        }
        this.m = m;
        renderer.setModel(m);
        render();
    }

    /** Returns the model being displayed
     */
    public Model getModel()
    {
        return m;
    }

    public void setRenderingBuffer(RenderingBuffer renderer)
    {
        this.renderer = renderer;
        render();
    }

    public void setDimensions(int width, int height)
    {
        renderer.setDimensions(width,height);
        updateDimensionsToRenderers();
        render();
    }

    public void zoomIn()
    {
        renderer.setZoom(renderer.getZoom()*1.1);
        render();
    }

    public void zoomOut()
    {
        renderer.setZoom(renderer.getZoom() / 1.1);
        render();
    }

    private void updateDimensionsToRenderers()
    {
        if (renderer!=null)
          setPreferredSize(new Dimension(renderer.getWidth()
           , renderer.getHeight()));
    }

    public void render()
    {
        if (renderer == null)
        {
            System.err.println(
                "modelViewPanel unable to render because renderer is null.");
            return;
        }
        switch (displaymode)
        { 
            case WIRE_FRAME:
                renderer.renderWireframe();
                break;

            case TEXTURE_MAPPED:
                renderer.realtimeTextureRender();
                break;

            case WHITE_VERTICES:
                renderer.renderWhiteVertices();
                break;

            default:
                System.err.println("Invalid display mode");
                break;
        }
        repaint();
    }

    public void paint(Graphics g)
    {
        if (renderer == null)
        {
            System.err.println(
                "modelViewPanel unable to paint "
                +"because renderer is null.");
            return;
        }
        Image img = renderer.getImage();

        if (img!=null)
           g.drawImage(img, 0, 0, null);
    }

    /** Returns the viewpoint the panel is showing the model from
     */
    public Vector3D getViewPoint()
    {
        return renderer.getViewPoint();
    }

    public void setViewPoint(Vector3D newViewPoint)
    {
        renderer.setViewPoint(newViewPoint);
        render();
    }

  /**
   Renders just the outlines of the triangles
   */
  public void renderWire()
  {
      renderer.renderWireframe();
  }

  public void rotateModel(Vector3D rotationVector)
  {
      if (m != null)
      {
          m.rotateModel(rotationVector);
          render();
      }
  }

  public int countTriangles()
  {
      if (m == null)
          return 0;

      return m.getNumberOfTriangles();  
  }

    public BufferedImage getDisplayImage()
    {
        return renderer.getImage();
    }
}

