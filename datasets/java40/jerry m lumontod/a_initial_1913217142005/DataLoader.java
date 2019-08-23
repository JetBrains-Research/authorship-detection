

package com.jml.eisapp.acctg.base;

import com.jml.eisapp.acctg.utils.src.gui.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.sql.*;



public class DataLoader {

	private DefaultTableModel model = new DefaultTableModel();
	private String strCode,strDesc;
	private int i;
	private Object[] obj;
		
	private ResultSetMetaData meta;
	private int colCount;	
	private Statement st;
	private ResultSet rs;	
	private Connection conn;
	

	public DataLoader() {
		super();
	}
		

	public DefaultTableModel LoadData(String tstrSQL) {
		
		i=2;
		obj = new Object[i];
		
		try {
		
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/eis?user=root&password=");
			//conn = DriverManager.getConnection(url);

			
			st = conn.createStatement();

			// Execute the query and store the result set and its metadata
			rs = st.executeQuery(tstrSQL);
			meta = rs.getMetaData();
			colCount = meta.getColumnCount();


			// and file the cache with the records from our query.  This would not be
			// practical if we were expecting a few million records in response to our
			// query, but we aren't, so we can do this.
			
			
			//manually set column headers here, query statements passed in this class
			//should alias the field names into ccode and cdesc respectively 
			
			model.addColumn("Code");
			model.addColumn("Description");
					
			while (rs.next()) {
				
				strCode=rs.getString("ccode");
				strDesc=rs.getString("cdesc");
				obj[0] = strCode;
				obj[1] = strDesc;
				model.addRow(obj);
				
			}
			
			//fireTableChanged(null); // notify everyone that we have a new table.
			
			
			
			
		} catch(Exception e) {
			System.out.println("Could not initialize the database.");
			e.printStackTrace();
		}
		
		

		try {
			if (st != null) { st.close(); }
			if (conn != null) { conn.close(); }
		} catch(Exception e) {
			System.out.println("Could not close the current connection.");
			e.printStackTrace();
		}
	
		return model;
			
	}
	
}

	