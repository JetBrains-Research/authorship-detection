package fileIO;

import model.*;
import java.io.*;
import java.util.*;


/** for loading Models from file
 */
public class ModelLoader
{
 private static final LinkedList<ModelFileLoader> loaders = getModelFileLoaders();

    /** Returns a list of ModelFileLoader objects
     */
    public static LinkedList<ModelFileLoader> getModelFileLoaders()
    {
      LinkedList<ModelFileLoader> mloaders = new LinkedList<ModelFileLoader>();

        /* The following could be done by checking 
         * the package folder for class files and looping through ones 
         * that implement ModelFileFilter.
        */

        mloaders.add(new STLModelFileLoader());
        mloaders.add(new PLYModelFileLoader());
        mloaders.add(new OBJModelFileLoader());
        mloaders.add(new VRMLModelFileLoader());
        return mloaders;
    }

    public static Model load(File f, loadProgressListener progressListener) throws IOException
    {
        try
        {
            progressListener.loadStatusUpdate(0,"Loading "+f.getName());
            Model m= load(new Model(), f,progressListener);
            progressListener.loadComplete();
            return m;
        }
        catch (Throwable t)
        {
            progressListener.loadComplete();
            t.printStackTrace();
            return null;
        }
    }
    
    public static Model load(Model m,File f,loadProgressListener progressListener) throws IOException
    {
        try
        {
        for (ModelFileLoader mfl: loaders)
        { 
           if (mfl.matchesFormat(f))
           { 
               mfl.loadModel(m,f,progressListener);
               return m;
           }
        }


        System.err.println("unknown file format");
        }
        catch (Throwable t)
        {
           t.printStackTrace();

        }
        return null;
    }

    /** Checks if the specified file should be loadable
     */
    public static boolean isLoadableModelFile(File f)
    { 
        for (ModelFileLoader mfl: loaders)
           if (mfl.matchesFormat(f))
               return true;
    
        return false;
    }

    public static String removePoundLineCommentAndTrim(String ln) 
    {
        if (ln == null)
            return null;
        int index1 = ln.indexOf('#'); // comment character
         if (index1 == 0)
             return "";
         else if (index1 >= 1)
             ln = ln.substring(0, index1);

         return ln.trim();
    }

}




