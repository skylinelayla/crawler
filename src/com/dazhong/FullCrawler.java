package com.dazhong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*
 * 由于商户不能爬取完全，这个类主要是为了遍历一个行政区所有的商户
 * 可以调用，也可以不调用，调用时数据量增多
 * 点评数为零的情况需要考虑，在PageParse类中
 * 
 */
public class FullCrawler {
	

	
	//传入首页的URL连接
	public void fullpower(String addurl,String tablename) throws Exception{
		
		String url2="http://www.dianping.com/search/category/1/10/"+addurl;
		
		httpGet get=new httpGet();
		get.getByString(url2);
		String result2=get.getResult();
		//相对地址转换为绝对的地址，即连接的绝对地址
		Document doc=Jsoup.parse(result2, "http://www.dianping.com/");
		
		Element links1=doc.getElementsByAttributeValue("class","nc-contain").first();
		Elements links = links1.getElementsByTag("a"); 
		httpGet httpget=new httpGet();
		PageParse page=new PageParse();
		PageGet pagenum=new PageGet();

		if (!links.isEmpty()) { 
	            for (Element link : links) { 
	                String linkHref = link.absUrl("href"); 
	                httpget.getByString(linkHref);
	               //获取页面的页数
	                
	               int pageNum=pagenum.getPage(linkHref);
	        for(int i=1;i<=pageNum;i++){
	        	String url=linkHref+"p"+i;
	        	httpget.getByString(url);
	       
	    		page.extract(httpget.getResult(),tablename);
	        	
	        	
	        	
	        }
	             
	            }}
		
		
	}
	/*
	public static void main(String[] args) throws Exception{
		String result="http://www.dianping.com/search/category/1/10/r10";
		httpGet get=new httpGet();
		get.getByString(result);
		String result2=get.getResult();
		FullCrawler full=new FullCrawler();
		full.fullpower(result2,"testfull7");
		
		
	}
	
*/
	public void doCraw(String url,String tablename) throws Exception{
		FullCrawler full=new FullCrawler();
		full.fullpower(url, tablename);
		System.out.println("Finnish");
	}
	
}
