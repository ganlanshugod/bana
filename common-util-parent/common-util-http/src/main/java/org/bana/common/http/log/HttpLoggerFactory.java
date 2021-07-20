/**
* @Company weipu   
* @Title: HttpLogger.java 
* @Package org.bana.wechat.qy.common.log 
* @author Liu Wenjie   
* @date 2015-5-27 下午2:28:15 
* @version V1.0   
*/ 
package org.bana.common.http.log;

import java.util.HashMap;
import java.util.Map;

import org.bana.common.http.BanaHttpException;

/** 
 * @ClassName: HttpLogger 
 * @Description: 微信的访问记录日志工具类
 *  
 */
public class HttpLoggerFactory {
	
	private static Class<? extends HttpLogger> cls;
	
	private static Map<String,HttpLogger> cacheMap = new HashMap<String, HttpLogger>();

	/**
	* @Description: 获取一条微信登录记录日志对象
	* @author Liu Wenjie   
	* @date 2015-5-27 下午2:42:17 
	* @return
	 */
	public static HttpLogger getHttpLogger(){
		if(cls != null){
			if(HttpLogger.class.isAssignableFrom(cls)){
				HttpLogger logger = cacheMap.get(cls.getName());
				if(logger == null) {
					try {
						logger = cls.newInstance();
					} catch (Exception e) {
						throw new BanaHttpException(BanaHttpException.LOG_ERROR,"获取日志记录的指定实现类时出现异常!",e);
					}
				}
				return logger;
			}
		}
		return new HttpLogger();
	}

	/**
	 * @Description: 属性 cls 的set方法 
	 * @param cls 
	 */
	public static void setCls(Class<? extends HttpLogger> cls) {
		HttpLoggerFactory.cls = cls;
	}
	
	/**
	 * @Description: 属性 cls 的set方法 
	 * @param cls 
	 */
	public static void setCls(Class<? extends HttpLogger> cls, HttpLogger logger) {
		HttpLoggerFactory.cls = cls;
		cacheMap.put(cls.getName(), logger);
	}
	
}
