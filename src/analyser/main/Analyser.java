package analyser.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import analyser.charts.PieChart;
import analyser.dataobjects.BlogURLInfo;
import analyser.utils.ConnectionUtils;
import analyser.utils.StringUtils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

public class Analyser {

	/**
	 * @param args
	 */
	
	
	private static void getTopicsByCity(String city,List<BlogURLInfo> result){
		Map<String,Integer> topicCount = Maps.newHashMap();
		for(BlogURLInfo obj:result){
			
			if(obj.getCity().equalsIgnoreCase(city)){
				List<String> topics = obj.getTopics();
				System.out.println(topics.toString());
			}
		}
		
	}
	
	private static void  getAllCities(List<BlogURLInfo> result){
		Multiset<String> wordsMultiset = HashMultiset.create();
		String city;
		for(BlogURLInfo obj:result){
			city = obj.getCity().trim().toLowerCase();
			if(city!=null && !city.equalsIgnoreCase("null"))
				wordsMultiset.add(obj.getCity().trim().toLowerCase());
			
		}
		for (String city1 : wordsMultiset.elementSet()) {
		    System.out.println(city1);
		}
		
	}
	private static void getMaxUsedTopics(List<BlogURLInfo> result){
		Multiset<String> wordsMultiset = HashMultiset.create();
		

		for(BlogURLInfo obj:result){
			
			List<String> topics = obj.getTopics();
			
			wordsMultiset.addAll(topics);
			
		}
		for (String type : Multisets.copyHighestCountFirst(wordsMultiset).elementSet()) {
		    System.out.println(type + ": " + wordsMultiset.count(type));
		}
		
	}
	
	private static Map<String,Integer> getNMaxUsedTopicsByCity(List<BlogURLInfo> result,String city,int n){
		Map<String,Integer> data = Maps.newHashMap();
		Multiset<String> wordsMultiset = HashMultiset.create();
		

		for(BlogURLInfo obj:result){
			
			if(obj.getCity().equalsIgnoreCase(city)){
				List<String> topics = obj.getTopics();
			
				wordsMultiset.addAll(topics);
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
		
			Connection conn = ConnectionUtils.getDBConnection();
			Statement st = conn.createStatement();
		    String query ="SELECT BlogURLTopics.BlogURL, BlogURLTopics.TopicsCSV, BlogURLSDemographs.City, BlogURLSDemographs.State, BlogURLSDemographs.Country"; 
		    query+=" FROM BlogURLSDemographs, BlogURLTopics";
		    query+="	WHERE BlogURLSDemographs.BlogURL = BlogURLTopics.BlogURL";
		    ResultSet rs = st.executeQuery(query);
		  
		    List<BlogURLInfo> result = Lists.newArrayList();
		    while (rs.next()){
		    	
		    	BlogURLInfo obj = new BlogURLInfo();
		    	obj.setBlogURL(rs.getString("BlogURL"));
		    	obj.setCity(rs.getString("City"));
		    	obj.setState(rs.getString("State"));
		    	obj.setCountry(rs.getString("Country"));
		    	obj.setTopics(StringUtils.convertToStringList(rs.getString("TopicsCSV")));
		    	result.add(obj);  
		    
		    }
		    
		    //getTopicsByCity("seattle",result); 
		    //getMaxUsedTopics(result);
		    getAllCities(result);
		    Map<String,Integer> data = getNMaxUsedTopicsByCity(result, "new york", 5);
		    PieChart demo = new PieChart("Comparison", "Top 5 topics in NY",data);
	        demo.pack();
	        demo.setVisible(true);
		    
	        data = getNMaxUsedTopicsByCity(result, "seattle", 5);
	        demo = new PieChart("Comparison", "Top 5 topics in seattle",data);
	        demo.pack();
	        demo.setVisible(true);
		   
	        data = getNMaxUsedTopicsByCity(result, "mysore", 5);
	        demo = new PieChart("Comparison", "Top 5 topics in mysore",data);
	        demo.pack();
	        demo.setVisible(true);
		}catch(Exception e){
			
			
		}


	}

}
