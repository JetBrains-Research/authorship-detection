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
public class LoginScreen extends JFrame{
	private JLabel username,password,cat;
	private JLabel t1,t2;
	public JTextField user;
	private JPasswordField pass;
	private JButton Login,Cancel;
	private JComboBox cboCat;
	private JPanel p1,p2,p4;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	public LoginScreen(){
		
		super ("System Login");
		 //MainMenu mainmenu=new MainMenu();
		username=new JLabel("Username");
		password=new JLabel("Password");               
		user=new JTextField(10);
		pass=new JPasswordField(10);
		cat=new JLabel("Login As");
		cboCat=new JComboBox();
		cboCat.addItem("Manager");
		cboCat.addItem("Supervisor");
		cboCat.addItem("Booking Clerk");
		
		Login=new JButton ("Login",new ImageIcon("Icon/i16x16/ok.png"));
		Cancel=new JButton("Cancel",new ImageIcon("Icon/i16x16/exit.png"));
		
		t1=new JLabel("Enter your username and password");
		t1.setFont(new Font("sansserif", Font.BOLD, 10));
		username.setForeground(Color.blue);
		password.setForeground(Color.blue);
		cat.setForeground(Color.blue);
		username.setFont(new Font("sansserif", Font.ITALIC, 14));
		password.setFont(new Font("sansserif", Font.ITALIC, 14));
		cat.setFont(new Font("sansserif", Font.ITALIC, 14));
		cboCat.setFont(new Font("sansserif", Font.ITALIC, 14));
		t1.setForeground(Color.red);
		t2=new JLabel("");
		p1=new JPanel (new GridLayout(4,2));
		p1.setPreferredSize(new Dimension(200,200));
        p1.add(t1);p1.add(t2);
		p1.add(username);p1.add(user);
		p1.add(password);p1.add(pass);
		p1.add(Login);p1.add(Cancel);
        p1.add(cat);p1.add(cboCat);
        ButtonListener listener=new ButtonListener();
		Login.addActionListener(listener);
		Cancel.addActionListener(listener);
        p2=new JPanel();
        p2.add(Login);	p2.add(Cancel);
       
        p4=new JPanel(new GridLayout(2,2));
        
        p4.add(p1) ;p4.add(p2);
        
       try

         {
	
        Statement s = Connect.getConnection().createStatement();
         }

     
      catch ( Exception excp )

      {
            excp.printStackTrace();
            	
	  }    
        getContentPane().setLayout(new BorderLayout(0,0));
		getContentPane().add(BorderLayout.CENTER, p4);
		
		setSize(370,250);
		setVisible(true);
		
		setResizable(false);
		setLocation((screen.width - 500)/2,((screen.height-350)/2));	

	}
	
	public void login()
	{
		String us = user.getText();
	    String SQL;
		
		String pas= pass.getText();
        //String category=cboCat.getSelectedItem();
		SQL = "SELECT * FROM users WHERE username='" + us + "'  AND password='"+pas+"'AND Category='"+cboCat.getSelectedItem()+"'";
		try
		{
			Statement stmt   = Connect.getConnection().createStatement();

			stmt.execute(SQL);
			ResultSet rs = stmt.getResultSet();
			
			boolean recordfound = rs.next();
             
			if (recordfound)
			{LoginValidity();
			
			 dispose();
			}else 
			
			{JOptionPane.showMessageDialog(null,"The system could not log you in.\n"+
			" Please make sure your username and password are correct","tobiluoch",JOptionPane.INFORMATION_MESSAGE);
			 user.setText("");
			 pass.setText("");
			}
			
		}
		catch(Exception ex){JOptionPane.showMessageDialog(null,ex.getMessage(), 
		"GENERAL EXCEPTION",JOptionPane.INFORMATION_MESSAGE);}		
	}

	private class ButtonListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			//LoginValidity();

			if(e.getSource()==Login)
			
			{ 
			 if (user.getText() == null ||
            user.getText().equals("")){   
            JOptionPane.showMessageDialog(null,"Enter username","tobiluoch"
           ,JOptionPane.DEFAULT_OPTION);
            user.requestFocus();
            return;
            }
            
           if (pass.getText() == null ||
           pass.getText().equals("")){   
           JOptionPane.showMessageDialog(null,"Enter password","tobiluoch",
           JOptionPane.DEFAULT_OPTION);
           pass.requestFocus();
           return;}
           
           login();
			}	
			else if(e.getSource()==Cancel){System.exit(0);}	
		}
	};
	public void LoginValidity(){
		if (cboCat.getSelectedItem().equals("Manager")){
			new MainMenu().loginvalidity();
		}
		else if (cboCat.getSelectedItem().equals("Supervisor")){
			new MainMenu().loginvalidity2();
		}
		else{
			new MainMenu().loginvalidity3();
		}
		 
		 	
		 
	}	
	public static void main(String[]args)
	{
		JFrame.setDefaultLookAndFeelDecorated(true);
		new LoginScreen();
	}
}

