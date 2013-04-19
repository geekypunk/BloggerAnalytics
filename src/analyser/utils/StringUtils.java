package analyser.utils;

import java.util.Arrays;
import java.util.List;

import com.google.common.collect.Lists;

public class StringUtils {

	public static String removeEnclosingBraces(String s){
		if(s.length()>2){
			String newString = s.substring(1, s.length()-2);
			return newString;
		}
		return "";
	}
	public static List<String> convertToStringList(String s){
		
		List<String> items = Arrays.asList(s.split("\\s*,\\s*|\\s+"));
		return items;
		
	}
	
	public static List<String> convertAllToLowerCase(List<String> list){
		
		List<String> items = Lists.newArrayList();
		for(String s:list){
			
			items.add(s.trim().toLowerCase());
		}
		return items;
		
	}
	
}
