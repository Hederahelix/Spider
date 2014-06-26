package com.strategy.decrypt;

import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.config.DBAccess;
import com.config.Order;
import com.config.WebConfig;
import com.utils.Tools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Dy_PlanB extends AbstractDecrypt {
	private static Logger logger = LogManager.getLogger(Dy_PlanB.class.getName());
	
	@Override
	public String decrypt_strategy(String Contents,Order task) {
		// TODO Auto-generated method stub
		Tools tools = Tools.getInstance();
		String regex ="tm\\[\\d*\\]=\"([^;]*)\"";  
		Pattern pt=Pattern.compile(regex,Pattern.CASE_INSENSITIVE);  
		Matcher mt=pt.matcher(Contents);  
		 StringBuffer res=new StringBuffer();

		 while(mt.find())
		 {
			 try {
				res.append(tools.decrypt(mt.group(1),task.getConfig().getDecrypt_file()));
				
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				logger.error(" ", e);
				e.printStackTrace();
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				logger.error(" ", e);
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				logger.error(" ", e);
				e.printStackTrace();
			}
			 
		 }
		return res.toString();		
	}

}
