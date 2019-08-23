/*import java.applet.Applet;
import java.awt.*;
import java.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.StringBuffer;
import java.io.IOException;
import java.io.*;
import java.sql.*;
import javax.swing.plaf.metal.*; 
public class Password extends JFrame{
	private JPasswordField text1,text2,text3;
	private JLabel label1,label2,label3;
	private JButton button1,button2;
	private JPanel panel1,panel2,panel3;
		public Password(){
		text1=new JPasswordField();
		text2=new JPasswordField();
		text3=new JPasswordField();
		
		button1=new JButton("Change");
		button2=new JButton("Cancel");
		
		label1=new JLabel("Old Password");
		label2=new JLabel("New Password");
		label3=new JLabel("Confirm Pass");
		panel1=new JPanel(new GridLayout(3,2));
		
		panel1.add(label1);panel1.add(text1);
		panel1.add(label2);panel1.add(text2);
		panel1.add(label3);panel1.add(text3);
		
		panel2=new JPanel();
		panel2.add(button1);
		panel2.add(button2);
		
		panel3=new JPanel();
		panel3.add(panel1);panel3.add(panel2);
		setSize(350,150);
		setResizable(false);
		getContentPane().add(panel3);
		
		button1.addActionListener(new java.awt.event.ActionListener() {
	   public void actionPerformed(java.awt.event.ActionEvent e) {
	              
			try 
				{
					Statement statement =Connect.getConnection().createStatement();
					{
						
                    String temp = "UPDATE user SET " +
                  
                    //"  Sname      ='" + txtSname.getText()      +
					//"',Fname      ='" + txtFname.getText()      +
                    //"',Lname      ='" + txtLname.getText()      +
					//"',Gender     ='" + cbogender.getSelectedItem()+
					//"',DOB        ='" + dobs.getText()          +
					//"',Designation='" + txtDesignation.getText()+
					//"',Telephone  ='" + txttelephone.getText()  +
					//"',E_Mail     ='" + txtemail.getText()      +
					"'password    ='" + txtaddress.getText()    +
					"' WHERE empNo LIKE'" + txtEmpNo.getText()  + "'";
					
                    int result = statement.executeUpdate( temp );
				 
				      setVisible(false);
				      dispose();
					}
				 
				}
				
				 catch ( SQLException sqlex ) {
                        sqlex.printStackTrace();
             }	
			}
		});
		
	}
	public static void main(String[]args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		new Password().setVisible(true);
	}
}*/