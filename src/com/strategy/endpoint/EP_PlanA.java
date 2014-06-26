package com.strategy.endpoint;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;

import com.config.Order;
import com.config.Record;
import com.config.Regex;
import com.config.WebConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.manger.Task;
import com.utils.HttpConn;
import com.utils.Monitor;
import com.utils.Tools;

public class EP_PlanA extends AbstractEndpoint {
	private static Logger logger = LogManager.getLogger(EP_PlanA.class.getName());
	
	@Override
	public String endpoint_strategy(String Contents,Order task,String Url)  {
		// TODO Auto-generated method stub
		String totalpage,currentpage,nexturl;
		WebConfig config = task.getConfig();
		Regex[]reg = config.getListPage().getReg();
		int len1 = reg.length,index2 = 0;
		HashMap<String, String> comm = new HashMap<String, String>();
		HashMap<String, String> tmp = new HashMap<String, String>();
		Monitor monitor = Monitor.getInstance();
		Tools tools = Tools.getInstance();
		
		for(int i=0;i<len1;i++)
		{
			if(reg[i].getName().equals("totalpage")||reg[i].getName().equals("currentpage")||reg[i].getName().equals("verdictkind"))
			{
				if((tmp = reg[i].doReg(Contents,task,Url,false)) != null)
					comm.putAll(tmp);
			}
			else if(reg[i].getName().equals("nexturl"))
			{
				index2 = i;//记下nexturl的正则表达式
				continue;
			}
		}
		
		totalpage = comm.get("totalpage");
		currentpage = comm.get("currentpage");
		
		if(totalpage == null || currentpage == null)
		{
			logger.warn("totalpage == null || currentpage == null url:"+task.getUrl());
			//Logger logger = Logger.getInstance();
			//logger.WriteError("totalpage == null || currentpage == null:"+task.getUrl());
			return null;
		}
		
		if(currentpage.equals(totalpage))
		{
			monitor.set(config.getWebName_en()+":"+comm.get("verdictkind"), 
					new Record(config.getWebName_en(),comm.get("verdictkind"),currentpage,totalpage));
			return null;
		}
		
		//nexturl
		if((tmp = reg[index2].doReg(Contents,task,Url,false)) == null){
			return null;
		}
		else{
			monitor.set(config.getWebName_en()+":"+comm.get("verdictkind"), 
					new Record(config.getWebName_en(),comm.get("verdictkind"),currentpage,totalpage));
			
			nexturl = tmp.get("nexturl");//tools.Realurl(tmp.get("nexturl"), config.getWeb_seed());
			System.out.println("nexturl: "+nexturl);
			return nexturl;
		}
		
		
	}
}
