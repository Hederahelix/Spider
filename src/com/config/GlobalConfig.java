package com.config;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import lombok.Data;


public class GlobalConfig extends Dynamic {
	private int thread_num;
	private int seedurlsize;//不是从xml读取的
	private String start_time;
	private String log_path;
	private String pro_path;
	private String decrypt_path;
	private String xml_path;
	private static GlobalConfig instance = null;
	
	private GlobalConfig() {
		// TODO Auto-generated constructor stub
	}	

	public synchronized static GlobalConfig getInstance(){
		if(instance == null){
			instance = new GlobalConfig();
		}
		return instance;
	}

	public int getThread_num() {
		return thread_num;
	}

	public void setThread_num(int thread_num) {
		this.thread_num = thread_num;
	}

	public int getSeedurlsize() {
		return seedurlsize;
	}

	public void setSeedurlsize(int seedurlsize) {
		this.seedurlsize = seedurlsize;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getLog_path() {
		return log_path;
	}

	public void setLog_path(String log_path) {
		this.log_path = log_path;
	}

	public String getPro_path() {
		return pro_path;
	}

	public void setPro_path(String pro_path) {
		this.pro_path = pro_path;
	}

	public String getDecrypt_path() {
		return decrypt_path;
	}

	public void setDecrypt_path(String decrypt_path) {
		this.decrypt_path = decrypt_path;
	}

	public String getXml_path() {
		return xml_path;
	}

	public void setXml_path(String xml_path) {
		this.xml_path = xml_path;
	}

	public static void setInstance(GlobalConfig instance) {
		GlobalConfig.instance = instance;
	}

	
}
