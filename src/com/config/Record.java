package com.config;

import lombok.Data;

public class Record {
	private String currentpage;
	private String totalpage;
	private String verdictkind;
	private String webname;
	
	public Record(String webname, String verdictkind, String currentpage, String totalpage) {
		super();
		this.currentpage = currentpage;
		this.totalpage = totalpage;
		this.verdictkind = verdictkind;
		this.webname = webname;
	}

	public String getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(String currentpage) {
		this.currentpage = currentpage;
	}

	public String getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(String totalpage) {
		this.totalpage = totalpage;
	}

	public String getVerdictkind() {
		return verdictkind;
	}

	public void setVerdictkind(String verdictkind) {
		this.verdictkind = verdictkind;
	}

	public String getWebname() {
		return webname;
	}

	public void setWebname(String webname) {
		this.webname = webname;
	}
	
	
}
