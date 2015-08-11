package com.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




import com.sql.sqlConnect;

/*
 * 建立数据库
 * 建立大众点评数据库新浪微博数据库
 */
public class creatDatabases {
	//在数据库中创建大众点评的表
		public void creatDazhongDataBase(String tablename) throws ClassNotFoundException, SQLException{
			//登陆sql数据库
		sqlConnect creatDazhong=new sqlConnect();
		Connection conn=creatDazhong.linkSql("");
		String sql=null;
		   Statement stmt = null;
		   ResultSet rs = null; 
		   int count = 0;
		   if(conn!=null){
			   
			   try {
	               sql = "CREATE DATABASE IF NOT EXISTS DazhongDataBase";
	               stmt = conn.createStatement();
	               stmt.executeUpdate(sql);
	               sql = "USE DazhongDataBase";
	               stmt = conn.createStatement();
	               stmt.executeUpdate(sql);
	               //创建表
	               sql = "create table if not exists "+tablename+"(id int(10) not null auto_increment ,shopname varchar(50), shopAdd varchar(50) primary key,lat double,lng double,commentNum int(10), key (id)) engine=InnoDB DEFAULT CHARSET=utf8";
	               stmt = conn.createStatement();
	               stmt.executeUpdate(sql);   
			   
		   }catch (SQLException e) {
	           e.printStackTrace();
	       }
		   }

		   stmt.close();
		    conn.close();
		}
		
		//在数据库中创建新浪的表
		public void creatSinaDataBase(String tablename) throws ClassNotFoundException, SQLException{
			sqlConnect creatDazhong=new sqlConnect();
			Connection conn=creatDazhong.linkSql("SinaDataBase");
			String sql=null;
			   Statement stmt = null;
			   ResultSet rs = null; 
			   int count = 0;
			   if(conn!=null){
				   
				   try {
		               sql = "CREATE DATABASE IF NOT EXISTS SinaDataBase";
		               stmt = conn.createStatement();
		               stmt.executeUpdate(sql);
		               sql = "USE SinaDataBase";
		               stmt = conn.createStatement();
		               stmt.executeUpdate(sql);
		               //创建表
		               sql = "create table if not exists "+tablename+" (id int(10) not null auto_increment, poiid varchar(20) primary key,title varchar(50),poiadd text,lat double,lng double,gaolat double,gaolon double,checknum int(10),key (id)) engine=InnoDB DEFAULT CHARSET=utf8";
		               stmt = conn.createStatement();
		               stmt.executeUpdate(sql);   
				   
			   }catch (SQLException e) {
		           e.printStackTrace();
		       }
				   
			   }
		
			   stmt.close();
			   conn.close();
		}
		
		
	}


