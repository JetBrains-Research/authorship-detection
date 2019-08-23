package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a specific file format
 */
public class STLModelFileSaver implements ModelFileSaver
{
  public boolean matchesFormat(String filename)
  {
      filename = filename.toLowerCase();

      if (filename.endsWith(".stl")||filename.endsWith(".stla"))
          return true;
      else
          return false;
  }

  /** Saves the model to the specified file
   */
    public void saveModel(Model m, File f) throws IOException
    {
       saveToSTL(m,f); 
    }

    public String getDescription()
    {
        return "(*.stl) Stereolithography";
    }

    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "stl";
    }

    public boolean savesFaces()
    {
        return true;
    }

  /** stereolithography files
   * format described at: http://orion.math.iastate.edu/burkardt/data/stl/stl.html
   */
    public static void saveToSTL(Model m,File f) throws IOException
    {
        FileOutputStream fout = new FileOutputStream(f);
        PrintStream out = new PrintStream(fout);
        int numTriangles = m.getNumberOfTriangles();
        Triangle []triangles = m.getTriangles();

        // loop through triangles
        for (int i = 0; i < numTriangles; i++)
        {
            Vector3D normal = triangles[i].getNormal();
            String solidName = "triangle" + i;
            out.println("solid " + solidName);
            out.println(" facet normal "
                + ModelSaver.getSpacedVectorCoordinates(normal));
            out.println("outer loop");
            for (int vi = 0; vi < 3; vi++)
            {
                Vector3D vertex = Triangle.getVertex(triangles[i],vi);
                out.println("   vertex" + ModelSaver.getSpacedVectorCoordinates(vertex));
            }
            out.println("  endloop");
            out.println(" endfacet");
            out.println("endsolid " + solidName);
        }

        fout.close();
    }

}