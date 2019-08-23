import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.applet.Applet;
import javax.swing.*;
import java.lang.StringBuffer;
import java.io.IOException;
import java.io.*;
import java.sql.*;  
public class Employee_Search extends JFrame{
	private JPanel panel1,panel2;
	private JLabel label1;
	private JTextField text1;
	private JButton button1,button2;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	public Employee_Search (){
		super("Searching Record");
		label1=new JLabel("ENTER EMPLOYEE_NO" );
		text1=new JTextField(10);
		button1=new JButton("Search");
		button2=new JButton("Cancel");
		label1.setForeground(Color.blue);
		panel1=new JPanel(new GridLayout(2,2));
		panel1.add(label1);panel1.add(text1);
		panel1.add(button1);panel1.add(button2);
		
		panel2=new JPanel();
		panel2.add(panel1);
		//getContentPane().setLayout(new BorderLayout());
		getContentPane().add( panel2);
		
		pack();
		setSize(300,100);
		setVisible(true);
		setLocation((screen.width - 500)/2,((screen.height-350)/2));
		button1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
		     
			}
		});
		button2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			 dispose();
			}
		});	
		
	}
	public static void main(String []args){
		JFrame.setDefaultLookAndFeelDecorated(true);
	   new Employee_Search().setVisible(true);
		}
}