/**
* @Company weipu   
* @Title: SimpleTransform.java 
* @Package org.bana.common.util.etl 
* @author Liu Wenjie   
* @date 2014-11-10 下午7:18:53 
* @version V1.0   
*/ 
package org.bana.common.util.etl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.etl.config.ColumnMapping;
import org.bana.common.util.etl.config.ColumnMapping.ColumnType;
import org.bana.common.util.etl.config.ETLConfig;
import org.bana.common.util.etl.config.Procedures;
import org.bana.common.util.etl.config.WhereCondition;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: SimpleTransform 
 * @Description: 实现简单的表输入和表述出,存取方式都是全量
 *  
 */
public class SimpleTransform {
	private static final Logger LOG = LoggerFactory.getLogger(SimpleTransform.class);
	/** 
	* @Fields sourceDatasource : 原数据源
	*/ 
	private DataSource sourceDatasource;
	
	/** 
	* @Fields destDatasource : 目标数据源
	*/ 
	protected DataSource destDatasource;
	
	
	/** 
	* @Fields configPath : 转换配置路径
	*/ 
	private String configPath;
	
	/** 
	* @Fields etlConfig : 
	*/ 
	protected ETLConfig etlConfig;
	
	
	/** 
	* @Description: 初始化配置项
	* @author Liu Wenjie   
	* @date 2015-8-7 下午3:53:20   
	*/ 
	protected void initConfig(){
		if(StringUtils.isBlank(configPath)){
			throw new BanaUtilException("config path is blank ...");
		}
		etlConfig = new ETLConfig();
		etlConfig.initConfig(configPath);
	}
	
	/** 
	* @Description: 定时执行数据抽取和同步的方法，表输入方法
	* @author Liu Wenjie   
	 * @throws Exception 
	* @date 2014-11-10 下午5:04:05   
	*/ 
	public void execute() throws Exception{
		long begin = System.currentTimeMillis();
		//初始化配置文件
		initConfig();
		//构造原数据库
		Connection sourceConn = sourceDatasource.getConnection();
		String selectSql = createSelectSql();
		PreparedStatement sourceStat = sourceConn.prepareStatement(selectSql);
		sourceStat.setFetchSize(etlConfig.getFetchSize());
		
		//目标数据库的插入语句
		Connection destConn = destDatasource.getConnection();
		//清除原表数据
		if(etlConfig.isTrancateTable()){
			trancateTargetTable(destConn);
		}
		destConn.setAutoCommit(false);
		String insertSql = createInsertSql();
		
		PreparedStatement destPrest = destConn.prepareStatement(insertSql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
		
		//执行转移
		ResultSet rs = sourceStat.executeQuery();
		
		int count = 0;
		while(rs.next()){
			count ++;
			excuteInsert(destConn,rs,destPrest,count);
		}
		
		destPrest.executeBatch();
		destConn.commit();
		rs.close();
		sourceStat.close();
		destPrest.close();
		
		LOG.info("transform table success..., count is " + count + ", total second is " + (System.currentTimeMillis() - begin)/1000 + "s");
		
		//执行存储过程 
		List<Procedures> procedures = etlConfig.getProcedures();
		if(procedures != null && !procedures.isEmpty()){
			for (Procedures proc : procedures) {
				String name = proc.getName();
				if(StringUtils.isNotBlank(name)){
					begin = System.currentTimeMillis();
					StringBuffer procSql = new StringBuffer("{ call ");
					procSql.append(name).append("(");
					List<String> params = proc.getParams();
					if(params != null && !params.isEmpty()){
						for ( int i = 0 ; i < params.size(); i++) {
							String param = params.get(i);
							procSql.append("'").append(param).append("'");
							if(i != params.size() -1 ){
								procSql.append(",");
							}
						}
					}
					procSql.append(") }");
					LOG.info("exectue procedure... " + procSql.toString());
					CallableStatement procStat = destConn.prepareCall(procSql.toString()); 
					procStat.execute();
					destConn.commit();
					LOG.info("exectue procedure success ... total second is " + (System.currentTimeMillis() - begin)/1000 + "s");
				}
			}
		}
		
		sourceConn.close();
		destConn.close();
		//TODO 增加日志功能
	}

	/** 
	* @Description: 清除表中的数据
	* @author Liu Wenjie   
	* @date 2015-8-7 下午4:34:02 
	* @param destConn
	* @throws SQLException  
	*/ 
	protected void trancateTargetTable(Connection destConn) throws SQLException {
		Statement statement = destConn.createStatement();
		StringBuffer truncateSql = new StringBuffer("TRUNCATE TABLE ");
		if(!StringUtils.isBlank(etlConfig.getDestTableSchama())){
			truncateSql.append(etlConfig.getDestTableSchama()).append(".");
		}
		truncateSql.append(etlConfig.getDestTableName());
		statement.execute(truncateSql.toString());
		statement.close();
	}
	
	/** 
	* @Description: 创建插入语句
	* @author Liu Wenjie   
	* @date 2014-11-10 下午9:50:25 
	* @return  
	*/ 
	protected String createInsertSql() {
		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO ");
		String schama = etlConfig.getDestTableSchama();
		if(!StringUtils.isBlank(schama)){
			sb.append(schama).append(".");
		}
		sb.append(etlConfig.getDestTableName()).append(" (");
		StringBuffer valueStr = new StringBuffer();
		for (int i = 0; i < etlConfig.getMappingList().size(); i++) {
			ColumnMapping mapping = etlConfig.getMappingList().get(i);
			String destColumnName = mapping.getDestColumnName();
			sb.append(destColumnName);
			valueStr.append("?");
			if(i != etlConfig.getMappingList().size()-1 ){
				sb.append(",");
				valueStr.append(",");
			}
		}
		sb.append(") values (").append(valueStr).append(")");
		return sb.toString();
	}

	/** 
	* @Description: 创建查询sql文件
	* @author Liu Wenjie   
	* @date 2014-11-10 下午9:27:18 
	* @return  
	*/ 
	private String createSelectSql(){
		StringBuffer sb = new StringBuffer();
		//查询字段
		sb.append("SELECT ");
		for (int i = 0; i < etlConfig.getMappingList().size(); i++) {
			ColumnMapping mapping = etlConfig.getMappingList().get(i);
			String sourceColumnName = mapping.getSourceColumnName();
			sb.append(sourceColumnName);
			if(i != etlConfig.getMappingList().size()-1 ){
				sb.append(",");
			}
		}
		sb.append(" FROM ");
		String schama = etlConfig.getSourceTableSchama();
		if(!StringUtils.isBlank(schama)){
			sb.append(schama).append(".");
		}
		sb.append(etlConfig.getSourceTableName());
		
		//where条件
		List<WhereCondition> sourceWhere = etlConfig.getSourceWhere();
		if(sourceWhere != null && !sourceWhere.isEmpty()){
			sb.append(" where ");
			for (WhereCondition whereCondition : sourceWhere) {
				sb.append(whereCondition.getWhereValue()).append(" ");
			}
		}
		
		return sb.toString();
	}
	
	/** 
	* @Description: 执行表述出方法
	* @author Liu Wenjie   
	 * @param destConn 
	* @date 2014-11-10 下午5:16:37 
	* @param rs  
	 * @throws Exception 
	*/ 
	private void excuteInsert(Connection destConn, ResultSet rs, PreparedStatement preStat, int count) throws Exception{
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
				preStat.setByte(i+1, rs.getByte(sourceColumnName));
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
			case DOUBLE:
				preStat.setDouble(i+1, rs.getDouble(sourceColumnName));
				break;
			case FLOAT:
				preStat.setFloat(i+1, rs.getFloat(sourceColumnName));
				break;
			case INT:
				preStat.setInt(i+1, rs.getInt(sourceColumnName));
				break;
			case SHORT:
				preStat.setShort(i+1, rs.getShort(sourceColumnName));
				break;
			case STRING:
				String value = rs.getString(sourceColumnName);
				if(!StringUtils.isBlank(value)){
					if(!StringUtils.isBlank(etlConfig.getSourceEncoding(),etlConfig.getDestEncoding())){
						value = new String(value.getBytes(etlConfig.getSourceEncoding()),etlConfig.getDestEncoding());
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
	 * @Description: 属性 sourceDatasource 的set方法 
	 * @param sourceDatasource 
	 */
	public void setSourceDatasource(DataSource sourceDatasource) {
		this.sourceDatasource = sourceDatasource;
	}

	/**
	 * @Description: 属性 destDatasource 的set方法 
	 * @param destDatasource 
	 */
	public void setDestDatasource(DataSource destDatasource) {
		this.destDatasource = destDatasource;
	}

	/**
	 * @Description: 属性 configPath 的get方法 
	 * @return configPath
	 */
	public String getConfigPath() {
		return configPath;
	}

	/**
	 * @Description: 属性 configPath 的set方法 
	 * @param configPath 
	 */
	public void setConfigPath(String configPath) {
		this.configPath = configPath;
	}

}
