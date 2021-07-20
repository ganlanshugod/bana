/**
* @Company weipu   
* @Title: URLUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-10-23 上午9:46:22 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: URLUtil 
 * @Description: URL的一个验证工具
 *  
 */
public class URLUtil {
	private static final Logger LOG =  LoggerFactory.getLogger(URLUtil.class);
	/** 
	* @Fields REQUEST_COUNT : 链接请求的访问次数
	*/ 
	private static int REQUEST_COUNT = 5;
	
	
	/** 
	* @Description: 从指定的url中移出指定的参数值
	* @author liuwenjie   
	* @date 2016-8-26 上午11:31:15 
	* @param url
	* @param name
	* @return  
	*/ 
	public static String removeUrlParam(String url,String... nameArr){
		if(nameArr != null && StringUtils.isNotBlank(url)){
			for (String name : nameArr) {
				if (StringUtils.isNotBlank(name)) {  
		            url = url.replaceAll("(&?" + name + "=[^&]*)", "").replaceAll("(\\?&)", "\\?");
		        }
			}
		}
		if(url.charAt(url.length() - 1) == '?'){
			url = url.substring(0,url.length()-1);
		}
        return url;  
	}
	
	/** 
	* @Description: 根据指定的参数名，替换对应的参数值，如果没有对应的参数值，则不进行替换操作
	* @author liuwenjie   
	* @date 2016-8-26 上午11:29:46 
	* @param url
	* @param name
	* @param newValue
	* @return  
	*/ 
	public static String replaceUrlParam(String url,String name,String newValue){
        if (StringUtils.isNotBlank(url) && StringUtils.isNotBlank(newValue)) {  
            url = url.replaceAll("(" + name + "=[^&]*)", name + "=" + newValue);  
        }  
        return url;  
	}
    /** 
    * @Description:  * 功能：检测当前URL是否可连接或是否有效, 
    * 描述：最多连接网络 3 次, 如果 3 次都不成功，视为该地址不可用 
    * @author Liu Wenjie   
    * @date 2014-10-23 上午9:46:31 
    * @param urlStr
    * @return  
    */ 
    public static int isConnect(String urlStr) {  
        int counts = 0;  
        int responseCode = 0;  
        if (StringUtils.isBlank(urlStr)) {                         
            return responseCode;                   
        }  
        while (counts < REQUEST_COUNT) {  
            long start = 0;  
            HttpURLConnection conn = null;
            try {  
                URL url = new URL(urlStr);  
                start = System.currentTimeMillis();  
                conn = (HttpURLConnection) url.openConnection();  
                responseCode = conn.getResponseCode();  
                LOG.info("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");  
                if (responseCode == 200) {  
                    LOG.info(urlStr+"--可用，返回的应答编码为" + responseCode);  
                }else{
                	LOG.info(urlStr + "--不可用，返回的应答编码为" + responseCode);
                }
                break;  
            }catch (Exception ex) {  
                counts++;   
                LOG.info("请求断开的URL一次需要："+(System.currentTimeMillis()-start)+"毫秒");  
                LOG.info("连接第 "+counts+" 次，"+urlStr+"--不可用");  
                continue;  
            } finally{
            	if(conn != null){
            		conn.disconnect();
            	}
            }
        }  
        return responseCode;  
    }
    
    
    /** 
	* @Description: 判断是否是已授权的url
	* @author Liu Wenjie   
	* @date 2014-11-13 下午2:42:51 
	* @param url  
	*/ 
	public static boolean authenticatorUrl(String url,String resource){
		if(url == null){
			return false;
		}
		String regStr = getRegStr(resource);
		
		Pattern p = Pattern.compile(regStr);
		Matcher matcher = p.matcher(url);
		return matcher.find();
	}
	
	
	/** 
	* @Description: 获取资源对应的匹配字符
	* @author Liu Wenjie   
	* @date 2014-11-13 下午5:42:48 
	* @param url
	* @return  
	*/
	public static String getRegStr(String resource){
		Pattern p = Pattern.compile("\\{\\w+\\}");
		Matcher matcher = p.matcher(resource);
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
	        matcher.appendReplacement(sb, ".+");
	    }
		matcher.appendTail(sb);
		String regx = sb.toString();
		if(regx.endsWith("/")){
			regx.substring(0,regx.length() -1);
		}
		return "^" + regx + "/*$";
	}
    
    
	/**
	 * @Description: 属性 rEQUEST_COUNT 的set方法 
	 * @param rEQUEST_COUNT 
	 */
	public static void setREQUEST_COUNT(int rEQUEST_COUNT) {
		REQUEST_COUNT = rEQUEST_COUNT;
	}
	 
}
