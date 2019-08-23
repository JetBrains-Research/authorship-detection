/* Database.java*/

import java.sql.*;
import java.awt.*;
import javax.swing.*;

public class Database{
	private static Connection con = null;
	public static Statement statement = null;
	
	public static void makeConnection() {
		JOptionPane.showMessageDialog(null, "database");
		String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
		String url = "jdbc:odbc:lib";
		String username = ""; 
		String password = "";

		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			statement = con.createStatement();
			
		} catch (ClassNotFoundException cnfex) {
			System.err.println("Failed to load JDBC/ODBC driver.");
			cnfex.printStackTrace();
			System.exit(1);
		} catch (SQLException sqlex) {
			System.err.println("Unable to connect");
			sqlex.printStackTrace();
		}
	}

	public static void closeConnection() {
		try {
			statement.close();
			con.close();
		} catch (SQLException sqlex) {
			System.out.println("Error in closing connection. Terminating program.");
			System.exit(1);
		}	
	}
	
	public static ResultSet executeQuery(String query) throws SQLException{
		return statement.executeQuery(query);
	}
	
	public static int executeUpdate(String query) throws SQLException {
		return statement.executeUpdate(query);
	}
}
