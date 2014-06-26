package com.manger;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

import com.config.Order;

public class UrlGate {
	private Hashtable<String, LinkedBlockingQueue<Order>> toDoUrl;//����Url
	private LinkedBlockingQueue<Order> DoneUrl;//��ȥUrl
	private static UrlGate instance;
	private int count;

	private UrlGate(){
		count = 0;
		toDoUrl = new Hashtable<String, LinkedBlockingQueue<Order>>();
		DoneUrl = new LinkedBlockingQueue<Order>();
	}  
	 
	public static synchronized UrlGate getInstance(){ 
		 
	    if (instance == null) {  
	        instance = new UrlGate();  
	    }  
	    return instance;  
    }
	
	public synchronized void inittoDoUrl(String host){
		LinkedBlockingQueue<Order> toDolist = toDoUrl.get(host);
		if(toDolist == null)
		{
			toDoUrl.put(host, new LinkedBlockingQueue<Order>());
		}
	}
	
	//�Ӵ���Url����ȡ
	public synchronized Order taketoDoUrl(String host) throws InterruptedException{
		Order tmp = null;
		LinkedBlockingQueue<Order> toDolist = toDoUrl.get(host);
		tmp = toDolist.take();
		return tmp;
	}
	
	//������Url����ȡ
	public Order takeDoneUrl() throws InterruptedException{
		Order tmp = null;
		tmp = DoneUrl.take();
		count--;
		return tmp;
	}
	
	//��url�������url
	public synchronized void puttoDoUrl(String host, Order order) throws InterruptedException{
		LinkedBlockingQueue<Order> toDolist = toDoUrl.get(host);
		toDolist.put(order);
		count++;
	}
	
	//��url��������url
	public void putDoneUrl(Order order) throws InterruptedException{
		DoneUrl.put(order);
	}
	
	public synchronized boolean isEmpty(){
		if(count == 0)
			return true;
		else
			return false;
	}
}
