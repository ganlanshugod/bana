/**
 * @Company 青鸟软通   
 * @Title: ClusterHttpSessionWrapper.java 
 * @Package com.jbinfo.i3618.session.service.impl 
 * @author Yang Shuangshuang   
 * @date 2015-4-11 下午1:55:34 
 * @version V1.0   
 */
package org.bana.web.session;

import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import org.bana.web.session.service.SessionService;

/**
 * @ClassName: ClusterHttpSessionWrapper
 * @Description: HttpSession的包装类，链接SessionService组件对session进行操作
 * 
 */
@SuppressWarnings("deprecation")
public class ClusterHttpSessionWrapper implements HttpSession {

	private SessionService sessionService;
	private Map<Object, Object> map;
	private String sessionId;
	private long creationTime;
	private long lastAccessedTime;
	private int maxInactiveInterval;
	private ServletContext servletContext;
	private boolean isNew = false;
	public static final String SESSION_CREATE_TIME = "_SESSION_CREATE_TIME_";
	public static final String SESSION_LAST_ACCESS_TIME = "_SESSION_LAST_ACCESS_TIME_";
	/**
	 * 标志位，是否对session中的信息作了更新操作
	 */
	private boolean changed;

	public ClusterHttpSessionWrapper(String sessionId,
			ServletContext servletContext, SessionService sessionService) {
		this.sessionId = sessionId;
		this.sessionService = sessionService;
		this.map = this.sessionService.getSession(sessionId);
		this.creationTime = (Long) this.map.get(SESSION_CREATE_TIME);
		this.lastAccessedTime = System.currentTimeMillis();
		this.servletContext = servletContext;
		this.isNew = true;
	}

	@Override
	public Object getAttribute(String key) {
		resetLastAccessTime();
		return this.map.get(key);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		resetLastAccessTime();
		Set<String> keySets = new HashSet<String>();
		Set<Object> ksys = this.map.keySet();
		Iterator<Object> it = ksys.iterator();
		while (it.hasNext()) {
			String str = it.next().toString();
			keySets.add(str);
		}
		return Collections.enumeration(keySets);
	}

	@Override
	public void invalidate() {
		resetLastAccessTime();
		this.map.clear();
		this.map.put(SESSION_CREATE_TIME, System.currentTimeMillis());
		this.changed = true;
		this.sessionService.removeSession(this.sessionId);
	}

	@Override
	public void removeAttribute(String key) {
		resetLastAccessTime();
		this.map.remove(key);
		this.changed = true;
	}

	@Override
	public void setAttribute(String key, Object value) {
		resetLastAccessTime();
		this.map.put(key, value);
		this.changed = true;
	}

	@Override
	public String getId() {
		return this.sessionId;
	}

	public void saveSession() {
		if (this.changed) {
			this.sessionService.updateSession(this.sessionId, this.map);
		}
		this.changed = false;
	}

	@Override
	public long getCreationTime() {
		return creationTime;
	}

	@Override
	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	private void resetLastAccessTime() {
		this.lastAccessedTime = System.currentTimeMillis();
		this.map.put(SESSION_LAST_ACCESS_TIME, this.lastAccessedTime);
		this.map.put("lastAccessTime", new Date());
		this.changed = true;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {
		this.maxInactiveInterval = interval;
	}

	@Override
	public int getMaxInactiveInterval() {
		return this.maxInactiveInterval;
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		throw new UnsupportedOperationException("该方法不被支持调用");
	}

	@Override
	public Object getValue(String name) {
		return getAttribute(name);
	}

	@Override
	public String[] getValueNames() {
		throw new UnsupportedOperationException("该方法不被支持调用");
	}

	@Override
	public void putValue(String name, Object value) {
		setAttribute(name, value);
	}

	@Override
	public void removeValue(String name) {
		removeAttribute(name);
	}

	@Override
	public boolean isNew() {
		return isNew;
	}
	
	
}
