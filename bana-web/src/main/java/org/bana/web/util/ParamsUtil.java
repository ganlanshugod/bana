/**
* @Company weipu   
* @Title: WebUtil.java 
* @Package com.jbinfo.common.util 
* @author Liu Wenjie   
* @date 2014-1-2 下午4:51:12 
* @version V1.0   
*/ 
package  org.bana.web.util;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * @ClassName: WebUtil 
 * @Description: web使用的几个工具类
 *  
 */
public class ParamsUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ParamsUtil.class);
	
	/**
	* @Description: 将请求中的所有参数，已字符串的形式格式化
	* @author Liu Wenjie   
	* @date 2013-11-15 下午3:23:19 
	* @param httpServletRequest
	* @return
	 */
	public static String toParameterString(HttpServletRequest httpServletRequest){
		if(httpServletRequest == null){
			LOG.warn("httpServletRequest is null");
			return "";
		}
		Enumeration<String> paramEnumeration = httpServletRequest.getParameterNames();
		if(!paramEnumeration.hasMoreElements()){
			return "";
		}
		StringBuffer stringBuffer = new StringBuffer();
		while(paramEnumeration.hasMoreElements()){
			String paramName = paramEnumeration.nextElement();
			stringBuffer.append("&");
			stringBuffer.append(paramName);
			stringBuffer.append("=");
			stringBuffer.append(httpServletRequest.getParameter(paramName));
		}
		stringBuffer.replace(0, 1, "?");
		return stringBuffer.toString();
	}
}
