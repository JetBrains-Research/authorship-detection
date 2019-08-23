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
public class NewUser extends JFrame {
	private JLabel username,password,confirm,label1,label2;
	private JPasswordField pass1,pass2;
	private JTextField txtusername,name;
	private JButton button1,button2;
	private JPanel panel1,panel2;
	private JComboBox combo1;
	Dimension screen 	= 	Toolkit.getDefaultToolkit().getScreenSize();
	public NewUser(){
		super ("Adding New User");
		label1=new JLabel("Name");
		label2=new JLabel("Category");
		username=new JLabel("Username");
		password=new JLabel("Password");
		confirm=new JLabel("Re-enter Password");
		pass1=new JPasswordField();
		pass2=new JPasswordField();
		txtusername=new JTextField();
		name=new JTextField();
    	combo1=new JComboBox();
		button1=new JButton ("Ok",new ImageIcon("Icon/i16x16/ok.png"));
		button2=new JButton("Cancel",new ImageIcon("Icon/i16x16/exit.png"));
		
		panel1=new JPanel(new GridLayout(6,2));
		panel1.add(label1);panel1.add(name);
		panel1.add(label2);panel1.add(combo1);
		panel1.add(username);panel1.add(txtusername);
		panel1.add(password);panel1.add(pass1);
		panel1.add(confirm); panel1.add(pass2);
		panel1.add(button1); panel1.add(button2);
		combo1.addItem("Manager");
		combo1.addItem("Booking Clerk");
 		combo1.addItem("Supervisor");
		panel2=new JPanel();
		panel2.add(panel1);
		getContentPane().add(panel2);
		setSize(350,195);
		setVisible(true);
	    setLocation((screen.width - 500)/2,((screen.height-350)/2));
	    setResizable(false);
	    name.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
           char c = e.getKeyChar();
           if (!(Character.isLetter(c) ||
              (c == KeyEvent.VK_BACK_SPACE) ||
              (c==KeyEvent.VK_SPACE) ||
              (c == KeyEvent.VK_DELETE))) {
             
             getToolkit().beep();
             JOptionPane.showMessageDialog(null,"Invalid Character","ERROR",
             JOptionPane.DEFAULT_OPTION);
             e.consume();
           }
         }
       });
      txtusername.addKeyListener(new KeyAdapter() {
         public void keyTyped(KeyEvent e) {
           char c = e.getKeyChar();
           if (!(Character.isLetter(c) ||
              (c == KeyEvent.VK_BACK_SPACE) ||
              (c==KeyEvent.VK_SPACE) ||
              (c == KeyEvent.VK_DELETE))) {
             
             getToolkit().beep();
             JOptionPane.showMessageDialog(null,"Invalid Character","ERROR",
             JOptionPane.DEFAULT_OPTION);
             e.consume();
           }
         }
       });
	  button1.addActionListener(new java.awt.event.ActionListener() {
	  public void actionPerformed(java.awt.event.ActionEvent e) {
	  
      
      if (name.getText() == null ||
      name.getText().equals("")){   
      JOptionPane.showMessageDialog(null,"Enter name","ERROR",
      JOptionPane.DEFAULT_OPTION);
      name.requestFocus();
      return;}
      if (txtusername.getText() == null ||
      txtusername.getText().equals("")){   
      JOptionPane.showMessageDialog(null,"Enter username","ERROR"
      ,JOptionPane.DEFAULT_OPTION);
      txtusername.requestFocus();
      return;
      }
      if (pass1.getText() == null ||
      pass1.getText().equals("")){   
      JOptionPane.showMessageDialog(null,"Enter password","ERROR",
      JOptionPane.DEFAULT_OPTION);
      pass1.requestFocus();
      return;}
      if (pass2.getText() == null ||
      pass2.getText().equals("")){   
      JOptionPane.showMessageDialog(null,"Confirm your password","ERROR",
      JOptionPane.DEFAULT_OPTION);
      pass2.requestFocus();
      return;}
      if (!pass1.getText().equals(pass2.getText())){
      	JOptionPane.showMessageDialog(null,"passwords do not match.","ERROR",
        JOptionPane.DEFAULT_OPTION);
        pass2.requestFocus();
        return;
      }
				try{
					Statement statement =Connect.getConnection().createStatement();
					{
						String temp = "INSERT INTO users (Name,Category,username, password) VALUES ('"+
                             name.getText()            +"', '" +
                             combo1.getSelectedItem()  +"', '" +                      
                             txtusername.getText() 	   + "', '" +  			   
			                 pass1.getText() 	       + "')";
			   							   	 
				    int result = statement.executeUpdate( temp );
					JOptionPane.showMessageDialog(null,"User is succesfully added",
                   "SUCCESS",JOptionPane.DEFAULT_OPTION);
                   dispose();
					}
					
				}
			     catch(Exception in){
			     	in.printStackTrace();
			     }
				
			}
		});
		button2.addActionListener( new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
	}
	public static void main(String[]args){
		JFrame.setDefaultLookAndFeelDecorated(true);
		new MainMenu().setVisible(true);
	}
}
