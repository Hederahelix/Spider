package com.utils;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.config.DBAccess;
import com.config.Dynamic;
import com.config.GlobalConfig;
import com.config.Regex;
import com.config.WebConfig;
import com.strategy.decrypt.Dy_PlanB;

public class test {
	
	public static void main(String[] args) {
		/*
		XmlReader x=new XmlReader();
		HttpConn conn = HttpConn.getInstance();
		WebConfig config = x.readWebConfig("D:/workspace/Crawl_Article_index/config_xml/2.xml");
		String url="http://ws.hncourt.org/index.php?cat2_id=1.01";
		String contents = conn.getSource(url);
		
		Regex reg = config.getListPage().getReg()[0];
		Pattern pt;
		Matcher mt;
		
		System.out.println(reg.getReg());
		System.out.println(reg.getReg().equals("<td width=\"31%\" height=\"29\" align=\"left\" background=\"images/main_back3a.jpg\" class=\"main_white_big2\">([^<]*)"));
		//pt=Pattern.compile("共(\\d+?)页",Pattern.CASE_INSENSITIVE);
		pt=Pattern.compile(reg.getReg(),Pattern.CASE_INSENSITIVE);
			
			
		mt=pt.matcher(contents);

		if(mt.find())
		{
			System.out.println("res: "+mt.group(1).trim());
		}
		
		String[] a,b,c = null;
		c = new String[2];
		c[0]="ASD";
		c[1]="ASD";
		System.out.println(c[1]);
		Hashtable<String, String> lasttime = new Hashtable<String, String>();
		lasttime.put("1", "value");
		lasttime.put("1", "value1");
		System.out.println(lasttime.get("2"));
		
		Jedis jj = new Jedis("localhost");        
		jj.set("key1", "I am value 1");        
		String ss = jj.get("key1");        
		System.out.println(ss);
		Hashtable<String, String> comm = new Hashtable<String, String>();
		comm.put("a", "a");
		comm.put("b", "b");
		comm.put("c", "c");
		comm.put("d", "d");
		comm.put("e", "e");
		for (Iterator it = comm.keySet().iterator(); it.hasNext(); ) {
		    String key = (String) it.next();
		    String value = comm.get(key);
		    System.out.println(key+value);
		} 
		
		String str[]={"aa","bb","cc"};
		fun(str);
		for(int i=0;i<str.length;i++)
			System.out.println(str[i]);*/
		//String a = "http://www.gdcourts.gov.cn/gdcourt/front/front!list.action?lmdm=LM45|http://www.gdcourts.gov.cn/gdcourt/front/front!list.action?lmdm=LM44|http://www.gdcourts.gov.cn/gdcourt/front/front!list.action?lmdm=LM43|http://www.gdcourts.gov.cn/gdcourt/front/front!list.action?lmdm=LM42";
		/*String realurl = "/index/more/1.html";
		
		String current = "http://www.baidu.com/public/3.html";
		String tmp;
		
		String webseed;*/
		/*if(current.startsWith("http://")){
			webseed = current.substring("http://".length());
			webseed = webseed.split("/")[0];
		}else{
			webseed = current.split("/")[0];
		}
		webseed = "http://" + webseed;*/
		
		/*Tools tools = Tools.getInstance();
		webseed =  tools.Seedurl(current);
		System.out.println(webseed);
		
		
		if(!realurl.startsWith(webseed))
		{
			if(realurl.startsWith("/"))
			{
				if(realurl.startsWith("http://"))
				{
					tmp = webseed.
					realurl = tmp+realurl;
				}else{
					tmp = webseed.split("/")[0];
					realurl = tmp+realurl;
				}
				realurl = webseed+realurl;
			}
			else
			{
				int endIndex = current.lastIndexOf("/");
				tmp = current.substring(0,endIndex); 
				endIndex = webseed.lastIndexOf("/");
				webseed = webseed.substring(0,endIndex); 
				realurl = tmp+"/"+realurl;
			}
				
		}
		System.out.println(realurl);
		String regex ="&amp;",tmp = realurl;  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(tmp);
		realurl = mt.replaceAll("&");*/
		/*String c = "asd<>asd";
		
		for(String url:c.split("<>"))
		{
			System.out.println("asd");
		}
		HttpConn conn = HttpConn.getInstance();
		String Contents = conn.getSource("http://218.94.26.51:8081/wsfb/show.action?search.ajxh=86488&search.wsly=320300%20A30");
		//System.out.println(Contents);
		
		Tools tools = Tools.getInstance();
		String regex ="tm\\[\\d*\\]=\"([^;]*)\"";  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(Contents);  
		 StringBuffer res=new StringBuffer();

		 while(mt.find())
		 {
			 try {
				res.append(tools.decrypt(mt.group(1),"G:/Workspaces/Spider/lib/paperDecode.js"));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
		 }

		System.out.println(res.toString());*/
		
		/*HttpConn conn = HttpConn.getInstance();
		String Contents = conn.getSource("http://ws.hncourt.org/");
		System.out.println(getEncoding(Contents.substring(0, 100)));
		String regex ="([\u4e00-\u9fa5]*法院)";  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(Contents);  

		 while(mt.find())
		 {
			
			 System.out.println(mt.group(1));
		 }
		 System.out.println("ok");*/
		
		//System.out.println(Contents);
		//System.out.println(StringEscapeUtils.unescapeHtml(Contents));
		
		/*String regex ="http://cdfy.chinacourt.org/article/index/id/M8gqMDAwNDAwMiACAAA%3D.shtml";
		if(regex.contains("http://"))
			regex = regex.substring("http://".length());
		
		
		System.out.println(regex);*/
		
		
		
		JedisPoolConfig config = new JedisPoolConfig();

        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；

        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。

        config.setMaxActive(500);

        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。

        config.setMaxIdle(5);

        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；

        config.setMaxWait(1000 * 100);

        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；

        config.setTestOnBorrow(true);
        
        config.setTestOnReturn(true);

        JedisPool pool = new JedisPool(config, "localhost");
        
        Jedis jedis = pool.getResource();  
        
        String keys = "name";  
          
        // 删数据  
        jedis.del(keys);  
        // 存数据  
        jedis.set(keys, "snowolf");  
        // 取数据  
        String value = jedis.get(keys);  
          
        System.out.println(value);  
          
        Map<String,String> user=new HashMap<String,String>();  
        user.put("name","minxr");  
        user.put("pwd","password");  
        jedis.hmset("user",user); 
        List<String> rsmap = jedis.hmget("user", "name");  
        System.out.println(rsmap);  
        // 释放对象池  
        pool.returnResource(jedis);
        
		return;
	}

}
