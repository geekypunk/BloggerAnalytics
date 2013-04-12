package analyser.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import analyser.charts.PieChart;
import analyser.dataobjects.AuthorInfo;
import analyser.utils.ConnectionUtils;
import analyser.utils.StringUtils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

public class BloggerAuthorInfoAnalyser {

	
	private static List<AuthorInfo> getDBToMemory(){
		
		List<AuthorInfo> result = Lists.newArrayList();
		try{
		
			String query ="SELECT * FROM  BloggerAuthorInfo";
		    ResultSet rs = ConnectionUtils.executeSELECTSQL(query);
		    
		    while (rs.next()){
		    	
		    	AuthorInfo obj = new AuthorInfo();
		    	obj.setCity(rs.getString("City"));
		    	obj.setState(rs.getString("State"));
		    	obj.setCountry(rs.getString("Country"));
		    	obj.setGender(rs.getString("Gender"));
		    	obj.setOccupation(rs.getString("Occupation"));
		    	obj.setInterests(rs.getString("Interests"));
		    	obj.setIntroduction(rs.getString("Introduction"));
		    	obj.setFavouriteBooks(rs.getString("Favourite Books"));
		    	obj.setFavouriteFilms(rs.getString("Favourite Films"));
		    	obj.setFavouriteMusic(rs.getString("Favourite Music"));
		    	result.add(obj);  
		    
		    }
		}catch(Exception e){
			
		}
		return result;
	}
	
	
		
	public static Map<String,Integer> getData(String city,String state,String country,String gender,int argsC,int n) throws SQLException{
		
		Map<String,Integer> data = Maps.newHashMap();
		Multiset<String> wordsMultiset = HashMultiset.create();
		
		String sql = "SELECT Interests,Introduction FROM  BloggerAuthorInfo 	WHERE ";
		if(city.length()>0){
			sql+=" City="+"'"+city+"'"+" AND ";
		}
		if(state.length()>0){
			sql+=" State="+"'"+state+"'"+" AND ";
		}
		if(country.length()>0){
			sql+=" Country="+"'"+country+"'"+" AND ";
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
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		
		while(rs.next()){
			if(rs.getString("Interests")!=null){
				List<String>  csvExtract = StringUtils.convertAllToLowerCase(StringUtils.convertToStringList(rs.getString("Interests")));
				wordsMultiset.addAll(csvExtract);

			}
			if(rs.getString("Introduction")!=null){
				List<String> introExtract = StringUtils.convertAllToLowerCase(StringUtils.convertToStringList(StringUtils.removeEnclosingBraces(rs.getString("Introduction"))));
			
				wordsMultiset.addAll(introExtract);
			}
			
		}
		int i=0;
		for (String type : Multisets.copyHighestCountFirst(wordsMultiset).elementSet()) {
		    System.out.println(type + ": " + wordsMultiset.count(type));
		    data.put(type, wordsMultiset.count(type));
		    i++;
		    if(i==n){
		    	break;
		    }
		}
		return data;
		
		
	}
	public static void main(String[] args) {
		
		try{
		
			Map<String,Integer> data = getData("", "", "Italy", "", 1,20);
			PieChart demo = new PieChart("Comparison", "Top 5 topics",data);
		    demo.pack();
		    demo.setVisible(true);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
