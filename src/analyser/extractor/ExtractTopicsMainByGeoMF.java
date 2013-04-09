package analyser.extractor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import maui.main.MauiWrapper;

public class ExtractTopicsMainByGeoMF {
	static String vocabularyName = "agrovoc_en";
	static String modelName = "fao780";
	static String dataDirectory = "../Maui1.2/";
	static String path = "/home/kira/CommonCrawl/N-Quads/MicroData/blogspot.com/extractedNQs/";
	static String perCompiledpath = "/home/kira/CommonCrawl/N-Quads/MicroData/blogspot.com/patterns";
	static String pathOfUrls = "/home/kira/CommonCrawl/N-Quads/MicroData/blogspot.com/";
	

	   
	private static void insertToDB(File file,PreparedStatement pstmt,MauiWrapper wrapper) {
	    	
	    try{
	    
	    	System.out.println(file.getName());
			/*
	    	String command = "grep -f "+pathOfUrls+"allUniqUrls "+path+file.getName()+" | grep '<http://schema.org/BlogPosting/articleBody>'";
			System.out.println(command);
			ProcessBuilder pb = new ProcessBuilder("bash", "-c", command);
			pb.redirectErrorStream(true);
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));
			*/
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			String line = reader.readLine();
			while (line != null) {
				line = reader.readLine();
				if(line!=null){
					line = line.replaceAll("\\\\n", "");
					int first = line.indexOf('"');
					int last = line.lastIndexOf('"');
					if(last-first>10){
						System.out.println(line.substring(first+1, last));
						
						ArrayList<String> keywords = wrapper.extractTopicsFromText(line.substring(first+1, last), 15);
						if(keywords.size()>0){
    						String url = line.substring(last+1,line.lastIndexOf("   ."));
    						String finalURL = url.trim().substring(1, url.length()-2);
    						System.out.println(finalURL);
    						System.out.println("-------------------------");
    						pstmt.setString(1, finalURL);
    						pstmt.setString(2, file.getName());
    						pstmt.setString(3, keywords.toString());
    						pstmt.executeUpdate();
						}
					}
				}
			}
			//p.waitFor();
			reader.close();
	    }catch(Exception e){
	    	e.printStackTrace();
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
			PreparedStatement pstmt = conn.prepareStatement("INSERT into BlogURLTopics VALUES(?,?,?)");	
			File folder = new File(path);
			MauiWrapper wrapper = new MauiWrapper(dataDirectory, vocabularyName, modelName);
			insertToDB(new File(perCompiledpath), pstmt, wrapper);
			//crawlDirectoryAndProcessFiles(folder,pstmt,wrapper);

		}
		 catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}
}
