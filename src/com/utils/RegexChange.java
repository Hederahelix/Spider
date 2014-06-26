package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegexChange {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true){
			System.out.println("输入要修改的正则表达式  :");
			BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
			String name = null;
			try {
				name = stdin.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String str = name.replaceAll("<","&lt;").replaceAll(">","&gt;");
			
			System.out.println(name);//输出要修改的正则表达式 
			System.out.println(str);//输入修改完成的正则表达式 
		}
	}

}
