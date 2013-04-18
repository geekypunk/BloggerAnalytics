package analyser.extractor;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.Connection;

import analyser.dataobjects.AuthorInfo;

import com.google.common.collect.Maps;

public class StandaloneJsoup {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		File f = new File("sampleBetaBloggerProfile.html");
		
		Map<String,String> cookies = Maps.newHashMap();
		Document doc= Jsoup.parse(FileUtils.readFileToString(f));
		Element table = doc.select("table").first();
		String state="",city="",country="";
		if(table!=null){
			Elements aa = table.getElementsByAttributeValue("class", "item-key");
			if(aa.size()>0){
			AuthorInfo ai = new AuthorInfo();
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

	}

}
		
	}
	
}
