import java.sql.*;
public class Connect
{
	private static String info;
	public static Connection getConnection()
	{
		try

         {
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                String dataSourceName = "RVB.mdb";
                String dbURL = "jdbc:odbc:Driver={Microsoft Access Driver (*.mdb)};DBQ=";
                dbURL += dataSourceName + ";DriverID=22;READONLY=true)";
                return DriverManager.getConnection(dbURL,"","");
               
      }

      
      catch ( ClassNotFoundException cnfex )           

      {
            cnfex.printStackTrace();
            info=info+"Connection unsuccessful\n" + cnfex.toString();
      }

      catch ( SQLException sqlex )

      {
            sqlex.printStackTrace();
            //info=info+"Connection unsuccessful\n" +sqlex.toString();
      }

      catch ( Exception excp )

      {
            excp.printStackTrace();
            info=info+excp.toString();
      
		
	}   
	return null; 
	}
}