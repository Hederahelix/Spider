package com.utils;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.manger.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HttpConn {
	private static int restTime = 30;
	private static Logger logger = LogManager.getLogger(HttpConn.class.getName());
	
	public static boolean testConn(String add){
		HttpURLConnection con = null;
		
        try 
        {
        	URL url = new URL(add);
	       	con = (HttpURLConnection)url.openConnection();
	    	con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
	    	int responseCode = con.getResponseCode();
	    	if(responseCode==200)
	    		return true;
	    	else
	    		return false;
	    	
        }catch (Exception e) {
        	return false;
        }
        finally{
            con.disconnect();
        }		
	}
	
	public static String getCharset(String strurl){
		String strencoding = null;
		URL url = null;
		try {
			url = new URL(strurl);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			logger.error(" ", e);
			e.printStackTrace();
		}
		//第一种方法
		try {
			strencoding = getCharset1(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block		
			System.out.println("网页编码分析失败："+e);
			try {	
				while(true)
				{
					System.out.println("网页编码分析失败:"+url);
					TimeUnit.SECONDS.sleep(restTime);
					strencoding = getCharset1(url);
					break;
				}	
			}catch(java.io.FileNotFoundException e1){
	        	System.out.println("捕获异常:"+e1);
	        	logger.warn(" ",e1);
	        	return null;
	        }catch (IOException e2) {

			}catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				logger.error(" ",e3);
				e3.printStackTrace();
			}
		}
		if(strencoding != null)
		{
			System.out.println("网页解码1");
			return strencoding;
		}
		
		//第二种方法
		try {
			strencoding = getCharset2(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("网页编码分析失败："+e);
			try {	
				while(true)
				{
					System.out.println("网页编码分析失败:"+url);
					TimeUnit.SECONDS.sleep(restTime);
					strencoding = getCharset2(url);
					break;
				}	
			}catch(java.io.FileNotFoundException e1){
	        	System.out.println("捕获异常:"+e1);   
	        	logger.warn(" ",e1);
	        	return null;
	        }catch (IOException e2) {

			}catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				logger.error(" ",e3);
				e3.printStackTrace();
			}

		}
		if(strencoding != null)
		{
			System.out.println("网页解码2");
			return strencoding;
		}
		
		//第三种方法
		try {
			strencoding = getCharset3(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("网页编码分析失败："+e);
			try {	
				while(true)
				{
					System.out.println("网页编码分析失败:"+url);
					TimeUnit.SECONDS.sleep(restTime);
					strencoding = getCharset3(url);
					break;
				}	
			}catch(java.io.FileNotFoundException e1){
	        	System.out.println("捕获异常:"+e1);     
	        	logger.warn(" ",e1);
	        	return null;
	        }catch (IOException e2) {

			}catch (InterruptedException e3) {
				// TODO Auto-generated catch block
				logger.error(" ",e3);
				e3.printStackTrace();
			}
		}
		
		System.out.println("网页解码3");
		return strencoding;
	}

	public static String getSource(String add)
    {
		StringBuffer contentBuffer = new StringBuffer();
		HttpURLConnection con = null;
		InputStream inStr = null;
        InputStreamReader istreamReader = null;
        BufferedReader buffStr = null;
        String charset = null;
        charset = getCharset(add);
        if(charset == null)
        	return null;
        try 
        {     
        	URL url=new URL(add); 
	       	con = (HttpURLConnection)url.openConnection();
	       	con.setConnectTimeout(10000);
			con.setReadTimeout(10000);
	       	//System.out.println(charset);
	       	con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            inStr = con.getInputStream();
            //istreamReader = new InputStreamReader(inStr,"UTF-8");      
            istreamReader = new InputStreamReader(inStr,charset);      
            buffStr = new BufferedReader(istreamReader);
            String str = null; 
            while((str = buffStr.readLine())!=null)
                contentBuffer.append(str);

        } 
        catch(java.io.FileNotFoundException e){
        	System.out.println("捕获异常:"+e);    
        	logger.warn(" ",e);
        	return null;
        }
        catch(Exception e){
        	System.out.println("捕获异常:"+e);
        	
        	try {
	        	while(testConn(add)==false)//网络无法链接 
	        	{
	        		System.out.println("网络无法链接 :"+add);
	        		TimeUnit.SECONDS.sleep(restTime);		
	        	}
        	} catch (InterruptedException e1) {
				//e1.printStackTrace();
			}
        	
        	return getSource(add);
        }
        finally{
        	try{
        		if(inStr!=null)
            		inStr.close();
            	if(buffStr!=null)
            		buffStr.close();
        	}catch (IOException e) {
                e.printStackTrace();
            }
        	
        	if(con!=null)
        		con.disconnect();
        }
        
        //休息一段时间
    	try {
			TimeUnit.SECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        return contentBuffer.toString();
    }

	public static String getCharset1(URL url) throws IOException{
		//从header中解析charset
		String strencoding = null;
		HttpURLConnection urlConnection = null;
		urlConnection = (HttpURLConnection)url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(10000);
		urlConnection.connect();

		// map存放的是header信息(url页面的头信息)
		Map<String, java.util.List<String>> map = urlConnection.getHeaderFields();
		Set<String> keys = map.keySet();
		Iterator<String> iterator = keys.iterator();

		String key = null;
		String tmp = null;
		while (iterator.hasNext()) {
			key = iterator.next();
			//System.out.println(key+":"+map.get(key));
			if (key != null) 
			{
				if(key.toLowerCase().equals("content-type"))
				{
					tmp = map.get(key).toString().toLowerCase();
					
					int m = tmp.indexOf("charset=");
					if (m != -1) 
					{
						strencoding = tmp.substring(m + 8).replace("]", "");
						return strencoding.toUpperCase();
					}
				}
			}
		}
		return strencoding;
	}

	public static String getCharset2(URL url) throws IOException
	{
		// 获取网页源码(英文字符和数字不会乱码，所以可以得到正确<meta/>区域)	
		String strencoding = null;
		String line;
		
		HttpURLConnection urlConnection;
		StringBuffer contentBuffer = new StringBuffer();
		
		urlConnection = (HttpURLConnection)url.openConnection();
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(10000);
		urlConnection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
		InputStream inStr = urlConnection.getInputStream();   
		InputStreamReader istreamReader = new InputStreamReader(inStr);      
		BufferedReader buffStr = new BufferedReader(istreamReader);
        String str = null; 
         
        while((str = buffStr.readLine())!=null)
            contentBuffer.append(str);	
		
		String htmlcode = contentBuffer.toString();
		// 解析html源码，取出<meta />区域，并取出charset
		String strbegin = "<meta";
		String strend = ">";
		String strtmp;
		int begin = htmlcode.indexOf(strbegin);
		int end = -1;
		int inttmp;
		while (begin > -1) {
			end = htmlcode.substring(begin).indexOf(strend);
			if (begin > -1 && end > -1) {
				strtmp = htmlcode.substring(begin, begin + end).toLowerCase();
				inttmp = strtmp.indexOf("charset");
				if (inttmp > -1) {
					strencoding = strtmp.substring(inttmp + 7, end).replace(
							"=", "").replace("/", "").replace("\"", "")
							.replace("\'", "").replace(" ", "");
					return strencoding.toUpperCase();
				}
			}
			htmlcode = htmlcode.substring(begin);
			begin = htmlcode.indexOf(strbegin);
		}	
		return strencoding;
	}

	public static String getCharset3(URL url) throws IOException 
	{	
		//使用第三方类库
		String strencoding = null;
		java.nio.charset.Charset charset = null;
		CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();	
		detector.add(new ParsingDetector(false));
		detector.add(JChardetFacade.getInstance());// 用到antlr.jar、chardet.jar
		detector.add(ASCIIDetector.getInstance());
		detector.add(UnicodeDetector.getInstance());
		charset = detector.detectCodepage(url);
		
		
		if (charset != null)
			strencoding = charset.name();

		// 设置默认网页字符编码
		if (strencoding == null) {
			System.out.println("采用默认网页字符编码:GBK");
			logger.warn(url+" 采用默认网页字符编码:GBK");
			strencoding = "GBK";
		}

		return strencoding.toUpperCase();
	}
}
