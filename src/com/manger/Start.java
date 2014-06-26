package com.manger;



import java.io.File;
import java.security.Timestamp;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.GlobalConfig;
import com.config.Order;
import com.config.Page;
import com.config.Regex;
import com.config.WebConfig;
import com.dao.Verdict;
import com.log.Logger;
import com.mysql.jdbc.TimeUtil;
import com.strategy.factory.Factory;
import com.utils.HttpConn;
import com.utils.Monitor;
import com.utils.Tools;
import com.utils.XmlReader;



public class Start {
	
	public static void init(){
		//读配置文件
		XmlReader x=new XmlReader();
		
		//读取网站全部配置文件
		x.readGlobalConfig("config_xml/config.xml");//读取全局配置文件
		GlobalConfig cf = GlobalConfig.getInstance();
		Logger logger = Logger.getInstance();
		logger.setPath(cf.getLog_path());
		Monitor monitor = Monitor.getInstance();
		monitor.setPath("pro/pro.txt");//设置进度文件
	}
	
	public static void start(){
		GlobalConfig cf = GlobalConfig.getInstance();
		//System.out.println(cf.getThread_num());
		ExecutorService pool = Executors.newFixedThreadPool(cf.getThread_num());
		WebConfig config;
		XmlReader x=new XmlReader();
		
		//从配置文件读取seedurl并将解析sorturl放入任务队列1
		for(int i=1;i<26;i++)//读取各个有网站配置文件
		{
			if(new File(cf.getXml_path()+i+".xml").exists())
			{
				config = x.readWebConfig(cf.getXml_path()+i+".xml");
				config.setSeedId(i);
				String Url = config.getSort_url();
				Verdict info = new Verdict();
				info.setArea(config.getArea());
				Task task = new Task(new Order(Task.SEEDTASK,Url,info,config,new Factory(config)));
				pool.execute(task);
			}
			else
				continue;
		}

		
		pool.shutdown();
		
		try {
			while(!pool.awaitTermination(2, TimeUnit.SECONDS))
				;
				//System.out.println("wait");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	
	public static void main(String[] args) {
		// TODO Auto-generated method stub		
		init();
		start();
		System.out.println("complete");
		System.out.flush();
	}

}
