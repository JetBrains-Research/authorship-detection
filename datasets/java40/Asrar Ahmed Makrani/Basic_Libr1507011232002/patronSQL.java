import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class patronSQL implements remark_Interface, General_Info_Interface, passwordInterface
{
	public Connection con;
	public String a, b, c, d, g, l, h, i, j, k;

	public patronSQL(String aa, String bb, String cc, String dd, String gg, String ll, String hh, String ii, String jj, String kk)
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
 					
 					
 			
 				String query = "INSERT INTO patronmaster (id, name, ic, status, salute, expiry_date, registered_by, Reg_date, Address, Tel, fax, email, group_type" + 
 						") VALUES ('" +
 					a +"','" +
 					b +"','" +
 					c +"','" +
 					status_combo.getSelectedItem() +"','" +
 					salute_combo.getSelectedItem() +"','" +
 					d +"','" +
 					usersName.getText() +"','" +
 					l +"','" +
 					h +"','" +
 					i +"','" +
 					j +"','" +
 					k +"','" +
 					group_combo.getSelectedItem() +"')";
		
 						JOptionPane.showMessageDialog(null, "insert");	
 				int result = statement.executeUpdate(query);
 				 				
		 		if (result == 1)
		 				
		 				{
		 				JOptionPane.showMessageDialog(null, "Insertion succesfull");
		 					patron_text.setText("P-");
			            	name_text.setText("");
			 				passport_text.setText("");
			 				expiry_date_text.setText("");
			 				reg_by_text.setText("");
			 				reg_date_text.setText("");
			 				textArea.setText("");
			 				tel_text.setText("");
			 				fax_text.setText("");
			 				email_text.setText("");	
		 				}
		 				else {
		 				JOptionPane.showMessageDialog(null, "Insertion Failed");
							patron_text.setText("P-");
			            	name_text.setText("");
			 				passport_text.setText("");
			 				expiry_date_text.setText("");
			 				reg_by_text.setText("");
			 				reg_date_text.setText("");
			 				textArea.setText("");
			 				tel_text.setText("");
			 				fax_text.setText("");
			 				email_text.setText("");
		 				}
 				
 			statement.close();
 			}
 			catch(SQLException sqlex) {
 			JOptionPane.showMessageDialog(null, sqlex);
 			}
 			try
 			{
 			con.close();
 			}
 			catch(SQLException sqlex) {
 			JOptionPane.showMessageDialog(null, sqlex);
 			}
 			}
 			
 	public void update()
 	{
 				
 				
 				try
 				{
 					
 					JOptionPane.showMessageDialog(null, "update");
 				Statement statement = con.createStatement();
				String query1 = "UPDATE patronmaster SET " +
				"name ='" + b +
				"', ic ='" + c +
				"', status ='" + status_combo.getSelectedItem() +
				"', salute ='" + salute_combo.getSelectedItem() +
				"', expiry_date ='" + d +
				"', registered_by ='" + g +
				"', Reg_date ='" + l +
				"', Address ='" + h +
				"', Tel ='" + i +
				"', fax ='" + j +
				"', email ='" + k +
				"', group_type ='" + group_combo.getSelectedItem() +
				"' WHERE id = '" + a + "'";
														
				
				int result = statement.executeUpdate(query1);
				
				
				if (result == 1)
				{
				JOptionPane.showMessageDialog(null, "Update Succesfull");
				patron_text.setText("");
			            	name_text.setText("");
			 				passport_text.setText("");
			 				expiry_date_text.setText("");
			 				reg_by_text.setText("");
			 				reg_date_text.setText("");
			 				textArea.setText("");
			 				tel_text.setText("");
			 				fax_text.setText("");
			 				email_text.setText("");	
            	
            	patron_text.setEditable(true);
			            	name_text.setEditable(true);
			 				passport_text.setEditable(true);
			 				expiry_date_text.setEditable(true);
			 				reg_by_text.setEditable(true);
			 				reg_date_text.setEditable(true);
			 				textArea.setEditable(true);
			 				tel_text.setEditable(true);
			 				fax_text.setEditable(true);
			 				email_text.setEditable(true);
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
	 		String query3 = "delete * from patronmaster "+
	 		" WHERE id = '" + a + "'";
	 		int result = statement.executeUpdate(query3);
	 			 		
			if (result == 1)
			{
				JOptionPane.showMessageDialog(null, "del Succesful");
				patron_text.setText("");
			            	name_text.setText("");
			 				passport_text.setText("");
			 				expiry_date_text.setText("");
			 				reg_by_text.setText("");
			 				reg_date_text.setText("");
			 				textArea.setText("");
			 				tel_text.setText("");
			 				fax_text.setText("");
			 				email_text.setText("");
			 				
			 				patron_text.setEditable(true);
			            	name_text.setEditable(true);
			 				passport_text.setEditable(true);
			 				expiry_date_text.setEditable(true);
			 				reg_by_text.setEditable(true);
			 				reg_date_text.setEditable(true);
			 				textArea.setEditable(true);
			 				tel_text.setEditable(true);
			 				fax_text.setEditable(true);
			 				email_text.setEditable(true);	
            	
            	}
			statement.close();
			
	 	}
 		}
 		catch (SQLException sqlex) 
 		{
 		}
	}
}