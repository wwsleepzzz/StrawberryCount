package com.zzz.wc.strawberrycount.util;

import com.zzz.wc.strawberrycount.database.TaskDBOpenHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static final SimpleDateFormat sdf_noTime = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm:ss");
	
	/**
	 * 換成民國
	 * @Date 2014/10/1  上午10:33:27
	 * @author Judy
	 * @param date
	 * @return String
	 */
	public static String toROCDate(Date date){
		String str ="";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int year =cal.get(Calendar.YEAR);
		year = year-1911;
		
		str=year+"."+ (cal.get(Calendar.MONTH)+1)+"."+cal.get(Calendar.DATE);
		return str;
	}
	
	/**
	 * 民國換成西元
	 * @Date 2014/10/1  上午10:33:11
	 * @author Judy
	 * @return Date
	 */
	public static Date toADDate(String str){
		Date date=null;
		if(str !=null){
			String sp[] =str.split("\\.");
			if(sp!=null && sp.length==3){
				int year=Integer.valueOf(toTrim(sp[0]));
				year=year+1911;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
				try {
					date= sdf.parse(year+"/"+ toTrim(sp[1])+"/"+toTrim(sp[2]));
				} catch (ParseException e) {
					e.printStackTrace();
					return date;
				}
			}
		}
		return date;
	}	
	
	public static String toTrim(String str){
		if(str !=null){
			str=str.trim();
		}
		return str;
	}
	
	public static int getMonth(Calendar cal){
		if(cal==null)
			return -1;
		return cal.get(Calendar.MONTH)+1;
	}
	
	public static int getMonth(Date date){
		if(date==null)
			return -1;
			
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.MONTH)+1;
	}
	
	public static int getHourOfDay(Date date){
		if(date==null)
			return -1;
			
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY);
	}
	
	public static int getDayOfMonth(Date date){
		if(date==null)
			return -1;
			
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_MONTH);
	}
	
	public static int getThisYear(){
		return Calendar.getInstance().get(Calendar.YEAR);
	}
	/**
	 * 取得當月最後一天
	 * @author wc
	 * @date 2015年5月27日 下午11:28:39
	 */
	public static int getLastDate(Calendar cal){
		cal.add(Calendar.MONTH, 1);//先跳到下一個月  
        cal.set(Calendar.DATE, 1);// 把日期設置為當月第一天   
        cal.add(Calendar.DATE, -1);// 日期回滾一天，也就是本月最後一天 
        return cal.get(Calendar.DATE);
	}
	
	/**
	 * 找上一個月
	 * @Date 2015/6/1  上午10:48:25
	 * @author Judy
	 * @return
	 */
	public static Calendar getPreMonth(int arrYear,int arrMonth){
		Calendar preCal=Calendar.getInstance();
		preCal.set(Calendar.YEAR, arrYear);
		preCal.set(Calendar.MONTH, arrMonth-1);//注意!月份從0開始
		preCal.set(Calendar.DAY_OF_MONTH, 1);
		preCal.add(Calendar.MONTH, -1);//找上一個月
		return preCal;
	}
	
	/**
	 * 找上個月
	 * @Date 2015/11/17  上午10:13:39
	 * @author Judy
	 * @return
	 */
	public static Date getPreMonth(){
		Calendar preCal=Calendar.getInstance();
		preCal.add(Calendar.MONTH, -1);//找上一個月
		
		return preCal.getTime();
	}
	
	public static int getYear(Date date){
		if(date==null)
			return -1;
			
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.YEAR);
	}
	
	
	
	/**
	 * 剪掉後面的時分秒
	 * @Date 2016/1/30  下午1:43:00
	 * @author Judy
	 * @param date
	 * @return
	 */
	public static Date trimTime(Date date){
		if(date==null)
			return date;
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		
		return cal.getTime();
	}

	public static Date getToday() {
		Calendar cl=Calendar.getInstance();
		Date date =  cl.getTime();

		return date;
	}

	public static Date stringToDate(String str){
		Date date = null;
		if(str==null || str.isEmpty()){
			return date;
		}

		try {
			date = sdf_noTime.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static Date stringToTime(String str){
		Date date = null;
		if(str==null || str.isEmpty()){
			return date;
		}

		try {
			date = hourFormat.parse(str);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	public static String timeToString(Date date){

		String str = null;
		if(date == null){
			return str;
		}

		try {
			str = hourFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}
}
