package fileIO;

import model.*;
import java.io.*;
import java.util.*;

/** 
 * format described at:
 * http://www.fileformat.info/format/wavefrontobj/
 * http://people.scs.fsu.edu/~burkardt/txt/obj_format.txt
 * 
 * 
 */
public class OBJModelFileLoader implements ModelFileLoader
{
    public boolean matchesFormat(File f)
    {
        String name = f.getName();
        name = name.toLowerCase();

        if (name.endsWith(".obj") || name.endsWith(".mod"))
            return true;
        else
            return false;
    }

    /** loads from the specific file
     */
    public void loadModel(Model m, File f, loadProgressListener progressListener) throws IOException
    {
        loadFromWaveFrontOBJ(m, f,progressListener);
    }

    public String getDescription()
    {
        return "(*.obj;*.mod) WaveFront Object files";
    }

    /** Loads from a WaveFront OBJ file
     */
    public static Model loadFromWaveFrontOBJ(Model m, File f, loadProgressListener progressListener) throws IOException
    {
        // check if this is binary or ascii

        // if ascii
        return PLYModelFileLoader.loadFromAsciiPolygonFile(m, f,progressListener);
    }

}