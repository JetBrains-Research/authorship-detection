package fileIO;

import model.*;
import java.io.*;
import java.util.*;

/** a class for saving models
 */
public class ModelSaver
{
 private static LinkedList<ModelFileSaver> mfSavers = getModelFileSavers();
 private Model m;

    private static LinkedList<ModelFileSaver>  getModelFileSavers()
    {
      LinkedList<ModelFileSaver> savers = new LinkedList<ModelFileSaver>();

        savers.add(new OBJModelFileSaver());
        savers.add(new STLModelFileSaver());
        savers.add(new VRMLModelFileSaver());
        savers.add(new TECModelFileSaver());
        savers.add(new PLYModelFileSaver());
        savers.add(new VLAModelFileSaver());
        savers.add(new XYZModelFileSaver());
        return savers;
    }

    public ModelSaver(Model m)
    {
        this.m = m;
    }

    /** returns true iff successful
     */
    public static boolean saveTo(Model m,File f) throws IOException
    {
       if (m==null || f==null)
            return false;

       for (ModelFileSaver mfs: mfSavers)
       {
          if (mfs.matchesFormat(f.getName()))
          {
              mfs.saveModel(m,f);
              return true;
          }
       }
       return false;
    }

    public static LinkedList<ModelFileSaverFileFilter> getCompleteModelFileSaverFileFilters()
    { 
     LinkedList<ModelFileSaverFileFilter> result = new LinkedList<ModelFileSaverFileFilter>();

       for (ModelFileSaver m: mfSavers)
       {
          if (m.savesFaces())
              result.add(new ModelFileSaverFileFilter(m));
       }
        return result;
    }

    public static String getSpacedVectorCoordinates(Vector3D v)
    {
        return " " + Vector3D.getSpacedVectorCoordinates(v);
    }
}