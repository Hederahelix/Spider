package com.utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import com.config.Record;


public class Monitor {
	private HashMap<String, Record> data;
	private String path;
	private static Monitor instance = null;
	
	private Monitor() {
		data = new HashMap<String, Record>();
	}
	
	public void setPath(String path)
	{
		this.path = path;
	}
	
	public synchronized void set(String key,Record value)
	{
		
		data.put(key, value);
		try {
			FileWriter fw = new FileWriter(path);
			for (Iterator it = data.keySet().iterator(); it.hasNext(); ) {
			    key = (String) it.next();
			    //System.out.println(key+value);
			    fw.write(data.get(key).toString()+System.getProperty("line.separator"));
			}

	        fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized static Monitor getInstance(){
		if(instance == null){
			instance = new Monitor();
		}
		return instance;
	}
	
	
}
