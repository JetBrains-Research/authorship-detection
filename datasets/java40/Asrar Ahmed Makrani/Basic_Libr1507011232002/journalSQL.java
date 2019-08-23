import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class journalSQL implements journal_master_info_Interface, journal_tab_Interface
{
	public Connection con;
	public String a, b, c, d, g, h, i, j, k, l;
	
	public journalSQL(String aa, String bb, String cc, String dd, String hh, String gg, String ii, String jj, String kk, String ll)
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
 		c = cc;
 		d = dd;
 		g = gg;
 		h = hh;
 		i = ii;
 		j = jj;
 		k = kk;
 		l = ll;
 		
 		
 	}
		
 		public void add()
 		{
 		try
 				{
 					
 					Statement statement = con.createStatement();
 					
 					if(!b.equals("")) 
 					{
 					
 				String query = "INSERT INTO Item (item_id, title, type, year, author, subject_area, location, avail, permission" + 
 						") VALUES ('" +
 					a +"','" +
 					b +"','" +
 					"Journal" +"','" +
 					l +"','" +
 					j +"','" +
 					g +"','" +
 					k +"','" +
 					"IN" +"','" +
 					type_combo.getSelectedItem() +"')";
 				
 				int result = statement.executeUpdate(query);
 				
 				String query2 = "INSERT INTO journal_detail (item_id, journal_no, type, category, issue_no, publisher, pages" + 
 						") VALUES ('" +
 					a +"','" +
 					c +"','" +
 					type_combo.getSelectedItem() +"','" +
 					category_combo.getSelectedItem() +"','" +
 					d +"','" +
 					h +"','" +
 					i +"')";
 				
 				int result2 = statement.executeUpdate(query2);
 				
 				if (result == 1 && result2 == 1)
 				
 				{
 					JOptionPane.showMessageDialog(null, "Insertion succesfull");
 				journal_no_text.setText("JL-");
            	journal_title_text.setText("");
            	issue_number_text.setText("");
            	class_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
 		}
 				else {
 					JOptionPane.showMessageDialog(null, "Insertion Failed");
				journal_no_text.setText("JL-");
            	journal_title_text.setText("");
            	issue_number_text.setText("");
            	class_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");
 				}
 			}
 			else
 			JOptionPane.showMessageDialog(null, "Enter Atleast one field");
 			statement.close();
 			}
 			catch(SQLException sqlex) {
 			}
 			}
 			
 	public void update()
 	{
 				
 				
 				try
 				{
 				Statement statement = con.createStatement();
				String query1 = "UPDATE Item SET " +
				"title = '" + b +
				"', type = '" + type_combo.getSelectedItem() +
				"', location = '" + l +
				"', author = '" + j +
				"', year = '" + g +
				"', subject_area = '" + k +
				"', permission = '" + type_combo.getSelectedItem() +
				"' WHERE item_id = '" + a + "'";
														
				int result = statement.executeUpdate(query1);
				
				String query2 = "UPDATE journal_detail SET " +
				"issue_no = '" + c +
				"', type = '" + type_combo.getSelectedItem() +
				"', category = '" + category_combo.getSelectedItem() +
				"', journal_no = '" + d +
				"', publisher = '" + h +
				"', pages = '" + i +
				"' WHERE item_id = '" + a + "'";
														
				int result2 = statement.executeUpdate(query2);
				
				if (result == 1 && result2 == 1)
				{
				JOptionPane.showMessageDialog(null, "Update Succesfull");
				journal_no_text.setText("JL-");
            	journal_title_text.setText("");
            	issue_number_text.setText("");
            	class_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	journal_no_text.setEditable(true);
            	journal_title_text.setEditable(true);
 				issue_number_text.setEditable(true);
 				class_text.setEditable(true);
 				publisher_text.setEditable(true);
 				year_text.setEditable(true);
 				pages_text.setEditable(true);
 				authorsArea.setEditable(true);
 				subjectArea.setEditable(true);
 				location_text.setEditable(true);
				}
				statement.close();
				}
				catch (SQLException sqlex) 
				{
				sqlex.printStackTrace();							 			
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
	 		" WHERE item_id = '%" + a + "%'";
	 		int result = statement.executeUpdate(query3);
	 		String query2 = "delete * from journal_detail "+
	 		" WHERE item_id = '%" + a + "%'";
	 		int result2 = statement.executeUpdate(query2);	 		
			if (result == 1 && result2 == 1)
			{
				JOptionPane.showMessageDialog(null, "del Succesful");
				journal_no_text.setText("JL-");
            	journal_title_text.setText("");
            	issue_number_text.setText("");
            	class_text.setText("");
            	publisher_text.setText("");
            	place_text.setText("");
            	year_text.setText("");
            	pages_text.setText("");
            	location_text.setText("");
            	authorsArea.setText("");
            	subjectArea.setText("");	
            	
            	journal_no_text.setEditable(true);
            	journal_title_text.setEditable(true);
 				issue_number_text.setEditable(true);
 				class_text.setEditable(true);
 				publisher_text.setEditable(true);
 				year_text.setEditable(true);
 				pages_text.setEditable(true);
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