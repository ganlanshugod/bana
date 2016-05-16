/**
* @Company 青鸟软通   
* @Title: AccessLogWrapper.java 
* @Package org.bana.web.log 
* @author Liu Wenjie   
* @date 2015-5-26 下午8:55:05 
* @version V1.0   
*/ 
package org.bana.web.log;

import javax.servlet.http.HttpServletRequest;

import org.bana.core.log.AccessLogDomain;
import org.bana.web.http.StatusExposingServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: AccessLogWrapper 
 * @Description: 访问日志的适配方法，可以独立运行，也可以继承其中的几个方法 
 *  
 */
public class AccessLogWrapper {
	
	private static final Logger LOG = LoggerFactory.getLogger(AccessLogWrapper.class);
	/** 
	* @Description: 初始化一个访问日志对象的domain类
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:07:00   
	*/ 
	public AccessLogDomain createAccessLogDomain(){
		return new AccessLogDomain();
	}

	/** 
	* @Description: 扩展对应的开始日志
	* @author Liu Wenjie   
	 * @param accessLogDomain 
	 * @param response 
	 * @param request 
	* @date 2015-5-26 下午9:42:27   
	*/ 
	public void doExtendBeginLog(HttpServletRequest request, StatusExposingServletResponse response, AccessLogDomain accessLogDomain) {
		// TODO Auto-generated method stub
	}

	/** 
	* @Description: 扩展对应的结束日志
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:53:25 
	* @param request
	* @param response
	* @param accessLogDomain  
	*/ 
	public void doExtendEndLog(HttpServletRequest request, StatusExposingServletResponse response, AccessLogDomain accessLogDomain) {
		// TODO Auto-generated method stub
	}

	/** 
	* @Description: 保存日志信息的方法
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:58:39 
	* @param logDomain  
	*/ 
	public void doSaveAccessLog(AccessLogDomain logDomain) {
		LOG.warn("当前的url执行日志没有进行真正的保存，只是进行控制台打印，请重载此方法来保存对应的执行日志" + logDomain);
	}
}
