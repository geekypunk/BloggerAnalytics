package analyser.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.google.translate.api.v2.core.Translator;
import org.google.translate.api.v2.core.TranslatorException;
import org.google.translate.api.v2.core.model.Translation;

import analyser.charts.PieChart;
import analyser.dataobjects.AuthorInfo;
import analyser.utils.ConnectionUtils;
import analyser.utils.StringUtils;

import com.google.api.GoogleAPI;
import com.google.api.GoogleAPIException;
import com.google.api.translate.Language;
import com.google.api.translate.Translate;
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
		wordsMultiset.elementSet().remove("");
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
		
	public static Map<String,Integer> getInterestsIntro(String city,String state,String country,String gender,int argsC,int n) throws SQLException{
		
		Map<String,Integer> data = Maps.newHashMap();
		Multiset<String> wordsMultiset = HashMultiset.create();
		
		String sql = "SELECT Interests,Introduction FROM  BloggerAuthorInfo 	WHERE ";
		sql = getWhereCondition(city, state, country, gender, argsC, sql);
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
		wordsMultiset.elementSet().remove("");
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
		return sql;
	} 
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
        
	}
	public static void main(String[] args) {
		
		try{
			
			String country = "Spain";
			System.out.println(getBloggersCount("", "", country, "", 1));
			Map<String,Integer> data = getMajorOccupations("", "", country, "", 1,30);
			PieChart demo = new PieChart("Comparison", "Top 5 topics",data);
		    demo.pack();
		    demo.setVisible(true);
		    
		    
		    data = getInterestsIntro("", "", country, "", 1,30);
			demo = new PieChart("Comparison", "Top 5 topics",data);
		    demo.pack();
		    demo.setVisible(true);
		    translatev2("Bonjour le monde");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
