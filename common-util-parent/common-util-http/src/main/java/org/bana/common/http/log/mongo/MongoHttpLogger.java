/**
* @Company weipu
* @Title: MongoHttpLogger.java 
* @Package org.bana.wechat.common.log.mongo 
* @author liuwenjie   
* @date May 15, 2020 3:05:37 PM 
* @version V1.0   
*/ 
package org.bana.common.http.log.mongo;

import org.bana.common.http.log.HttpLogDomain;
import org.bana.common.http.log.HttpLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;

/** 
* @ClassName: MongoHttpLogger 
* @Description: 微信日志的mongo保存方式
* @author liuwenjie   
*/
public class MongoHttpLogger extends HttpLogger{
	
	private MongoTemplate mongoTemplate;
	
	private String collectionName = "t_http_api_log";
	
	private static final Logger LOG = LoggerFactory.getLogger(MongoHttpLogger.class);
	/**
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date May 15, 2020 3:06:46 PM 
	* @param logDomain 
	* @see org.bana.wechat.common.log.HttpLogger#doSaveHttpLog(org.bana.wechat.common.log.HttpLogDomain) 
	*/ 
	@Override
	protected void doSaveHttpLog(HttpLogDomain logDomain) {
//		super.doSaveHttpLog(logDomain);
//		Map<String, Object> convertBean = BeanToMapUtil.convertBean(param);
		LOG.info("===微信使用Mongo保存访问记录===" + logDomain.getUrl());
		mongoTemplate.save(logDomain, collectionName);
	}
	public MongoTemplate getMongoTemplate() {
		return mongoTemplate;
	}
	public void setMongoTemplate(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}
	public String getCollectionName() {
		return collectionName;
	}
	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}
	
	
}
