package com.modify;

import com.config.WebConfig;

public class mod1 implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub
		return "http://www.gdcourts.gov.cn/gdcourt/front/front!list.action?lmdm=LM"+Group[0]+"&showMany=&page.pageNo="+Group[1]+"&page.orderBy=fbrq&page.order=desc&fullText=";
	}

}
