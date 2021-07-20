/**
* @Company weipu   
* @Title: SessionServiceMongoImpl.java 
* @Package org.bana.web.session.service.impl 
* @author Liu Wenjie   
* @date 2015-7-14 下午5:14:16 
* @version V1.0   
*/ 
package org.bana.web.session.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.mongodb.MongoDBFactory;
import org.bana.web.session.service.SessionService;
import org.bana.web.session.service.impl.SessionMongoDBEncoder.SessionMongoDBEncoderFactory;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBEncoderFactory;
import com.mongodb.DBObject;

/** 
 * @ClassName: SessionServiceMongoImpl 
 * @Description: sessionService的MongoDb实现类
 *  
 */
public class SessionServiceMongoImpl implements SessionService{
	
	private static final String SESSION_KEY = "sessionId";
	private static final String EXTEND_SESSION_KEY_ = "_extend_session_map";
	
	/** 
	* @Fields mogoDbFactory : mogoDB的链接工厂类
	*/ 
	private MongoDBFactory mongoDBFactory;
	
	/** 
	* @Fields tableName : mongoDb的session实现类的数据库
	*/ 
	private String tableName = "session";
	
	/** 
	* @Fields dbName : 不同的系统可以使用不同的数据库来保存
	*/ 
	private String dbName = "bana";
	
	private static DBEncoderFactory encoderFactory = new SessionMongoDBEncoderFactory();
	
	
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-14 下午5:14:57 
	* @param sessionId
	* @return 
	* @see org.bana.web.session.service.SessionService#getSession(java.lang.String) 
	*/ 
	@SuppressWarnings({ "unchecked" })
	@Override
	public Map<Object, Object> getSession(String sessionId) {
		DBCollection sessionCollection = mongoDBFactory.getMongoDBCollection(dbName,tableName);
		sessionCollection.setDBEncoderFactory(encoderFactory);
		DBObject session = sessionCollection.findOne(new BasicDBObject(SESSION_KEY, sessionId));
		if(session == null){
			Date createDate = new Date();
			session = new BasicDBObject(SESSION_KEY,sessionId)
								.append("createTime", createDate)
								.append("_SESSION_CREATE_TIME_", createDate.getTime());
			sessionCollection.insert(session);
			sessionCollection.createIndex(new BasicDBObject(SESSION_KEY,1),new BasicDBObject("unique",true));
			return session.toMap();
		}else{
			//从数据库中读出session内容，将所有byte数组
			Map<Object,Object> map = session.toMap();
			//遍历这个map对象，如果value值是一个byte数组的时候，尝试进行对象转换，如果可以转化的话就进行转化，如果不能则不进行转换
			Set<Entry<Object, Object>> entrySet = map.entrySet();
			for (Entry<Object, Object> entry : entrySet) {
				if(entry.getValue() instanceof byte[]){
					InputStream inputStream = new ByteArrayInputStream((byte[])entry.getValue());  
					ObjectInputStream in = null;
					try {
						in = new ObjectInputStream(inputStream);
						Object obj = in.readObject();
						map.put(entry.getKey(), obj);
					} catch (Exception e) {
						//如果出现了错误，就不进行处理
						try {
							inputStream.close();
							if(in != null){
								in.close();
							}
						} catch (IOException e1) {
							//do nothing
						}
					}
				}
			}
			//将扩展的sessioin字段类型进行重组和修改
			Object object = map.get(EXTEND_SESSION_KEY_);
			if(object != null && object instanceof ExtendsSession){
				map.putAll(((ExtendsSession)object).toMap());
				map.put(EXTEND_SESSION_KEY_, null);
				map.remove(EXTEND_SESSION_KEY_);
			}
			return map;
		}
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-14 下午5:14:57 
	* @param sessionId
	* @param session 
	* @see org.bana.web.session.service.SessionService#updateSession(java.lang.String, java.util.Map) 
	*/ 
	@Override
	public void updateSession(String sessionId, Map<Object, Object> session) {
		if(StringUtils.isBlank(sessionId)){
			throw new BanaUtilException("sessionId 为空 ");
		}
		if(session == null){
			session = new HashMap<Object, Object>();
		}
		if(session.get(SESSION_KEY) == null || !sessionId.equals(session.get(SESSION_KEY))){
			session.put(SESSION_KEY, sessionId);
		}
		DBCollection sessionCollection = mongoDBFactory.getMongoDBCollection(dbName,tableName);
		sessionCollection.setDBEncoderFactory(encoderFactory);
		//过滤验证对应参数，如果session对象中的session中的key包含对应key值，则复制到extendMap对象中
		Set<Entry<Object, Object>> entrySet = session.entrySet();
		//找到需要替换的配置对象
		List<String> extendsKey = new ArrayList<String>();
		for (Entry<Object, Object> entry : entrySet) {
			if(entry.getKey() instanceof String){
				String key = (String)entry.getKey();
				if ( key.contains( "\0" ) || key.contains( "." ) || key.startsWith( "$" )){
					extendsKey.add(key);
				}
			}
		}
		//替换对应的配置内容
		if(!extendsKey.isEmpty()){
			ExtendsSession extendsSession = new ExtendsSession();
			for (String key : extendsKey) {
				extendsSession.put(key, session.get(key));
				session.put(key, null);
				session.remove(key);
			}
			if(!extendsSession.isEmpty()){
				session.put(EXTEND_SESSION_KEY_, extendsSession);
			}
		}
		
		BasicDBObject newDBObject = new BasicDBObject(session);
		sessionCollection.update(new BasicDBObject(SESSION_KEY,sessionId), newDBObject, true, false);
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-14 下午5:14:57 
	* @param id 
	* @see org.bana.web.session.service.SessionService#removeSession(java.lang.String) 
	*/ 
	@Override
	public void removeSession(String sessionId) {
		DBCollection sessionCollection = mongoDBFactory.getMongoDBCollection(dbName,tableName);
		sessionCollection.remove(new BasicDBObject(SESSION_KEY,sessionId));
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-7-14 下午5:14:57 
	* @param sessionId
	* @param session 
	* @see org.bana.web.session.service.SessionService#addSession(java.lang.String, java.util.Map) 
	*/ 
	@Override
	public void addSession(String sessionId, Map<Object, Object> session) {
		updateSession(sessionId, session);
	}

	/*=============getter and setter ===============*/
	/**
	 * @Description: 属性 mongoDBFactory 的set方法 
	 * @param mongoDBFactory 
	 */
	public void setMongoDBFactory(MongoDBFactory mongoDBFactory) {
		this.mongoDBFactory = mongoDBFactory;
	}

	/**
	 * @Description: 属性 tableName 的set方法 
	 * @param tableName 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @Description: 属性 dbName 的set方法 
	 * @param dbName 
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	

}
