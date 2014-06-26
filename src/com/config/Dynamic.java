package com.config;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Dynamic {
	
	//获得xxx属性的getxxx或者setxxx 其中kind为“get”或者“set” 
	private String convert(String kind,String param) {
		String fnname;
		String oldChar = param.substring(0, 1);
		String newChar = oldChar.toUpperCase();
		fnname = kind+param.replaceFirst(oldChar, newChar);
		return fnname;
	}

	//获得xxx属性的getxxx或者setxxx方法 其中kind为“get”或者“set” 
	private Method findMethod(String kind,String param){
		Method method = null;
		if(kind.equals("get")){
			try {
				method = this.getClass().getMethod
						(convert(kind,param));
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
		}else{
			Field field = null;
			try {
				field = this.getClass().getDeclaredField(param);
				method = this.getClass().getMethod
						(convert(kind,param),field.getType());
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchFieldException e1) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}//获取setName方法中参数的字段
			catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
		}
		return method;
	}
	
	//获取某个赋值方法
	public <T> boolean setFunction(String param,T value){		
		Method method = findMethod("set",param);
		
		if(method != null){
			Class type = method.getParameterTypes()[0];
			try {
				if(type.equals(int.class)){
					int realvalue = new Integer((String)value);
					method.invoke(this, realvalue);
				}
				
				else if(type.equals(long.class)){
					long realvalue = new Long((String)value);
					method.invoke(this, realvalue);
				}
				
				else if(type.equals(float.class)){
					float realvalue = new Float((String)value);
					method.invoke(this, realvalue);
				}
				
				else if(type.equals(boolean.class)){
					boolean realvalue = new Boolean((String)value);
					method.invoke(this, realvalue);
				}
				
				else{
					method.invoke(this, value);
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}else{
			return false;
		}
		
	}

	//获取某个取值方法
	public <T> T getFunction(String param){
		Method method = findMethod("get",param);;
		
		if(method != null){
			try {
				return (T)method.invoke(this);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
}
