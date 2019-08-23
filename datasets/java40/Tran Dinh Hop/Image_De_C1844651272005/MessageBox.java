/**
 	*************************************************************************************
	* File: MessageBox.java                Date: 21/06/2004		      Version: 1.01 		*
	*-----------------------------------------------------------------------------------*
	* This program is released under the GNU General Public License 2.00. 	              
	* Details of GNU GPL at http://www.opensource.org/licenses/gpl-license.php  		    
	* You must agree to this license before using, copying or modifying this code.								    
	*
 	* Redistribution and use in source and binary forms, with or without
 	* modification, are permitted provided that the following conditions
 	* are met:
 	*
 	* 1. Redistributions of source code must retain the above copyright
 	*    notice, this list of conditions and the following disclaimer.
 	* 2. The source can be used and modified by individual/organizations, 
	*	  ONLY IF the source will not be used 
 	*    for commercial purposes or incorporated into commercial applications.
	*
 	* --------------------------------- WARRANTY --------------------------------- 
	* THIS SOFTWARE IS PROVIDED BY THE AUTHOR "AS IS" AND ANY EXPRESS OR IMPLIED 
	* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF 
	* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.  
	* IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
	* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, 
	* PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
	* OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
	* WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR 
	* OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF 
	* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
	*
 	* --------------------------------- CONTACT --------------------------------- 
	* This project was written in a burning hurry, so it is not a model of efficient
	* nor even good code.
	* Please send comments, bug reports, improvements to: hoptrandinh@yahoo.com  
	*
	* @Author Tran Dinh Hop, 6 May 2004
	* @Please visit http://www.freewebs.com/DigitZone for updated version
*/


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.*;

import Utils.MemoryMonitor;

public class MessageBox extends JPanel implements ActionListener{

	private JTextField tfInput;
	private JButton mbtStudent;
	private JTextArea msgBoard;
	
	public MessageBox() {
		
		this.setLayout(new FlowLayout());
	
		mbtStudent = new JButton("Contact...");
		mbtStudent.setFont (new Font("Arial", Font.BOLD, 14) );
		mbtStudent.setMnemonic('C');
	   mbtStudent.setToolTipText("Click here for getting my e-mail");
	   mbtStudent.addActionListener(this);
	
		tfInput =  new JTextField (11);
				
		JPanel memMonitor = new JPanel();
		final MemoryMonitor memMon = new MemoryMonitor();
		memMonitor.add(memMon);
		memMon.surf.start();
		
		msgBoard = new JTextArea();
		msgBoard = new JTextArea ("-- MONITORING --", 16, 13);
		msgBoard.setFont (new Font("Arial", Font.PLAIN, 11) );
		msgBoard.setEditable(false);
		msgBoard.setBackground(Color.black);
		msgBoard.setForeground(Color.white);
  	  	JScrollPane scrollPane = new JScrollPane(msgBoard,
                             JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                             JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		this.add(mbtStudent);
		this.add(tfInput);
	  	this.add(scrollPane); 
		this.add(memMonitor); 
	}
	
	public  void actionPerformed(ActionEvent e){
      if(e.getSource()==(Object)mbtStudent){
				tfInput.setFont (new Font("Arial Narrow", Font.PLAIN, 11) );
				tfInput.setText("hoptrandinh@yahoo.com");
		}
	}

	public  void displayMessage(String msg){
		msgBoard.append("\n"+msg);
	}

}
