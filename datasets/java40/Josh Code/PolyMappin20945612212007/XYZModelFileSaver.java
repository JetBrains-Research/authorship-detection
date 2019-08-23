package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a XYZ format
 * 
 * described:
 * http://people.scs.fsu.edu/~burkardt/data/xyz/xyz.html
 * 
 * This will only store vertices and not facial or surface information. 
 * That is why this should only be used for exporting and not loading models.
 */
public class XYZModelFileSaver implements ModelFileSaver
{
    public boolean matchesFormat(String filename)
    {
        filename = filename.toLowerCase();

        if (filename.endsWith(".xyz"))
            return true;
        else
            return false;
    }

    /** saves the model from the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToXYZ(m, f);
    }

    public String getDescription()
    {
        return "(*.xyz) Table files";
    }

    /** returns the extension commonly used by the file format
     */
    public String getCommonExtension()
    {
        return "xyz";
    }

    public boolean savesFaces()
    {
        return false;
    }

    public static void saveToXYZ(Model m, File f) throws IOException
    {
        PrintStream out = new PrintStream(new FileOutputStream(f));
        Triangle[] triangles = m.getTriangles();
        int numTriangles = m.getNumberOfTriangles();

        out.println();
        out.println("# generated with Josh Greig's opensource PolyMapper");
        out.println("# available at:");
        out.println("# http://www.planet-source-code.com");
        out.println("# http://www.programmersheaven.com");
        out.println();

        // loop through triangles
        for (int i = 0; i < numTriangles;i++ )
        {
            // loop through vertices of each triangle
            for (int vi = 0; vi < 3; vi++)
            { 
               Vector3D v = Triangle.getVertex(triangles[i],vi);
               out.println(Vector3D.getSpacedVectorCoordinates(v));
            }
            out.println(); 
             // just leave an empty line to help group the vertices
        }

        out.close();
    }


}





