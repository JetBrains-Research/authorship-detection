
package com.jml.eisapp.acctg.utils.src.gui;

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.event.*;
import java.awt.event.*;
import javax.swing.*;

//must implement mouselistener and actionlistener 
//else trouble  i didnt figured out why

public class MsgBox implements ActionListener, MouseListener{

	private JFrame jfDummy = new JFrame();
	
	public MsgBox() {
		super();
	}
	
	public int ShowMsg(String tstrMsg,String tstrTitle) {
		return JOptionPane.showConfirmDialog(jfDummy,tstrMsg,tstrTitle,JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);
	}
	
	public int ShowMsg(String tstrTitle) {
		//return JOptionPane.showConfirmDialog(jfDummy,tstrTitle,JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
		return JOptionPane.showConfirmDialog(jfDummy,tstrTitle,"Press OK to continue",JOptionPane.CLOSED_OPTION,JOptionPane.WARNING_MESSAGE);
		//JOptionPane.showMessageDialog(jfDummy,tstrTitle);
	}

	public void actionPerformed(ActionEvent e) {
	}


	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}


	public void mouseExited(MouseEvent e) {
	}


	public void mousePressed(MouseEvent e) {
	}


	public void mouseReleased(MouseEvent e) {
		
	}
	public static void main(String[] args) {
		
		MsgBox MsgBoxInst = new MsgBox();
		MsgBoxInst.ShowMsg("test");
		
	}
}
