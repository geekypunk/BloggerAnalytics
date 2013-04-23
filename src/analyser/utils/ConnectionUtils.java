package analyser.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionUtils {
	public static Connection getDBConnection(){
        String url = "jdbc:mysql://localhost:3306/interestDemograph";
        String user = "root";
        String password = "";
        Connection conn = null; 

        try {
            conn = DriverManager.getConnection(url, user, password);
        }catch(Exception e){
        	e.printStackTrace();
        	
        }
        return conn;
	}
	
	public static ResultSet executeSELECTSQL(String sql) throws SQLException{
		Connection conn = ConnectionUtils.getDBConnection();
		Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(sql);
	    return rs;
	}
	

}
