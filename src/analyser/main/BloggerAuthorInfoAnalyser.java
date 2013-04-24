package analyser.main;

import java.sql.SQLException;
import java.util.Map;

import analyser.charts.ChartUtils;
import analyser.charts.PieChart;
import analyser.reports.ReportDataGenerator;

import com.google.common.collect.Maps;

public class BloggerAuthorInfoAnalyser {

	
	public static void showMajorOccupationsChart(String city,String state,String country,String gender,int argC,int n) throws SQLException{
		Map<String,Integer>dataSet = Maps.newHashMap();
		dataSet = ReportDataGenerator.getMajorOccupations(city,state, country, gender, argC,n);
		ChartUtils.renderPieChart("Comparison", "Top 10 blogger's occupations in "+city+","+state+","+country, dataSet);
		
	}
	public static void showMajorTopicsByGeoChart(String topic,int n) throws SQLException{
		Map<String,Integer>dataSet = Maps.newHashMap();
		PieChart demo = null;
		dataSet = ReportDataGenerator.getCitiesByTopics(topic,10);
		demo = new PieChart("Comparison", "Top 10 cities with interest in "+topic,dataSet);
	    demo.pack();
	    demo.setVisible(true);
		
	}
	
	public static void showInterestByGeoInfoChart(String city,String state,String country,String gender,int argC,int n) throws SQLException{
		
		Map<String,Integer>dataSet = Maps.newHashMap();
		PieChart demo = null;
		dataSet = ReportDataGenerator.getInterestsIntro(city, state, country, gender, argC,n);
		demo = new PieChart("Comparison","Most blogged topics in "+city+","+state+","+country ,dataSet);
	    demo.pack();
	    demo.setVisible(true);
		
	}
	public static void showTopCitiesByNoOfBloggers(int n) throws SQLException{
		Map<String,Integer>dataSet = Maps.newHashMap();
		PieChart demo = null;
		dataSet = ReportDataGenerator.getTopNBloggingCities(n);
		demo = new PieChart("Comparison", "Top 10 cities with bloggers",dataSet);
	    demo.pack();
	    demo.setVisible(true);
	}
	public static void showTopBloggingCitiesByGender(String gender,int n) throws SQLException{
		Map<String,Integer>dataSet = Maps.newHashMap();
		PieChart demo = null;
		dataSet = ReportDataGenerator.getTopNBloggingCitiesByGender(gender,10);
		demo = new PieChart("Comparison", "Top 10 cities with "+gender+" bloggers",dataSet);
		demo.pack();
		demo.setVisible(true);
		
	}
	public static void main(String[] args) {
		
		try{
			
			String country = "";
			String state = "";
			String city = "San Francisco";
			String topic="politics";
			String gender="Female";
			System.out.println(ReportDataGenerator.getBloggersCount("", "", "", "", 0));
			
			showMajorOccupationsChart(city,state,country,gender,2,10);
			showMajorTopicsByGeoChart(topic,10);
			showInterestByGeoInfoChart(city,state,country,gender,2,10);
			showTopCitiesByNoOfBloggers(10);
			showTopBloggingCitiesByGender(gender,10);
			
		    
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
