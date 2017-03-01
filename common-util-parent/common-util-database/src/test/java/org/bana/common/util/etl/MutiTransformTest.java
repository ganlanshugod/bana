/**
* @Company 青鸟软通   
* @Title: MutiTransformTest.java 
* @Package org.bana.common.util.etl 
* @author Liu Wenjie   
* @date 2015-8-7 下午5:34:59 
* @version V1.0   
*/ 
package org.bana.common.util.etl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.bana.common.util.jdbc.DbUtil;
import org.bana.common.util.mongodb.MongoDBFactory;
import org.junit.Ignore;
import org.junit.Test;

import com.mongodb.MongoClient;

/** 
 * @ClassName: MutiTransformTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
@Ignore
public class MutiTransformTest {

	/**
	 * Test method for {@link org.bana.common.util.etl.MutiTransform#execute()}.
	 * @throws Exception 
	 */
	@Test
	public void testExecute() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/activity_user.xml");
		tf.execute();
	}
	
	private MongoDBFactory getFactory(){
		MongoDBFactory mongoFactory = new MongoDBFactory();
		Map<String,String> server = new HashMap<String,String>();
		server.put("host", "121.42.199.71");
		server.put("port", "27017");
		List<Map<String,String>> serverAddress = new ArrayList<Map<String,String>>();
		serverAddress.add(server);
		mongoFactory.setServerAddress(serverAddress);
		
		Map<String,String> mongoCred = new HashMap<String, String>();
		mongoCred.put("userName", "i3618");
		mongoCred.put("database","bana");
		mongoCred.put("password", "Jbinfo456123");
		List<Map<String,String>> mongoCredList = new ArrayList<Map<String,String>>();
		mongoCredList.add(mongoCred);
		mongoFactory.setCredentialsList(mongoCredList);
		
		mongoFactory.init();
		return mongoFactory;
	}
	
	@Test
	public void testTransUserVisit() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/activity_user.xml");
		tf.execute();
	}
	/** 
	* @Description: 图文点击量统计
	* @author Richard Core   
	* @date 2015-11-27 下午5:45:00 
	* @throws Exception  
	*/ 
	@Test
	public void testTransMsgReadData() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/testMsgReadConfig.xml");
		tf.execute();
	}
	/** 
	* @Description: 维护个人信息抽奖数据导出
	* @author Richard Core   
	* @date 2015-11-27 下午5:44:23 
	* @throws Exception  
	*/ 
	@Test
	public void testTransGamePrizeData() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/testGamePrizeReadConfig.xml");
		tf.execute();
	}
	/** 
	* @Description: 应用访问量统计-不包含用户
	* @author Richard Core   
	* @date 2015-11-27 下午5:43:58 
	* @throws Exception  
	*/ 
	@Test
	public void testTransAppVisit() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/appVisitConfig.xml");
		tf.execute();
	}
	
	/** 
	* @Description: 应用访问量统计，学校、应用统计不含用户明细
	* @author Huang nana   
	* @date 2015-12-15 上午9:47:40 
	* @throws Exception  
	*/ 
	@Test
	public void testTransAppVisitNew() throws Exception {
		DataSource dataSource = DbUtil.getDataSource();
		MongoDBFactory factory = getFactory();
		MongoClient client = factory.getClient();
		MutiTransform tf = new MutiTransform();
		tf.setSourceClient(client);
		tf.setDestDatasource(dataSource);
		tf.setConfigPath("/etl/app_visit_total.xml");
		tf.execute();
	}
}
