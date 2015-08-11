package com.dazhong;
/*对下载到的网页进行解析，提取出店面的连接
 * 
 * 
 * 
 */




import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.locks.LockSupport;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.frame.DazhongFrame;
import com.frame.SinaFrame;
import com.sql.creatDatabases;
import com.sql.sqlOperate;





public class PageParse {
	
	
	//记录连接的变量
	 public static double lat;
	 public static double lng;
	 public static String shopAdd;
	public static String linkHref;
	public static String shopName;
	public static int Num;
	
	
	
	
	
	public void extract(String result,String tablename) throws ClientProtocolException, IOException,SAXException, ParserConfigurationException, ClassNotFoundException, SQLException{
		
	
		
		
	
		creatDatabases data=new creatDatabases();
		data.creatDazhongDataBase(tablename);
		sqlOperate sqlop=new sqlOperate();
		
	      
	      
		//将网页中的相对地址转变成绝对地址
		
		 //使用jsoup进行解析,注意相对地址和绝对地址
	      Document doc=Jsoup.parse(result, "http://www.dianping.com/shop");
	      String title=doc.title();
	      String text=doc.text();
	      //抽取连接
	   
	     //抽取地址
	    
	     GeoTranse geo=new GeoTranse();
	     Elements links5=doc.select("div.txt");
	  
	     for(Element link6:links5){
	    	 
	    	Elements links1=link6.select("[class=addr]"); 
	    	String shopAdd1=links1.text();
	    	
	    	Elements links2=link6.select("[class=review-num]");
	    	Elements CommentNum=links2.select("b");
	    	//点评数为零的情况：
	    	if(CommentNum.text().isEmpty()){
	    		Num=Integer.parseInt("0");
	    	}else{
	    	
	    	
	    	 Num=Integer.parseInt(CommentNum.text());
	    	 }
	    	 Elements links3=link6.select("[data-hippo-type=shop]");
	    	 linkHref=links3.attr("abs:href");
	    	 shopName=links3.attr("title");
	    	
	    	geo.transeCoord(shopAdd1);
	    	lat=Double.parseDouble(geo.getLat());
	    	lng=Double.parseDouble(geo.getLng());
	    	System.out.println("OK");
	        System.out.println(shopAdd1+Num+shopName+lat+lng);
	               
	        // stmt.executeUpdate("INSERT  INTO shop(shopName,shopAdd,lat,lng,commentNum) VALUES ('"+shopName+"','"+shopAdd1+"','"+lat+"','"+lng+"','"+Num+"')");
		    
	        //若字符串中含有单引号，则会引起语句错误如何更改,转义的方法
	        String r="''";
	        shopName=shopName.replace("'",r);
	        //考虑到地址也有可能会有单引号，故对地址也要进行转义
	        shopAdd1=shopAdd1.replace("'", r);
	        sqlop.insertDaTable(tablename,shopAdd1, shopName, lat, lng, Num);
	     }
	         
	     }
	   

	    
	}

