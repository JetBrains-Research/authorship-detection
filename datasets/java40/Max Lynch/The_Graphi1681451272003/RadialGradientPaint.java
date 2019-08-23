package com.gs.paint;


import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;



/**
 * This class provides a way to fill a shape with a radial color gradient
 * pattern.
 * Given an Ellipse and 2 Colors, color1 and color2, the paint will render 
 * a color starting at color1 at the center of the Ellipse to color2 at 
 * its boundary. All pixels lying outside the Ellipse boundary have the
 * color2 value.
 *
 * @author Vincent Hardy
 * @version 1.0, 09.11.1998
 */
public class RadialGradientPaint implements Paint {
  /* The Ellipse controlling colors */
  Color color1, color2;

  /** The Ellipse bounds */
  Rectangle2D.Float gradientBounds;

  /** Transparency */
  int transparency;

  /**
   * @return center gradient color
   */
  public Color getCenterColor(){
    return color1;
  }

  /**
   * @return boundary color
   */
  public Color getBoundaryColor(){
    return color2;
  }

  /**
   * @return gradient bounds
   */
  public Rectangle2D getBounds(){
    return (Rectangle2D)gradientBounds.clone();
  }

  /**
   * @param bounds the bounds of the Ellipse defining the gradient. User space.
   * @param color1 Color at the ellipse focal points.
   * @param color2 Color at the ellipse boundary and beyond.
   */
  public RadialGradientPaint(Rectangle2D bounds, Color color1, Color color2) {
    this.color1 = color1;
    this.color2 = color2;
    this.gradientBounds = new Rectangle2D.Float();
    gradientBounds.setRect((float)bounds.getX(), (float)bounds.getY(), 
			   (float)bounds.getWidth(), (float)bounds.getHeight());

    int a1 = color1.getAlpha();
    int a2 = color2.getAlpha();
    transparency = (((a1 & a2) == 0xff) ? OPAQUE : TRANSLUCENT);
  }
  
  /**
   * Creates and returns a context used to generate the color pattern.
   */
  public PaintContext createContext(ColorModel cm,
				    Rectangle deviceBounds,
				    Rectangle2D userBounds,
				    AffineTransform transform,
				    RenderingHints hints) {
    try{
      return (new RadialGradientPaintContext(gradientBounds, color1, color2, transform));
    }catch(NoninvertibleTransformException e){
      throw new IllegalArgumentException("transform should be invertible");
    }
  }

  /**
   * Return the transparency mode for this GradientPaint.
   * @see Transparency
   */
  public int getTransparency() {
    return transparency;
  }


}
