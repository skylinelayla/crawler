package com.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/*实现数据库连接
 * 
 * 
 */
public class sqlConnect {
	
	public static Connection linkSql(String database) throws ClassNotFoundException, SQLException{
		
		 Class.forName("com.mysql.jdbc.Driver");
	      System.out.println("OK");
		   String url1="jdbc:mysql://localhost:3306/"+database;
          Connection conn=DriverManager.getConnection(url1,"root","");
          System.out.println("LINK TO DATABASE SUCCESS!");
          return conn;
	}
	
	
	
}
