package com.manger;

import java.util.LinkedList;

public class DownLoad implements Runnable{
	private static volatile boolean off;
	
	public static void stop()
	{
		off = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		UrlList urllist = UrlList.getInstance();
		PackAge Package = null;
		try {
			while(!off)
			{
				Package = urllist.getPackage();
				
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
