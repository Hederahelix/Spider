package com.manger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import com.config.GlobalConfig;
import com.config.Order;
import com.config.Page;
import com.config.Regex;
import com.config.WebConfig;
import com.dao.Verdict;
import com.dao.VerdictDao;
import com.strategy.factory.Factory;
import com.utils.HttpConn;
import com.utils.Redis;



public class Task implements Runnable{
	private ThreadLocal<LinkedList<Order>> taskQue = new ThreadLocal<LinkedList<Order>>(); 
	private static AtomicInteger count = new AtomicInteger(0);//�Ѿ���ɵ�����
	private Order initorder;	
	public static final int SEEDTASK = 1;//��seedUrl����ȡsortUrl������fun1
	public static final int SORTTASK = 2;//��sortUrl����ȡpageUrl������fun2
	public static final int PAGETASK = 3;//��pageUrl����ȡ����Ҫ��Ϣ������fun3
	private static Logger logger = LogManager.getLogger(Task.class.getName());
	
	public Task(Order order) {
		initorder = order;
	}
	
	public void init()
	{
		taskQue.set(new LinkedList<Order>());
		taskQue.get().offer(initorder);
	}

	public void increase(int seedid)
	{
		System.out.println("seedid: "+seedid+"finish:"+count.incrementAndGet());
		System.out.flush();
	}
	
	//��queȡ��һ��seedurlȻ��ȥ��ȡsorturl����que
	public void fun1(Order order) {
		int seedId = order.getConfig().getSeedId();
		
		String Url = order.getUrl();
		String contents = HttpConn.getSource(Url);

		WebConfig config = order.getConfig();
		Page Sortpage = config.getSortPage();

		HashMap<String, String> comm = Sortpage.doReg(contents, order, Url);

		
		String []sorturl;
		String sort_url;
		//Logger logger = Logger.getInstance();
		
		if(comm.get("sorturl") != null)
		{
			sorturl = comm.get("sorturl").split("<>");
			
			String key = null;
			Verdict info = null;
			for(int i=0;i<sorturl.length;i++)
			{
				sort_url = sorturl[i];
				info = new Verdict(order.getInfo());

				for (Iterator it = comm.keySet().iterator(); it.hasNext(); ) 
				{
				    key = (String) it.next();
				    System.out.println(key);
				    if(info.getFunction(key) == null)
				    {
				    	if(comm.get(key).contains("<>"))
					    	info.setFunction(key, comm.get(key).split("<>")[i]);
					    else
					    	info.setFunction(key, comm.get(key));
				    } 			    
				}
				
				taskQue.get().offer(new Order(SORTTASK,sort_url,info,config,new Factory(config)));
			}
		}
		else
		{
			//logger.WriteError("fail to get sorturl from "+Url);
			logger.warn("fail to get sorturl from "+Url);
		}		
	}
	
	//��sortUrl��ȡpageUrl
	public void fun2(Order order) {
		int seedId = order.getConfig().getSeedId();	
		Redis redis = Redis.getInstance();
		String contents,url;
		Verdict info;
		HashMap<String, String> comm = null;
		
		url=order.getUrl();
		System.out.println(url);
		System.out.flush();
		
		contents = HttpConn.getSource(url);
		Page Listpage = order.getConfig().getListPage();
		//ֻȥ��list��url
		String []pageurl;
		String page_url;
		
		comm = Listpage.doReg(contents, order, url);
		
		if(comm.get("pageurl") != null)//��ȥpageurl
		{
			pageurl = comm.get("pageurl").split("<>");
			
			for(int i=0;i<pageurl.length;i++)
			{	
				page_url = pageurl[i];
				if(!redis.exists(page_url))//���id����ַ��û����ȥ��
				{
					info=new Verdict(order.getInfo());
					info.setUrl(page_url);
					System.out.println(page_url);
					System.out.flush();
					
					for (Iterator it = comm.keySet().iterator(); it.hasNext(); ) 
					{
						String key = (String) it.next();

					    if(info.getFunction(key) == null)
					    {
					    	if(comm.get(key).contains("<>"))
						    	info.setFunction(key, comm.get(key).split("<>")[i]);
						    else
						    	info.setFunction(key, comm.get(key));
					    } 
					} 

					taskQue.get().offer(new Order(PAGETASK,page_url,info,order.getConfig(),order.getFactory()));
					
					redis.set(page_url,"");
				}
				else//���id����ַ�Ѿ���ȥ���� ����Ͷ�������
				{
					logger.trace("���id����ַ�Ѿ���ȥ���� ����Ͷ�������");
					//Logger logger = Logger.getInstance();
					//System.out.println("���id����ַ�Ѿ���ȥ����");
					//logger.WriteError("���id����ַ�Ѿ���ȥ����:"+page_url);
					//return;
				}
			}
		}else{
			//Logger logger = Logger.getInstance();
			//System.out.println("���id����ַ�Ѿ���ȥ����");
			//logger.WriteError("�����ַ��pageUrl���ܻ�ȡ:"+url);
			logger.warn("�����ַ��pageUrl���ܻ�ȡ:"+url);
		}
		
	
		//��һҳ����
		String nextUrl = order.endpoint_strategy(contents,url);
		if(nextUrl != null)
		{
			taskQue.get().offer(new Order(SORTTASK,nextUrl,new Verdict(order.getInfo()),order.getConfig(),order.getFactory()));
		}

	}
	
	public void fun3(Order order) {
		int seedId = order.getConfig().getSeedId();
		String key;
		String contents,url;
		Verdict info;
		HashMap<String, String> comm = null;

		comm = new HashMap<String, String>();
		url = order.getUrl();
		contents = HttpConn.getSource(url);
		Page Page = order.getConfig().getPage();
		
		if(contents != null)
		{
			contents = order.decrypt_strategy(contents);//��ҳ�����
			info = order.getInfo();
			comm = Page.doReg(contents, order,url);
			
			for (Iterator it = comm.keySet().iterator(); it.hasNext(); ) 
			{
			    key = (String) it.next();
			    if(info.getFunction(key) == null)
			    {
			    	info.setFunction(key, (String)comm.get(key));
			    }
				
			} 

			VerdictDao vdd = new VerdictDao();
			vdd.save(info);//�������ݿ�		
		}
	}
	
	public void fun(Order order)
	{
		switch(order.getTaskStyle())
		{
			case SEEDTASK:
				fun1(order);
				break;
			case SORTTASK:
				fun2(order);
				break;
			case PAGETASK:
				fun3(order);
				increase(order.getConfig().getSeedId());
				break;
		}
	}

	@Override
	public void run() 
	{
		init();
		LinkedList<Order> que = taskQue.get();
		GlobalConfig gf= GlobalConfig.getInstance();
		//Logger logg = Logger.getInstance();
		String area = taskQue.get().getFirst().getConfig().getArea();
		//logg.WriteInf(gf.getPro_path()+"thread.txt", Thread.currentThread().getName(), "����"+area);
		logger.info(" [thread] "+Thread.currentThread().getName()+"����"+area);
		
		Order order;
		while(!que.isEmpty())
		{
			order=que.remove();
			fun(order);	
		}
		//logg.WriteInf(gf.getPro_path()+"thread.txt", Thread.currentThread().getName(), "���"+area);	
		logger.info(" [thread] "+Thread.currentThread().getName()+"����"+area);
	}

}
