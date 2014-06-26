package com.strategy.factory;

import com.config.Order;
import com.config.WebConfig;
import com.strategy.decrypt.*;
import com.strategy.endpoint.*;



public class Factory {
	private AbstractDecrypt dy;
	private AbstractEndpoint ep;

	public Factory(WebConfig config)
	{
		try {
			dy = (AbstractDecrypt) Class.forName("com.strategy.decrypt."+config.getDecrypt_strategy()).newInstance();
			ep = (AbstractEndpoint) Class.forName("com.strategy.endpoint."+config.getEndpoint_strategy()).newInstance();
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
	}
	
	public String decrypt_strategy(String Contents,Order task) //解密策略
	{
		return dy.decrypt_strategy(Contents,task);
	}
	
	public String endpoint_strategy(String Contents,Order task, String Url) //怎么离开下一页的策略
	{
		return ep.endpoint_strategy(Contents,task,Url);
	}
}
