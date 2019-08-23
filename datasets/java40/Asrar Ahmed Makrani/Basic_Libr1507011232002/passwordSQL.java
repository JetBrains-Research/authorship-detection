import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class passwordSQL
{
	public Connection con;
	public int cnt = 0;
	public String usname, uspasswd;
	public boolean checked;
	public int ctr;
	
	public passwordSQL(String usnames, String uspasswds, boolean checkeds)
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
 		usname = usnames;
 		uspasswd = uspasswds;
 		checked = checkeds;
 		
 		check();
 		
 	}		
 			public boolean check()
 			{ 
 			try
 				{
 					String uname, pass;
 					Statement s = con.createStatement();
 					String sqlsel = "SELECT * FROM User WHERE userid = '" + usname + "'";
 					
 					ResultSet rs = s.executeQuery(sqlsel);
 					try
 					{
 						rs.next();
 						
 						uname = rs.getString("userid");
 						pass = rs.getString("password");
 						 						
 						if( usname.equals(uname ) && uspasswd.equals(pass))
 						{
 							checked = true;
 							
 						}
 						else
 						{
 							checked = false;
 						}
 					
 						
 					}
 					
 					catch(SQLException sqlex){
 						checked = false;
 						
 					}
 					
 					
 				}
 				catch(SQLException sqlex)
 				{
 					System.err.println("unable to connect");
 					sqlex.printStackTrace();
 					JOptionPane.showMessageDialog(null, sqlex);
 				}
 				return checked;
	}
}