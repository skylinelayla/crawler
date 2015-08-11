package com.dazhong;
/*大众点评爬虫主程序
 * 
 * 
 * 
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	public static String page1;
	public void Docraw(String addurl,String tablename,int page) throws Exception{
		try{
		
			httpGet httpget=new httpGet();
			
	

		
	
		
		//遍历页数50页，根据网页具体情况进行爬取。
		for(int i=1;i<=page;i++){
		String url="http://www.dianping.com/search/category/1/10/"+addurl+"p"+i+"?aid=fa89eb545da239c0f50513814bc6c46c";
		httpget.getByString(url);
		//解析网页
		PageParse pageparse=new PageParse();
		pageparse.extract(httpget.getResult(),tablename);
	
		
			
		}
		}catch(SQLException ex){
			ex.printStackTrace();
		}
		
		
		
		
		
		
	}
	

}
