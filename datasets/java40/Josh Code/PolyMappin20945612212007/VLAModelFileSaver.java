package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a VLA format
 * 
 * described:
 * http://people.scs.fsu.edu/~burkardt/data/vla/vla.html
 * 
 * This will only store vertex edges or line segments and not facial or surface information. 
 * That is why this should only be used for exporting wireframe but not loading models.
 */
public class VLAModelFileSaver implements ModelFileSaver
{
    public boolean matchesFormat(String filename)
    {
        filename = filename.toLowerCase();

        if (filename.endsWith(".vla"))
            return true;
        else
            return false;
    }

    /** Saves the model to the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToVLA(m, f);
    }

    public String getDescription()
    {
        return "(*.vla) Vector Line Array - no faces";
    }

    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "vla";
    }

    public boolean savesFaces()
    {
        return false;
    }


    public static void saveToVLA(Model m, File f) throws IOException
    {
      PrintStream out = new PrintStream(new FileOutputStream(f));
      Triangle[] triangles = m.getTriangles();
      int numTriangles = m.getNumberOfTriangles();

        out.println("set intensity EXPLICIT");
        out.println("set parametric NON_PARAMETRIC");
        out.println("set filecontent LINES");
        out.println("set filetype NEW");
        out.println("set depthcue 0");
        out.println("set defaultdraw stellar");
        out.println("set coordsys RIGHT");
        out.println("set library_id UNKNOWN");
        out.println("set comment generated with Josh Greig's opensource PolyMapper");
        out.println("set comment available at: http://www.planet-source-code.com");
        out.println("set comment also at: http://www.programmersheaven.com");

        // loop through triangles
        for (int i = 0; i < numTriangles; i++)
        { 
           // loop through vertices
            for (int vi = 1; vi <= 3; vi++)
            {
                Vector3D v1 = Triangle.getVertex(triangles[i], vi-1);
                Vector3D v2 = Triangle.getVertex(triangles[i], vi%3);
                out.println("; triangle "+i+", edge "+vi);
                out.println("P "+Vector3D.getSpacedVectorCoordinates(v1));
                out.println("L " + Vector3D.getSpacedVectorCoordinates(v2));
            }
        }
        out.close();
    }

}