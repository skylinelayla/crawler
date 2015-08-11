package com.sina;

import java.text.ParseException;
import java.util.Date;

/*Unix时间转换类
 * 实现Unix时间和北京时间互转
 * 
 */
public class transTime {
	
	public long nowTime(){
		//显示系统当前的Unix时间
	long epoch = System.currentTimeMillis();
	return epoch;
	
	}
	public String Unix2Beijing(long unix){
		//Unix时间转北京时间
		String time=new java.text.SimpleDateFormat("yyyy MM-dd HH:mm:ss").format(new java.util.Date (unix));
		return time;
	}
	public long Beijing2Unix(String beijing) throws ParseException{
		//注意北京时间的格式为 dd/MM/yyyy HH:mm:ss，北京时间转Unix时间
		long epoch1 = new java.text.SimpleDateFormat ("dd/MM/yyyy HH:mm:ss").parse(beijing).getTime();
		return epoch1;
	}
	//将系统时间转换为连续的数字，这样可以作为表名
	public String timename(long time){
		String name=new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date(time));
		return name;
	}
}

