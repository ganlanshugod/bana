/**
 * @Company 青鸟软通   
 * @Title: EhcacheCacheManagerProxy.java 
 * @Package com.jbinfo.i3618.cache 
 * @author Yang Shuangshuang   
 * @date 2015-4-10 下午5:17:09 
 * @version V1.0   
 */
package org.bana.core.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.cluster.CacheCluster;
import net.sf.ehcache.cluster.ClusterNode;
import net.sf.ehcache.cluster.ClusterScheme;
import net.sf.ehcache.cluster.ClusterTopologyListener;

import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.support.AbstractCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.util.Assert;

/**
 * @ClassName: EhcacheCacheManagerProxy
 * @Description: CacheManager的包装类
 * 
 */
public class EhcacheCacheManagerProxy extends AbstractCacheManager {

	private static final String SIMPLE_CACHE_NAME = "simpleCache";
	private CacheManager cacheManager;
	private SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
	private List<ClusterTopologyListener> listenerList;
	private boolean globalFlag = true; // 外部标志位
	private boolean innerFlag = true; // 内部标志位

	/**
	 * Returns the backing Ehcache {@link net.sf.ehcache.CacheManager}.
	 * 
	 * @return
	 */
	public CacheManager getCacheManager() {
		return cacheManager;
	}

	/**
	 * Sets the backing EhCache {@link net.sf.ehcache.CacheManager}.
	 */
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 
	 * @param globalFlag
	 */
	public void setGlobalFlag(boolean globalFlag) {
		this.globalFlag = globalFlag;
	}

	public void setListenerList(List<ClusterTopologyListener> listenerList) {
		this.listenerList = listenerList;
	}

	@Override
	public void afterPropertiesSet() {
		// set default cache to simpleManager
		initSimpleCacheManager();

		if (!globalFlag) {
			return;
		}
		super.afterPropertiesSet();
	}

	@Override
	protected Collection<Cache> loadCaches() {
		Assert.notNull(this.cacheManager,
				"A backing EhCache CacheManager is required");
		Status status = this.cacheManager.getStatus();
		Assert.isTrue(Status.STATUS_ALIVE.equals(status),
				"An 'alive' EhCache CacheManager is required - current cache is "
						+ status.toString());

		String[] names = this.cacheManager.getCacheNames();
		Collection<Cache> caches = new LinkedHashSet<Cache>(names.length);
		for (String name : names) {
			caches.add(new EhCacheCache(this.cacheManager.getEhcache(name)));
		}
		// add cluster listener
		addClusterListener();

		return caches;
	}

	@Override
	public Cache getCache(String name) {
		if (!globalFlag || !innerFlag) {
			return simpleCacheManager.getCache(SIMPLE_CACHE_NAME);
		}
		Cache cache = super.getCache(name);
		if (cache == null) {
			// check the EhCache cache again
			// (in case the cache was added at runtime)
			Ehcache ehcache = this.cacheManager.getEhcache(name);
			if (ehcache != null) {
				cache = new EhCacheCache(ehcache);
				addCache(cache);
			}
		}
		return cache;
	}

	private void addClusterListener() {
		if (listenerList == null) {
			listenerList = new ArrayList<ClusterTopologyListener>();
		}
		listenerList.add(new ClusterTopologyListener() {
			@Override
			public void nodeLeft(ClusterNode node) {
				// LOGGER.error("cluster nodeLeft,nodeIp=" + (node == null ?
				// "null" : node.getIp()));
				innerFlag = true;
			}

			@Override
			public void nodeJoined(ClusterNode node) {
				// LOGGER.error("cluster nodeJoined,nodeIp=" + (node == null ?
				// "null" : node.getIp()));
				innerFlag = true;
			}

			@Override
			public void clusterRejoined(ClusterNode oldNode, ClusterNode newNode) {
				// LOGGER.error("cluster clusterRejoined,oldNodeIp=" + (oldNode
				// == null ? "null" : oldNode.getIp()) + ",newNodeIp=" +
				// (newNode == null ? "null" : newNode.getIp()));
				innerFlag = true;
			}

			@Override
			public void clusterOnline(ClusterNode node) {
				// LOGGER.error("cluster clusterOnline,nodeIp=" + (node == null
				// ? "null" : node.getIp()));
				innerFlag = true;
			}

			@Override
			public void clusterOffline(ClusterNode node) {
				// if(LOGGER.isWarnEnabled()){
				// LOGGER.warn("cluster clusterOffline,nodeIp=" + (node == null
				// ? "null" : node.getIp()));
				// }
				innerFlag = false;
			}
		});
		CacheCluster cluster = cacheManager
				.getCluster(ClusterScheme.TERRACOTTA);
		for (ClusterTopologyListener listener : listenerList) {
			if (listener == null) {
				continue;
			}
			cluster.addTopologyListener(listener);
		}
	}

	private void initSimpleCacheManager() {
		List<Cache> caches = new ArrayList<Cache>();
		ConcurrentMapCache memoryOnlyCache = new ConcurrentMapCache(
				SIMPLE_CACHE_NAME);
		// net.sf.ehcache.Cache memoryOnlyCache = new
		// net.sf.ehcache.Cache(SIMPLE_CACHE_NAME, 5000, false, false, 30, 20);
		// memoryOnlyCache.setCacheManager(cacheManager);
		// memoryOnlyCache.initialise();
		caches.add(memoryOnlyCache);
		simpleCacheManager.setCaches(caches);
		simpleCacheManager.afterPropertiesSet();
	}

}
