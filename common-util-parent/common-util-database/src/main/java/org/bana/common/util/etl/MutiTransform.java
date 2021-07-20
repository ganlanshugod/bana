/**
* @Company weipu   
* @Title: MutiTransform.java 
* @Package org.bana.common.util.etl 
* @author Liu Wenjie   
* @date 2015-8-7 下午3:54:31 
* @version V1.0   
*/ 
package org.bana.common.util.etl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.bana.common.util.basic.DateUtil;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.etl.config.ColumnMapping;
import org.bana.common.util.etl.config.ColumnMapping.ColumnType;
import org.bana.common.util.etl.config.ETLConfig;
import org.bana.common.util.etl.config.MongoGroup;
import org.bana.common.util.etl.config.MongoGroup.Condition;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

/** 
 * @ClassName: MutiTransform 
 * @Description: 多种类型的转化工具,提供nosql如mongodb类型的数据转接
 *  
 */
public class MutiTransform extends SimpleTransform{
	
	private static final Logger LOG = LoggerFactory.getLogger(MutiTransform.class);
	
	private Mongo sourceClient;
	
	/**
	* <p>Description: 转化方法</p> 
	* @author Liu Wenjie   
	* @date 2015-8-7 下午3:57:21  
	* @see org.bana.common.util.etl.SimpleTransform#execute() 
	*/ 
	public void execute() throws Exception{
		long begin = System.currentTimeMillis();
		if(sourceClient == null){
			super.execute();
			return;
		}
		//从mongoDB中获取数据，保存到目标数据库中
		this.initConfig();
		MongoGroup sourceGroup = etlConfig.getSourceGroup();
		List<DBObject> aggregateList = etlConfig.getAggregateList();
		DB db = sourceClient.getDB(etlConfig.getSourceTableSchama());
		if(sourceGroup != null){
//			BasicDBObject keyf = new BasicDBObject("$keyf",sourceGroup.getKeyf());
			BasicDBObject condition = new BasicDBObject();
			List<Condition> conditions = sourceGroup.getConditions();
			if(conditions != null){
				for (Condition conditionCond : conditions) {
					//如果没有比较类型
					if(StringUtils.isBlank(conditionCond.getType())){
						condition.put(conditionCond.getName(), conditionCond.getValue());
					}else if("$between".equals(conditionCond.getType())){//如果有比较类型则增加
						condition.put(conditionCond.getName(), 
								new BasicDBObject("$lte",ETLConfig.getRealScriptValue(conditionCond.getMax(),null))
								.append("$gte", ETLConfig.getRealScriptValue(conditionCond.getMin(),null)));
					}else{
						condition.put(conditionCond.getName(), new BasicDBObject(conditionCond.getType(),conditionCond.getValue()));
					}
				}
			}
			String reduce = sourceGroup.getReduce();
			BasicDBObject initial = new BasicDBObject();
			Map<String, Object> initialMap = sourceGroup.getInitial();
			if(initialMap != null){
				Set<Entry<String, Object>> entrySet = initialMap.entrySet();
				for (Entry<String, Object> entry : entrySet) {
					initial.put(entry.getKey(), entry.getValue());
				}
			}
			BasicDBObjectBuilder group = BasicDBObjectBuilder.start()
					.add( "ns" , etlConfig.getSourceTableName())
					.add( "$keyf" , sourceGroup.getKeyf() ) 
					.add( "cond" , condition)
					.add( "$reduce" , reduce )
					.add( "initial" , initial );
			CommandResult res = db.command( new BasicDBObject( "group" , group.get()));
			res.throwOnError();
//			BasicDBList sourceList = (BasicDBList)sourceCollection.group(keyf, condition, initial, reduce);
			BasicDBList sourceList = (BasicDBList)res.get( "retval" );
			executeInsertList(begin, sourceList);
		}else if(aggregateList != null && !aggregateList.isEmpty()){
			DBCollection collection = db.getCollection(etlConfig.getSourceTableName());
			LOG.info("开始执行聚合查询...");
			AggregationOutput aggregate = collection.aggregate(aggregateList);
			Iterator<DBObject> iterator = aggregate.results().iterator();
			LOG.info("开始执行数据转移...");
			//执行转移
			LOG.info("获取目标数据元链接...");
			Connection destConn = destDatasource.getConnection();
			//清除原表数据
			if(etlConfig.isTrancateTable()){
				LOG.info("执行清除原表链接...");
				trancateTargetTable(destConn);
			}
			LOG.info("进行数据转移中...");
			destConn.setAutoCommit(false);
			String insertSql = super.createInsertSql();
			PreparedStatement destPrest = destConn.prepareStatement(insertSql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			int count = 0;
			while (iterator.hasNext()) {
				count ++;
				BasicDBObject next = (BasicDBObject)iterator.next();
				excuteMongoInsert(destConn,next,destPrest,count);
//				LOG.info("executing ..." + count);
			}
			destPrest.executeBatch();
			destConn.commit();
			destPrest.close();
			LOG.info("transform table success..., count is " + count + ", total second is " + (System.currentTimeMillis() - begin)/1000 + "s");
		}else{
			throw new BanaUtilException("mongoDB数据库没有指定sourceGroup配置,目前不支持其他方式查询");
		}
	}

	/** 
	* @Description: 执行转移对应集合数据的方法
	* @author Liu Wenjie   
	* @date 2015-10-23 下午4:46:12 
	* @param begin
	* @param sourceList
	* @throws SQLException
	* @throws Exception  
	*/ 
	private void executeInsertList(long begin, BasicDBList sourceList) throws SQLException, Exception {
		if(sourceList != null){
			//执行转移
			Connection destConn = destDatasource.getConnection();
			//清除原表数据
			if(etlConfig.isTrancateTable()){
				trancateTargetTable(destConn);
			}
			destConn.setAutoCommit(false);
			String insertSql = super.createInsertSql();
			PreparedStatement destPrest = destConn.prepareStatement(insertSql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
			
			int count = 0;
			for (int i = 0; i < sourceList.size(); i++) {
				count++;
				BasicDBObject object = (BasicDBObject)sourceList.get(i);
				excuteMongoInsert(destConn,object,destPrest,count);
			}
			
			destPrest.executeBatch();
			destConn.commit();
			destPrest.close();
			LOG.info("transform table success..., count is " + count + ", total second is " + (System.currentTimeMillis() - begin)/1000 + "s");
		}
	}
	
	
	

	/** 
	* @Description: 使用monggoDB数据执行插入
	* @author Liu Wenjie   
	* @date 2015-8-7 下午5:29:05 
	* @param destConn
	* @param object
	* @param destPrest
	* @param i  
	*/ 
	private void excuteMongoInsert(Connection destConn, BasicDBObject rs, PreparedStatement preStat, int count)  throws Exception{
		//设置参数
		for (int i = 0; i < etlConfig.getMappingList().size(); i++) {
			ColumnMapping mapping = etlConfig.getMappingList().get(i);
			String sourceColumnName = mapping.getSourceColumnName();
			ColumnType columnType = mapping.getColumnType();
			switch (columnType) {
			case LONG:
				preStat.setLong(i+1, rs.getLong(sourceColumnName));
				break;
			case BYTE:
			case SHORT:
			case INT:
				preStat.setInt(i+1, rs.getInt(sourceColumnName));
				break;
			case DATE:
				Date clumnDate1 = rs.getDate(sourceColumnName);
				if(clumnDate1 != null){
					java.sql.Date date = new java.sql.Date(clumnDate1.getTime());
					preStat.setDate(i+1, date);
				}else{
					preStat.setDate(i+1, null);
				}
				break;
			case TIMESTAMP:
			case DATETIME:
				Date clumnDate2 = rs.getDate(sourceColumnName);
				if(clumnDate2 != null){
					java.sql.Timestamp date = new java.sql.Timestamp(clumnDate2.getTime());
					preStat.setTimestamp(i+1, date);
				}else{
					preStat.setTimestamp(i+1, null);
				}
				break;
			case FLOAT:
			case DOUBLE:
				preStat.setDouble(i+1, rs.getDouble(sourceColumnName));
				break;
			case STRING:
				String value = rs.getString(sourceColumnName);
				if(!StringUtils.isBlank(value)){
					if(!StringUtils.isBlank(etlConfig.getSourceEncoding(),etlConfig.getDestEncoding())){
						value = new String(value.getBytes(etlConfig.getSourceEncoding()),etlConfig.getDestEncoding());
					}
					if(!StringUtils.isBlank(mapping.getDateFormat())){//有时间转化方式
						try {
							Date date = rs.getDate(sourceColumnName);
							value = DateUtil.toString(date, mapping.getDateFormat());
						} catch (RuntimeException e) {
							//不是时间格式的，可以使用long值来判断是否可以转化为时间格式
							try{
								long parseLong = Long.parseLong(value);
								value = DateUtil.toString(new Date(parseLong), mapping.getDateFormat());
							}catch (RuntimeException e2) {
								//不是数字类型的,使用自动类型访问
								e2.printStackTrace();
							}
						}
					}
				}
				
				preStat.setString(i+1, value);
				break;
			}
		}
		preStat.addBatch();
		
		if(count% etlConfig.getBatchSize() == 0){
			preStat.executeBatch();
			LOG.info("executed ... " + count);
			if(etlConfig.isStageCommint()){
				destConn.commit();
			}
		}
	}

	/**
	 * @Description: 属性 sourceClient 的set方法 
	 * @param sourceClient 
	 */
	public void setSourceClient(Mongo sourceClient) {
		this.sourceClient = sourceClient;
	}
	
}
