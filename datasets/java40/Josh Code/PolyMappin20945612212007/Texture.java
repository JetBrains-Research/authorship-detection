package model;

import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

public class Texture
{
  /** the texture image this refers to
   */
 private BufferedImage mainBuffer;
 private byte[] mainPixelData;

  /**
   * the place all rendering is to be done on
   */
 private RenderingBuffer resultBuf;


    /** renders into the specified triangle
     */
    public void renderTriangle(Point p1, Point p2, Point p3)
    {
      // sort the points
      Point temp = p1;
        /* The following statements are basically following the bubble sort algorithm for 3 elements
         * Avoiding overhead from loops makes this more efficient, though.
         */
      if (temp.y > p2.y)
      {
          p1 = p2;
          p2 = temp;
      }
      if (temp.y > p3.y)
      {
          p2 = p3;
          p3 = temp;
      }
      if (p1.y > p2.y)
      {
          temp = p1;
          p1 = p2;
          p2 = temp;
      }

        // render top part of triangle
    //  renderTriangleTop(p1,p2,);

        // render bottom part of triangle

    }

    /** loops through the top part of a triangle
     */
    private void renderTriangleTop(Point p1, Point p2, double slope)
    { 
    
    }

    /** loops through the bottom part of a triangle
     */
    private void renderTriangleBottom(Point p1, Point p2, double slope)
    { 
       
    }

    /**
     */
    public void setRenderDestination(RenderingBuffer resultBuf)
    {
       this.resultBuf = resultBuf;
    }

    /** sets the texture image
     */
    public void setImage(Image img)
    {
        mainBuffer = new BufferedImage(img.getWidth(null),img.getHeight(null)
            ,BufferedImage.TYPE_3BYTE_BGR);
        Graphics g = mainBuffer.getGraphics();
         g.drawImage(img, 0, 0, null);
         mainPixelData = (byte[])((DataBufferByte)mainBuffer.getRaster().getDataBuffer())
             .getData();
    }

    public Image getImage()
    { 
       return mainBuffer;
    }

}


