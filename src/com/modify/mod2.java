package com.modify;

import com.config.WebConfig;

public class mod2 implements Modify {

	@Override
	public String execute(String[] Group, WebConfig Conf) {
		// TODO Auto-generated method stub
		return "http://tjfy.chinacourt.org/public/"+Group[0];
	}

}
