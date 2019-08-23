
package com.jml.eisapp.acctg.base;

import com.jml.eisapp.acctg.utils.src.gui.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;


public class jf extends JFrame  implements ContainerListener, KeyListener {

	public jf() {
		super();
		//AnchorLayout thisLayout = new AnchorLayout();
		//this.getContentPane().setLayout(thisLayout);		
		addKeyAndContainerListenerRecursively(this);
	}

	//ContainerListener, KeyListener implementation

	private void addKeyAndContainerListenerRecursively(Component c) {

		c.removeKeyListener(this);

		c.addKeyListener(this);

		if(c instanceof Container) {
			Container cont = (Container)c;
			cont.removeContainerListener(this);
			cont.addContainerListener(this);
			Component[] children = cont.getComponents();
			for(int i = 0; i < children.length; i++){
				addKeyAndContainerListenerRecursively(children[i]);
			}
		}
	}


    private void removeKeyAndContainerListenerRecursively(Component c) {
		c.removeKeyListener(this);
		if(c instanceof Container){
			Container cont = (Container)c;
			cont.removeContainerListener(this);
			Component[] children = cont.getComponents();
			for(int i = 0; i < children.length; i++){
				removeKeyAndContainerListenerRecursively(children[i]);
			}
		}
	}

	//ContainerListener interface
	public void componentAdded(ContainerEvent e) {
		addKeyAndContainerListenerRecursively(e.getChild());
	}


	public void componentRemoved(ContainerEvent e) {
		removeKeyAndContainerListenerRecursively(e.getChild());
	}

	//end, ContainerListener interface

	//KeyListener interface
	public void keyPressed(KeyEvent e) {

		int code = e.getKeyCode();
		if(code == KeyEvent.VK_ESCAPE){
			setVisible(false);

			//System.exit(0);
		}

	}

	public void keyReleased(KeyEvent e) {

	}

	public void keyTyped(KeyEvent e) {

	}

	//end, KeyListener interface
	//end, ContainerListener, KeyListener implementation

}