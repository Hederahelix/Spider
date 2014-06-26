package com.config;

import lombok.Data;

import com.dao.Verdict;
import com.strategy.factory.Factory;


public class Order {
	
	private int taskStyle;//���ɹ���
	private String url;//���ɹ���
	private Verdict info;//���ɹ���
	private WebConfig config;//�ɹ������䣩
	private Factory factory;//�ɹ������䣩
	
	public Order(int taskStyle,String url, Verdict info, WebConfig config, Factory factory) {
		super();
		this.taskStyle = taskStyle;
		this.url = url;
		this.info = info;
		this.config = config;
		this.factory = factory;
	}
	
	public String decrypt_strategy(String Contents) //���ܲ���
	{
		return factory.decrypt_strategy(Contents,this);
	}
	
	public String endpoint_strategy(String Contents, String Url)//��ô�뿪��һҳ�Ĳ���
	{
		return factory.endpoint_strategy(Contents,this,Url);
	}

	public int getTaskStyle() {
		return taskStyle;
	}

	public void setTaskStyle(int taskStyle) {
		this.taskStyle = taskStyle;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Verdict getInfo() {
		return info;
	}

	public void setInfo(Verdict info) {
		this.info = info;
	}

	public WebConfig getConfig() {
		return config;
	}

	public void setConfig(WebConfig config) {
		this.config = config;
	}

	public Factory getFactory() {
		return factory;
	}

	public void setFactory(Factory factory) {
		this.factory = factory;
	}
}
