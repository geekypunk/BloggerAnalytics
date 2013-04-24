package analyser.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import analyser.dataobjects.AuthorInfo;

import com.google.common.collect.Lists;

public class ConnectionUtils {
	public static Connection getDBConnection(){
        String url = "jdbc:mysql://localhost:3306/interestDemograph";
        String user = "root";
        String password = "root";
        Connection conn = null; 

        try {
            conn = DriverManager.getConnection(url, user, password);
        }catch(Exception e){
        	e.printStackTrace();
        	
        }
        return conn;
	}
	
	public static ResultSet executeSELECTSQL(String sql) throws SQLException{
		System.out.println("Executing SQL "+sql);
		Connection conn = ConnectionUtils.getDBConnection();
		Statement st = conn.createStatement();
	    ResultSet rs = st.executeQuery(sql);
	    return rs;
	}
	
   public static List<AuthorInfo> getDataSetInMemory() throws SQLException{
		
		List<AuthorInfo> aiList = Lists.newArrayList();
		
		String sql = "SELECT * FROM  BloggerAuthorInfo";
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		AuthorInfo ai = null;
		while(rs.next()){
			
			ai = new AuthorInfo();
			ai.setCity(rs.getString("City"));
			ai.setCountry(rs.getString("Country"));
			ai.setGender(rs.getString("Gender"));
			ai.setInterests(rs.getString("Interests"));
			ai.setOccupation(rs.getString("Occupation"));
			ai.setFavouriteBooks(rs.getString("Favourite Books"));
			ai.setFavouriteFilms(rs.getString("Favourite Movies"));
			ai.setFavouriteMusic(rs.getString("Favourite Music"));
			ai.setIntroduction(rs.getString("Introduction"));
			ai.setState(rs.getString("State"));
			aiList.add(ai);
			
			
		}
		return aiList;
		
		
	}
   public static String getWhereCondition(String city, String state,
			String country, String gender, int argsC, String sql) {
		if(city.length()>0){
			sql+=" City="+"'"+city+"'"+" AND ";
		}
		if(state.length()>0){
			sql+=" State="+"'"+state+"'"+" AND ";
		}
		if(country.length()>0 && gender.length()>0){
			sql+=" Country="+"'"+country+"'"+" AND ";
		}else if ((country.length()>0 && gender.length() == 0)){
			sql+=" Country="+"'"+country+"'";
		}
		if(gender.length()>0){
			sql+=" Gender="+"'"+gender+"'";
		}
		if(argsC ==1){
			sql = sql.replaceAll("AND", "");
		}
		if(argsC ==0){
			sql = sql.replaceAll("WHERE", "");
		}
		return sql;
	} 


}
