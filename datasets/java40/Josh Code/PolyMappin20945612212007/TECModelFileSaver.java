package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a TEC format used mostly by the TECPLOT program
 * 
 * described:
 * http://people.scs.fsu.edu/~burkardt/data/tec/tec.html
 * 
 * Tecplot software available at:
 * http://www.tecplot.com/
 * 
 */
public class TECModelFileSaver implements ModelFileSaver
{
    public boolean matchesFormat(String filename)
    {
        filename = filename.toLowerCase();

        if (filename.endsWith(".tec"))
            return true;
        else
            return false;
    }

    /** Saves the model to the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToTEC(m, f);
    }

    public String getDescription()
    {
        return "(*.tec) Tecplot files";
    }

    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "tec";
    }

    public boolean savesFaces()
    {
        return true;
    }


    public static void saveToTEC(Model m, File f) throws IOException
    {
        PrintStream out = new PrintStream(new FileOutputStream(f));
        Triangle[] triangles = m.getTriangles();
        int numTriangles = m.getNumberOfTriangles();
        int numVertices = numTriangles * 3;
        out.println("TITLE = \"cube.tec created by IVREAD.\"");
        out.println("VARIABLES = \"X\", \"Y\", \"Z\"");
        out.println("ZONE N = " + numVertices + ", E = " + numTriangles
            + ", DATAPACKING = POINT, ZONETYPE = FETRIANGLE");

        // loop through triangles
        for (int i = 0; i < numTriangles; i++)
        {
            // loop through the triangle's vertices
            for (int vi = 0; vi < 3; vi++)
            {
                Vector3D v = Triangle.getVertex(triangles[i], vi);
                out.println(Vector3D.getSpacedVectorCoordinates(v));
            }
        }
        // loop through triangles
        for (int i = 0; i < numTriangles; i++)
        {
            int offset = i * 3;
            out.println(offset+" "+(offset+1)+" "+(offset+2));
        }
    }


}

