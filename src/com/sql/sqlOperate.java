package com.sql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.sql.sqlConnect;
/*包含操作数据库的方法
 * 
 * 
 */
public class sqlOperate {
	//向大众点评表中插入数据
	public void insertDaTable(String tablename,String shopadd,String shopname,double lat,double lon,int num) throws SQLException, ClassNotFoundException{
		
		Connection conn=sqlConnect.linkSql("DazhongDataBase");
		Statement stmt;
		stmt=conn.createStatement();
		stmt.executeUpdate("INSERT IGNORE INTO "+tablename+"(shopname,shopadd,lat,lng,commentnum) VALUES('"+shopname+"','"+shopadd+"','"+lat+"','"+lon+"','"+num+"')");
		stmt.close();
		conn.close();
	}
	
	public void insertSina(String tablename,String poiid,String title,String poiadd,double lat,double lon,double gaolat,double gaolon,int checknum ) throws SQLException, ClassNotFoundException{
		//向新浪表格插入数据
		Connection conn=sqlConnect.linkSql("SinaDataBase");
		Statement stmt;
		stmt=conn.createStatement();
		
		stmt.executeUpdate("INSERT IGNORE INTO "+tablename+"(poiid,poiadd,title,lat,lng,gaolat,gaolon,checknum) VALUES('"+poiid+"','"+poiadd+"','"+title+"','"+lat+"','"+lon+"','"+gaolat+"','"+gaolon+"','"+checknum+"')");

		stmt.close();
		conn.close();
	}

}
