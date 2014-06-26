package com.config;

import lombok.Data;

import com.strategy.factory.Factory;



public class WebConfig extends Dynamic{
	private String webName_en;
	private String webName_cn;
	private String web_seed;
	private String sort_url;
	private String last_Crawl_time;
	private String endpoint_strategy;
	private String decrypt_strategy;
	private String decrypt_file;
	private String area;
	private int seedId;
	private Page SortPage;
	private Page ListPage;
	private Page Page;
	public String getWebName_en() {
		return webName_en;
	}
	public void setWebName_en(String webName_en) {
		this.webName_en = webName_en;
	}
	public String getWebName_cn() {
		return webName_cn;
	}
	public void setWebName_cn(String webName_cn) {
		this.webName_cn = webName_cn;
	}
	public String getWeb_seed() {
		return web_seed;
	}
	public void setWeb_seed(String web_seed) {
		this.web_seed = web_seed;
	}
	public String getSort_url() {
		return sort_url;
	}
	public void setSort_url(String sort_url) {
		this.sort_url = sort_url;
	}
	public String getLast_Crawl_time() {
		return last_Crawl_time;
	}
	public void setLast_Crawl_time(String last_Crawl_time) {
		this.last_Crawl_time = last_Crawl_time;
	}
	public String getEndpoint_strategy() {
		return endpoint_strategy;
	}
	public void setEndpoint_strategy(String endpoint_strategy) {
		this.endpoint_strategy = endpoint_strategy;
	}
	public String getDecrypt_strategy() {
		return decrypt_strategy;
	}
	public void setDecrypt_strategy(String decrypt_strategy) {
		this.decrypt_strategy = decrypt_strategy;
	}
	public String getDecrypt_file() {
		return decrypt_file;
	}
	public void setDecrypt_file(String decrypt_file) {
		this.decrypt_file = decrypt_file;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public int getSeedId() {
		return seedId;
	}
	public void setSeedId(int seedId) {
		this.seedId = seedId;
	}
	public Page getSortPage() {
		return SortPage;
	}
	public void setSortPage(Page sortPage) {
		SortPage = sortPage;
	}
	public Page getListPage() {
		return ListPage;
	}
	public void setListPage(Page listPage) {
		ListPage = listPage;
	}
	public Page getPage() {
		return Page;
	}
	public void setPage(Page page) {
		Page = page;
	}		

	
}
