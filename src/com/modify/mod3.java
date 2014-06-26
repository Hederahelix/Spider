package com.modify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.WebConfig;
import com.utils.HttpConn;
import com.utils.Tools;

public class mod3 implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub

		Tools tools = Tools.getInstance();
		String Content = HttpConn.getSource(Group[0]);
		String Url = null;
		
		Pattern pt=Pattern.compile("<iframe src=\"([^\"]*)\" id=\"mainFrame\"",Pattern.CASE_INSENSITIVE);					
		Matcher mt=pt.matcher(Content);
		
		while(mt.find())
		{
			Url = tools.Realurl(mt.group(1), tools.Seedurl(Group[0]), Group[0]);
		}
		return Url;
	}

}
