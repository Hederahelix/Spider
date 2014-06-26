package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.utils.HttpConn;

import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DBAccess extends Dynamic {
	private String driver_name; 
	private String db_url; 
	private String username; 
	private String password;
	private ThreadLocal<Connection> connection = new ThreadLocal<Connection>(); 
	private static DBAccess instance = null;
	private static Logger logger = LogManager.getLogger(DBAccess.class.getName());
	
	private DBAccess() {
	}
	
	public synchronized static DBAccess getInstance(){
		if(instance == null){
			instance = new DBAccess();
		}
		return instance;
	}

	public Connection getConnect(){
		Connection conn = connection.get();
        if(conn==null)
        {
            try {
                try {
					Class.forName(driver_name).newInstance();
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					logger.error(" ", e);
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(" ", e);
				}
            } catch (ClassNotFoundException e) {
                System.out.println(" class not found: " + e.getMessage());
                logger.error(" ", e);
                e.printStackTrace();
            }
    
            try {
            	conn = DriverManager.getConnection(db_url,username,password);
            } catch (SQLException sqlex) {
                System.err.println("DatabaseBean connection error"+ sqlex.getMessage());
                logger.error(" ", sqlex);
                sqlex.printStackTrace();
            }
        }
        return conn;
    }
	
	public void closeConn(){
		Connection conn = connection.get();
		try {
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(" ", e);
			e.printStackTrace();
		}
	}

	public String getDriver_name() {
		return driver_name;
	}

	public void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	public String getDb_url() {
		return db_url;
	}

	public void setDb_url(String db_url) {
		this.db_url = db_url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ThreadLocal<Connection> getConnection() {
		return connection;
	}

	public void setConnection(ThreadLocal<Connection> connection) {
		this.connection = connection;
	}

	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		DBAccess.logger = logger;
	}

	public static void setInstance(DBAccess instance) {
		DBAccess.instance = instance;
	}
	
	
}
