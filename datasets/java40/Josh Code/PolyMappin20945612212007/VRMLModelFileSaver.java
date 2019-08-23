package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a specific file format
 */
public class VRMLModelFileSaver implements ModelFileSaver
{
    public boolean matchesFormat(String filename)
    {
        filename = filename.toLowerCase();

        if (filename.endsWith(".wrl")||filename.endsWith(".vrml"))
            return true;
        else
            return false;
    }

    /** Saves the model to the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToVRML(m, f);
    }

    public String getDescription()
    {
        return "(*.wrl) Virtual Reality Modeling Language files";
    }

    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "wrl";
    }

    public boolean savesFaces()
    {
        return true;
    }

    /** Saves the model to a VRML file
     */
  public static void saveToVRML(Model m,File f) throws IOException
  {
    FileOutputStream fout = new FileOutputStream(f);
    PrintStream out = new PrintStream(fout);
    int numTriangles = m.getNumberOfTriangles();
    Triangle []triangles = m.getTriangles();

    out.println("#VRML V2.0 utf8");
    // loop through triangles
    for (int i = 0; i < numTriangles; i++)
    {
        out.println("Triangle {");

        for (int vi = 0; vi < 3; vi++)
        {
            Vector3D vertex = Triangle.getVertex(triangles[i],vi);
            out.println("   vertex" + ModelSaver.getSpacedVectorCoordinates(vertex));
        }

        out.println("}");
    }
    fout.close();
  }
 
}