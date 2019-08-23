
package com.jml.eisapp.acctg.base;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.sql.*;


public class SelDataList extends DefaultComboBoxModel {
	
	protected Vector moSelDataList;
	
	public SelDataList(String tstrSQL) {
		retrieveData(tstrSQL);
	}

	
	public double GetSelectedCode(Object toObj) {
		double mdbl=0;
		
		if (toObj instanceof SelData) {
			SelData moData = (SelData)toObj;
			mdbl = moData.GetItemCode();
		}
		return mdbl;
	}

	public void retrieveData(String tstrSQL) {

		if (tstrSQL.length()<1) {
			return;
		}

		//Thread runner = new Thread() {

		//	public void run() {

				try {

					Class.forName("com.mysql.jdbc.Driver").newInstance();			
					Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/eis?user=root&password=");
					//System.out.println("Connection open");
					Statement st;
					ResultSet rs;
					st=conn.createStatement();
					rs=st.executeQuery(tstrSQL);

					//moSelDataList.removeAllElements();

					while (rs.next()) {


						double mdblCode= rs.getDouble(1);
						String mstrDesc= rs.getString(2);

						addElement(new SelData(mdblCode,mstrDesc));
						
					}

					rs.close();
					st.close();
					conn.close();

				} catch (Exception e) {

					e.printStackTrace();
					System.err.println("Load data error: "+e.toString());

				}

		//	}
		//};

		//runner.start();
	}
	
	
}

