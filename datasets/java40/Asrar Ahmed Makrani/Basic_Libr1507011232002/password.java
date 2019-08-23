import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.EventListener.*;
import java.text.*;
import java.io.*;

public class password extends JInternalFrame implements passwordInterface, mainInterface
{
	private Container c;
	public String uname,pass,usname;
	public int ctr = 0;
	public CheckBoxHandler handler = new CheckBoxHandler();
	public boolean rem;
	
	public password() 
    {
        
        super("Password", 
              false, //resizable
              false, //closable
              false, //maximizable
              false);//iconifiable
		
		rem = false;
		c = getContentPane();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
       
 		int paintx = (screenSize.width);
		int painty = (screenSize.height);
		setBounds( (paintx - 300)/2 , ((painty-50) - 150)/2,
						 300,150);
				
		c.setLayout( null );
		
	    user.setBounds( 20,15,80,20 );
        user.setForeground(Color.black);
        c.add( user );
        
        passwod.setBounds( 20,40,80,20 );
        passwod.setForeground(Color.black);
        c.add( passwod ); 
        
        usersName.setBounds( 100,15,170,20 );
        c.add( usersName );
        
        userPassword.setBounds( 100,40,170,20 );
        c.add( userPassword );
        
       	okButton.setBounds( 75,80,70,25 );
		c.add( okButton );
        
        closeButton.setBounds( 155,80,70,25 );
        c.add( closeButton );
        
        chk.setBounds(100, 60,170,20);
        chk.setBackground(Color.gray);
        chk.addItemListener(handler);
        c.add(chk);
        
        
        
        okButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
 			{
 				usname = usersName.getText();
 				String uspasswd = userPassword.getText();
 				boolean checked = false;
 				passwordSQL passql = new passwordSQL(usname, uspasswd, checked);
 				checked = passql.check();
 				
 				if (checked == true)
 				{
 				JOptionPane.showMessageDialog(null, "Welcome, " + usname );
 				add.setEnabled(true);
 				String dd;
				dd = formatter.format(currentDate);
        		USERName.setText("Welcome " + usname + " today is " + dd);
        		menuItem3.setEnabled(false);
        		menuItem6.setEnabled(true);
 				dispose();	
 				}
 				
 				else {
 					JOptionPane.showMessageDialog(null, "Login Failed. Please Try again");
 					ctr = ctr + 1;
 					System.out.println(ctr);
 					if (ctr > 3)
 					{
 						JOptionPane.showMessageDialog(null, "Please, Contact the Administrator");
 						ctr = 0;
 						dispose();
 					}
 					passwordSQL passq = new passwordSQL(usname, uspasswd, checked);
 					checked = passq.check();
 					}
 					
 					if (rem == false) 
 					{
 						usersName.setText("");
 						userPassword.setText("");
 					}
 			dispose();		
 			}
 			
 		});
        		
				
		closeButton.addActionListener( new ActionListener()
		{
			public void actionPerformed( ActionEvent e )
 			{			
 					
 					dispose(); 				
 			}
 		});
 		dispose();
 		}
 		private class CheckBoxHandler implements ItemListener {
 		
 		public void itemStateChanged(ItemEvent e)
 		{
 		if (e.getSource() == chk)
 		{
 			if(e.getStateChange() == ItemEvent.SELECTED)
 			{
 			rem = true;
 			}
 			else
 			rem = false;
    	}
 		}
    	
	}	
}
