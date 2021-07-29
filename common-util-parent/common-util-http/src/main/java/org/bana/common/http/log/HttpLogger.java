/**
* @Company weipu   
* @Title: HttpLogger.java 
* @Package org.bana.wechat.qy.common.log 
* @author Liu Wenjie   
* @date 2015-5-27 下午2:28:15 
* @version V1.0   
*/ 
package org.bana.common.http.log;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.bana.common.http.util.ThreadPoolUtil;
import org.bana.common.util.basic.MapRunable;
import org.bana.common.util.exception.ThrowableUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/** 
 * @ClassName: HttpLogger 
 * @Description: 微信的访问记录日志工具类
 *  
 */
public class HttpLogger {

	protected static ThreadLocal<HttpLogDomain> HttpLogThreadLocal = new ThreadLocal<HttpLogDomain>();

	private	static ExecutorService pool = ThreadPoolUtil.getThreadPool();
	
	private static final Logger LOG = LoggerFactory.getLogger(HttpLogger.class);
	
	/** 
	* @Description: 保存结束对象
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:15:19 
	* @param result  
	*/ 
	public void logEnd() {
		HttpLogDomain HttpLogDomain = getHttpLogDomain();
		Date endDate = new Date();
		HttpLogDomain.setEndTime(endDate);
		HttpLogDomain.setEndMillisecond(endDate.getTime());
		Long startMillisecond = HttpLogDomain.getStartMillisecond();
		if(startMillisecond != null ){
			HttpLogDomain.setDuration(endDate.getTime() - startMillisecond);
		}
	}

	/** 
	* @Description: 记录异常信息
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:15:25 
	* @param e  
	*/ 
	public void logException(Exception e) {
		HttpLogDomain HttpLogDomain = getHttpLogDomain();
		HttpLogDomain.setExceptionClass(e.getClass().toString());
		HttpLogDomain.setExceptionMessage(ThrowableUtil.getStackTrace(e));
	}

	/** 
	* @Description: 
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:15:29   
	*/ 
	public final void saveLog() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monitorLog", getHttpLogDomain());
		pool.execute(new MapRunable(map) {
			@Override
			public void run() {
				HttpLogDomain logDomain = (HttpLogDomain) map.get("monitorLog");
				doSaveHttpLog(logDomain);
			}
		});
		HttpLogThreadLocal.remove();
	}
	
	/**
	* @Description: 需要具体的子类进行实现
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:50:31 
	* @param logDomain
	 */
	protected void doSaveHttpLog(HttpLogDomain logDomain){
		LOG.warn("===http访问的日志没有保存，只是控制台打印=== " + logDomain);
		LOG.warn("url 地址为 " + logDomain.getUrl());
		LOG.warn("参数为===" + logDomain.getParamData());
		LOG.warn("HTTP Status code 为 ===" + logDomain.getStatusCode());
		LOG.warn("返回结果为==" + logDomain.getResult());
		Map<String, String> responseHeader = logDomain.getResponseHeader();
		if(responseHeader != null) {
			LOG.warn("返回结果中的header参数为==" + responseHeader);
			Set<Entry<String, String>> entrySet = responseHeader.entrySet();
			for (Entry<String, String> entry : entrySet) {
				LOG.warn(entry.getKey() + "==" + entry.getValue());
			}
		}
		
	}
	/** 
	* @Description: 记录开始信息
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:17:31 
	* @param url
	* @param string
	* @param httpPost  
	*/ 
	public void logBegin(String url, String postData ,String method) {
		Date beginDate = new Date();
		HttpLogDomain HttpLogDomain = getHttpLogDomain();
		HttpLogDomain.setStartTime(beginDate);
		HttpLogDomain.setStartMillisecond(beginDate.getTime());
		HttpLogDomain.setUrl(url);
		HttpLogDomain.setParamData(postData);
		HttpLogDomain.setExecuteMethod(method);
	}
	
	/**
	* @Description: 获取日志记录对象
	* @author Liu Wenjie   
	* @date 2015-5-27 下午4:51:08 
	* @return
	 */
	public HttpLogDomain getHttpLogDomain(){
		HttpLogDomain HttpLogDomain = HttpLogThreadLocal.get();
		if(HttpLogDomain == null){
			HttpLogDomain = new HttpLogDomain();
			HttpLogThreadLocal.set(HttpLogDomain);
		}
		return HttpLogDomain;
	}

}
