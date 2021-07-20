/**
 * @Company weipu   
 * @Title: EhcacheManagerFactoryBeanProxy.java 
 * @Package com.jbinfo.i3618.Cache 
 * @author Yang Shuangshuang   
 * @date 2015-4-10 下午5:05:04 
 * @version V1.0   
 */
package org.bana.core.cache;

import java.io.IOException;

import net.sf.ehcache.CacheException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;

/**
 * @ClassName: EhcacheManagerFactoryBeanProxy
 * @Description: EhCacheManagerFactory包装类
 * 
 */
public class EhcacheManagerFactoryBeanProxy extends EhCacheManagerFactoryBean {
protected final Logger logger = LoggerFactory.getLogger(EhcacheManagerFactoryBeanProxy.class);
	
	private boolean cacheFlag = true;

	@Override
	public void afterPropertiesSet() throws IOException, CacheException {
		if(!cacheFlag){
			logger.warn("************ehcache disabled!!!");
			return;
		}
		super.afterPropertiesSet();
	}

	public boolean isCacheFlag() {
		return cacheFlag;
	}

	public void setCacheFlag(boolean cacheFlag) {
		this.cacheFlag = cacheFlag;
	}
}
