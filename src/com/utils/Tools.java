package com.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.log.Logger;


public class Tools {
	private static Tools instance = null;
	
	private Tools() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public synchronized static Tools getInstance(){
		if(instance == null){
			instance = new Tools();
		}
		return instance;
	}
	
	public String decrypt(String contents,String path) throws FileNotFoundException, ScriptException, NoSuchMethodException
	{
		ScriptEngineManager mgr = new ScriptEngineManager(); 
		ScriptEngine engine = mgr.getEngineByName("JavaScript");
		BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(new File(path)))); 
		engine.eval(buf);  
		Invocable inv = (Invocable) engine;
		
		String res = null;
		res = (String) inv.invokeFunction("paperDecode",contents);	
		return res;
	}
	
	public String parse(String contents) 
	{  
		String regex ="<[^>]+>|&nbsp;";   
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		
		Matcher mt=pt.matcher(contents);
		return mt.replaceAll("").trim();
	}
	
	public String Seedurl(String url){
		String webseed;
		if(url.startsWith("http://")){
			webseed = url.substring("http://".length());
			webseed = webseed.split("/")[0];
		}else{
			webseed = url.split("/")[0];
		}
		webseed = "http://" + webseed;
		//System.out.println(webseed);
		return webseed;
	}
	
	public String Realurl(String url,String webseed,String current){
		String tmp;
		String realurl=new String(url);
		if(!realurl.startsWith(webseed))
		{
			if(realurl.startsWith("/"))
			{
				realurl = webseed + realurl;
			}
			else
			{
				current = current.substring("http://".length());
				if(current.contains("/"))
				{
					int endIndex = current.lastIndexOf("/");
					tmp = current.substring(0,endIndex);
				}
				else
					tmp = current;
				realurl = "http://"+tmp+"/"+realurl;
			}
				
		}
		String regex ="&amp;";
		tmp = realurl;  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(tmp);
		realurl = mt.replaceAll("&");
		return realurl;
	}

	public String Realtime(String time){
		String reg = "(\\d{4})[^0123456789]*(\\d{2})[^0123456789]*(\\d{2})[^0123456789]*(\\d*)[^0123456789]*(\\d*)[^0123456789]*(\\d*)";
		HashMap<String, String> hm = new HashMap<String, String>();
		Pattern pt=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);					
		Matcher mt=pt.matcher(time);
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
