
package com.jml.eisapp.acctg.base;

import java.sql.*;

public class SubClassIsValid {
	private String mstrTheMessage="";
	private String mstrSQL;
		
	public void SubClassIsValid(String tstrCompCode,String tstrDeptCode,String tstrSubDeptCode,String tstrClassCode,String tstrSubClassCode) {

		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost/eis?user=root&password=");
			//System.out.println("Connection open");
			Statement st;
			ResultSet rs;
			st=conn.createStatement();
			mstrSQL = "select * from acct8 where csubclasscode = '" + tstrSubClassCode + "' and ccompcode = '" + tstrCompCode + "' and cdeptcode = '" + tstrDeptCode + "' and csubdeptcode = '" + tstrSubDeptCode + "' and cclasscode = '" + tstrClassCode + "'";
			rs=st.executeQuery(mstrSQL);
			
			if (rs.next()) {
				mstrTheMessage="";
			}else {
				mstrTheMessage="Sub class code not found!";
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

