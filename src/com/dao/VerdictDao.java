package com.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.config.DBAccess;
import com.utils.HttpConn;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VerdictDao {
	public final String TABLE = "wenshu";
	private static Logger logger = LogManager.getLogger(VerdictDao.class.getName());
	
	public void save(Verdict vet) {
		DBAccess db = DBAccess.getInstance();
		Connection con = db.getConnect();
		Statement stmt = null;
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			logger.error(" ", e1);
			e1.printStackTrace();
		}		

		vet.check();
		
		String sql="insert into "+TABLE+" (title,time,court,verdictkind,number,"
				+ "content,url,area)values('"+vet.getTitle()+"','"+vet.getTime()+"','"
				+vet.getCourt()+"','"+vet.getVerdictkind()+"','"+vet.getNumber()
				+"','"+vet.getContent()+"','"+vet.getUrl()+"','"+vet.getArea()+"');";	   

		try {
			stmt.execute(sql);
			//con.setAutoCommit(false);// 更改JDBC事务的默认提交方式
			//stmt.executeUpdate(sql);
			//con.commit();//提交JDBC事务
			//con.setAutoCommit(true);// 恢复JDBC事务的默认提交方式
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" ", e);
			e.printStackTrace();
		}
		
		try {
			if(stmt!=null)
				stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" ", e);
			e.printStackTrace();
		}
		
	}

}
