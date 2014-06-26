package com.modify;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.WebConfig;
import com.log.Logger;

public class RealTime implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub
		String reg = "(\\d{4})[^0123456789]*(\\d{2})[^0123456789]*(\\d{2})[^0123456789]*(\\d*)[^0123456789]*(\\d*)[^0123456789]*(\\d*)";
		Logger logger = Logger.getInstance();
		HashMap<String, String> hm = new HashMap<String, String>();
		Pattern pt=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);					
		Matcher mt=pt.matcher(Group[0]);
		String date = "",month = null,day = null,hour = null,min = null,sec = null,year = null;
		while(mt.find())
		{
			year = mt.group(1).trim();
			month = mt.group(2).trim();
			day = mt.group(3).trim();
			hour = mt.group(4).trim();
			min = mt.group(5).trim();
			sec = mt.group(6).trim();
			
			if(year!=null){
				date += year; 
			}
			if(month!=null){
				date += "-"+month; 
			}
			if(day!=null){
				date += "-"+day; 
			}
			if(hour!=null){
				date += " "+hour; 
			}
			if(min!=null){
				date += ":"+min; 
			}
			if(sec!=null){
				date += ":"+sec; 
			}
			
		}
		return date;
	}

}
