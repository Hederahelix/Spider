package com.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.dao.Verdict;

import com.modify.Modify;
import com.strategy.decrypt.AbstractDecrypt;
import com.utils.Tools;

public class Regex extends Dynamic{
	private String reg;
	private String name;
	private String model;
	private String group_num;
	private String group[];
	private String modify;

	private static Logger logger = LogManager.getLogger(Regex.class.getName());


	
	private String regonce(Matcher mt,String contents,WebConfig Conf,String url) 
	{
		String value = null;
		Modify mod = null;
		int num = Integer.parseInt(group_num);
		String tvalue[] = new String[num];
		Tools tools = Tools.getInstance();
		
		//Logger logger = Logger.getInstance();

		
		try 
		{
			for(int i=0;i<num;i++)
			{
				if(mt.group(i+1)!=null)
				{
					if(name.equals("content"))
						tvalue[i] = mt.group(i+1);//去掉标签
					else
						tvalue[i] = tools.parse(mt.group(i+1));//去掉标签
				}
				else//group没有匹配完
				{
					tvalue[i] = null;
					logger.error("fail to parse some group: "+url+" "+this);
					//logger.WriteError("fail to parse some group: "+url+" "+this+" contents:"+contents);
				}					
			}//FOR
		} catch (java.lang.IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			System.out.println("error:"+url+" "+this+" contents:"+contents);
			System.out.flush();
			e.printStackTrace();
		}
		
		
		if(modify!=null)
		{
			try {
				mod = (Modify) Class.forName("com.modify."+modify).newInstance();
				value = mod.execute(tvalue, Conf);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			value = tvalue[0];
		}
		
		if(name.equals("time"))
			value = tools.Realtime(value);
		
		return value;
	} 
	
	public HashMap doReg(String contents,WebConfig Conf,String url,boolean ignore){
		HashMap<String, String> hm = new HashMap<String, String>();
		Pattern pt=Pattern.compile(reg,Pattern.CASE_INSENSITIVE);					
		Matcher mt=pt.matcher(contents);
		boolean b = false;
		String key = name,value = null;
		//Logger logger = Logger.getInstance();
		
		int pos = 1,count = 1;
		String kind = model.split("-")[0];
		if(kind.equals("single"))
			pos = Integer.valueOf(model.split("-")[1]);
			
		while(mt.find())
		{
			if((kind.equals("single"))&&(pos!=count))
			{
				count++;
				continue;
			}
			
			value = regonce(mt,contents,Conf,url);
			if(value != null)
				b = true;
			
			if(hm.containsKey(key)){
				value = value+"<>"+hm.get(key);
			}
			
			hm.put(key, value);
			count++;
		}//while
		
		if(b)
			return hm;
		else
		{
			if(!ignore)
				//logger.WriteError("fail to parse reg:"+url+" "+this+" contents:"+contents);
				logger.warn("fail to parse reg:"+url+" "+this);//可能是url的问题
			return null;
		}
	}
	
	public HashMap doReg(String contents,Order task,String Url,boolean ignore){
		HashMap<String, String> hm = doReg(contents,task.getConfig(),Url,ignore);
		
		//将Url地址补全
		if(hm != null)
		{
			String webseed = task.getConfig().getWeb_seed();
			String current = task.getUrl();
			String tmp = null;
			Tools tools = Tools.getInstance();
			if(hm.containsKey("sorturl"))
			{
				if(hm.get("sorturl").contains("<>"))
				{
					tmp = "";
					for(String url:hm.get("sorturl").split("<>"))
					{
						tmp += "<>"+tools.Realurl(url, webseed, current);
					}
					tmp = tmp.substring("<>".length());
				}else{
					
					tmp = tools.Realurl(hm.get("sorturl"),webseed,current);
				}
				
				hm.put("sorturl", tmp);
				
			}else if(hm.containsKey("pageurl"))
			{
				if(hm.get("pageurl").contains("<>"))
				{
					tmp = "";
					for(String url:hm.get("pageurl").split("<>"))
					{
						tmp += "<>"+tools.Realurl(url, webseed, current);
					}
					tmp = tmp.substring("<>".length());
					
				}else{
					
					tmp = tools.Realurl(hm.get("pageurl"),webseed,current);
				}
				
				hm.put("pageurl", tmp);
				
			}else if(hm.containsKey("nexturl"))
			{
				if(hm.get("nexturl").contains("<>"))
				{
					tmp = "";
					for(String url:hm.get("nexturl").split("<>"))
					{
						tmp += "<>"+tools.Realurl(url, webseed, current);
					}
					tmp = tmp.substring("<>".length());
					
				}else{
					
					tmp = tools.Realurl(hm.get("nexturl"),webseed,current);
				}
				
				hm.put("nexturl", tmp);
			}
			
			return hm;
		}
		else
			return null;
	}

	
	public String getReg() {
		return reg;
	}

	public void setReg(String reg) {
		this.reg = reg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getGroup_num() {
		return group_num;
	}

	public void setGroup_num(String group_num) {
		this.group_num = group_num;
	}

	public String[] getGroup() {
		return group;
	}

	public void setGroup(String[] group) {
		this.group = group;
	}

	public String getModify() {
		return modify;
	}

	public void setModify(String modify) {
		this.modify = modify;
	}

	
	
}
