/**
 * @Company 青鸟软通   
 * @Title: ClusterHttpServletRequestWrapper.java 
 * @Package com.jbinfo.i3618.session.service.impl 
 * @author Yang Shuangshuang   
 * @date 2015-4-11 下午2:19:06 
 * @version V1.0   
 */
package org.bana.web.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

import org.bana.web.session.service.SessionService;

/**
 * @ClassName: ClusterHttpServletRequestWrapper
 * @Description: 基于SessionService管理的httpRequest包装
 * 
 */
public class ClusterHttpServletRequestWrapper extends HttpServletRequestWrapper {
	private SessionService sessionService;
	private String sessionId;
	private HttpSession httpSession;
	private ServletContext servletContext;

	public ClusterHttpServletRequestWrapper(ServletContext servletContext,
			HttpServletRequest request, String sessionId,
			SessionService sessionService) {
		super(request);
		this.sessionId = sessionId;
		this.sessionService = sessionService;
		this.servletContext = servletContext;
	}

	@Override
	public HttpSession getSession(boolean create) {
		if (this.httpSession == null) {
			this.httpSession = new ClusterHttpSessionWrapper(this.sessionId,
					servletContext, this.sessionService);
		}
		return this.httpSession;
	}

	@Override
	public HttpSession getSession() {
		return getSession(false);
	}


}
