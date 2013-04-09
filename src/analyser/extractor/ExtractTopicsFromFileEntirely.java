package analyser.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import maui.main.MauiWrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import analyser.dataobjects.AuthorInfo;

public class ExtractTopicsFromFileEntirely {
	static String vocabularyName = "agrovoc_en";
	static String modelName = "fao780";
	static String dataDirectory = "../Maui1.2/";
	static String path = "/home/kira/CommonCrawl/N-Quads/MicroData/blogspot.com/extractedNQs/";
	public static final int FILE_SIZE = 5*1024 * 1024;
	

	   
	private static void insertToDB(File file,PreparedStatement pstmt,MauiWrapper wrapper) {
	   
		
		Logger logger = Logger.getLogger("MyLog");
   	 	FileHandler fh;
   	 	
	    try{
	    	fh = new FileHandler("log/myLog", FILE_SIZE, 1, true);
	        logger.addHandler(fh);
	        logger.setLevel(Level.ALL);
	        SimpleFormatter formatter = new SimpleFormatter();
	        fh.setFormatter(formatter);
	    	BufferedReader br = new BufferedReader(new FileReader(file));
	    	String line;
	    	String[] tmp = null;
	    	String profileUrl = null,blogUrl=null;
	    	
	    	logger.log(Level.INFO, "Scanning file:"+file.getName()); 
	    	while ((line = br.readLine()) != null) {
	    		if(line.contains("http://www.blogger.com/profile/")){
	    			tmp = line.split(" ");
	    			profileUrl = tmp[2].substring(1, tmp[2].length()-1);
	    			blogUrl = tmp[3].substring(1, tmp[3].length()-1);
	    			Document doc = Jsoup.connect(profileUrl).timeout(10000).get();
	    			Element table = doc.select("table").first();
	    			String state="",city="",country="";
	    			if(table!=null){
	    				Elements aa = table.getElementsByAttributeValue("class", "item-key");
	    				if(aa.size()>0){
	    				AuthorInfo ai = new AuthorInfo();
	    				ai.setProfileUrl(profileUrl);
	    				String infoTitle=null;
	    				for(Element elem:aa){
	    					infoTitle = elem.text();
	    					
		    				String val = elem.nextElementSibling().text();
		    				if(infoTitle.equalsIgnoreCase("gender")){
		    					ai.setGender(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("occupation")){
		    					ai.setOccupation(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("interests")){
		    					ai.setInterests(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("introduction")){
		    					ai.setIntroduction(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("Favourite Books")){
		    					ai.setFavouriteBooks(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("Favourite Music")){
		    					ai.setFavouriteMusic(val);
		    				}
		    				if(infoTitle.equalsIgnoreCase("Favourite Films")){
		    					ai.setFavouriteFilms(val);
		    				}
		    				Element temp;
							String text;
		    				if(infoTitle.equalsIgnoreCase("location")){
		    					Element loc = elem.nextElementSibling();
		    					temp = loc.getElementsByAttributeValue("class", "locality").first();
			    				if(temp!=null){
			    					text = temp.text();
			    					if(text.indexOf(",")!=-1){
			    						city =  text.substring(0, text.lastIndexOf(","));
			    						ai.setCity(city);
			    					}
			    					else{
			    						ai.setCity(text);;
			    					}
			    					
			    				}
			    				temp = loc.getElementsByAttributeValue("class", "region").first();
			    				if(temp!=null){
			    					text = temp.text();
			    					if(text.indexOf(",")!=-1){
				    					state =  text.substring(0, text.lastIndexOf(","));
				    					ai.setState(state);
			    					}else{
			    						ai.setState(text);
			    					}
			    				}
			    				temp = loc.getElementsByAttributeValue("class", "country-name").first();
			    				if(temp!=null){
			    					country = temp.text();
			    					ai.setCountry(country);
			    				}
		    				
		    				}
	    				

	    				}
	    				System.out.println(ai.toString());
	    				System.out.println("-------------------------");
	    				pstmt.setString(1, ai.getProfileUrl());
						pstmt.setString(2, ai.getCity());
						pstmt.setString(3, ai.getState());
						pstmt.setString(4, ai.getCountry());
						pstmt.setString(5, ai.getGender());
						pstmt.setString(6, ai.getOccupation());
						pstmt.setString(7, ai.getInterests());
						if(ai.getIntroduction()!=null && ai.getIntroduction().length()>5){
							pstmt.setString(8, wrapper.extractTopicsFromText(ai.getIntroduction(),5).toString());
						}else{
							pstmt.setString(8, ai.getIntroduction());
						}
						
						pstmt.setString(9, ai.getFavouriteBooks());
						pstmt.setString(10, ai.getFavouriteFilms());
						pstmt.setString(11, ai.getFavouriteMusic());
						pstmt.setString(12, blogUrl);
						pstmt.executeUpdate();
	    				}
	    			}
	    		
	    		}
	    		
	    	}
	    	br.close();
					
	    }catch(Exception e){
	    	logger.log(Level.WARNING, e.toString());
	    }
   }
	        
	

	private static void crawlDirectoryAndProcessFiles(File directory,PreparedStatement pstmt,MauiWrapper wrapper) {
	    for (File file : directory.listFiles()) {
	    	if (file.isFile()) {
		        
	    		if(file.getName().endsWith(".sort")){
	    			insertToDB(file, pstmt, wrapper); 
	    		}
	    	}
	    }
    }
	
	
	private static Connection getDBConnection(){
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
	public static void main(String args[]) throws FileNotFoundException {

		
	    
		try {
			Connection conn = getDBConnection();
			PreparedStatement pstmt = conn.prepareStatement("INSERT into BloggerAuthorInfo VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");	
			File folder = new File(path);
			MauiWrapper wrapper = new MauiWrapper(dataDirectory, vocabularyName, modelName);
			crawlDirectoryAndProcessFiles(folder,pstmt,wrapper);

		}
		 catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
}
