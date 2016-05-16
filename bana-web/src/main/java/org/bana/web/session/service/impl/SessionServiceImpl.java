/**
 * @Company 青鸟软通   
 * @Title: SessionServiceImpl.java 
 * @Package com.jbinfo.i3618.system.session.service.impl 
 * @author Yang Shuangshuang   
 * @date 2015-4-11 下午1:37:33 
 * @version V1.0   
 */
package org.bana.web.session.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.bana.core.cache.EhcacheCacheManagerProxy;
import org.bana.web.session.service.SessionService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.util.Assert;

/**
 * @ClassName: SessionServiceImpl
 * @Description: 基于memcach实现的session存储操作
 * 
 */
public class SessionServiceImpl implements SessionService, InitializingBean {

	private EhcacheCacheManagerProxy cacheManager;
	private String cacheName = "session";

	public void setCacheManager(EhcacheCacheManagerProxy cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<Object, Object> getSession(String sessionId) {
		Cache cache = getCache();
		ValueWrapper valueWrapper = cache.get(sessionId);
		if (valueWrapper == null) {
			Map<Object, Object> session = new HashMap<Object, Object>();
			session.put("_SESSION_CREATE_TIME_", System.currentTimeMillis());// session创建时间
			addSession(sessionId, session);
			return session;
		} else {
			return (Map<Object, Object>) valueWrapper.get();
		}
	}

	@Override
	public void updateSession(String sessionId, Map<Object, Object> session) {
		Cache cache = getCache();
		cache.put(sessionId, session);
	}

	private Cache getCache() {
		Cache cache = cacheManager.getCache(this.cacheName);
		Assert.notNull(cache);
		return cache;
	}

	@Override
	public void removeSession(String sessionId) {
		Cache cache = getCache();
		cache.evict(sessionId);
	}

	/**
	 * @Description: 属性 cacheManager 的get方法 
	 * @return cacheManager
	 */
	public EhcacheCacheManagerProxy getCacheManager() {
		return cacheManager;
	}

	/**
	 * @Description: 属性 cacheName 的get方法 
	 * @return cacheName
	 */
	public String getCacheName() {
		return cacheName;
	}

	@Override
	public void addSession(String id, Map<Object, Object> session) {
		Cache cache = getCache();
		cache.put(id, session);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cacheManager);
		Assert.notNull(cacheName);
		Assert.hasText(cacheName);
	}
}
