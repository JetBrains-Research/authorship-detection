package fileIO;

import javax.swing.*;
import java.io.*;
import javax.swing.filechooser.*;

/** a FileFilter that wraps around a ModelFileSaver to accept only the files it saves with
 */
public class ModelFileSaverFileFilter extends javax.swing.filechooser.FileFilter
{
    private ModelFileSaver mfs;

    public ModelFileSaverFileFilter(ModelFileSaver mfs)
    { 
       this.mfs = mfs;
    }

    public boolean accept(File f)
    {
        if (f == null)
            return false;

        return mfs.matchesFormat(f.getName());
    }

    public String getDescription()
    {
        return mfs.getDescription();
    }

    public static String getFileNameExtension(String filename)
    {
        int index = filename.lastIndexOf('.');

        if (index > 0)
            return filename.substring(index+1);
        else
            return null;
    }

    /** Sets the filename's extension to one matching what this filefilter is for
     */
    public static String setFileNameExtension(String filename, String newExtension)
    {
        int index = filename.lastIndexOf('.');

        if (index > 0)
            filename = filename.substring(0, index);

        filename = filename + "." + newExtension;

        return filename;
    }

    public String setFileNameExtension(String filename)
    {
        String newExtension = mfs.getCommonExtension();
        return setFileNameExtension(filename, newExtension);
    }
}