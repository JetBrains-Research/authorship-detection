import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class General_Info extends JPanel implements General_Info_Interface, remark_Interface{
    public Connection connection;
     	public Icon userPic = new ImageIcon("default.gif");
	 public JLabel pic = new JLabel();
    
    public General_Info() {
  
    	pic.setIcon(userPic);
    	String url = "jdbc:odbc:lib";
 		
 		
 		try {
 			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 			connection = DriverManager.getConnection(url);
 		}
 		catch(ClassNotFoundException cnfex)  {
 			System.err.println("Failed to load driver");
 			cnfex.printStackTrace();
 			System.exit(1);
 		}
 		catch(SQLException sqlex){
 			System.err.println("unable to connect");
 			sqlex.printStackTrace();
 		}
 		
    	//validation for telephone
    	tel_text.addKeyListener(new KeyAdapter() {
    	public void keyTyped(KeyEvent e) {
      		char c = e.getKeyChar();      
      		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
        	getToolkit().beep();
        	e.consume();
      		}
    		}
  		});
    	
    	//validation for fax
    	fax_text.addKeyListener(new KeyAdapter() {
    	public void keyTyped(KeyEvent e) {
      		char c = e.getKeyChar();      
      		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
        	getToolkit().beep();
        	e.consume();
      		}
    		}
  		});
    	
    	patron_text.setText("P-");
    	patron_text.addFocusListener(new MyActionListener()
		{
		public void focusLost(FocusEvent e)
			{
								
			try
			{
				Statement statement = connection.createStatement();
				
				String query2 = "SELECT * FROM patronmaster " + 
 								"WHERE id = '" + 
 								patron_text.getText() +"'";
					
					ResultSet rs2 = statement.executeQuery(query2);
					int cnt=0;
					while(rs2.next())
					{
						cnt++;
					}
					if (cnt!=0)
					{				
					
					try{
					userPic = new ImageIcon(patron_text.getText() + ".gif");
					pic.setIcon(userPic);
					}
					catch(Exception ex)
					{
						ex.printStackTrace();
						pic.setIcon(userPic);
					}
					
					String query1 = "SELECT * FROM patronmaster " + 
 								"WHERE id = '" + 
 								patron_text.getText() +"'";
					
					ResultSet rs1 = statement.executeQuery(query1);
					try
					{
						rs1.next();
								            	
			            	int confirm = JOptionPane.showConfirmDialog(null, "This record Exists, would you like to update it?", "CONFIRM", JOptionPane.YES_NO_OPTION);
			            	if(confirm == JOptionPane.NO_OPTION)
			 				{
			            	patron_text.setEditable(false);
			            	name_text.setEditable(false);
			 				passport_text.setEditable(false);
			 				expiry_date_text.setEditable(false);
			 				reg_by_text.setEditable(false);
			 				reg_date_text.setEditable(false);
			 				textArea.setEditable(false);
			 				tel_text.setEditable(false);
			 				fax_text.setEditable(false);
			 				email_text.setEditable(false);	
							}
					
							else
			 				{ 
					 				
            				}
			            	name_text.setText(rs1.getString(2));
			 				passport_text.setText(rs1.getString(3));
			 				expiry_date_text.setText(rs1.getString(6));
			 				reg_by_text.setText(rs1.getString(7));
			 				reg_date_text.setText(rs1.getString(8));
			 				textArea.setText(rs1.getString(9));
			 				tel_text.setText(rs1.getString(10));
			 				fax_text.setText(rs1.getString(11));
			 				email_text.setText(rs1.getString(12));
			 				status_combo.setSelectedItem(rs1.getString(4));
			 				salute_combo.setSelectedItem(rs1.getString(5));
			 				group_combo.setSelectedItem(rs1.getString(13));
			            
			 				statement.close(); 
			 			
					}
					
					
					catch (SQLException sqlex)
					{
					pic.setIcon(userPic);	
					}
				}
			}
				catch (SQLException sqlex)
					{
					pic.setIcon(userPic);
					}
								
					}
					});
  
    	
    	
        scroll.add(address);
        scroll.add(scrollPane);
        
        text.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
        gbc.gridy = 0;
        gbc.gridx = 0;
        text.add(tel, gbc);
        gbc.gridy = 1;
        text.add(fax, gbc);
        gbc.gridy = 2;
        text.add(email, gbc);
        gbc.gridy = 0;
        gbc.gridx = 2;
        text.add(tel_text, gbc);
        gbc.gridy = 1;
        text.add(fax_text, gbc);
        gbc.gridy = 2;
        text.add(email_text, gbc);
        
        pic.setSize(50,50);
        pane1.add(scroll, BorderLayout.CENTER);
        pane1.add(pic, BorderLayout.EAST);
    	pane1.add(text, BorderLayout.WEST);  
    	
    	add(pane1);

    }
    
    }
