/**
* @Company weipu   
* @Title: SessionServiceTest.java 
* @Package org.bana.web.session.service 
* @author Liu Wenjie   
* @date 2015-7-14 下午5:25:29 
* @version V1.0   
*/ 
package org.bana.web.session.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bana.common.util.mongodb.MongoDBFactory;
import org.bana.web.session.service.impl.SessionServiceMongoImpl;
import org.junit.Before;
import org.junit.Test;

import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/** 
 * @ClassName: SessionServiceTest 
 * @Description: 测试session的mongoDB实现方式 
 *  
 */
public class SessionServiceTest {
	
	private SessionService sessionService;
	
	@Before
	public void init(){
		MongoDBFactory mongoDBFactory = createMongoDBFactory();
		SessionServiceMongoImpl sessionServiceImpl = new SessionServiceMongoImpl();
		sessionServiceImpl.setMongoDBFactory(mongoDBFactory);
//		sessionServiceImpl.setDbName("admin");
		sessionService = sessionServiceImpl;
	}
	
	private MongoDBFactory createMongoDBFactory(){
		MongoDBFactory mongoFactory = new MongoDBFactory();
		Map<String,String> server = new HashMap<String,String>();
		server.put("host", "121.42.199.71");
		server.put("port", "27017");
		List<Map<String,String>> serverAddress = new ArrayList<Map<String,String>>();
		serverAddress.add(server);
		mongoFactory.setServerAddress(serverAddress);
		
		Map<String,String> mongoCred = new HashMap<String, String>();
		mongoCred.put("userName", "root");
		mongoCred.put("database","admin");
		mongoCred.put("password", "jbinfo");
		Map<String,String> mongoCred2 = new HashMap<String, String>();
		mongoCred2.put("userName", "i3618");
		mongoCred2.put("database","bana");
		mongoCred2.put("password", "Jbinfo456123");
		List<Map<String,String>> mongoCredList = new ArrayList<Map<String,String>>();
//		mongoCredList.add(mongoCred);
		mongoCredList.add(mongoCred2);
		mongoFactory.setCredentialsList(mongoCredList);
		mongoFactory.init();
		return mongoFactory;
	}
	
	@Test
	public void testCollectioin(){
		MongoDBFactory mongoDBFactory = createMongoDBFactory();
		DBCollection mongoDBCollection = mongoDBFactory.getMongoDBCollection("local", "startup_log");
		DBObject findOne = mongoDBCollection.findOne();
		System.out.println(findOne.toMap());
	}

	/**
	 * Test method for {@link org.bana.web.session.service.SessionService#getSession(java.lang.String)}.
	 */
	@Test
	public void testGetSession() {
		Map<Object, Object> session = sessionService.getSession("4b1b1a7f-a600-4ac9-99e3-ba211f6e92aa!1650373400");
		System.out.println(session.get("loginContext"));
	}

	/**
	 * Test method for {@link org.bana.web.session.service.SessionService#updateSession(java.lang.String, java.util.Map)}.
	 */
	@Test
	public void testUpdateSession() {
		Map<Object, Object> session = sessionService.getSession("test10");
		session.put("loginContext",Arrays.asList("name","value","test") );
		sessionService.updateSession("test10", session);
	}

	/**
	 * Test method for {@link org.bana.web.session.service.SessionService#removeSession(java.lang.String)}.
	 */
	@Test
	public void testRemoveSession() {
		sessionService.removeSession("test10");
	}

	/**
	 * Test method for {@link org.bana.web.session.service.SessionService#addSession(java.lang.String, java.util.Map)}.
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void testAddSession() throws UnsupportedEncodingException {
		Map<Object, Object> session = new HashMap<Object, Object>();
		session.put("loginContext",new LoginContext("hello","world"));
		session.put("name", "test");
		session.put("byte2", new byte[]{12,23,34,51,5});
		session.put("com.jbinfo.test",  "hello");
		session.put("com.jbinfo.test2",  new Date());
		sessionService.updateSession("test10", session);
	}
	@Test
	public void testCommon() throws IOException, ClassNotFoundException{
		System.out.println(URLEncoder.encode("com.jbinfo.test","utf-8"));
		LoginContext loginContext = new LoginContext("hello","world");
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
		ObjectOutputStream out = new ObjectOutputStream(outputStream);
	    out.writeObject(loginContext);  
	    byte[] attribute = outputStream.toByteArray();
//	    attribute = new byte[]{1,34,23,53,24,25,24};
		InputStream inputStream = new ByteArrayInputStream((byte[])attribute);  
	    ObjectInputStream in = new ObjectInputStream(inputStream);  
	    Object obj = in.readObject();
	    System.out.println(obj);
	}
	
	public static class LoginContext implements Serializable{
		/** 
		* @Fields serialVersionUID : 
		*/ 
		private static final long serialVersionUID = 2937278475097905258L;
		private String name ;
		private String value;
		
		/** 
		* <p>Description: </p> 
		* @author liuwenjie   
		* @date 2016-5-11 下午12:55:55 
		* @param name
		* @param value 
		*/ 
		public LoginContext(String name, String value) {
			super();
			this.name = name;
			this.value = value;
		}
		/**
		 * @Description: 属性 name 的get方法 
		 * @return name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @Description: 属性 name 的set方法 
		 * @param name 
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @Description: 属性 value 的get方法 
		 * @return value
		 */
		public String getValue() {
			return value;
		}
		/**
		 * @Description: 属性 value 的set方法 
		 * @param value 
		 */
		public void setValue(String value) {
			this.value = value;
		}
		
	}

}
