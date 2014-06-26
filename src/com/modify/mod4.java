package com.modify;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.config.WebConfig;
import com.utils.HttpConn;
import com.utils.Tools;

public class mod4 implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub

		Tools tools = Tools.getInstance();
		String tmp = Group[0].replaceFirst("index", "list");
		String Content = HttpConn.getSource(tmp),Url = null;
		Pattern pt=Pattern.compile("<iframe src=\"([^\"]*)\" frameborder=\"0\" scrolling=\"no\" width=\"700px\" height=\"840px\">",Pattern.CASE_INSENSITIVE);					
		Matcher mt=pt.matcher(Content);
		
		while(mt.find())
		{
			Url = tools.Realurl(mt.group(1), tools.Seedurl(tmp), tmp);
		}
		return Url;
	}

}
