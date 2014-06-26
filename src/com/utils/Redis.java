package com.utils;
  
import redis.clients.jedis.Jedis;   

public class Redis {
	private Jedis jedis; 
	private static Redis instance = null; 
	
	private Redis() {
		super();
		jedis = new Jedis("localhost");
		jedis.select(15);
		// TODO Auto-generated constructor stub
	}
	
	public synchronized static Redis getInstance(){
		if(instance == null){
			instance = new Redis();
		}
		return instance;
	}
	
	public synchronized boolean exists(String key)
	{
		return jedis.exists(key);
	}
	
	public synchronized void set(String key, String value)
	{
		jedis.set(key,value);
	}

}
