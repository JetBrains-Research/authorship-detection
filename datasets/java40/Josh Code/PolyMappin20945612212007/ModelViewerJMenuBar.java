
package UI;

import model.*;
import fileIO.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.*;
import java.awt.*;
import java.awt.image.*;

public class ModelViewerJMenuBar extends JMenuBar
    implements KeyListener
{
 private static javax.swing.filechooser.FileFilter imageFilter = new ImageFileFilter();
 private modelViewer mv;
 private JMenu fileM;
 private JMenuItem fileOpen;
 private JMenuItem zoomIn;
 private JMenuItem zoomOut;
 private JMenu exportMenu;
 private JMenuItem saveDisplay; 
 private JFileChooser chooser;
    private progressPainter progressP;

    public ModelViewerJMenuBar(modelViewer mv)
    {
        chooser = new JFileChooser();
        gotoInitialDirectory();
        progressP = new progressPainter(mv.getDisplayPanel());

        this.mv = mv;
        fileM = new JMenu("File");
        JMenu view = new JMenu("View");
        JMenu modelMenu = new ModelJMenu(mv);
        JList renderModes = new RenderModeJList(mv);
        JMenu viewType = new JMenu("Viewing Mode");
        exportMenu = new exportJMenu(mv);
        viewType.add(renderModes);
        view.add(viewType);
        saveDisplay = new JMenuItem("Save Display");

        zoomIn = new JMenuItem("Zoom In");
        zoomIn.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_PLUS, ActionEvent.CTRL_MASK));

        zoomOut = new JMenuItem("Zoom Out");
        zoomOut.setAccelerator(KeyStroke.getKeyStroke(
                  KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
        view.add(zoomIn);
        view.add(zoomOut);

        fileOpen = new JMenuItem("Open");
        fileM.add(fileOpen);
        fileM.add(exportMenu);
        fileM.add(saveDisplay);
        add(fileM);
        add(view);
        add(modelMenu);

        addListeners();
    }

    private void addListeners()
    {
        fileOpen.addActionListener(
            new ActionListener()
         {
             public void actionPerformed(ActionEvent ae)
             {
                 openFile();
             }
         }
        );

         zoomIn.addActionListener(
               new ActionListener()
         {
             public void actionPerformed(ActionEvent ae)
             {
                 zoomIn();
             }
         }
           );
         zoomOut.addActionListener(
               new ActionListener()
         {
             public void actionPerformed(ActionEvent ae)
             {
                 zoomOut();
             }
         }
           );

         saveDisplay.addActionListener(
               new ActionListener()
         {
             public void actionPerformed(ActionEvent ae)
             {
                 saveDisplayImage();
             }
         }
       );
   }

    private void gotoInitialDirectory()
    {
        gotoInitialDirectory(chooser);
    }

    public static void gotoInitialDirectory(JFileChooser chooser)
    {
        try
        {
            File f = new File("./models");
            chooser.setCurrentDirectory(f);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
    }

    private void zoomIn()
    {
        mv.zoomIn();    
    }

    private void zoomOut()
    {
        mv.zoomOut();
    }

    public void openFile()
    {
        chooser.setFileFilter(new loadableModelFileFilter());

        // ...           
        int response = chooser.showOpenDialog(mv);
        if (response == JFileChooser.APPROVE_OPTION)
        {
            File f = chooser.getSelectedFile();
            try
            {
                Model m = fileIO.ModelLoader.load(f, progressP);
                if (m != null)
                    mv.setModel(m);
                else
                    JOptionPane.showMessageDialog(mv,
                        "Unable to load the selected file. "
                        + " The format may not be supported.");
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
                JOptionPane.showMessageDialog(mv,
                    "Unable to load the selected file.");
            }
        }
    }

    /** saves the display
     */
    private void saveDisplayImage()
    {
        chooser.resetChoosableFileFilters();
        chooser.setFileFilter(imageFilter);
        int response = chooser.showSaveDialog(mv);

        if (response==JFileChooser.APPROVE_OPTION)
        {
            
            try
            {
              BufferedImage img = mv.getDisplayImage();
              File f = chooser.getSelectedFile();
              String formatStr=ModelFileSaverFileFilter.getFileNameExtension(f.getAbsolutePath());

              if (formatStr == null)
                  formatStr = "png";
              else if (!(formatStr.equals("jpg") || formatStr.equals("png") || 
                  (formatStr.equals("gif") || formatStr.equals("tiff"))))
                  formatStr = "png";

              f = new File(ModelFileSaverFileFilter.setFileNameExtension(f.getAbsolutePath(),formatStr));

              ImageIO.write(img,formatStr,f);
           // save the image
            }
            catch (Exception e)
            {
                e.printStackTrace();
               JOptionPane.showMessageDialog(mv,"Unable to save the display image.");
            }   
        }
    }

  public void keyPressed(KeyEvent e)
  {
      if (e.isControlDown())
      {
          switch (e.getKeyChar())
          {
              case '+':
                  zoomIn();
                  break;

              case '-':
                  zoomOut();
                  break;
          }
      }
  }

  public void keyReleased(KeyEvent e)
  {
  }

    public void keyTyped(KeyEvent e)
    { 
    }
}