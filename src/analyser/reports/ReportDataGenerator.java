package analyser.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import analyser.dataobjects.AuthorInfo;
import analyser.utils.ConnectionUtils;
import analyser.utils.StringUtils;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

public class ReportDataGenerator {

public static Map<String,Integer> getMajorOccupations(String city,String state,String country,String gender,int argsC,int n) throws SQLException{
		
		Map<String,Integer> data = Maps.newHashMap();
		Multiset<String> wordsMultiset = HashMultiset.create();
		
		String sql = "SELECT Occupation FROM  BloggerAuthorInfo 	WHERE ";
		sql = ConnectionUtils.getWhereCondition(city, state, country, gender, argsC, sql);
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		
		while(rs.next()){
			String value = rs.getString("Occupation");
			if(value!=null){
				List<String>  csvExtract = StringUtils.convertAllToLowerCase(Arrays.asList(value.split("[,&,\" \",/]")));
				wordsMultiset.addAll(csvExtract);

			}
			
		}
		data = getTopNData(n, wordsMultiset);
		return data;
		
	}
		
	public static Map<String,Integer> getInterestsIntro(String city,String state,String country,String gender,int argsC,int n) throws SQLException{
		
		
		Multiset<String> wordsMultiset = HashMultiset.create();
		
		String sql = "SELECT Interests,Introduction FROM  BloggerAuthorInfo 	WHERE ";
		sql = ConnectionUtils.getWhereCondition(city, state, country, gender, argsC, sql);
		
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		
		getWordsMultiSet(wordsMultiset, rs);
		Map<String, Integer> data = getTopNData(n, wordsMultiset);
		return data;
		
		
	}

	private static Map<String, Integer> getTopNData(int n,
			Multiset<String> wordsMultiset) {
		final String[] ENGLISH_STOP_WORDS = {
			"a", "an", "and", "are", "as", "at", "be", "but", "by",
			"for", "if", "in", "into", "is", "it",
			"no", "not", "of", "on", "or", "such",
			"that", "the", "their", "then", "there", "these",
			"they", "this", "to", "was", "will", "with",""," "
			};
		wordsMultiset.removeAll(Arrays.asList(ENGLISH_STOP_WORDS));
		Map<String,Integer> data = Maps.newHashMap();
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

	private static void getWordsMultiSet(Multiset<String> wordsMultiset,
			ResultSet rs) throws SQLException {
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
		wordsMultiset.elementSet().remove("");
	}
	public static int getBloggersCount(String city,String state,String country,String gender,int argsC) throws SQLException{
	
		
		String sql = "SELECT count(*) FROM BloggerAuthorInfo WHERE ";
		sql = ConnectionUtils.getWhereCondition(city, state, country, gender, argsC, sql);
		System.out.println(sql);
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		int count=0;
		while(rs.next()){
			count = rs.getInt(1);
			
		}
		return count;
	
	}
	
	public static Map<String,Integer> getCitiesByTopics(String topic,int n) throws SQLException{
	
		Multiset<String> cityMultiset = HashMultiset.create();
		
		List<AuthorInfo> list = ConnectionUtils.getDataSetInMemory();
		
		for(AuthorInfo ai : list){
			if(ai.getInterests() !=null && ai.getInterests().toLowerCase().contains(topic.toLowerCase())){
				if(ai.getCity()!=null)
					cityMultiset.add(ai.getCity());
			}
			if(ai.getIntroduction()!=null && ai.getIntroduction().toLowerCase().contains(topic.toLowerCase())){
				if(ai.getCity()!=null)
					cityMultiset.add(ai.getCity());

			}
			
		}	
		Map<String, Integer> data = getTopNData(n, cityMultiset);
		return data;
	}
	public static Map<String,Integer> getTopNBloggingCities(int n) throws SQLException{
		
		Multiset<String> cityMultiset = HashMultiset.create();
		
		List<AuthorInfo> list = ConnectionUtils.getDataSetInMemory();
		
		for(AuthorInfo ai : list){
				if(ai.getCity()!=null)
					cityMultiset.add(ai.getCity());
			
				if(ai.getCity()!=null)
					cityMultiset.add(ai.getCity());

			
		}	
		Map<String, Integer> data = Maps.newHashMap();
		if(n==0){
			 data = getTopNData(cityMultiset.size(), cityMultiset);
		}
		else{
			 data = getTopNData(n, cityMultiset);

		}
		
		return data;
	}
	public static Map<String,Integer> getTopNBloggingCitiesByGender(String gender,int n) throws SQLException{
		
		Multiset<String> cityMultiset = HashMultiset.create();
		
		List<AuthorInfo> list = ConnectionUtils.getDataSetInMemory();
		for(AuthorInfo ai : list){
			if(ai.getGender()!=null && ai.getGender().contains(gender)){
				if(ai.getCity()!=null)
					cityMultiset.add(ai.getCity());
			}
			
		}	
		Map<String, Integer> data = Maps.newHashMap();
		if(n==0){
			 data = getTopNData(cityMultiset.size(), cityMultiset);
		}
		else{
			 data = getTopNData(n, cityMultiset);

		}
		
		return data;
	}
}
