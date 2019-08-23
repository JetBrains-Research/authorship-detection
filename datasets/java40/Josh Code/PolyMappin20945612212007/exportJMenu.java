package UI;

import fileIO.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

/** 
 * A menu for exporting models or parts of models
 */
public class exportJMenu extends JMenu implements ActionListener
{ 
 private JFileChooser chooser;
 private JMenuItem exportWireFrame;
 private JMenuItem exportVertices;
 private JMenuItem exportModel;
 private modelViewer mv;

    public exportJMenu(modelViewer mv)
    {
       super("Export");
       this.mv = mv;
        chooser = new JFileChooser();
        ModelViewerJMenuBar.gotoInitialDirectory(chooser);
        exportWireFrame = new JMenuItem("Wire Frame");
        exportVertices = new JMenuItem("Vertices");
        exportModel = new JMenuItem("Model");

        add(exportVertices);
        add(exportWireFrame);
        add(exportModel);

       addListeners();
    }

    private void addListeners()
    {
       // add the listeners here 
        exportWireFrame.addActionListener(this);
        exportVertices.addActionListener(this);
        exportModel.addActionListener(this);
    }

    public void actionPerformed(ActionEvent ae)
    {
        Object source = ae.getSource();
        ModelFileSaverFileFilter mfsff = null;
        chooser.resetChoosableFileFilters();

        if (source == exportVertices)
        {
            mfsff = (new ModelFileSaverFileFilter(new XYZModelFileSaver()));
        }
        else if (source == exportWireFrame)
        {
            mfsff = (new ModelFileSaverFileFilter(new VLAModelFileSaver()));
        }
        else if (source == exportModel)
        { 
           // add filters for all the formats supporting faces
            LinkedList<ModelFileSaverFileFilter> modelFileFilters = 
                ModelSaver.getCompleteModelFileSaverFileFilters();

            boolean firstSet=false;
            for (ModelFileSaverFileFilter m: modelFileFilters)
            {
                if (firstSet)
                {
                   chooser.addChoosableFileFilter(m);                
                }
                else
                {
                   chooser.setFileFilter(m);
                    firstSet = true;               
                }
            }
        }
        if (mfsff != null)
            chooser.setFileFilter(mfsff);

      // chooser.setFilter(); 
       // set filter to appropriate file types              
     int response = chooser.showSaveDialog(mv);

      if (response == JFileChooser.APPROVE_OPTION)
      {
         // save the model
          File f = chooser.getSelectedFile();
          try
          {
              String filename = f.getAbsolutePath();
              if (mfsff!=null)
                filename = mfsff.setFileNameExtension(filename);
              else
              {
                  javax.swing.filechooser.FileFilter ff = chooser.getFileFilter();
                  if (ff instanceof ModelFileSaverFileFilter)
                  {
                     filename = ((ModelFileSaverFileFilter)ff).setFileNameExtension(filename);
                  }
              }
              f = new File(filename);
              ModelSaver.saveTo(mv.getModel(),f);
          }
          catch (Throwable t)
          {
              t.printStackTrace();
              JOptionPane.showMessageDialog(mv,"Unable to save properly.");
          }
      }
    }

}