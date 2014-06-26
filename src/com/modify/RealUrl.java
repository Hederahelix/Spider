package com.modify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.WebConfig;

public class RealUrl implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub
		String realurl=new String(Group[0]);
		String webseed = Conf.getWeb_seed();
		if(!realurl.startsWith(webseed)){
			if(!Group[0].startsWith("/"))
				realurl = "/"+realurl;
			realurl = webseed+realurl;
		}
		String regex ="&amp;",tmp = realurl;  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(tmp);
		realurl = mt.replaceAll("&");
		return realurl;
	}

}
