package com.manger;

public class ScheduleItem implements Comparable{
	public String host;
	public int interval;
	public int readytime;
	
	public ScheduleItem(String host, int interval, int readytime) {
		super();
		this.host = host;
		this.interval = interval;
		this.readytime = readytime;
	}

	@Override
	public int compareTo(Object o) {
		// TODO Auto-generated method stub
		if(o instanceof ScheduleItem){  
			ScheduleItem item = (ScheduleItem)o;  
            if(this.readytime>item.readytime){  
                return 1;  
            }  
            else{  
                return 0;  
            }  
        }  
        return -1; 
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public int getReadytime() {
		return readytime;
	}

	public void setReadytime(int readytime) {
		this.readytime = readytime;
	}
	
	
}
