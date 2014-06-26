package com.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.config.DBAccess;
import com.config.GlobalConfig;
import com.config.Page;
import com.config.Regex;
import com.config.WebConfig;


public class XmlReader {
	public void readGlobalConfig(String Path) 
	{
		SAXReader sr = new SAXReader();//获取读取xml的对象。
		Document doc = null;
		try {
			doc = sr.read(Path);//得到xml所在位置。然后开始读取。并将数据放入doc中
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Element root = doc.getRootElement();//向外取数据，获取xml的根节点。
		Element el = null;
		Iterator it = null;
		
		//web_config
		GlobalConfig cf = GlobalConfig.getInstance();
		Element config = root.element("web_config");
		for (it=config.elementIterator(); it.hasNext();)
		{
			el = (Element) it.next();
			cf.setFunction(el.getName(),el.getTextTrim());
		}
		
		//db_config
		DBAccess db = DBAccess.getInstance();
		config = root.element("db_config");
		for (it=config.elementIterator(); it.hasNext();)
		{
			el = (Element) it.next();
			db.setFunction(el.getName(),el.getTextTrim());
		}
	}
	
	public WebConfig readWebConfig(String Path) 
	{
		WebConfig cf = new WebConfig();
		SAXReader sr = new SAXReader();//获取读取xml的对象。
		Document doc = null;
		try {
			doc = sr.read(Path);//得到xml所在位置。然后开始读取。并将数据放入doc中
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element root = doc.getRootElement();//向外取数据，获取xml的根节点。
		Element el = null;
		Iterator it = null;
		Attribute att = null;
		
		//web_config
		Element config = root.element("web_config");
		for (it=config.elementIterator(); it.hasNext();)
		{
			el = (Element) it.next();
			cf.setFunction(el.getName(), el.getTextTrim());
			//System.out.println(el.getName()+":"+el.getTextTrim());
		}
		//web_seed
		Tools tools = Tools.getInstance();		
		cf.setWeb_seed(tools.Seedurl(cf.getSort_url()));
		
		//web_seed	
		GlobalConfig con = GlobalConfig.getInstance();
		cf.setDecrypt_file(con.getDecrypt_path()+cf.getDecrypt_file());
		
		//System.out.println("-------------------------");
		
		//SortPage
		Page sortpage = new Page();
		Element fields = root.element("SortPage");
		Regex[] reg = new Regex[fields.elements().size()];
		int group_num,index,i;
		String group[] = null;
		String model = "single-1";
		String modify = null;
		Iterator it_child = null;
		for (it=fields.elementIterator(),index=0,i=0; it.hasNext();i++)
		{
			el = (Element)it.next();
			att = el.attribute(0);
			reg[i] = new Regex();
			reg[i].setFunction(att.getName(), att.getValue());
			//System.out.println(att.getName()+":"+att.getValue());
			for (it_child=el.elementIterator(); it_child.hasNext();)
			{	//field
				el = (Element) it_child.next();
				//reg
				if(el.getName().equals("reg")){
					//System.out.println(el.getName()+":"+el.getTextTrim());
					reg[i].setFunction(el.getName(), el.getTextTrim());
				}
				//model
				else if(el.getName().equals("model"))
				{
					model = el.getTextTrim()+"-";
					if(el.getTextTrim().equals("single"))
						model= model + el.attribute(0).getValue();
					
					reg[i].setFunction(el.getName(), model);
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}
				//group_num
				else if(el.getName().equals("group_num"))
				{
					group_num = Integer.valueOf(el.getTextTrim());
					group=new String[group_num]; 
					reg[i].setFunction(el.getName(), el.getTextTrim());
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}	
				//group
				else if(el.getName().equals("group"))
				{
					index = Integer.valueOf(el.attribute(0).getValue())-1;
					group[index] = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
				//modify
				else 
				{
					modify = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
			}
			reg[i].setGroup(group);
			reg[i].setModify(modify);
			modify = null;
		}
		sortpage.setFunction("reg", reg);
		
		//System.out.println("-------------------------");
		//listpage
		Page listpage = new Page();
		fields = root.element("ListPage");
		reg = new Regex[fields.elements().size()];
		model = "single-1";
		group = null;
		modify = null;
		it_child = null;
		for (it=fields.elementIterator(),index=0,i=0; it.hasNext();i++)
		{
			el = (Element)it.next();
			att = el.attribute(0);
			reg[i] = new Regex();
			reg[i].setFunction(att.getName(), att.getValue());
			//System.out.println(el.getName()+":"+att.getValue());
			for (it_child=el.elementIterator(); it_child.hasNext();)
			{	//field
				el = (Element) it_child.next();
				//reg
				if(el.getName().equals("reg")){
					//System.out.println(el.getName()+":"+el.getTextTrim());
					reg[i].setFunction(el.getName(), el.getTextTrim());
				}
				//model
				else if(el.getName().equals("model"))
				{
					model = el.getTextTrim()+"-";
					if(el.getTextTrim().equals("single"))
						model= model + el.attribute(0).getValue();
					
					reg[i].setFunction(el.getName(), model);
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}
				//group_num
				else if(el.getName().equals("group_num"))
				{
					group_num = Integer.valueOf(el.getTextTrim());
					group=new String[group_num]; 
					reg[i].setFunction(el.getName(), el.getTextTrim());
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}	
				//group
				else if(el.getName().equals("group"))
				{
					index = Integer.valueOf(el.attribute(0).getValue())-1;
					group[index] = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
				//modify
				else 
				{
					modify = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
			}
			reg[i].setGroup(group);
			reg[i].setModify(modify);
			modify = null;
		}
		listpage.setFunction("reg", reg);
		
		//System.out.println("-------------------------");
		//page
		Page page = new Page();
		fields = root.element("Page");
		reg = new Regex[fields.elements().size()];
		model = "single-1";
		group = null;
		it_child = null;
		for (it=fields.elementIterator(),index=0,i=0; it.hasNext();i++)
		{
			el = (Element)it.next();
			att = el.attribute(0);
			reg[i] = new Regex();
			reg[i].setFunction(att.getName(), att.getValue());
			//System.out.println(el.getName()+":"+att.getValue());
			for (it_child=el.elementIterator(); it_child.hasNext();)
			{	//field
				el = (Element) it_child.next();
				//reg
				if(el.getName().equals("reg")){
					//System.out.println(el.getName()+":"+el.getTextTrim());
					reg[i].setFunction(el.getName(), el.getTextTrim());
				}
				//model
				else if(el.getName().equals("model"))
				{
					model = el.getTextTrim()+"-";
					if(el.getTextTrim().equals("single"))
						model= model + el.attribute(0).getValue();
					
					reg[i].setFunction(el.getName(), model);
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}
				//group_num
				else if(el.getName().equals("group_num"))
				{
					group_num = Integer.valueOf(el.getTextTrim());
					group=new String[group_num]; 
					reg[i].setFunction(el.getName(), el.getTextTrim());
					//System.out.println(el.getName()+":"+el.getTextTrim());
				}	
				//group
				else if(el.getName().equals("group"))
				{
					index = Integer.valueOf(el.attribute(0).getValue())-1;
					group[index] = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
				//modify
				else 
				{
					modify = new String(el.getTextTrim());
					//System.out.println(el.getName()+""+el.attribute(0).getValue()+":"+el.getTextTrim());
				}
			}
			reg[i].setGroup(group);
			reg[i].setModify(modify);
			modify = null;
		}
		page.setFunction("reg", reg);
		
		cf.setFunction("SortPage", sortpage);
		cf.setFunction("ListPage", listpage);
		cf.setFunction("Page", page);
		return cf; 
	}

	public boolean setTime(String Path,String Time){
		String value = "";
	
			SAXReader sr = new SAXReader();//获取读取xml的对象。
			Document doc = null;
			try {
				doc = sr.read(Path);//得到xml所在位置。然后开始读取。并将数据放入doc中
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Element root = doc.getRootElement();//根节点
			Element config = root.element("web_config");
			Element el = null;
			Iterator it = null;
			
			for (it=config.elementIterator(); it.hasNext();)
			{
				el = (Element) it.next();
				if(el.getName().equals("last_Crawl_time")){
					el.setText(Time);
				}
			}

			OutputFormat format = OutputFormat.createPrettyPrint();  
			format.setEncoding("UTF-8");  
			XMLWriter writer = null;
			try {
				writer = new XMLWriter(new FileWriter(new File(Path)), format);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			try {
				writer.write(doc);
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}    
			return true;
		}

	

}
