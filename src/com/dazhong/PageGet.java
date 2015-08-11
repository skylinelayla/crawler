package com.dazhong;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/*获取页面页码的类，传入一个URL，可以获取大众点评网站上的页码
 * 返回int类型
 * 对于页面只有一页的页面需要加入进判断
 */
public class PageGet {
	public static int pagenumber;
	public static String numQueue;
	
	public int getPage (String url) throws Exception{
		httpGet httpget=new httpGet();
		httpget.getByString(url);
		String res=httpget.getResult();
		
		Document doc=Jsoup.parse(res);
		Elements links1=doc.getElementsByAttributeValue("class","shop-wrap");
		
	for(Element link:links1){
	  numQueue=links1.select("[class=page]").text();
		
	}
	if(numQueue.isEmpty()){
		pagenumber=1;
	}else{
	//提取字符串中的数字，然后转换成字符串数组，并转成整型
	String[] ss=numQueue.split("\\D+");
	int[] pageQ=new int[ss.length];
	for(int i=0;i<pageQ.length;i++){
		pageQ[i]=Integer.parseInt(ss[i]);
	}
	//提取数组中最大的数字
	   Arrays.sort(pageQ);
	   pagenumber=pageQ[pageQ.length-1];}
       return pagenumber;
	
	}
	
		
	

}
