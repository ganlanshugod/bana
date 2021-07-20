/**
* @Company weipu   
* @Title: AccessLogFilter.java 
* @Package org.bana.web.filter 
* @author Liu Wenjie   
* @date 2015-5-26 下午7:51:15 
* @version V1.0   
*/ 
package org.bana.web.log;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bana.common.util.basic.MapRunable;
import org.bana.common.util.exception.ThrowableUtil;
import org.bana.core.log.AccessLogDomain;
import org.bana.web.http.StatusExposingServletResponse;
import org.bana.web.util.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/** 
 * @ClassName: AccessLogFilter 
 * @Description: web端进行日志行为记录的过滤器方法
 *  
 */
public class AccessLogFilter implements Filter{
	private static final Logger LOG = LoggerFactory.getLogger(AccessLogFilter.class);
	
	//创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。
	private	static ExecutorService pool = Executors.newCachedThreadPool();
	
	/** 
	* @Fields accessLogWrapper : 执行日志扩展和保存的service类
	*/ 
	private AccessLogWrapper accessLogWrapper;
	
	private List<String> ignoreList;
	
	private static final String DEFAULT_IGNORE = ".*\\.(css|js|png|jpeg|gif|jpg)";
	
	/**
	* <p>Description: 初始化访问日志记录 </p> 
	* @author Liu Wenjie   
	* @date 2015-5-26 下午7:52:15 
	* @param filterConfig
	* @throws ServletException 
	* @see javax.servlet.Filter#init(javax.servlet.FilterConfig) 
	*/ 
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//加载配置参数
		Map<String, String> initMap = new HashMap<String, String>();
		for (Enumeration<String> enu = filterConfig.getInitParameterNames(); enu.hasMoreElements();) {
			String key = enu.nextElement();
			initMap.put(key, filterConfig.getInitParameter(key));
		}
		//初始化accessLogWrapper
		ApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(filterConfig.getServletContext());
		String accessLogWrapperId = StringUtils.defaultIfEmpty(initMap.get("accessLogWrapperId"), "accessLogWrapper");

		this.accessLogWrapper = applicationContext.getBean(accessLogWrapperId, AccessLogWrapper.class);
		//
		String ignoreStr = initMap.get("ignoreList");
		if(!StringUtils.isBlank(ignoreStr)){
			ignoreList = Arrays.asList(ignoreStr.split(","));
		}else{
			ignoreList = Arrays.asList(DEFAULT_IGNORE);
		}
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-5-26 下午7:52:15 
	* @param request
	* @param response
	* @param chain
	* @throws IOException
	* @throws ServletException 
	* @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain) 
	*/ 
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		LOG.info("AccessLogFilter.. 执行开始=========== " + request.getRequestURL());
		String url = request.getRequestURL().toString();
		//判断是否拦截记录日志
		if(ignoreList != null && !ignoreList.isEmpty()){
			for (String pattern : ignoreList) {
				if(url.matches(StringUtils.trim(pattern))){
					//与忽略的匹配则不去执行，返回
					chain.doFilter(request, servletResponse);
					LOG.info("AccessLogFilter.. 执行结束，忽略日志=========== " + request.getRequestURL());
					return;
				}
			}
		}
		//初始化一个保存的日志对象
		AccessLogDomain accessLogDomain = accessLogWrapper.createAccessLogDomain();
		StatusExposingServletResponse response = new StatusExposingServletResponse((HttpServletResponse)servletResponse);
		try {
			if(accessLogDomain == null){
				LOG.error("获取的保存访问日志的对象为空");
				chain.doFilter(request, response);
				return;
			}
			//创建需要保存的日志对象
			//访问前的参数属性
			createBeginLog(request,response,accessLogDomain);
			chain.doFilter(request, response);
			createEndLog(request,response,accessLogDomain);
		} catch(IOException e){
			createEndLog(request, response, accessLogDomain);
			accessLogDomain.setErrorMessage(ThrowableUtil.getStackTrace(e));
			throw e;
		} catch(ServletException e){
			createEndLog(request, response, accessLogDomain);
			accessLogDomain.setErrorMessage(ThrowableUtil.getStackTrace(e));
			throw e;
		} finally {
			saveAccessLog(accessLogDomain);
			LOG.info("AccessLogFilter.. 执行结束===========" + request.getRequestURL());
		}
	}
	
	/** 
	* @Description: 执行保存日志，另起一个线程进行日志的保存工作
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:53:57 
	* @param accessLogDomain  
	*/ 
	private void saveAccessLog(AccessLogDomain accessLogDomain) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monitorLog", accessLogDomain);
		pool.execute(new MapRunable(map) {
			@Override
			public void run() {
				AccessLogDomain logDomain = (AccessLogDomain) map.get("monitorLog");
				accessLogWrapper.doSaveAccessLog(logDomain);
			}
		});
		
	}
	
	public static void main(String[] args) {
		String regx = ".*\\.(css|js|png|jpeg|gif|jpg)";
		System.out.println("http://ww.baodk.sdf/sdkflj.jsp".matches(regx));
	}

	/** 
	* @Description: 创建结束时的日志内容
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:43:38 
	* @param request
	* @param response  
	 * @param accessLogDomain 
	*/ 
	private void createEndLog(HttpServletRequest request, StatusExposingServletResponse response, AccessLogDomain accessLogDomain) {
		//结束时间
		Date endDate = new Date();
		accessLogDomain.setEndTime(endDate);
		accessLogDomain.setEndMillisecond(endDate.getTime());
		//计算执行时间
		Long startMillisecond = accessLogDomain.getStartMillisecond();
		if(startMillisecond != null){
			accessLogDomain.setDuration(endDate.getTime() - startMillisecond);
		}
		//计算执行的返回结果状态和内容信息
		accessLogDomain.setStatusCode(String.valueOf(response.getStatus()));
		accessLogDomain.setErrorMessage(response.getMessage());
		
		//增加扩展点，可以追加结束时的日志信息
		accessLogWrapper.doExtendEndLog(request,response,accessLogDomain);
	}

	/** 
	* @Description: 创建开始的参数信息
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:18:07 
	* @param request
	* @param response
	* @param accessLogDomain  
	*/ 
	private void createBeginLog(HttpServletRequest request, StatusExposingServletResponse response, AccessLogDomain accessLogDomain) {
		//设置开始时间
		Date beginDate = new Date();
		accessLogDomain.setStartTime(beginDate);
		accessLogDomain.setStartMillisecond(beginDate.getTime());
		//用户ip
		accessLogDomain.setUserIp(NetworkUtil.getIpAddress(request));
		//用户客户端信息
		accessLogDomain.setClientInfo(request.getHeader("user-agent"));
		//访问类型
		accessLogDomain.setExecuteMethod(request.getMethod());
		//参数的map对象
		Map<String, String[]> parameterMap = NetworkUtil.getParameterMap(request);
		accessLogDomain.setParamsMap(parameterMap);
		//访问的url链接
		accessLogDomain.setExecuteUrl(request.getRequestURL().toString());
		//提供扩展点，可以增加开始的日志信息
		accessLogWrapper.doExtendBeginLog(request,response,accessLogDomain);
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-5-26 下午7:52:15  
	* @see javax.servlet.Filter#destroy() 
	*/ 
	@Override
	public void destroy() {
		
	}

	
}
