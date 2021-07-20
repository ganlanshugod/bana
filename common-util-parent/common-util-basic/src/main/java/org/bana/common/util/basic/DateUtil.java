/**
* @Company weipu   
* @Title: DateUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-10-29 上午9:09:50 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: DateUtil 
 * @Description: 日期类型的工具类 
 *  
 */
public class DateUtil extends DateUtils{
	
//	private static final Logger LOG = LoggerFactory.getLogger(DateUtil.class);
	
	/** 
	* @Fields supportDateFormat : 目前支持的日期格式问题
	*/ 
	private static String[] supportDateFormat = new String[]{
		"yyyy-MM-dd'T'HH:mm:ss.SSSXXX","yyyy-MM-dd'T'HH:mm:ss.SSSZ",
		"yyyy-MM-dd'T'HH:mm:ssXXX","yyyy-MM-dd'T'HH:mm:ssZ",
		"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm","yyyy-MM-dd","yyyy-MM",
		"yyyy年MM月dd日  HH时mm分ss秒","yyyy年MM月dd日  HH时mm分","yyyy年MM月dd日","yyyy年MM月",
		"MM/dd/yyyy"
	};
	
	
	/**
     * 获取格式为"yyyy年MM月dd日 HH:mm:ss"格式的当前时间字符串
     * @return 
     */
    public static String getNowString(){
        return getNowString("yyyy年MM月dd日  HH:mm:ss");
    }
    
    /**
     * 根据传入的格式获取当前时间字符串（格式必须良好）
     * @param dateFormat
     * @return
     */
    public static String getNowString(String dateFormat){
    	
    	String dateFormatValue = "";
        if(StringUtils.isBlank(dateFormat)){
        	dateFormatValue = "yyyy年MM月dd日  HH:mm:ss";
        }else{
        	dateFormatValue = dateFormat;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatValue);
        return sdf.format(new Date());
    }
    
    /** 
    * @Description: 格式化对应的时间
    * @author Liu Wenjie   
    * @date 2014-11-24 下午5:24:27 
    * @param dateStr
    * @param dateFormat
    * @return  
    */ 
    public static Date formateToDate(String dateStr,String dateFormat){
    	if(StringUtils.isBlank(dateStr)){
    		return null;
    	}
    	String dateFormatValue = "";
		if(StringUtils.isBlank(dateFormat)){
			dateFormatValue = "yyyy年MM月dd日  HH:mm:ss";
	    }else{
	     	dateFormatValue = dateFormat;
	    }
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormatValue);
        try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return formateToDate(dateStr);
		}
    }
    
    /** 
    * @Description: 当前支持的所有时间格式逐步实验来解析对应的时间格式
    * @author Liu Wenjie   
    * @date 2014-11-25 下午11:34:18 
    * @param dateStr
    * @return  
    */ 
    public static Date formateToDate(String dateStr){
    	if(StringUtils.isBlank(dateStr)){
    		return null;
    	}
    	for (String format : supportDateFormat) {
			DateFormat df = new SimpleDateFormat(format);
			try {
				return df.parse(dateStr);
			} catch (ParseException ex) {
				continue;
			}
		}
		throw new BanaUtilException("解析日期 " + dateStr + " 失败,不支持的时间格式");
    }
    
    /** 
    * @Description: 按照指定的日期格式,转化日期字符串
    * @author Liu Wenjie   
    * @date 2014-11-15 下午5:51:15 
    * @param date
    * @param dateFormat
    * @return  
    */ 
    public static String toString(Date date,String dateFormat){
    	String dateFormatValue = "yyyy年MM月dd日  HH:mm:ss";
        if(!StringUtils.isBlank(dateFormat)){
        	dateFormatValue = dateFormat;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormatValue);
        if(date == null){
        	date = new Date();
        }
        return sdf.format(date);
    }

	/** 
	* @Description: 判断当前日期是否是昨天
	* @author Liu Wenjie   
	* @date 2014-11-24 下午4:50:20 
	* @param updateTime
	* @return  
	*/ 
    public static boolean isYesterday(Date oldTime) {  
    	if(oldTime == null){
    		return false;
    	}
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        String nowString = getNowString("yyyy-MM-dd");
        Date today = formateToDate(nowString, "yyyy-MM-dd");
        //昨天 86400000=24*60*60*1000 一天  
        if((today.getTime()-oldTime.getTime())>0 && (today.getTime()-oldTime.getTime())<=86400000) {  
            return true;  
        }  
        return false;
    }

	/** 
	* @Description: 判断制定的日期是否是时间格式
	* @author Liu Wenjie   
	* @date 2014-11-24 下午5:42:55 
	* @param updateTime
	* @return  
	*/ 
	public static boolean isToday(Date newTime) {
		if(newTime == null){
    		return false;
    	}
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点  
        String nowString = getNowString("yyyy-MM-dd");
        Date today = formateToDate(nowString, "yyyy-MM-dd");
        //昨天 86400000=24*60*60*1000 一天  
        if((newTime.getTime() - today.getTime())>0 && (newTime.getTime() - today.getTime())<=86400000) {  
            return true;  
        }
        return false;
	}  
	/**
	* @Description: 获取今天的其实时间，00：00：00
	* @author Liu Wenjie   
	* @date 2015-8-11 下午2:04:56 
	* @return
	 */
	public static Date getTodayBegin(){
		Calendar cal = GregorianCalendar.getInstance();
//		cal.setTime(new Date());
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/** 
	* @Description: 获取当前时间的下一天
	* @author Liu Wenjie   
	* @date 2015-9-17 下午7:51:58 
	* @return  
	*/ 
	public static Date nextDay(){
		Calendar cal = GregorianCalendar.getInstance();
//		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/** 
	* @Description: 取得月第一天
	* @author Han Tongyang   
	* @date 2016-1-20 下午4:52:51 
	* @param date
	* @return  
	*/ 
	public static Date getFirstDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMinimum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMinimum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMinimum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMinimum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	/** 
	* @Description: 取得月第一天
	* @author Han Tongyang   
	* @date 2016-1-20 下午4:31:59 
	* @param date
	* @param format
	* @return String
	*/ 
	public static String getFirstDateOfMonth(Date date, String format) {
		return toString(getFirstDateOfMonth(date), format);
	}

	/** 
	* @Description: 取得月最后一天
	* @author Han Tongyang   
	* @date 2016-1-20 下午4:52:41 
	* @param date
	* @return Date  
	*/
	public static Date getLastDateOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		c.set(Calendar.HOUR_OF_DAY,c.getActualMaximum(Calendar.HOUR_OF_DAY));
		c.set(Calendar.MINUTE,c.getActualMaximum(Calendar.MINUTE));
		c.set(Calendar.SECOND,c.getActualMaximum(Calendar.SECOND));
		c.set(Calendar.MILLISECOND, c.getActualMaximum(Calendar.MILLISECOND));
		return c.getTime();
	}
	
	/** 
	* @Description: 取得月最后一天
	* @author Han Tongyang   
	* @date 2016-1-20 下午4:31:59 
	* @param date
	* @param format
	* @return String
	*/ 
	public static String getLastDateOfMonth(Date date, String format) {
		return toString(getLastDateOfMonth(date), format);
	}
	
	/** 
	* @Description: 获取日期对应的年、月、日、小时、分钟
	* @author Han Tongyang   
	* @date 2016-2-21 下午3:57:19 
	* @param date
	* @param calendarType 例如：Calendar.YEAR
	* @return  
	*/ 
	public static int getCalendarDate(Date date, int calendarType){
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		return now.get(calendarType);
	}
	
	/** 
	* @Description: 根据日期格式条件，验证字符串是否是想要的日期格式
	* @author Han Tongyang   
	* @date 2016-2-21 上午9:31:24 
	* @param str
	* @param formatStr
	* @return  
	*/ 
	public static boolean isValidDate(String str, String formatStr) {
		if(StringUtils.isBlank(formatStr)){
			formatStr = "yyyy-MM-dd";
		}
		boolean convertSuccess = true;
		// 02-29特殊处理，modified by zhang zhichao
		if("MM-dd".equals(formatStr) && "02-29".equals(str)) {
			return convertSuccess;
		}
		// 根据参数指定要格式化的日期格式
		SimpleDateFormat format = new SimpleDateFormat(formatStr);
		try {
			// 设置lenient为false，否则SimpleDateFormat会比较宽松地验证日期，比如2015/02/29会被接受，并转换成2015/03/01
			format.setLenient(false);
			format.parse(str);
		} catch (ParseException e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	/** 
	* @Description: 获取两个日期中的所有日期列表，默认格式yyyy-MM-dd
	* @author Han Tongyang   
	* @date 2016-2-29 下午5:16:23 
	* @param beginDate
	* @param endDate
	* @param format
	* @return  
	*/ 
	public static List<String> intervalDateDetailList(String beginDate, String endDate, String format){
		if(StringUtils.isBlank(format)){
			format = "yyyy-MM-dd";
		}
		List<String> dates = new ArrayList<String>();
		Calendar startDay = Calendar.getInstance();
		Calendar endDay = Calendar.getInstance();
		startDay.setTime(DateUtil.formateToDate(beginDate, "yyyy-MM-dd"));
		endDay.setTime(DateUtil.formateToDate(endDate, "yyyy-MM-dd"));
		if(startDay.compareTo(endDay) > 0){
			return null;
		}
		if(startDay.compareTo(endDay) == 0){
			dates.add(DateUtil.toString(DateUtil.formateToDate(beginDate, "yyyy-MM-dd"), format));
			return dates;
		}
		//添加开始时间
		dates.add(DateUtil.toString(startDay.getTime(), format));
		//循环添加其余日期
		Calendar currentDate = startDay;
		while (true) {
			// 日期加一  
			currentDate.add(Calendar.DATE, 1);
			dates.add(DateUtil.toString(currentDate.getTime(), format));
			//如果当前日期大于结束日期，则跳出循环
			if(currentDate.compareTo(endDay) == 0){
				break;
			}
		}
		return dates;
	}
	
	/**
	 * @Description: 字符串类型日期格式的计算
	 * @author Han Tongyang
	 * @date 2016-2-20 下午6:13:47
	 * @param smdate
	 * @param bdate
	 * @param format 日期格式
	 * @param returnType 返回值类型, d表示天，h表示小时，m表示分钟
	 * @return
	 */
	public static int daysBetween(String smdate, String bdate, String format, String returnType){
		if(StringUtils.isBlank(smdate) || StringUtils.isBlank(bdate)){
			return -1;
		}
		try {
			if(StringUtils.isBlank(format)){
				format = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return calculationDiff(sdf.parse(smdate), sdf.parse(bdate), returnType);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * 计算两个日期之间相差的天数
	 * 
	 * @author Han Tongyang
	 * @date 2016-2-21 上午9:59:40
	 * @param smdate
	 * @param bdate
	 * @param format 日期格式
	 * @param returnType 返回值类型, d表示天，h表示小时，m表示分钟
	 * @return
	 */
	public static int daysBetween(Date smdate, Date bdate, String format, String returnType){
		try {
			if(StringUtils.isBlank(format)){
				format = "yyyy-MM-dd";
			}
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			return calculationDiff(smdate, bdate, returnType);
		} catch (Exception e) {
			return -1;
		}
	}
	
	/** 
	* @Description: 计算两个日期差值
	* @author Han Tongyang   
	* @date 2016-2-21 上午10:46:53 
	* @param smdate
	* @param bdate
	* @param returnType
	* @return  
	*/ 
	private static int calculationDiff(Date smdate, Date bdate, String returnType){
		Calendar cal = Calendar.getInstance();
		//将smdate转换成long类型
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		//将bdate转换成long类型
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		//比较smdate和bdate，并转换成对应的类型
		long between_days = 0;
		if("m".equals(returnType)){
			between_days = (time2 - time1) / (1000 * 60);
		}else if("h".equals(returnType)){
			between_days = (time2 - time1) / (1000 * 3600);
		}else{
			between_days = (time2 - time1) / (1000 * 3600 * 24);	
		}
		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/** 
	* @Description: 根据传入日期和相隔天数计算相隔的日期，并格式化
	* @author Han Tongyang   
	* @date 2016-4-12 下午1:54:49 
	* @param date
	* @param day
	* @param flag true:正推即当前日期后的日期，false：倒推即当前日期前的日期
	* @param format
	* @return  
	*/ 
	public static String calendarDate(Date date, int day, boolean flag, String format){
		if(!flag){
			day = -day;
		}
		Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, day);
        Date returnDate = cal.getTime();
        if(StringUtils.isBlank(format)){
        	format = "yyyy-MM-dd";
        }
        return toString(returnDate, format);
	}
	
	/** 
	* @Description: 获取指定日期的前一天，指定日期为空的话则获取当前日期的前一天
	* @author Han Tongyang   
	* @date 2016-5-23 下午7:57:16 
	* @param today
	* @return  
	*/ 
	public static Date getYesterDay(Date today){
		Calendar cal = Calendar.getInstance();
		if(today == null){
			today = new Date();
		}
		cal.setTime(today);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}
	
}
