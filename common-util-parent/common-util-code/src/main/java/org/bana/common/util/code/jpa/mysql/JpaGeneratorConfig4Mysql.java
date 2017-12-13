/**
* @Company 青鸟软通   
* @Title: JpaGeneratorConfig4Mysql.java 
* @Package org.bana.common.util.code.jpa.mysql
* @author Huang Nana 
* @date 2017-12-11 14:53:22
* @version V1.0   
*/ 
package org.bana.common.util.code.jpa.mysql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bana.common.util.code.dao.Column;
import org.bana.common.util.code.dao.Table;
import org.bana.common.util.code.dao.mybatis.MybatisGeneratorConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig;
import org.bana.common.util.code.impl.CodeTemplateConfig.GeneratorFileType;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.jdbc.DbUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/** 
 * @ClassName: JpaGeneratorConfig4Mysql 
 * @Description: 生成entity repository
 *  
 */
public class JpaGeneratorConfig4Mysql extends MybatisGeneratorConfig {
	private static final Logger LOG = LoggerFactory.getLogger(JpaGeneratorConfig4Mysql.class);
	
	/** 
	* @Fields tableName : 表名
	*/ 
	private String tableName;
	
	/** 
	* @Fields databaseName : 所属的数据库名称 
	*/ 
	private String databaseName;
	
	/** 
	* @Fields databaseUseInSql : 生成的sql语句中，是否包含database名称
	*/ 
	private boolean databaseUseInSql;
	
	/** 
	* @Fields table : table对象，解析后的对象
	*/ 
	private Table table;
	
	
	public static CodeTemplateConfig default_entity = new CodeTemplateConfig(GeneratorFileType.Entity,"","code/jpa/mysql/defaultEntity.vm");
	public static CodeTemplateConfig default_pk_entity = new CodeTemplateConfig(GeneratorFileType.PKEntity,"","code/jpa/mysql/defaultPKEntity.vm");
	
	public static CodeTemplateConfig abstract_auditing_entity = new CodeTemplateConfig(GeneratorFileType.PKEntity,"","code/jpa/mysql/abstractAuditingEntity.vm");
	public static CodeTemplateConfig default_repository = new CodeTemplateConfig(GeneratorFileType.Repository,"","code/jpa/mysql/defaultRepository.vm");
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-30 下午8:32:46  
	*/ 
	public JpaGeneratorConfig4Mysql(String tableName, String databaseName,boolean databaseUseInSql){
		this.tableName = tableName;
		this.databaseName = databaseName;
		this.databaseUseInSql = databaseUseInSql;
		try {
			initTableAttribute();
		} catch (Exception e) {
			throw new BanaUtilException("初始化表结构内容时出错",e);
		}
		initCodeTemplateConfig();
	}
	
	/** 
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Feb 26, 2016 4:51:15 PM 
	* @param tableName
	* @param databaseName 
	*/ 
	public JpaGeneratorConfig4Mysql(String tableName, String databaseName) {
		this(tableName, databaseName, true);
	}




	/** 
	* @Description: 初始化对应
	* @author Liu Wenjie   
	* @date 2014-11-2 下午11:26:33   
	*/ 
	private void initCodeTemplateConfig() {
		if(this.codeVelocities == null){
			this.codeVelocities = new ArrayList<CodeTemplateConfig>();
		}
		this.codeVelocities.add(default_entity);
		if(this.table.findPriColumnList().size() > 1){//多主键需要设置默认的主键生成方式
			this.codeVelocities.add(default_pk_entity);
		}
		if(this.resourceVelocities == null){
			this.resourceVelocities = new ArrayList<CodeTemplateConfig>();
		}
	}


	/** 
	* @Description: 实例化表结构属性
	* @author Liu Wenjie   
	* @date 2014-10-29 下午3:28:24   
	*/ 
	private void initTableAttribute() throws Exception{
		this.checkParams();
		Connection connection = DbUtil.getConnection();
		//验证表结构是否存在
		String sql = "SELECT TABLE_NAME" +
					" FROM information_schema.Tables" +
					" WHERE TABLE_SCHEMA = ?" +
					" AND table_name = ?";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, this.databaseName);
		statement.setString(2, this.tableName);
		ResultSet rs = statement.executeQuery();
		if(!rs.next()){
			throw new BanaUtilException("table " + this.tableName + " in database " + this.databaseName + " does not exits, canot generate Mybatis files....");
		}
		rs.close();
		statement.close();
		//获取表结构的具体信息
		sql = "SELECT * FROM  information_schema.COLUMNS " +
				" WHERE table_name = ?" +
				" and TABLE_SCHEMA = ?";
		statement = connection.prepareStatement(sql);
		statement.setString(1, this.tableName);
		statement.setString(2, this.databaseName);
		rs = statement.executeQuery();
		//构建表属性对象
		this.table = new Table();
		this.table.setTableName(this.tableName);
		this.table.setTableSchama(this.databaseName);
		this.table.setTableSchamaUseInSql(this.databaseUseInSql);
		List<Column> columnList = new ArrayList<Column>();
		while(rs.next()){
			Column column = initTableColumns(rs);
			columnList.add(column);
		}
		this.table.setColumnList(columnList);
		rs.close();
		statement.close();
		//获取表结构的约束类型
		sql = "SELECT * FROM  information_schema.table_constraints " +  
				" WHERE table_name = ?" +
				" and TABLE_SCHEMA = ?" +
				" AND CONSTRAINT_TYPE = 'PRIMARY KEY'";
		statement = connection.prepareStatement(sql);
		statement.setString(1, this.tableName);
		statement.setString(2, this.databaseName);
		rs = statement.executeQuery();
		this.table.setHasPrimaryKey(rs.next());
		rs.close();
		statement.close();
		DbUtil.closeConnection();
	}
	
	/** 
	* @Description: 根据rs对象初始化TableColumns
	* @author Liu Wenjie   
	* @date 2014-10-30 下午8:19:47 
	* @param rs
	* @return  
	*/ 
	private Column initTableColumns(ResultSet rs) {
		try {
			Column column = new Column();
			column.setColumnKey(rs.getString("COLUMN_KEY"));
			column.setColumnName(rs.getString("COLUMN_NAME"));
			column.setColumnType(rs.getString("COLUMN_TYPE"));
			column.setDataType(rs.getString("DATA_TYPE"));
			column.setExtra(rs.getString("EXTRA"));
			column.setNullAble("YES".equalsIgnoreCase(rs.getString("IS_NULLABLE")));
			column.setColumnComment(rs.getString("COLUMN_COMMENT"));
			column.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
			return column;
		} catch (Exception e) {
			throw new BanaUtilException("init table columns error======",e);
		}
				
		
	}

	/** 
	* @Description: 检查Mybatis生成类中的 参数配置
	* @author Liu Wenjie   
	* @date 2014-10-29 下午3:37:52   
	*/ 
	private void checkParams(){
		if(StringUtils.isBlank(this.tableName)){
			throw new BanaUtilException("table name is blank ,can not generate mybaties code");
		}
		if(StringUtils.isBlank(this.databaseName)){
			throw new BanaUtilException("database name is blank ,can not generate mybaties code");
		}
	}
	
	
	/*===================getter and setter =====================*/
	


	/**
	 * @Description: 属性 log 的get方法 
	 * @return log
	 */
	public static Logger getLog() {
		return LOG;
	}

	/**
	 * @Description: 属性 table 的get方法 
	 * @return table
	 */
	public Table getTable() {
		return table;
	}

	/**
	 * @Description: 属性 tableName 的get方法 
	 * @return tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @Description: 属性 tableName 的set方法 
	 * @param tableName 
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @Description: 属性 databaseName 的get方法 
	 * @return databaseName
	 */
	public String getDatabaseName() {
		return databaseName;
	}

	/**
	 * @Description: 属性 databaseName 的set方法 
	 * @param databaseName 
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	/**
	 * @Description: 属性 databaseUseInSql 的get方法 
	 * @return databaseUseInSql
	 */
	public boolean isDatabaseUseInSql() {
		return databaseUseInSql;
	}


	/**
	 * @Description: 属性 databaseUseInSql 的set方法 
	 * @param databaseUseInSql 
	 */
	public void setDatabaseUseInSql(boolean databaseUseInSql) {
		this.databaseUseInSql = databaseUseInSql;
	}
	
}
