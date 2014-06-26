package com.strategy.endpoint;

import java.util.HashMap;

import com.config.Order;
import com.config.Record;
import com.config.Regex;
import com.config.WebConfig;
import com.utils.HttpConn;
import com.utils.Monitor;
import com.utils.Tools;

//下一页是否为空
public class EP_PlanB extends AbstractEndpoint {

	@Override
	public String endpoint_strategy(String Contents,Order task,String Url) {
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
				if((tmp = reg[i].doReg(Contents,task,Url,true)) != null)
					comm.putAll(tmp);
			}
			else if(reg[i].getName().equals("nexturl"))
			{
				index2 = i;//记下nexturl的正则表达式
				continue;
			}
		}
		
		//nexturl
		tmp = reg[index2].doReg(Contents,task,Url,false);
		if(tmp == null||tmp.equals(task.getUrl())){
			currentpage = comm.get("currentpage");
			
			monitor.set(config.getWebName_en()+":"+comm.get("verdictkind"), 
					new Record(config.getWebName_en(),comm.get("verdictkind"),currentpage,currentpage));
			
			return null;
		}
		else{
			totalpage = comm.get("totalpage");
			currentpage = comm.get("currentpage");
			
			monitor.set(config.getWebName_en()+":"+comm.get("verdictkind"), 
					new Record(config.getWebName_en(),comm.get("verdictkind"),currentpage,totalpage));
			
			nexturl = tmp.get("nexturl");//tools.Realurl(tmp.get("nexturl"), config.getWeb_seed());
			System.out.println("nexturl: "+nexturl);
			return nexturl;
		}	
		
	}

}
