import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class videoSQL implements video_master_info_Interface, video_tab_Interface
{
	public Connection con;
	public String a, b, j, k, l;

	public videoSQL(String aa, String bb, String jj, String kk, String ll)
	{
			String url = "jdbc:odbc:lib";
		
		//Load the driver to allow connection to the database
		try {
 			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
 			con = DriverManager.getConnection(url);
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
 		a = aa;
 		b = bb;
 		j = jj;
 		k = kk;
 		l = ll;
 		
 		
 	}
		
 		public void add()
 		{
 		try
 				{
 					
 					Statement statement = con.createStatement();
 					
 					
 			
 				String query = "INSERT INTO Item (item_id, title, type, author, subject_area, location, avail, permission" + 
 						") VALUES ('" +
 					a +"','" +
 					b +"','" +
 					"Video" +"','" +
 					l +"','" +
 					j +"','" +
 					k +"','" +
 					"IN" +"','" +
 					type_combo.getSelectedItem() +"')";
		
		
		 		if (!Video_no_text.getText().equals(""))
 					{
 						JOptionPane.showMessageDialog(null, "insert");	
 				int result = statement.executeUpdate(query);
 				
 				
 				
 				String query2 = "INSERT INTO video_detail (item_id, type, category" + 
 							") VALUES ('" +
 					a +"','" +
 					type_combo.getSelectedItem() +"','" +
 					category_combo.getSelectedItem() +"')";
 					
 								
 						int result2 = statement.executeUpdate(query2);
 				
		 		if (result == 1 && result2 == 1)
		 				
		 				{
		 				JOptionPane.showMessageDialog(null, "Insertion succesfull");
		 				Video_no_text.setText("VD-");
		            	Video_title_text.setText("");
		            	location_text.setText("");
		            	authorsArea.setText("");
		            	subjectArea.setText("");	
		 				}
		 				else {
		 				JOptionPane.showMessageDialog(null, "Insertion Failed");
						Video_no_text.setText("VD-");
		            	Video_title_text.setText("");
		            	location_text.setText("");
		            	authorsArea.setText("");
		            	subjectArea.setText("");
		 				}
		 				
		 			}
 				
 			statement.close();
 			}
 			catch(SQLException sqlex) {
 			
 			}
 			}
 			
 	public void update()
 	{
 				
 				
 				try
 				{
 					
 					JOptionPane.showMessageDialog(null, "update");
 				Statement statement = con.createStatement();
				String query1 = "UPDATE Item SET " +
				"title ='" + b +
				"', type ='" + "Video" +
				"', location ='" + l +
				"', author ='" + j +
				"', subject_area ='" + k +
				"', permission ='" + type_combo.getSelectedItem() +
				"' WHERE item_id = '" + a + "'";
														
				
				int result = statement.executeUpdate(query1);
				
				String query2 = "UPDATE video_detail SET " +
				"type ='" + type_combo.getSelectedItem() +
				"', category ='" + category_combo.getSelectedItem() +
				"' WHERE item_id = '" + a + "'";
														
				int result2 = statement.executeUpdate(query2);
				
				if (result == 1 && result2 == 1)
				{
				JOptionPane.showMessageDialog(null, "Update Succesfull");
				Video_no_text.setText("VD-");
            	Video_title_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	Video_no_text.setEditable(true);
            	Video_title_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
				}
				statement.close();
				}
				catch (SQLException sqlex) 
				{
											 			
				}
 				
	}
	
	public void delete()
	{
		try
 		{
 		Statement statement = con.createStatement();
 		int rs = JOptionPane.showConfirmDialog(null, "WARNING!! Record once deleted cannot be retrieved!! Continue with delete?", "CONFIRM", JOptionPane.YES_NO_OPTION);
 		if (rs == JOptionPane.YES_OPTION)
 		{
	 		String query3 = "delete * from Item "+
	 		" WHERE item_id = '" + a + "'";
	 		int result = statement.executeUpdate(query3);
	 		String query2 = "delete * from video_detail "+
	 		" WHERE item_id = '" + a + "'";
	 		int result2 = statement.executeUpdate(query2);	 		
			if (result == 1 && result2 == 1)
			{
				JOptionPane.showMessageDialog(null, "del Succesful");
				Video_no_text.setText("VD-");
            	Video_title_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	Video_no_text.setEditable(true);
            	Video_title_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
			}
			statement.close();
			
	 	}
 		}
 		catch (SQLException sqlex) 
 		{
 		}
	}
}