/**
* @Company weipu   
* @Title: NetworkUtil.java 
* @Package com.jbinfo.i3618.web.util 
* @author Liu Wenjie   
* @date 2015-5-13 下午9:15:14 
* @version V1.0   
*/ 
package org.bana.web.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: NetworkUtil 
 * @Description: 网络单的工具类 
 *  
 */
public final class NetworkUtil {  
    /** 
     * Logger for this class 
     */  
    private static Logger logger = LoggerFactory.getLogger(NetworkUtil.class);  
    
    
    /**
    * @Description: 获取当前访问的URL
    * @author Liu Wenjie   
    * @date 2015-5-21 下午11:10:31 
    * @param request
    * @return
     */
    public static String getCurrentUrl(HttpServletRequest request){
    	String projectPath = request.getScheme() + "://"
    			+ request.getServerName();
    	String port = request.getServerPort() + "";
    	if(!port.equals("80")){
    		projectPath += ":" + port;
    	}
    	
    	String path = request.getContextPath();
    	String basePath = projectPath + path;
    	String servletPath = request.getServletPath();
//    	String requestURI = request.getRequestURI();
//    	StringBuffer requestURL = request.getRequestURL();
    	return basePath + servletPath;
    }
  
    /** 
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址; 
     *  
     * @param request 
     * @return 
     * @throws IOException 
     */  
    public final static String getIpAddress(HttpServletRequest request) {  
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址  
  
        String ip = request.getHeader("X-Forwarded-For");  
        if (logger.isInfoEnabled()) {  
            logger.info("getIpAddress(HttpServletRequest) - X-Forwarded-For - String ip=" + ip);  
        }  
  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("Proxy-Client-IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("WL-Proxy-Client-IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - WL-Proxy-Client-IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_CLIENT_IP");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_CLIENT_IP - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - HTTP_X_FORWARDED_FOR - String ip=" + ip);  
                }  
            }  
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
                ip = request.getRemoteAddr();  
                if (logger.isInfoEnabled()) {  
                    logger.info("getIpAddress(HttpServletRequest) - getRemoteAddr - String ip=" + ip);  
                }  
            }  
        } else if (ip.length() > 15) {  
            String[] ips = ip.split(",");  
            for (int index = 0; index < ips.length; index++) {  
                String strIp = (String) ips[index];  
                if (!("unknown".equalsIgnoreCase(strIp))) {  
                    ip = strIp;  
                    break;  
                }  
            }  
        }  
        return ip;  
    }
    
    /**
     * @Description: 获取一个参数Map的副本
     * @author Liu Wenjie   
     * @date 2015-5-13 下午10:02:10 
     * @param request
     * @return
      */
     public static Map<String,String[]> getParameterMap(HttpServletRequest request) {
         // 参数Map
     	Map<String,String[]> properties = request.getParameterMap();
     	
         // 返回值Map
     	Map<String,String[]>  returnMap = new HashMap<String,String[]> ();
         Set<Entry<String, String[]>> entrySet = properties.entrySet();
         for (Entry<String, String[]> entry2 : entrySet) {
         	String name = (String) entry2.getKey();
         	String[] valueObj = entry2.getValue();
         	String[] value2 = new String[valueObj.length];
         	System.arraycopy(valueObj, 0, value2, 0, valueObj.length);
         	returnMap.put(name, value2);
 		}
         return returnMap;
     }
}
