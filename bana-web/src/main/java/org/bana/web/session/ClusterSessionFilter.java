package org.bana.web.session;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.bana.web.session.service.SessionService;
import org.bana.web.util.NetworkUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 基于Memcached实现的session共享
 * 
 * @author WangXuzheng
 * 
 */
public class ClusterSessionFilter implements Filter {
	private static final Logger LOG = LoggerFactory.getLogger(ClusterSessionFilter.class);
	/**
	 * 默认的保存session的变量名
	 */
	private static final String DEFAULT_SESSION_KEY = "sid";
	private static final String CONTANTS_FIRST_DOMAIN = "first_level_domain";
	private String sessionKey = DEFAULT_SESSION_KEY;
	private String cookieDomain;
	private String cookiePath;
	private boolean checkIp;
	/**
	 * 忽略不被解析的url
	 */
	private Pattern requestUriIgnorePattern;
	/**
	 * sessionService对象
	 */
	private SessionService sessionService;
	private ServletContext servletContext;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.servletContext = filterConfig.getServletContext();
		Map<String, String> initMap = new HashMap<String, String>();
		for (Enumeration<String> enu = filterConfig.getInitParameterNames(); enu.hasMoreElements();) {
			String key = enu.nextElement();
			initMap.put(key, filterConfig.getInitParameter(key));
		}
		String contextPath = filterConfig.getServletContext().getContextPath();
		String path = null;
		if (contextPath.startsWith("/")) {
			path = contextPath.substring(1);
		} else {
			path = DEFAULT_SESSION_KEY;
		}
		// 从context中获取sessionKey和domain的变量参数
		this.sessionKey = StringUtils.defaultIfEmpty((String) this.servletContext.getAttribute("sessionKey"),
				path + "_sessionId");
		this.cookieDomain = StringUtils.defaultIfEmpty((String) this.servletContext.getAttribute("cookieDomain"), "");
		this.cookiePath = StringUtils.defaultIfEmpty((String) this.servletContext.getAttribute("cookiePath"), "/");
		// 使用sessionFilter配置中的参数赋值
		if (!StringUtils.isBlank(initMap.get("sessionKey"))) {
			this.sessionKey = initMap.get("sessionKey");
		}
		if (!StringUtils.isBlank(initMap.get("cookieDomain"))) {
			this.cookieDomain = initMap.get("cookieDomain");
		}
		if (!StringUtils.isBlank(initMap.get("cookiePath"))) {
			this.cookiePath = initMap.get("cookiePath");
		}
		if (!StringUtils.isBlank(initMap.get("checkIp"))) {
			this.checkIp = initMap.get("checkIp").equals("true");
		}

		ApplicationContext applicationContext = WebApplicationContextUtils
				.getWebApplicationContext(filterConfig.getServletContext());
		String sessionServiceId = StringUtils.defaultIfEmpty(initMap.get("sessionServiceId"), "sessionService");
		this.sessionService = applicationContext.getBean(sessionServiceId, SessionService.class);
		String ignorUrlPattern = StringUtils.defaultIfEmpty(initMap.get("requestUriIgnorePattern"), "");
		this.requestUriIgnorePattern = Pattern.compile(ignorUrlPattern, Pattern.CASE_INSENSITIVE);
		LOG.debug("SessionFilter初始完成" + initMap);
		LOG.debug("SessionFilter初始完成,关键属性值为 sessionKey=" + sessionKey + ",cookieDomain=" + cookieDomain + ",cookiePath="
				+ cookiePath);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {
		LOG.debug("SessionFilter.. 执行开始===========");
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		// 提出特殊连接的过滤
		String requestUrl = request.getRequestURI();
		if (this.requestUriIgnorePattern.matcher(requestUrl).matches()) {
			chain.doFilter(servletRequest, servletResponse);
			return;
		}

		Cookie cookies[] = request.getCookies();
		String sid = "";
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookie.getName().equals(sessionKey)) {
					sid = cookie.getValue();
					break;
				}
			}
		}
		String ip = NetworkUtil.getIpAddress(request);
		int ipHash = ip.hashCode();
		String sessionId = generateSessionString(ipHash);
		if (StringUtils.isBlank(sid)) {
			resetSessionIdValueInCookie(request, response, sessionId);
			sid = sessionId;
		} else {// 判断是否伪造session
			if (checkIp) {
				String[] sessionIdArr = sid.split("!");
				if (sessionIdArr != null && sessionIdArr.length == 2) {
					if (!StringUtils.equals(String.valueOf(ipHash), sessionIdArr[1])) {
						resetSessionIdValueInCookie(request, response, sessionId);
						sid = sessionId;
					}
				}
			}
		}
		ClusterHttpServletRequestWrapper clusterHttpServletRequest = new ClusterHttpServletRequestWrapper(
				servletContext, request, sid, this.sessionService);
		chain.doFilter(clusterHttpServletRequest, servletResponse);
		// 更新session的活动时间
		ClusterHttpSessionWrapper clusterHttpSessionWrapper = (ClusterHttpSessionWrapper) clusterHttpServletRequest
				.getSession();
		if (clusterHttpSessionWrapper != null) {
			clusterHttpSessionWrapper.saveSession();
		}
		LOG.debug("SessionFilter.. 执行结束===========");
	}

	private void resetSessionIdValueInCookie(HttpServletRequest request, HttpServletResponse response,
			String sessionId) {
		Cookie cookie = new Cookie(sessionKey, sessionId);
		cookie.setMaxAge(-1);
		if (StringUtils.isNotBlank(this.cookieDomain)) {
			if (this.cookieDomain.equalsIgnoreCase(CONTANTS_FIRST_DOMAIN)) {
				String domainName = request.getServerName();
				// TODO 还要有一个判断是否是当前IP的功能，待完善
				if (domainName.indexOf(".") == -1) {
					cookie.setDomain(domainName);
				} else {// 一班没有“."的都是localhost
					String localDomain = domainName.substring(domainName.indexOf("."));
					cookie.setDomain(localDomain);
				}
			} else {
				cookie.setDomain(this.cookieDomain);
			}
		}
		cookie.setPath(this.cookiePath);
		response.addCookie(cookie);
	}

	/**
	 * 生成sessionID，格式xxxx!zzzz
	 * 
	 * @param ipHash
	 * @return
	 */
	private String generateSessionString(int ipHash) {
		return new StringBuilder(UUID.randomUUID().toString()).append("!").append(ipHash).toString();
	}

	@Override
	public void destroy() {
	}

}
