package com.manger;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import com.config.Order;



public class Schedule implements Runnable{
	private LinkedList<ScheduleItem> restTime;
	private static volatile boolean off;
	private int nowtime;
	private int timeout;
	
	public Schedule(){
		nowtime = 0;
		timeout = 10;
		restTime = new LinkedList<ScheduleItem>();
	}  
	
	public static void stop()
	{
		off = true;
	}
	
	public void putItem(String host, int interval){
		restTime.offer(new ScheduleItem(host ,interval ,0));
	}
	
	
	public boolean isAnyItemReady(){
		ScheduleItem item = restTime.peek();
		if(item == null)
			return false;
		
		if(item.getReadytime()<nowtime)
			return true;
		else
			return false;
	}
	
	public boolean isItemReady(ScheduleItem item){
		if(item.getReadytime()<nowtime)
			return true;
		else
			return false;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UrlGate urlgate = UrlGate.getInstance();
		UrlList urllist = UrlList.getInstance();
		try {
			
			while(!off)
			{
				if(isAnyItemReady())
				{
					for(ScheduleItem item:restTime)
					{  
						if(isItemReady(item)) 
			            {
							restTime.remove(item);
			            	Order order;
							
								order = urlgate.taketoDoUrl(item.getHost());
								if(order != null)
				            		urllist.putPackage(new PackAge(item, order));
			            }
			            else
			            {
			            	break;
			            } 	
			        }
					 
				}
	
				TimeUnit.SECONDS.sleep(timeout);
				nowtime += timeout;
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

