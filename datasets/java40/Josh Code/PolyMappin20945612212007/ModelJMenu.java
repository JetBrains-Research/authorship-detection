package UI;

import model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.awt.image.*;


public class ModelJMenu extends JMenu
{ 
 private modelViewer mv;
 private JMenuItem scaleModel;
 private JMenu flipAxis;
 private JMenuItem flipX;
 private JMenuItem flipY;
 private JMenuItem flipZ;
 private JMenuItem scaleAxis;
 private JMenuItem countTriangles;

    public ModelJMenu(modelViewer mv)
    {
        super("Model");
        this.mv = mv;
        scaleModel = new JMenuItem("Scale Model");
        flipAxis = new JMenu("Flip Axis");
        flipX = new JMenuItem("X");
        flipY = new JMenuItem("Y");
        flipZ = new JMenuItem("Z");
        scaleAxis = new JMenuItem("Scale Each axis");

        flipAxis.add(flipX);
        flipAxis.add(flipY);
        flipAxis.add(flipZ);

        countTriangles = new JMenuItem("Triangle Count");
        add(scaleModel);
        add(scaleAxis);
        add(flipAxis);
        add(countTriangles);

        addListeners();
    }

    private void addListeners()
    {
        scaleModel.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                // ...           
                String response = JOptionPane.showInputDialog(mv,
                     "What would you like to scale all vector components by?",
                     "1.000");

                if (response != null)
                {
                    double d = Double.parseDouble(response);
                    mv.scaleVectors(Vector3D.getFromCoordinates(d, d, d));
                }
            }
        }
        );
        flipX.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                mv.scaleVectors(Vector3D.getFromCoordinates(-1, 1, 1));
            }
        }
         );
        flipY.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                mv.scaleVectors(Vector3D.getFromCoordinates(1, -1, 1));
            }
        }
        );
        flipZ.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                mv.scaleVectors(Vector3D.getFromCoordinates(1, 1, -1));
            }
        }
         );
        scaleAxis.addActionListener(
            new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                String scaleStr = JOptionPane.showInputDialog(mv,
                    "What would you like to scale all vector components by?",
                    "1.000 1.000 1.000");
                if (scaleStr != null)
                {
                    try
                    {
                        StringTokenizer st = new StringTokenizer(scaleStr, " \t");
                        double x = Double.parseDouble(st.nextToken());
                        double y = Double.parseDouble(st.nextToken());
                        double z = Double.parseDouble(st.nextToken());
                        mv.scaleVectors(Vector3D.getFromCoordinates(x, y, z));
                    }
                    catch (NumberFormatException nfe)
                    {
                        nfe.printStackTrace();
                        JOptionPane.showMessageDialog(mv,
                            "Unable to scale the model because at least one of the inputted values is not a valid real number: "
                             + scaleStr);
                    }
                }
            }
        }
         );
        countTriangles.addActionListener(
             new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {

                JOptionPane.showMessageDialog(mv,
                   "There are " + mv.countTriangles() + " triangles in the model.");
            }
        }
          );    
    
    }
}
