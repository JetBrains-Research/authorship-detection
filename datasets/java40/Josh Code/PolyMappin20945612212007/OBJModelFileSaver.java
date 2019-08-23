package fileIO;

import model.*;
import java.io.*;

/**
 * Saves Wavefront Object files
 * 
 * format described:
 * http://www.royriggs.com/obj.html
 * http://people.scs.fsu.edu/~burkardt/data/obj/obj.html
 * http://en.wikipedia.org/wiki/Obj
 * 
 */
public class OBJModelFileSaver implements ModelFileSaver
{
  public boolean matchesFormat(String filename)
  {
      filename = filename.toLowerCase();

      if (filename.endsWith(".obj"))
          return true;
      else
          return false;
  }

    /** Saves the model to the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToWaveFrontOBJ(m, f); 
    }

    public String getDescription()
    {
        return "(*.obj) WaveFront Object files";
    }

    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "obj";
    }

    public boolean savesFaces()
    {
        return true;
    }

    /**
     */
    public void saveToWaveFrontOBJ(Model m,File f) throws IOException 
    {
      FileOutputStream fout = new FileOutputStream(f);
      PrintStream out = new PrintStream(fout);
      int numTriangles = m.getNumberOfTriangles();
      Triangle[] triangles = m.getTriangles();

        // output vectors
      for (int t = 0; t < numTriangles; t++)
      {
          out.println("# vertices for triangle "+(t+1));
          for (int v = 0; v < 3; v++)
          {
              out.println("v " + ModelSaver.getSpacedVectorCoordinates(
                  Triangle.getVertex(triangles[t],v)));
          }
      }

        // output the faces
      for (int t = 0; t < numTriangles; t++)
      {
          int offset = t * 3;
          out.println("f "+(offset)+" "+(offset+1)+" "+(offset+2));  
          // link the face to the indexed vertices
      }
      out.close();
    }

}