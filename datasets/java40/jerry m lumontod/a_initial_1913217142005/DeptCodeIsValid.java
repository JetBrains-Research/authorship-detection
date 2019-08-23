
package com.jml.eisapp.acctg.base;

import java.sql.*;

public class DeptCodeIsValid {
	private String mstrTheMessage="";
	private String mstrSQL;
		
	public void DeptCodeIsValid(String tstrCompCode,String tstrDeptCode) {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/eis?user=root&password=");
			//System.out.println("Connection open");
			Statement st;
			ResultSet rs;
			st=conn.createStatement();
			mstrSQL = "select * from acct5 where cdeptcode = '" + tstrDeptCode + "' and ccompcode = '" + tstrCompCode + "'";
			rs=st.executeQuery(mstrSQL);
			
			if (rs.next()) {
				mstrTheMessage="";
			}else {
				mstrTheMessage="Department code not found!";
			}

			rs.close();
			st.close();
			conn.close();

		} catch (Exception e) {

			e.printStackTrace();
			System.err.println("Load data error: "+e.toString());

		}

	}
	
	public String TheMessage() {
		return mstrTheMessage;	
	}	
}

