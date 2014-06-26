package com.log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import lombok.Data;

import com.dao.Verdict;

public class Logger {
	private static String path;
	private static Logger instance = null;
	private static Hashtable<String, String> infor = new Hashtable<String, String>();
	
	private Logger() {
	}
	
	public synchronized static Logger getInstance(){
		if(instance == null){
			instance = new Logger();
		}
		return instance;
	}
	

	/*public synchronized static void WriteError(String content){
		File file = new File(path); 
		if (!file.exists()) { 
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		} 
		
		try {
			BufferedWriter bw1 = new BufferedWriter(new FileWriter(path, true));
			bw1.write(content+System.getProperty("line.separator"));
			bw1.flush();
			bw1.close();
		}catch (IOException e) {
			e.printStackTrace();
		}
		
	}*/
	
	public static void WriteInf(String fpath,String key,String content){
		infor.put(key, content);
		try {
			FileWriter fw = new FileWriter(fpath); 
			for (Iterator it = infor.keySet().iterator(); it.hasNext(); ) {
			    key = (String) it.next();
			    fw.write(key+":"+(String)infor.get(key)+System.getProperty("line.separator"));
			} 
			
			fw.close(); 
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  	
	}

	
	public static String getPath() {
		return path;
	}

	public static void setPath(String path) {
		Logger.path = path;
	}

	public static Hashtable<String, String> getInfor() {
		return infor;
	}

	public static void setInfor(Hashtable<String, String> infor) {
		Logger.infor = infor;
	}

	public static void setInstance(Logger instance) {
		Logger.instance = instance;
	}
}
