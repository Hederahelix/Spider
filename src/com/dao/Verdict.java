package com.dao;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import lombok.Data;

import com.config.Dynamic;


public class Verdict extends Dynamic{
	private String title;
	private String time;
	private String court;
	private String verdictkind;
	private String number;
	private String content;
	private String url;
	private String area;
	private static Logger logger = LogManager.getLogger(VerdictDao.class.getName());
	
	public Verdict() 
	{
		this.title = null;
		this.time = null;
		this.court = null;
		this.verdictkind = null;
		this.number = null;
		this.content = null;
		this.url = null;
		this.area = null;
	}
	
	public Verdict(String title, String time, String court, String verdictkind,
			String number, String content, String url) 
	{
		this.title = title;
		this.time = time;
		this.court = court;
		this.verdictkind = verdictkind;
		this.number = number;
		this.content = content;
		this.url = url;
		this.area = area;
	}
	
	public Verdict(Verdict verdict) 
	{
		if(verdict.getTitle()!=null)
			this.title = new String(verdict.getTitle());
		else	
			this.title = null;
		
		if(verdict.getTime()!=null)
			this.time = new String(verdict.getTime());
		else	
			this.time = null;
		
		if(verdict.getCourt()!=null)
			this.court = new String(verdict.getCourt());
		else
			this.court = null;
		
		if(verdict.getVerdictkind()!=null)
			this.verdictkind = new String(verdict.getVerdictkind());
		else
			this.verdictkind = null;
		
		if(verdict.getNumber()!=null)
			this.number = new String(verdict.getNumber());
		else
			this.number = null;
		
		if(verdict.getContent()!=null)
			this.content = new String(verdict.getContent());
		else
			this.content = null;
		
		if(verdict.getUrl()!=null)
			this.url = new String(verdict.getUrl());
		else
			this.url = null;
		
		if(verdict.getArea()!=null)
			this.area = new String(verdict.getArea());
		else
			this.area = null;
	}

	public void check()
	{
		String info = "url: "+url;
		boolean flag = false;
		
		if(title == null)
		{
			flag = true;
			info += " title是空的 ";
		}
			
		if(time == null)
		{
			flag = true;
			info += " time是空的 ";
		}
		
		if(court == null)
		{
			flag = true;
			info += " court是空的 ";
		}	
		
		if(verdictkind == null)
		{
			flag = true;
			info += " verdictkind是空的 ";
		}
			
		if(number == null)
		{
			flag = true;
			info += " number是空的 ";
		}
			
		if(content == null)
		{
			flag = true;
			info += " content是空的 ";
		}
			
		if(area == null)
		{
			flag = true;
			info += " area是空的 ";
		}
			
		if(flag)
			logger.warn("[result] "+info);
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCourt() {
		return court;
	}

	public void setCourt(String court) {
		this.court = court;
	}

	public String getVerdictkind() {
		return verdictkind;
	}

	public void setVerdictkind(String verdictkind) {
		this.verdictkind = verdictkind;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	
}
