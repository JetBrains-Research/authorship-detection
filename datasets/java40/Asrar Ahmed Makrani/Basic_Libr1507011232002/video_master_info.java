import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class video_master_info extends JPanel implements video_Interface, video_master_info_Interface, video_tab_Interface{
     public Connection connection;
     public int confirm;
    
    public video_master_info() {    	
	
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
			
  			
  	Video_no_text.setText("VD-");		
  	Video_no_text.addFocusListener(new MyActionListener()
		{
			public void focusLost(FocusEvent e)
			{
			try
			{
				Statement statement = connection.createStatement();
					String query1 = "SELECT * FROM Item " + 
 								"WHERE item_id = '" + 
 								Video_no_text.getText() +"'";
					
					ResultSet rs1 = statement.executeQuery(query1);
					try
					{
						rs1.next();
								            	
			            	Video_title_text.setText(rs1.getString(2));
			 				authorsArea.setText(rs1.getString(5));
			 				subjectArea.setText(rs1.getString(7));
			 				location_text.setText(rs1.getString(4));
			 				type_combo.setSelectedItem(rs1.getString(3));			 					
			 		}
					catch (SQLException sqlex)
					{
						
					}
						
					String query = "SELECT * FROM video_detail " + 
 								"WHERE item_id = '" + 
 								Video_no_text.getText() +"'";
					
					ResultSet rs = statement.executeQuery(query);
					try
					{
						rs.next();
						
			            	type_combo.setSelectedItem(rs.getString(2));
			            	category_combo.setSelectedItem(rs.getString(3));
			            	
			            	int confirm = JOptionPane.showConfirmDialog(null, "This record Exists, would you like to update it?", "CONFIRM", JOptionPane.YES_NO_OPTION);
			 				
			 				if(confirm == JOptionPane.NO_OPTION)
			 				{
			            	Video_no_text.setEditable(false);
			            	Video_title_text.setEditable(false);
			 				authorsArea.setEditable(false);
			 				subjectArea.setEditable(false);
			 				location_text.setEditable(false);	
							}
					
							else
			 				{ 
					 				
            				}
											 										
								statement.close(); 
					}
					
					
					catch (SQLException sqlex)
					{
						
					}
				}
				catch (SQLException sqlex)
					{
					
					}
								
					}
					});
  	
	
	Video_master.setFont (new Font ("Impact", Font.PLAIN, 16));

	text.setLayout(new GridBagLayout());
	GridBagConstraints gbc = new GridBagConstraints();
		
    gbc.gridy = 0;
    gbc.gridx = 0;
    text.add(Video_master, gbc);
    
    gbc.gridy = 1;
    text.add(Video_no, gbc);
    gbc.gridy = 2;
    text.add(Video_title, gbc);
    gbc.gridx = 2;
    gbc.gridy = 1;
    text.add(Video_type, gbc);
    gbc.gridy = 2;
    text.add(Video_cat, gbc);
    gbc.gridy = 1;
    gbc.gridx = 1;
    text.add(Video_no_text, gbc);
    gbc.gridy = 2;
    text.add(Video_title_text, gbc);
    gbc.gridx = 3;
    gbc.gridy = 1;
    text.add(type_combo, gbc);
    gbc.gridy = 2;
    text.add(category_combo, gbc);
    add(text);
    setBackground(Color.blue);
    }
    
    }
