
/* Class Option Dialog is the dialog class for the option dialog */

package com.gs.ui;
import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;

import com.gs.ui.option.*;

public class OptionDialog extends JDialog implements OptionConstants {
	

	GraphicsFrame frame;
	
	OptionDisplay display; //= new OptionDisplay(this); //The configurable panels of the dialog
	OptionList list;// = new OptionList(this, display); //The option list, takes an OptionDisplay	
	
	public OptionDialog(GraphicsFrame frame)
	{
		super(frame.getFrame(), "Options");
		this.frame = frame;
		display = new OptionDisplay(this);
		list = new OptionList(this, display);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		cp.add(list, BorderLayout.WEST);
		cp.add(display, BorderLayout.CENTER);
		

		list.setPreferredSize(new Dimension(100, 440));
						
        setSize(500, 400);
        //setResizable(false);
        show();
      }
  

class OptionList extends JList implements ListSelectionListener {
	
	OptionDisplay display;
		
	Object items[] = { "General", "Rendering", "Look and Feel" };
	//OptionPanel itemPanels[];
	
	public OptionList(OptionDialog dlg, OptionDisplay display)
	{
		super();
		setListData(items);
		this.display = display;
		addListSelectionListener(this);
				
	}
	
	public void valueChanged(ListSelectionEvent e)
	{
		if(e.getValueIsAdjusting() == false)
		{
			String str = (String)getSelectedValue();	
			if(str == items[0])
			{
				display.setOptionPanel(GENERAL_OPTION);
			} else if(str == items[1])
			{
				System.out.println("Your Rendering Fine Today!");
			} else if(str == items[2])
			{
				System.out.println("Its all about how you look and feel.");
			}
		}
		
	}
	
		
	
}

class OptionDisplay extends JPanel {
	
	GeneralPanel generalPanel;
	
	OptionDialog dialog;
	public OptionDisplay(OptionDialog dialog)
	{	
		this.dialog = dialog;
		
		generalPanel = new GeneralPanel(dialog);
		
		setLayout(new BorderLayout());
		setBackground(Color.cyan);
		
		
	}
	
	public void setOptionPanel(int p)
	{
		System.out.println ("OPTION");
		switch(p)
		{
			case GENERAL_OPTION:    removeAll();
									repaint();
									add(generalPanel, BorderLayout.CENTER);
									generalPanel.repaint();
									repaint();
									dialog.repaint();
									break;
			case LOOKANDFEEL_OPTION: removeAll();
									
									break;
		}
									 	
		/*if(dialog != null)
		{	
			System.out.println("Inside repainter");
			dialog.repaint();			
		}*/
		//repaint();
		
	}
	
		
}
	
}	
	
	