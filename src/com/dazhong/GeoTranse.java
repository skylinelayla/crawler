package com.dazhong;
/*
 * 为了测试百度开放接口
 * 类似于http请求
 * 这里准备封装成一个方法
 * 包含解析输出经纬度信息。
 * 返回的文件是XML文件信息，使用DOM方式解析XML文件
 * 调用解析结果两个get方法
 * 有时百度接口没有数据，此时输出没有相关数据
 * 处理新浪微博的坐标经纬度数据，利用百度提供的逆地址编码服务接口
 * 然后进行解析
 * 添加GPS坐标和google坐标转换接口，soso地图和高德地图转换接口
 * 实现将传入的坐标转换成百度地图的坐标
 * GPS,google,soso,amap
 * bug是 变量问题
 * 
 */
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.bcel.generic.CPInstruction;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jetty.util.ajax.JSONObjectConvertor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


public class GeoTranse {
	public static String Coordinate;
	 public static String lat;
     public static String lng;
     public static String address;
     public static String transeadd;
     public static double translat;
     public static double translon;
     public static String addBaidu;
    
	//实际地址转换为百度经纬度
	public void transeCoord(String address) throws IOException, ParserConfigurationException, SAXException{
	
	 String add=java.net.URLEncoder.encode(address,"utf-8");
	 //进行http请求传入的URL是爬取到的地址加百度接口可以解析的请求网址
     String url="http://api.map.baidu.com/geocoder/v2/?ak=IloGy85Sya4jSMXIH14YIlrb&callback=renderOption&output=xml&address=+"+add+"&city=上海市";
	  
	        CloseableHttpClient httpclient = HttpClients.createDefault();
	         
	        try {
	            HttpGet httpget = new HttpGet(url);
	            //System.out.println("executing request " + httpget.getURI());
	 
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	 
	                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        return entity != null ? EntityUtils.toString(entity) : null;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }
	            };
	            Coordinate = httpclient.execute(httpget, responseHandler);//网页下载存储变量
	      
	           //System.out.println(Coordinate);
	           //解析xml文件
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	             DocumentBuilder builder = factory.newDocumentBuilder();
	             StringReader sr = new StringReader(Coordinate);
	             InputSource is = new InputSource(sr);
	             Document doc = builder.parse(is);
	             Element rootElement = doc.getDocumentElement();
	             NodeList location=rootElement.getElementsByTagName("result");
	             //当有解析有问题时的判断数值status正常是0，不正常是1
	             int Think = Integer.parseInt(doc.getElementsByTagName("status").item(0).getFirstChild().getTextContent());
	            // System.out.println(Think);
	             
	           
	             for (int i = 0; i < location.getLength(); i++) {
	            	 if(Think==1) 
	            	 { continue;}
	            	 else{
	            	 Element element = (Element)location.item(i); 
	            	 String name=element.getAttribute("loaction"); 
	            	 
	            	 lat=element.getElementsByTagName("lat").item(0).getFirstChild().getNodeValue();  
	         
	            	 lng=element.getElementsByTagName("lng").item(0).getFirstChild().getNodeValue();  
	            	//System.out.println(lat);
	            	//System.out.println(lng);
	            	 
	            	 }
	            
	             }
	           

	         
	           
	        } finally {
	            httpclient.close();
	        }
	    }
	
	public String getLat(){
		return lat;
	}
	public String getLng(){
		return lng;
	}
	
	//经纬度转换为实际地址
	public String addtranse(double sinalat,double sinalng) throws IOException, ParserConfigurationException, SAXException{
		 String url="http://api.map.baidu.com/geocoder/v2/?ak=IloGy85Sya4jSMXIH14YIlrb&callback=renderReverse&location="+sinalat+","+sinalng+"&output=xml&pois=0";
		 CloseableHttpClient httpclient = HttpClients.createDefault();
         
	        try {
	            HttpGet httpget = new HttpGet(url);
	            System.out.println("executing request " + httpget.getURI());
	 
	            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
	 
	                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
	                    int status = response.getStatusLine().getStatusCode();
	                    if (status >= 200 && status < 300) {
	                        HttpEntity entity = response.getEntity();
	                        return entity != null ? EntityUtils.toString(entity) : null;
	                    } else {
	                        throw new ClientProtocolException("Unexpected response status: " + status);
	                    }
	                }
	            };
	            address = httpclient.execute(httpget, responseHandler);
	            //System.out.println(address);
	            //解析xml文件
	            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	             DocumentBuilder builder = factory.newDocumentBuilder();
	             StringReader sr = new StringReader(address);
	             InputSource is = new InputSource(sr);
	             Document doc = builder.parse(is);
	             Element rootElement = doc.getDocumentElement();
	             NodeList location=rootElement.getElementsByTagName("result");
	             int Think = Integer.parseInt(doc.getElementsByTagName("status").item(0).getFirstChild().getTextContent());
	             for (int i = 0; i < location.getLength(); i++) {
	            	 if(Think==1) 
	            	 { continue;}
	            	 else{
	            	 Element element = (Element)location.item(i); 
	            	
	            	 
	            	 transeadd=element.getElementsByTagName("formatted_address").item(0).getFirstChild().getNodeValue();  
	         
	            	// lng=element.getElementsByTagName("lng").item(0).getFirstChild().getNodeValue();  
	            	//System.out.println(lat);
	            	//System.out.println(transeadd);
	            	 }}
	}finally {
        httpclient.close();
    }
	
	return transeadd;
}

	//进行不同地图坐标系的转换
	public void transMapcoordinate(String mapName,double latIn,double lonIn) throws ClientProtocolException, IOException, ParserConfigurationException, SAXException{
		String[] map={"GPS","google","soso","amap","sogou"};
		
		
		if(mapName==map[0]||mapName==map[4]){
			//坐标是GPS米制坐标或者是搜狗地图坐标转换
			String url="http://api.map.baidu.com/geoconv/v1/?coords="+lonIn+","+latIn+"&from=2&to=5&ak=IloGy85Sya4jSMXIH14YIlrb";
			 CloseableHttpClient httpclient = HttpClients.createDefault();
	         
		        try {
		            HttpGet httpget = new HttpGet(url);
		            System.out.println("executing request " + httpget.getURI());
		 
		            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		 
		                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
		                    int status = response.getStatusLine().getStatusCode();
		                    if (status >= 200 && status < 300) {
		                        HttpEntity entity = response.getEntity();
		                        return entity != null ? EntityUtils.toString(entity) : null;
		                    } else {
		                        throw new ClientProtocolException("Unexpected response status: " + status);
		                    }
		                }
		            };
		            addBaidu = httpclient.execute(httpget, responseHandler);
		          // System.out.println(addBaidu);
			
		            
		          //解析xml文件
		            
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		             DocumentBuilder builder = factory.newDocumentBuilder();
		             StringReader sr = new StringReader(addBaidu);
		             InputSource is = new InputSource(sr);
		             Document doc = builder.parse(is);
		             Element rootElement = doc.getDocumentElement();
		             NodeList location=rootElement.getElementsByTagName("result");
		             int Think = Integer.parseInt(doc.getElementsByTagName("status").item(0).getFirstChild().getTextContent());
		             for (int i = 0; i < location.getLength(); i++) {
		            	 if(Think==1) 
		            	 { continue;}
		            	 else{
		            	 Element element = (Element)location.item(i); 
		            
		            	 
		            	  translon=Double.parseDouble(element.getElementsByTagName("x").item(0).getFirstChild().getNodeValue());
	 
		            	  translat=Double.parseDouble(element.getElementsByTagName("y").item(0).getFirstChild().getNodeValue());
		         
		            	/*System.out.println(translat);
		            	System.out.println(translon);
		            	*/
		            	 
		            	 }
		            
		             }
		            
		            
		}
		finally{
			httpclient.close();
		}
		
		
		
		
	}
		else if(mapName==map[1]||mapName==map[2]||mapName==map[3]){
			//谷歌，soso地图,高德地图
			
			String url2="http://api.map.baidu.com/geoconv/v1/?coords="+lonIn+","+latIn+"&from=3&to=5&output=xml&ak=IloGy85Sya4jSMXIH14YIlrb";
			 CloseableHttpClient httpclient2 = HttpClients.createDefault();
	         
		        try {
		            HttpGet httpget = new HttpGet(url2);
		            System.out.println("executing request " + httpget.getURI());
		 
		            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
		 
		                public String handleResponse(final HttpResponse response) throws ClientProtocolException, IOException {
		                    int status = response.getStatusLine().getStatusCode();
		                    if (status >= 200 && status < 300) {
		                        HttpEntity entity = response.getEntity();
		                        return entity != null ? EntityUtils.toString(entity) : null;
		                    } else {
		                        throw new ClientProtocolException("Unexpected response status: " + status);
		                    }
		                }
		            };
		            addBaidu=httpclient2.execute(httpget, responseHandler);
		            //System.out.println(addBaidu);
		            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		             DocumentBuilder builder = factory.newDocumentBuilder();
		             StringReader sr = new StringReader(addBaidu);
		             InputSource is = new InputSource(sr);
		             Document doc = builder.parse(is);
		             Element rootElement = doc.getDocumentElement();
		             NodeList location=rootElement.getElementsByTagName("result");
		             int Think = Integer.parseInt(doc.getElementsByTagName("status").item(0).getFirstChild().getTextContent());
		             for (int i = 0; i < location.getLength(); i++) {
		            	 if(Think==1) 
		            	 { continue;}
		            	 else{
		            	 Element element = (Element)location.item(i); 
		            		 
		            	 translon=Double.parseDouble(element.getElementsByTagName("x").item(0).getFirstChild().getNodeValue());
		            	 
		            	  translat=Double.parseDouble(element.getElementsByTagName("y").item(0).getFirstChild().getNodeValue());
		         
		            	
		            	
		            	 }
		            
		             }
		            
		            
		            
			
			
		}
		finally{
			httpclient2.close();
		}
			
		//将传入的坐标转换为新浪坐标
		       
		      
			
			
		}
	}
	
	
	
	  public void transSinacoord(double lat,double lon){
			String url="http://api.map.baidu.com/geoconv/v1/?coords="+lon+","+lat+"&from=2&to=5&ak=IloGy85Sya4jSMXIH14YIlrb";
      }
	
	
	public double getTranslat(){
		
		return translat;
		
	}
	public double getTranslon(){
		return translon;
	}
}	 
		
		
	


