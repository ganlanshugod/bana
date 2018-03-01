/**
* @Company 青鸟软通   
* @Title: StringUtils.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-9-3 下午2:20:42 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bana.common.util.exception.BanaUtilException;

/** 
 * @ClassName: StringUtils 
 * @Description: 字符串的一些公共处理方法
 *  
 */
public class StringUtils extends org.apache.commons.lang.StringUtils{
	
	/** 
	* @Description: 判断一组字符串是否为空白，有一个为空白则返回true
	* @author Liu Wenjie   
	* @date 2014-10-27 下午1:22:55 
	* @param strs
	* @return  
	*/ 
	public static boolean isBlank(String... strs){
		if(strs == null || strs.length == 0){
			return true;
		}
		for (String string : strs) {
			if(org.apache.commons.lang.StringUtils.isBlank(string)){
				return true;
			}
		}
		return false;
	}
	
	/** 
	* @Description: 判断一组字符串是否为空白，所有字段都是空白才返回true，有一个不为空白返回false
	* @author Liu Wenjie   
	* @date 2014-10-27 下午1:22:55 
	* @param strs
	* @return  
	*/ 
	public static boolean allIsBlank(String... strs){
		if(strs == null || strs.length == 0){
			return true;
		}
		for (String string : strs) {
			if(org.apache.commons.lang.StringUtils.isNotBlank(string)){
				return false;
			}
		}
		return true;
	}
	
	/** 
	* @Description: 判断一组字符串是否为empty，有一个为empty则返回true
	* @author Liu Wenjie   
	* @date 2014-10-28 下午7:39:07 
	* @param strs
	* @return  
	*/ 
	public static boolean isEmpty(String... strs){
		if(strs == null || strs.length < 0){
			return true;
		}
		for (String string : strs) {
			if(org.apache.commons.lang.StringUtils.isEmpty(string)){
				return true;
			}
		}
		return false;
	}
	
	
	/** 
	* @Description: 使用context中的属性替换对应的${}包含的所有属性
	* @author Liu Wenjie   
	* @date 2014-10-27 下午6:48:51 
	* @param context
	* @return  
	*/ 
	public static String replaceAll(String sourceStr,Map<String,String> context){
		if(isBlank(sourceStr)){
			return sourceStr;
		}
		Pattern p = Pattern.compile("\\$\\{\\w+\\}");
		Matcher matcher = p.matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
            String temp = matcher.group();
            String key = temp.substring(2,temp.length() -1);
            String value = context.get(key);
            if(value != null){
            	matcher.appendReplacement(sb, value);
            }
        }
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/** 
	* @Description: 使用context中的属性替换对应的${}包含的所有属性
	* @author Liu Wenjie   
	* @date 2014-10-27 下午6:48:51 
	* @param context
	* @return  
	*/ 
	public static String replaceAllObject(String sourceStr,Map<String,Object> context){
		if(isBlank(sourceStr)){
			return sourceStr;
		}
		Pattern p = Pattern.compile("\\$\\{\\w+\\}");
		Matcher matcher = p.matcher(sourceStr);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
            String temp = matcher.group();
            String key = temp.substring(2,temp.length() -1);
            Object value = context.get(key);
            if(value != null){
            	matcher.appendReplacement(sb, value.toString());
            }
        }
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	
	/** 
	* @Description: 拆分字符串为一个数组
	* @author Liu Wenjie   
	* @date 2015-11-3 下午4:46:52 
	* @param str
	* @param split
	* @return  
	*/ 
	public static String[] split(String str,String split){
		if(isBlank(str)){
			return new String[]{};
		}
		return org.apache.commons.lang.StringUtils.split(str, split);
	}
	
	/** 
	* @Description: 按顺序拼接数组中的字符串，拼接后合并到指定的路径中,路径的分隔符要使用"/",并且最后的一个字符串后面一定是没有"/"的,即使原发功能法有,也会被删掉
	* @author Liu Wenjie   
	* @date 2014-10-28 下午7:34:56 
	* @param url
	* @return  
	*/ 
	public static String unionAsFilePath(String... paths){
		if(paths == null || paths.length == 0){
			return "";
		}
		StringBuffer sb = new StringBuffer();
		
		for (int i = 0; i < paths.length; i++) {
			String path = paths[i];
			if(isBlank(path)){
				continue;
			}
			if(i != 0 && !path.startsWith("/")){
				path = "/" + path;
			}
			if(path.endsWith("/")){
				path = path.substring(0,path.length() - 1);
			}
			sb.append(path);
		}
		return sb.toString();
	}
	
	/** 
	* @Description: 将给出的字符串的首字母变为大写
	* @author Liu Wenjie   
	* @date 2014-10-29 下午1:07:32 
	* @param str
	* @return  
	*/ 
	public static String upcaseFirstChar(String str){
		if(isBlank(str)){
			return str;
		}
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}
	
	/**
	* @Description: 返回给定的字段的JavaBean规范的驼峰式命名的内容
	* @author Liu Wenjie   
	* @date 2015-5-9 下午4:31:15 
	* @return
	 */
	public static String getJavaName(String columnName){
		Pattern p = Pattern.compile("_\\w");
		Matcher matcher = p.matcher(columnName.toLowerCase());
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			String group = matcher.group();
			matcher.appendReplacement(sb, group.substring(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	
	/**
	* @Description: 将字符串中的所有空格都删除掉
	* @author Liu Wenjie   
	* @date 2013-11-27 下午8:31:29 
	* @param source
	* @return
	 */
	public static String  trimAll(String source){
		if(source == null){
			return null;
		}
		if("".equals(source.trim())){
			return "";
		}
		//最后是一个中文全角的空格
		return source.trim().replaceAll("\\s+", "").replaceAll(" ", "");
	}
	
	/**
	* @Description: 删除所有不和发的\\uxxx的字段
	* @author liuwenjie   
	* @date 2016-4-25 下午1:52:43 
	* @param source
	* @return
	 */
	public static String replaceUxxx(String source){
		if(source == null){
			return null;
		}
		if("".equals(source.trim())){
			return "";
		}
		return source.replaceAll("\u0014", "").replaceAll("\u0017", "");
	}
	/** 
	* @Description: 将url参数转换成map
	* @author Liu Wenjie   
	* @date 2015-5-9 下午5:07:01 
	* @param param 
	* @return  aa=11&bb=22&cc=33
	*/ 
	public static Map<String, Object> getUrlParams(String param) {
		Map<String, Object> map = new HashMap<String, Object>(0);
		if (StringUtils.isBlank(param)) {
			return map;
		}
		String[] params = param.split("&");
		for (int i = 0; i < params.length; i++) {
			String[] p = params[i].split("=");
			if (p.length == 2) {
				map.put(p[0], p[1]);
			}
		}
		return map;
	}

	/** 
	* @Description: 将map转换成url
	* @author Liu Wenjie   
	* @date 2015-5-9 下午5:07:22 
	* @param map
	* @return  
	*/ 
	public static String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			Object value = entry.getValue();
			if(value != null){
				if(!(value instanceof String) || !StringUtils.isBlank((String)value)){
					sb.append(entry.getKey() + "=" + String.valueOf(entry.getValue()));
					sb.append("&");
				}
			}
		}
		String s = sb.toString();
		if (s.endsWith("&")) {
			s = substringBeforeLast(s, "&");
		}
		return s;
	}
	
	/**
	* @Description: 使用默认的字符串串联一个字符串集合，组成一个或者正则表达式的字符串
	* @author Liu Wenjie   
	* @date 2015-6-4 下午4:48:07 
	* @param strList
	* @return
	 */
	public static String unionAsRegx(List<String> strList){
		return unionAsRegxWithChar(strList,'#');
	}
	
	/** 
	* @Description: 使用指定的字符串串联一个字符串集合，组成一个或者正则表达式的字符串
	* @author Liu Wenjie   
	* @date 2015-6-4 下午4:45:39 
	* @param strList
	* @param seq
	* @return  
	*/ 
	public static String unionAsRegxWithChar(List<String> strList, char seq){
		if(strList == null || strList.isEmpty()){
			return "" + seq + seq;
		}
		StringBuffer sb = new StringBuffer();
		for (String str : strList) {
			if(StringUtils.isNotBlank(str)){
				sb.append(seq).append(str).append(seq).append("|");
			}
		}
		String string = sb.toString();
		if(StringUtils.isNotBlank(string) && string.endsWith("|")){
			string = string.substring(0,string.length() - 1);
		}
		return string;
	}
	
	/**
	* @Description: 使用指定的字符组合集合的字符串，并且开始和结尾都有指定字符
	* @author Liu Wenjie  
	* @date 2015-6-4 下午4:54:15 
	* @param strList
	* @param seq
	* @return
	 */
	public static String joinFirstEnd(List<? extends Object> strList, char seq){
		if(strList == null){
			return null;
		}
		return seq + join(strList,seq) + seq;
	}
	
	/** 
	* @Description:使用默认的字符组合集合的字符串，并且开始和结尾都有默认字符
	* @author Liu Wenjie   
	* @date 2015-6-4 下午5:01:18 
	* @param strList
	* @return  
	*/ 
	public static String joinFirstEnd(List<? extends Object>  strList){
		return joinFirstEnd(strList,'#');
	}
	
	/** 
	* @Description: 获取一个随机的英文字符串
	* @author Liu Wenjie   
	* @date 2015-7-1 下午5:18:09 
	* @return  
	*/ 
	public static String getRandomStr() {
		return getRandomStr(16);
	}
	
	/** 
	* @Description: 获取指定长度的随机字符串
	* @author liuwenjie   
	* @date 2017-1-20 下午1:18:08 
	* @param num
	* @return  
	*/ 
	public static String getRandomStr(int num){
		if(num <= 0){
			num = 16;
		}
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < num; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}
	
	/** 
	* @Description: 将一个strList转化为Long类型的集合
	* @author Liu Wenjie   
	* @date 2015-9-21 下午1:53:46 
	* @param strList
	* @return  
	*/ 
	public static List<Long> parseStringListToLong(List<String> strList){
		if(strList == null){
			return null;
		}
		List<Long> longList = new ArrayList<Long>();
		for (String string : strList) {
			if(!isBlank(string)){
				longList.add(Long.parseLong(string));
			}
		}
		return longList;
	}
	
	/** 
	* @Description: 将一个strList转化为Long类型的集合
	* @author Liu Wenjie   
	* @date 2015-9-21 下午1:53:46 
	* @param strList
	* @return  
	*/ 
	public static List<Integer> parseStringListToInteger(List<String> strList){
		if(strList == null){
			return null;
		}
		List<Integer> intList = new ArrayList<Integer>();
		for (String string : strList) {
			if(!isBlank(string)){
				intList.add(Integer.parseInt(string));
			}
		}
		return intList;
	}
	
	
	/**
	 * 获取字符串的长度，如果有中文，则每个中文字符计为2位
	 * @param value 指定的字符串
	 * @return 字符串的长度
	 */
	public static int chineselength(String s) {
		if (s == null)  
	       return 0;  
		char[] c = s.toCharArray();  
		int len = 0;  
		for (int i = 0; i < c.length; i++) {  
	       len++;  
	       if (!isLetter(c[i])) {  
	           len++;  
	       }  
		}  
		return len;  
	}
	
	
	/** 
	* @Description: 判断是否是字符
	* @author liuwenjie   
	* @date 2017-1-19 下午8:26:13 
	* @param c
	* @return  
	*/ 
	public static boolean isLetter(char c) {   
       int k = 0x80;   
       return c / k == 0 ? true : false;   
	}  
}
