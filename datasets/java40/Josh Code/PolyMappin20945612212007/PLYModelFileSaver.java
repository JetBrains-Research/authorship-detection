package fileIO;

import model.*;
import java.io.*;

/**
 * Loads a specific file format
 */
public class PLYModelFileSaver implements ModelFileSaver
{
    public boolean matchesFormat(String filename)
    {
        filename = filename.toLowerCase();

        if (filename.endsWith(".ply"))
            return true;
        else
            return false;
    }

    /** Saves the model to the specified file
     */
    public void saveModel(Model m, File f) throws IOException
    {
        saveToAsciiPLY(m, f);
    }

    public String getDescription()
    {
        return "(*.ply) Polygon files";
    }
    /** returns the extension commonly used by the file format
 */
    public String getCommonExtension()
    {
        return "ply";
    }

    public boolean savesFaces()
    {
        return true;
    }

    public static void saveToAsciiPLY(Model m,File f) throws IOException 
    {
      FileOutputStream fout = new FileOutputStream(f);
      PrintStream out = new PrintStream(fout);
        printAsciiPLYHeader(m,out);
        printAsciiVertices(m,out);
        printTriangleFaces(m, out);
        out.close();
    }

    private static void printAsciiVertices(Model m, PrintStream out) throws IOException
    { 
     int numTriangles = m.getNumberOfTriangles();
     Triangle[] triangles = m.getTriangles();

       // loop through triangles
       for (int ti = 0; ti < numTriangles; ti++)
       {
           // loop through vertices of each triangle
           for (int tv = 0; tv < 3; tv++)
           {
               Vector3D v = Triangle.getVertex(triangles[ti], tv);
               out.print(Vector3D.getSpacedVectorCoordinates(v));
               out.print(" 255 255 255\n"); // make vertex white
           }
       }
    }

    private static void printTriangleFaces(Model m, PrintStream out) throws IOException
    {
      int numTriangles = m.getNumberOfTriangles();

        // loop through triangles
        for (int ti = 0; ti < numTriangles; ti++)
        {
            int offset = ti*3;
            out.print("3 "+(offset)+" "+(offset+1)+" "+(offset+2)+'\n');
        }
    }

    private static void printAsciiPLYHeader(Model m, PrintStream out) throws IOException
    {
        out.print("ply\n");
        out.print("format ascii 1.0\n");
        out.print("comment generator Josh Greig's opensource "
            +"PolyMapper available at http://www.planet-source-code.com"
            +" and http://www.programmersheaven.com\n");
        out.print("element vertex "+(m.getNumberOfTriangles()*3)+'\n');
        out.print("property float x\n");
        out.print("property float y\n");
        out.print("property float z\n");
        out.print("property uchar red\n");
        out.print("property uchar green\n");
        out.print("property uchar blue\n");
        out.print("element face "+m.getNumberOfTriangles()+'\n');
        out.print("property list uchar int vertex_index\n");
        out.print("end_header\n");
    }

}

