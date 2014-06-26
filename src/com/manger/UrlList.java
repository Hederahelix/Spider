package com.manger;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;

public class UrlList {
	private LinkedBlockingQueue<PackAge> UrlPool;//≈¿»•Url
	private static UrlList instance;
	
	private UrlList(){
		UrlPool = new LinkedBlockingQueue<PackAge>();
	} 
	
	public static synchronized UrlList getInstance(){ 
		 
	    if (instance == null) {  
	        instance = new UrlList();  
	    }  
	    return instance;  
    }
	
	public PackAge getPackage() throws InterruptedException{ 
		PackAge Package = UrlPool.take();
		return Package;
    }
	
	public void putPackage(PackAge Package) throws InterruptedException{ 
		UrlPool.put(Package);
    }
}
