package analyser.main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
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


	public static List<AuthorInfo> getInMemory() throws SQLException{
		
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
	public static Map<String,Integer> getMajorOccupations(String city,String state,String country,String gender,int argsC,int n) throws SQLException{
		
		Map<String,Integer> data = Maps.newHashMap();
		Multiset<String> wordsMultiset = HashMultiset.create();
		
		String sql = "SELECT Occupation FROM  BloggerAuthorInfo 	WHERE ";
		sql = getWhereCondition(city, state, country, gender, argsC, sql);
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
		sql = getWhereCondition(city, state, country, gender, argsC, sql);
		
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
		sql = getWhereCondition(city, state, country, gender, argsC, sql);
		System.out.println(sql);
		ResultSet rs  = ConnectionUtils.executeSELECTSQL(sql);
		int count=0;
		while(rs.next()){
			count = rs.getInt(1);
			
		}
		return count;
	
	}

	private static String getWhereCondition(String city, String state,
			String country, String gender, int argsC, String sql) {
		if(city.length()>0){
			sql+=" City="+"'"+city+"'"+" AND ";
		}
		if(state.length()>0){
			sql+=" State="+"'"+state+"'"+" AND ";
		}
		if(country.length()>0 && gender.length()>0){
			sql+=" Country="+"'"+country+"'"+" AND ";
		}else{
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
	/*
	public static String translate() throws GoogleAPIException{

	    // Set the Google Translate API key
	    // See: http://code.google.com/apis/language/translate/v2/getting_started.html
		GoogleAPI.setHttpReferrer("http://dsdsdsd.com");
	    GoogleAPI.setKey("AIzaSyAgYKirsvHSokGca24ZZ_zSdwkSXUibSwM");

	    String translatedText = Translate.DEFAULT.execute("Bonjour le monde", Language.FRENCH, Language.ENGLISH);

	    System.out.println(translatedText);
		return translatedText;

	}
	public static String translatev2(String text) throws IOException, URISyntaxException, TranslatorException {

	    Translator translator =   new Translator("AIzaSyAgYKirsvHSokGca24ZZ_zSdwkSXUibSwM");
		Translation fromUnknown = translator.translate(text, null, "en");
		System.out.println("Detected Language " + fromUnknown.getDetectedSourceLanguage());
		return fromUnknown.getTranslatedText();
        
	}*/
	
	public static Map<String,Integer> getCitiesByTopics(String topic,int n) throws SQLException{
	
		Multiset<String> cityMultiset = HashMultiset.create();
		
		List<AuthorInfo> list = getInMemory();
		
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
	public static void main(String[] args) {
		
		try{
			
			String country = "United States";
			String state = "New York";
			String city = "Los Angeles";
			String topic = "music";
			System.out.println(getBloggersCount("", state, country, "", 2));
			Map<String,Integer> data = Maps.newHashMap();
			PieChart demo = null;

			
			data = getMajorOccupations("", "", country, "", 1,10);
			demo = new PieChart("Comparison", "Top 10 blogger's occupations in "+country,data);
		    demo.pack();
		    demo.setVisible(true);
		    /*
		    
		    data = getInterestsIntro("", state, country, "", 2,10);
			demo = new PieChart("Comparison", "Top 10 blogged topics in "+country,data);
		    demo.pack();
		    demo.setVisible(true);
		    
		    data = getCitiesByTopics(topic,10);
			demo = new PieChart("Comparison", "Top 10 cities with interest in "+topic,data);
		    demo.pack();
		    demo.setVisible(true);
		    */
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
