package com.sina;
/*获取某一地点范围内的poi点以及签到总数
 * 将爬取到的坐标先进行转换再进行地质转码，
 * 新浪使用的是高德地图数据amap
 * 
 */
import org.json.JSONArray;
import org.json.JSONObject;

import com.dazhong.GeoTranse;
import com.dazhong.httpGet;
import com.sql.creatDatabases;
import com.sql.sqlOperate;

public class nearbyPOI {
	public void goCraw(String tablename,double latIn,double lonIn,int range) throws Exception{
		//数据库操作
		
		
		creatDatabases data=new creatDatabases();
		data.creatSinaDataBase(tablename);
		sqlOperate sqlop=new sqlOperate();
		//将传入的坐标转换成新浪坐标
		
		
		
		String url="https://api.weibo.com/2/place/nearby/pois.json";
		String param="lat="+latIn+"&long="+lonIn+"&range="+range+"&count=50&access_token=";
		String urlParam=url+"?"+param;
		//请求get
		httpGet httpget=new httpGet();
		httpget.getByString(urlParam);
		
		String result=httpget.getResult();
		//解析json
		
		JSONObject jsonobj=new JSONObject(result);
		
		JSONArray jsonArray=jsonobj.getJSONArray("pois");
		//获取该地点周围总的poi数量以此来计算页码
		int num=jsonobj.getInt("total_number");
		System.out.println(num);
	
		int totalnumber=num;
		int count=50;
		int page=totalnumber/count+1;
		int i=1;
		
		//对获取了页数的所有网页进行遍历并且进行解析
		System.out.println(result);
		String url2="https://api.weibo.com/2/place/nearby/pois.json";
	while(i<=page){
		
		
		String param2="lat="+latIn+"&long="+lonIn+"&range="+range+"&page="+i+"&count=50&access_token=";
        String urlNameString = url2 + "?" + param2;
        httpGet httpget2=new httpGet();
		httpget2.getByString(urlNameString);
		
		String result2=httpget2.getResult();
		JSONObject jsonobj2=new JSONObject(result2);
		JSONArray jsonArray2=jsonobj2.getJSONArray("pois");
		for(int j=0;j<jsonArray2.length();j++){
		JSONObject poisObj=jsonArray2.getJSONObject(j);
		String poiid=poisObj.getString("poiid");
		double lat=poisObj.getDouble("lat");
		double lon=poisObj.getDouble("lon");
		int checknum=poisObj.getInt("checkin_num");
		//发现新浪返回的json数据中含有地址和商户名（即poi的签到地点名）欧日！
		
		String addsina=poisObj.getString("address");
		String addtitle=poisObj.getString("title");
		//调用百度地址转码服务
	/*	GeoTranse geo1=new GeoTranse();
		geo1.transeCoord(addsina);
*/
		
		
		//转义
		String r="''";
        String title=addtitle.replace("'",r);
		GeoTranse geo=new GeoTranse();
		//对高德地图的坐标数据进行转换
		geo.transMapcoordinate("amap", lat, lon);
		double latBaidu=geo.getTranslat();
		double lonBaidu=geo.getTranslon();
		
		/*double latBaidu=Double.parseDouble(geo1.getLat());
		double lonBaidu=Double.parseDouble(geo1.getLng());
		*/
		
		String poiadd=geo.addtranse(latBaidu, lonBaidu);
		
	
		//System.out.println(result2);
		System.out.println("lat is"+latBaidu+"long is"+lonBaidu+"poiid is"+poiid+"checknum is"+checknum+"sina add is"+addsina+"title is"+addtitle);
		System.out.println("lat is"+lat+"long is"+lon);
		sqlop.insertSina(tablename,poiid,title,addsina, latBaidu,lonBaidu,lat,lon,checknum);
		
		
		
	}
		i++;
		}
	}
}
