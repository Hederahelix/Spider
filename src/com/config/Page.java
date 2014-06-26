package com.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

import com.dao.Verdict;
import com.manger.Task;

import lombok.Data;


public class Page extends Dynamic{
	private Regex reg[];
	
	public HashMap doReg(String contents,Order task,String url){
		int len = reg.length;	
		HashMap<String, String> comm = new HashMap<String, String>();
		HashMap<String, String> tmp = new HashMap<String, String>();
			
		for(int i=0;i<len;i++)
		{
			if(reg[i].getName().equals("totalpage")||reg[i].getName().equals("currentpage")||
					reg[i].getName().equals("nexturl"))
				
				continue;
			else
			{
				tmp = reg[i].doReg(contents,task,url,false);
				if(tmp != null)
					comm.putAll(tmp);
			}
		}
		
		return comm;
	}
	
	
	public Regex[] getReg() {
		return reg;
	}

	public void setReg(Regex[] reg) {
		this.reg = reg;
	}
	
	
}
