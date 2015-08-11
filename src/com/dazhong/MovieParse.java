package com.dazhong;
/*爬取电影院的信息
 * 
 * 
 * 
 */
import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.http.client.ClientProtocolException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import com.sql.creatDatabases;
import com.sql.sqlOperate;

public class MovieParse {
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
	     //Statement stmt;
 		// stmt=conn.createStatement();
	     for(Element link6:links5){
	    	 
	    	Elements links1=link6.select("[class=addr]"); 
	    	String shopAdd1=links1.text();
	    	
	    	Elements links2=link6.select("[class=review-num]");
	    	
	    	Elements CommentNum=links2.select("b");
	    	
	    	if(CommentNum.text().isEmpty()){
	    		Num=Integer.parseInt("0");
	    	}
	    	else{

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
	        sqlop.insertDaTable(tablename,shopAdd1, shopName, lat, lng, Num);
	     }
	         
	     }
	   
	

}
