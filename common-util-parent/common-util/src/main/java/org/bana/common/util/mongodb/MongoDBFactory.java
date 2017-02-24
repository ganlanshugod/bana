/**
 * @Company 青鸟软通   
 * @Title: MongoDBFactory.java 
 * @Package  
 * @author Liu Wenjie   
 * @date 2015-1-22 下午3:19:49 
 * @version V1.0   
 */
package org.bana.common.util.mongodb;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

/** 
 * @ClassName: MongoDBFactory 
 * @Description: MongoDB的链接工厂类
 *  
 */
public class MongoDBFactory {
	
	private static final Logger LOG = LoggerFactory.getLogger(MongoDBFactory.class);
	
	/** 
	* @Fields client : 工厂类内容使用的链接客户端，初始化后复制，一个factory实例对应一个client客户端
	*/ 
	private MongoClient client;
	
	/*=================初始化链接工厂需要的属性值===================== begin*/
	
	/** 
	* @Fields serverAdress : mongo 服务器或副本集的配置信息
	*/ 
	private List<Map<String,String>> serverAddress;
	
	/** 
	* @ClassName: ServerAddressEle 
	* @Description: serverAddress 应该包换的元素集合
	*  
	*/ 
	private enum ServerAddressEle{
		host,port;
		public static String[] stringValues(){
			List<String> valueList = new ArrayList<String>();
			for (ServerAddressEle serverAddressEle : values()) {
				valueList.add(serverAddressEle.toString());
			}
			return valueList.toArray(new String[valueList.size()]);
		}
	}
	
	/** 
	* @Fields credentialsList : mongo服务器的凭据信息
	*/ 
	private List<Map<String,String>> credentialsList;
	
	/** 
	* @ClassName: CredentialEle 
	* @Description: 凭据对应的内容元素集合
	*  
	*/ 
	private enum CredentialEle{
		userName,password,database;
		public static String[] stringValues(){
			List<String> valueList = new ArrayList<String>();
			for (CredentialEle credential : values()) {
				valueList.add(credential.toString());
			}
			return valueList.toArray(new String[valueList.size()]);
		}
	}
	
	/** 
	* @Fields options : MongoDB 的链接配置项
	*/ 
	private List<Map<String,String>> options;
	/*=================初始化链接工厂需要的属性值===================== end*/
	
	/** 
	* @Description: 初始化MongoDB链接工厂
	* @author Liu Wenjie   
	* @date 2015-1-22 下午3:31:56   
	*/ 
	public void init(){
		checkParams();
		//serveraddress 
		List<ServerAddress> seeds = new ArrayList<ServerAddress>();
		for (Map<String,String> server : serverAddress) {
			try {
				seeds.add(new ServerAddress(server.get(ServerAddressEle.host.toString()),Integer.valueOf(server.get(ServerAddressEle.port.toString()))));
			} catch (NumberFormatException e) {
				throw new BanaUtilException("端口号 " + server.get(ServerAddressEle.port) + "不是合法的字符",e);
			} catch (UnknownHostException e) {
				throw new BanaUtilException("ip地址  " + server.get(ServerAddressEle.host) + "未知的服务器地址",e);
			}
		}
		//credentialList 
		List<MongoCredential> mongoCredList = new ArrayList<MongoCredential>();
		for (Map<String,String> mongoCred  : credentialsList) {
			mongoCredList.add(MongoCredential.createMongoCRCredential(mongoCred.get(CredentialEle.userName.toString()), mongoCred.get(CredentialEle.database.toString()), mongoCred.get(CredentialEle.password.toString()).toCharArray()));
		}
		
		client = new MongoClient(seeds, mongoCredList);
	}
	
	
	/** 
	* @Description: 验证参数是否可用
	* @author Liu Wenjie   
	* @date 2015-1-22 下午4:51:21   
	*/ 
	private void checkParams() {
		// check serverAddress
		if(serverAddress == null || serverAddress.isEmpty()){
			LOG.error("serverAddress canot be null or empty!!!");
			throw new BanaUtilException("serverAddress canot be null or empty!!!");
		}else{
			String[] values = ServerAddressEle.stringValues();
			for (Map<String,String> send : serverAddress) {
				if(!send.keySet().containsAll(Arrays.asList(values))){
					LOG.error("serverAddress contains all params " + Arrays.toString(values));
					throw new BanaUtilException("serverAddress contains all params " + Arrays.toString(values));
				}
			}
		}
		
		//check credentialsList
		if(credentialsList != null || !credentialsList.isEmpty()){
			String[] values = CredentialEle.stringValues();
			for (Map<String,String> credential : credentialsList) {
				if(!credential.keySet().containsAll(Arrays.asList(values))){
					throw new BanaUtilException("credentialsList must contains all params " + Arrays.toString(values));
				}
			}
		}
	}
	
	/** 
	* @Description: 按照db名称获取DB 数据库链接
	* @author Liu Wenjie   
	* @date 2015-1-23 下午2:05:04 
	* @param dbname
	* @return  
	*/ 
	public DBCollection getMongoDBCollection(String dbname,String colName){
		if(client == null){
			throw new BanaUtilException("MongoFactory 还没有初始化或者初始化失败， 请先执行 init方法初始化");
		}
		DB db = client.getDB(dbname);
		
		DBCollection collection = db.getCollection(colName);
		return collection;
	}
	
	
	/** 
	* @Description: 获取collection代理，可以进行事务控制，但是目前没有链接池管理，可能出现内存泄漏，慎重使用
	* @author Liu Wenjie   
	* @date 2015-1-26 上午9:38:27 
	* @param dbname
	* @param colName
	* @return  
	*/ 
	public DBCollection getMongoDBCollectionProxy(String dbname,String colName){
		if(client == null){
			throw new BanaUtilException("MongoFactory 还没有初始化或者初始化失败， 请先执行 init方法初始化");
		}
		DB db = client.getDB(dbname);
		DBCollection collection = db.getCollection(colName);
		MongoDBTransactionFacadeCglib dbProxy = new MongoDBTransactionFacadeCglib();
		return dbProxy.getInstance(collection,db,colName);
	}
	
	/*================getter and setter =====================*/
	
	/**
	 * @Description: 属性 serverAdress 的get方法 
	 * @return serverAdress
	 */
	public List<Map<String, String>> getServerAddress() {
		return serverAddress;
	}



	/**
	 * @Description: 属性 serverAdress 的set方法 
	 * @param serverAdress 
	 */
	public void setServerAddress(List<Map<String, String>> serverAddress) {
		this.serverAddress = serverAddress;
	}



	/**
	 * @Description: 属性 credentialsList 的get方法 
	 * @return credentialsList
	 */
	public List<Map<String, String>> getCredentialsList() {
		return credentialsList;
	}



	/**
	 * @Description: 属性 credentialsList 的set方法 
	 * @param credentialsList 
	 */
	public void setCredentialsList(List<Map<String, String>> credentialsList) {
		this.credentialsList = credentialsList;
	}



	/**
	 * @Description: 属性 options 的get方法 
	 * @return options
	 */
	public List<Map<String, String>> getOptions() {
		return options;
	}



	/**
	 * @Description: 属性 options 的set方法 
	 * @param options 
	 */
	public void setOptions(List<Map<String, String>> options) {
		this.options = options;
	}


	/**
	 * @Description: 属性 client 的get方法 
	 * @return client
	 */
	public MongoClient getClient() {
		return client;
	}
	
}
