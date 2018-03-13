/**
* @Company 青鸟软通   
* @Title: ETLConfig.java 
* @Package org.bana.common.util.etl 
* @author Liu Wenjie   
* @date 2014-11-10 下午7:47:18 
* @version V1.0   
*/ 
package org.bana.common.util.etl.config;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bana.common.util.basic.DateUtil;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.basic.XmlLoader;
import org.bana.common.util.etl.config.ColumnMapping.ColumnType;
import org.bana.common.util.etl.config.MongoGroup.Condition;
import org.bana.common.util.exception.BanaUtilException;
import org.bson.types.ObjectId;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

/** 
 * @ClassName: ETLConfig 
 * @Description: ETL同步的配置文件
 *  
 */
public class ETLConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(ETLConfig.class);
	
	private String sourceTableSchama;
	private String sourceTableName;
	private String sourceEncoding;
	
	private String destTableSchama;
	private String destTableName;
	private String destEncoding;
	
	private List<ColumnMapping> mappingList;
	
	private List<WhereCondition> sourceWhere;
	private List<WhereCondition> destWhere;
	
	private MongoGroup sourceGroup;
	private List<DBObject> aggregateList; //mongoDB的聚合函数方法
	
	private List<Procedures> procedures;
	
	/** 
	* @Fields fetchSize : 刷新数量
	*/ 
	private int fetchSize = 500;
	
	/** 
	* @Fields batchSize : 分批执行的大小
	*/ 
	private int batchSize = 500;
	/** 
	* @Fields stageCommint : 阶段提交
	*/ 
	private boolean stageCommint = true;
	
	/** 
	* @Fields trancateTable : 是否裁剪表
	*/ 
	private boolean trancateTable = true;
	
	/** 
	* @Description: 获取表达式的值
	* @author Liu Wenjie   
	* @date 2015-8-11 下午2:11:14 
	* @param script
	* @return  
	*/ 
	public static Object getRealScriptValue(String script){
		return getRealScriptValue(script,null);
	}
	/** 
	* @Description: 获取表达式的值
	* @author Liu Wenjie   
	* @date 2015-8-11 下午2:11:14 
	* @param script
	* @return  
	*/ 
	public static Object getRealScriptValue(String script,String parentDataType){
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("$todayMillisecond", DateUtil.getTodayBegin().getTime());
        engine.put("$todaySecond", DateUtil.getTodayBegin().getTime()/1000);
        try {
			Object result = engine.eval(script);
			if(result instanceof Double){
				result =  Math.round((Double)result);
			}
			if("ObjectId".equals(parentDataType)){
				if(result instanceof Long){
					if(String.valueOf(result).length() == 13 ){//millisecond 
						return new ObjectId(new Date((Long)result));
					}else if(String.valueOf(result).length() == 10){
						return new ObjectId(new Date((Long)result*1000));
					}
				}
			}
			
			return result;
		} catch (ScriptException e) {
			throw new BanaUtilException("执行表达式错误",e);
		}
	}
	
	public static void main(String[] args) throws Exception {
		String script = "$yesterdayMillisecond - 1000 * 60 * 60 * 24;";
		ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        engine.put("$yesterdayMillisecond", new Date().getTime());
//        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        Object result =  engine.eval(script);
        System.out.println("abc=" + result);
        
	}
	
	/** 
	* @Description: 初始化配置信息
	* @author Liu Wenjie   
	* @date 2014-11-10 下午10:20:48 
	* @param configFilePath  
	*/ 
	public void initConfig(String configFilePath){
		Document document = XmlLoader.getDocument(configFilePath);
		Element root = document.getRootElement();
		//加载统一配置
		loadCommonConfig(root);
		//加载原表信息
		loadSourceTable(root);
		//加载目标表信息
		loadDestTable(root);
		//加在mapping信息
		loadMappings(root);
		//加载where条件 
		loadWhereCondition(root);
		//加载分组信息
		loadGroup(root);
		//加载聚合函数信息
		loadAggregate(root);
		//load 存储过程 
		loadProcedures(root);
	}
	
	/** 
	* @Description: 加载聚合配置表达式
	* @author Liu Wenjie   
	* @date 2015-10-23 下午5:56:54 
	* @param root  
	*/ 
	private void loadAggregate(Element root) {
		// TODO Auto-generated method stub
		Element aggregate = (Element)root.selectSingleNode("aggregate");
		if(aggregate == null){
			LOG.warn("当前没有配置要执行的mongo数据库的Aggregate信息......");
			return;
		}
		List<Element> commandList = (List<Element>)aggregate.elements();
		if(commandList != null){
			aggregateList = new ArrayList<DBObject>();
			for (Element element : commandList) {
				List<Element> optionsList = (List<Element>)element.elements();
				BasicDBObject command = null;
				if(optionsList != null && !optionsList.isEmpty()){
					command = new BasicDBObject(element.attributeValue("key"),getCommandDBObject(optionsList,element.attributeValue("dataType")));
				}else{
					command = new BasicDBObject(element.attributeValue("key"),getRealValue(element.attributeValue("value"),element.attributeValue("dataType")));
				}
				aggregateList.add(command);
			}
		}
	}

	/** 
	* @Description: 根据选项获取对应的配置集合
	* @author Liu Wenjie   
	* @date 2015-10-23 下午7:59:05 
	* @param optionsList
	* @return  
	*/ 
	private Object getCommandDBObject(List<Element> optionsList,String parentDataType) {
		BasicDBObject options = new BasicDBObject();
		if(optionsList != null){
			for (Element element : optionsList) {
				List<Element> subList = (List<Element>)element.elements();
				if(subList != null && !subList.isEmpty()){
					if("list".equals(element.attributeValue("dataType"))){
						options.append(element.attributeValue("key"),getCommandDBObjectList(subList));
					}else{
						options.append(element.attributeValue("key"),getCommandDBObject(subList,element.attributeValue("dataType")));
					}
				}else{
					options.append(element.attributeValue("key"),getRealValue(element.attributeValue("value"),element.attributeValue("dataType"),parentDataType));
				}
			}
		}
		return options;
	}

	/** 
	* @Description: TODO (这里用一句话描述这个类的作用)
	* @author Liu Wenjie   
	* @date 2015-10-23 下午8:50:31 
	* @param subList
	* @return  
	*/ 
	private Object getCommandDBObjectList(List<Element> optionsList) {
		BasicDBList dbList = new BasicDBList();
		if(optionsList != null){
			for (Element element : optionsList) {
				List<Element> subList = (List<Element>)element.elements();
				if(subList != null && !subList.isEmpty()){
					if("list".equals(element.attributeValue("dataType"))){
						dbList.add(new BasicDBObject(element.attributeValue("key"),getCommandDBObjectList(subList)));
					}else{
						dbList.add(new BasicDBObject(element.attributeValue("key"),getCommandDBObject(subList,element.attributeValue("dataType"))));
					}
				}else{
					dbList.add(getRealValue(element.attributeValue("value"),element.attributeValue("dataType")));
				}
			}
		}
		return dbList;
	}
	
	/** 
	* @Description: 获取元素数据
	* @author liuwenjie   
	* @date 2016-5-24 上午10:15:51 
	* @param attributeValue
	* @param attributeValue2
	* @param parentDataType
	* @return  
	*/ 
	private Object getRealValue(String value, String dateType) {
		return getRealValue(value, dateType,null);
	}

	/** 
	* @Description: 
	* @author Liu Wenjie   
	* @date 2015-10-23 下午8:15:51 
	* @param attributeValue
	* @param attributeValue2
	* @return  
	*/ 
	private Object getRealValue(String value, String dataType,String parentDataType) {
		//add 2016-05-24 add parentDataType,if is ObjectId
		if(StringUtils.isBlank(dataType)){
			return value;
		}else if("boolean".equalsIgnoreCase(dataType)){
			return Boolean.valueOf(value);
		}else if("Date".equalsIgnoreCase(dataType)){
			return DateUtil.formateToDate(value);
		}else if("int".equalsIgnoreCase(dataType)){
			return Integer.parseInt(value);
		}else if("long".equalsIgnoreCase(dataType)){
			return Long.parseLong(value);
		}else if("todayMillisecondscript".equalsIgnoreCase(dataType)){
			return getRealScriptValue(value,parentDataType);
		}else if("todaysecondscript".equalsIgnoreCase(dataType)){
			return getRealScriptValue(value,parentDataType);
		}else{
			return value;
		}
	}

	/** 
	* @Description: 加载分组信息
	* @author Liu Wenjie   
	* @date 2015-8-7 下午4:23:09 
	* @param root  
	*/ 
	private void loadGroup(Element root) {
		//加载mongoDB的分组信息
		Element sourceGroupRoot = (Element)root.selectSingleNode("sourceGroup");
		if(sourceGroupRoot == null){
			LOG.warn("当前没有配置要执行的mongo数据库的分组信息......");
			return;
		}
		sourceGroup = new MongoGroup();
		//key值
		Element keyf = (Element)sourceGroupRoot.selectSingleNode("keyf");
		if(keyf != null){
			sourceGroup.setKeyf(keyf.getText());
		}
		//conditions
		@SuppressWarnings("unchecked")
		List<Element> conditions = (List<Element>)sourceGroupRoot.selectNodes("conditions/condition");
		if(conditions != null){
			List<Condition> condList = new ArrayList<MongoGroup.Condition>();
			for (Element element : conditions) {
				Condition cond = new Condition();
				cond.setName(element.attributeValue("name"));
				cond.setValue(element.attributeValue("value"));
				cond.setType(element.attributeValue("type"));
				cond.setMin(element.attributeValue("min"));
				cond.setMax(element.attributeValue("max"));
				cond.setDataType(element.attributeValue("dataType"));
				condList.add(cond);
			}
			sourceGroup.setConditions(condList);
		}
		//reduce
		Element reduce = (Element)sourceGroupRoot.selectSingleNode("reduce");
		if(reduce != null){
			sourceGroup.setReduce(reduce.getText());
		}
		
		//initial
		@SuppressWarnings("unchecked")
		List<Element> initials = (List<Element>)sourceGroupRoot.selectNodes("initial/property");
		if(initials != null){
			Map<String,Object> initialsMap = new HashMap<String, Object>();
			for (Element element : initials) {
				initialsMap.put(element.attributeValue("name"), element.attributeValue("value"));
			}
			sourceGroup.setInitial(initialsMap);
		}
	}


	/** 
	* @Description: 加载存储过程的配置
	* @author Liu Wenjie   
	* @date 2014-11-28 下午8:11:28 
	* @param root  
	*/ 
	private void loadProcedures(Element root) {
		@SuppressWarnings("unchecked")
		List<Element> selectNodes = root.selectNodes("procedures/procedure");
		if(selectNodes == null || selectNodes.isEmpty()){
			LOG.warn("当前没有配置要执行的存储过程......");
			return;
		}
		procedures = new ArrayList<Procedures>();
		for (Element element : selectNodes) {
			Procedures procedure = new Procedures();
			
			Attribute name = element.attribute("name");
			if(name != null && StringUtils.isNotBlank(name.getValue())){
				procedure.setName(name.getValue());
			}else{
				throw new BanaUtilException("procedure must to config attribute name");
			}
			@SuppressWarnings("unchecked")
			List<Element> params = element.selectNodes("param");
			if(params != null){
				List<String> paramsList = new ArrayList<String>();
				for (Element param : params) {
					Attribute attribute = param.attribute("value");
					paramsList.add(attribute.getValue());
				}
				procedure.setParams(paramsList);
			}
			procedures.add(procedure);
		}
	}

	/** 
	* @Description: 加载系统的where条件
	* @author Liu Wenjie   
	* @date 2014-11-24 上午11:54:01 
	* @param root  
	*/ 
	private void loadWhereCondition(Element root) {
		@SuppressWarnings("unchecked")
		List<Element> selectNodes = root.selectNodes("where/source");
		if(selectNodes == null || selectNodes.isEmpty()){
			LOG.warn("no config for source where ......");
		}else{
			sourceWhere = new ArrayList<WhereCondition>();
			for (Element element : selectNodes) {
				WhereCondition where = new WhereCondition();
				where.setWhereValue(element.getText());
				sourceWhere.add(where);
			}
		}
	}

	/** 
	* @Description: 加载统一配置
	* @author Liu Wenjie   
	* @date 2014-11-10 下午10:19:46 
	* @param root  
	*/ 
	private void loadCommonConfig(Element root) {
		Attribute fetchSize = root.attribute("fetchSize");
		if(fetchSize != null){
			this.fetchSize = Integer.valueOf(fetchSize.getValue());
		}
		
		Attribute batchSize = root.attribute("batchSize");
		if(batchSize != null){
			this.batchSize = Integer.valueOf(batchSize.getValue());
		}
		
		Attribute trancateTable = root.attribute("trancateTable");
		if(trancateTable != null){
			this.trancateTable = Boolean.valueOf(trancateTable.getValue());
		}
		
		Attribute stageCommint = root.attribute("stageCommint");
		if(stageCommint != null){
			this.stageCommint = Boolean.valueOf(stageCommint.getValue());
		}
	}

	/** 
	* @Description: 加载列的mapping信息
	* @author Liu Wenjie   
	* @date 2014-11-10 下午8:39:56 
	* @param root  
	*/ 
	private void loadMappings(Element root){
		@SuppressWarnings("unchecked")
		List<Element> selectNodes = root.selectNodes("mappings/mapping");
		if(selectNodes == null || selectNodes.isEmpty()){
			throw new BanaUtilException("can not find mapping nodes.......");
		}
		mappingList = new ArrayList<ColumnMapping>();
		for (Element element : selectNodes) {
			ColumnMapping mapping = new ColumnMapping();
			
			Attribute source = element.attribute("source");
			if(source != null){
				mapping.setSourceColumnName(source.getValue());
			}
			
			Attribute dest = element.attribute("dest");
			if(dest != null){
				mapping.setDestColumnName(dest.getValue());
			}
			
			
			Attribute type = element.attribute("type");
			if(type != null){
				mapping.setColumnType(ColumnType.getInstance(type.getValue()));
			}
			
			Attribute defaultValue = element.attribute("default");
			if(defaultValue != null){
				mapping.setDefaultValue(defaultValue.getValue());
			}
			
			mapping.setDateFormat(element.attributeValue("dateFormat"));
			mappingList.add(mapping);
		}
	}
	
	/** 
	* @Description: 加载源表信息
	* @author Liu Wenjie   
	* @date 2014-11-10 下午8:30:42 
	* @param root  
	*/ 
	private void loadSourceTable(Element root){
		String schemaPath ="sourceTable/property[@name='schama']";
		this.sourceTableSchama = root.selectSingleNode(schemaPath).getText();
		String tablePath = "sourceTable/property[@name='name']";
		this.sourceTableName = root.selectSingleNode(tablePath).getText();
		String encodingPath = "sourceTable/property[@name='encoding']";
		this.sourceEncoding = root.selectSingleNode(encodingPath).getText();
	}
	
	/** 
	* @Description: 加载目标表信息
	* @author Liu Wenjie   
	* @date 2014-11-10 下午8:38:15 
	* @param root  
	*/ 
	private void loadDestTable(Element root){
		String schemaPath ="destTable/property[@name='schama']";
		this.destTableSchama = root.selectSingleNode(schemaPath).getText();
		String tablePath = "destTable/property[@name='name']";
		this.destTableName = root.selectSingleNode(tablePath).getText();
		String encodingPath = "destTable/property[@name='encoding']";
		this.destEncoding = root.selectSingleNode(encodingPath).getText();
	}
	
	
	/*===========getter and setter ============*/
	/**
	 * @Description: 属性 sourceTableName 的get方法 
	 * @return sourceTableName
	 */
	public String getSourceTableName() {
		return sourceTableName;
	}
	/**
	 * @Description: 属性 sourceTableName 的set方法 
	 * @param sourceTableName 
	 */
	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}
	/**
	 * @Description: 属性 sourceEncoding 的get方法 
	 * @return sourceEncoding
	 */
	public String getSourceEncoding() {
		return sourceEncoding;
	}
	/**
	 * @Description: 属性 sourceEncoding 的set方法 
	 * @param sourceEncoding 
	 */
	public void setSourceEncoding(String sourceEncoding) {
		this.sourceEncoding = sourceEncoding;
	}
	/**
	 * @Description: 属性 destEncoding 的get方法 
	 * @return destEncoding
	 */
	public String getDestEncoding() {
		return destEncoding;
	}
	/**
	 * @Description: 属性 destEncoding 的set方法 
	 * @param destEncoding 
	 */
	public void setDestEncoding(String destEncoding) {
		this.destEncoding = destEncoding;
	}
	/**
	 * @Description: 属性 mappingList 的get方法 
	 * @return mappingList
	 */
	public List<ColumnMapping> getMappingList() {
		return mappingList;
	}
	/**
	 * @Description: 属性 mappingList 的set方法 
	 * @param mappingList 
	 */
	public void setMappingList(List<ColumnMapping> mappingList) {
		this.mappingList = mappingList;
	}


	/**
	 * @Description: 属性 sourceTableSchama 的get方法 
	 * @return sourceTableSchama
	 */
	public String getSourceTableSchama() {
		return sourceTableSchama;
	}


	/**
	 * @Description: 属性 sourceTableSchama 的set方法 
	 * @param sourceTableSchama 
	 */
	public void setSourceTableSchama(String sourceTableSchama) {
		this.sourceTableSchama = sourceTableSchama;
	}


	/**
	 * @Description: 属性 destTableSchama 的get方法 
	 * @return destTableSchama
	 */
	public String getDestTableSchama() {
		return destTableSchama;
	}


	/**
	 * @Description: 属性 destTableSchama 的set方法 
	 * @param destTableSchama 
	 */
	public void setDestTableSchama(String destTableSchama) {
		this.destTableSchama = destTableSchama;
	}


	/**
	 * @Description: 属性 destTableName 的get方法 
	 * @return destTableName
	 */
	public String getDestTableName() {
		return destTableName;
	}


	/**
	 * @Description: 属性 destTableName 的set方法 
	 * @param destTableName 
	 */
	public void setDestTableName(String destTableName) {
		this.destTableName = destTableName;
	}
	

	/**
	 * @Description: 属性 fetchSize 的get方法 
	 * @return fetchSize
	 */
	public int getFetchSize() {
		return fetchSize;
	}

	/**
	 * @Description: 属性 fetchSize 的set方法 
	 * @param fetchSize 
	 */
	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	/**
	 * @Description: 属性 stageCommint 的get方法 
	 * @return stageCommint
	 */
	public boolean isStageCommint() {
		return stageCommint;
	}

	/**
	 * @Description: 属性 stageCommint 的set方法 
	 * @param stageCommint 
	 */
	public void setStageCommint(boolean stageCommint) {
		this.stageCommint = stageCommint;
	}


	/**
	 * @Description: 属性 trancateTable 的get方法 
	 * @return trancateTable
	 */
	public boolean isTrancateTable() {
		return trancateTable;
	}

	/**
	 * @Description: 属性 trancateTable 的set方法 
	 * @param trancateTable 
	 */
	public void setTrancateTable(boolean trancateTable) {
		this.trancateTable = trancateTable;
	}

	
	/**
	 * @Description: 属性 batchSize 的get方法 
	 * @return batchSize
	 */
	public int getBatchSize() {
		return batchSize;
	}

	/**
	 * @Description: 属性 batchSize 的set方法 
	 * @param batchSize 
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}
	
	

	/**
	 * @Description: 属性 sourceWhere 的get方法 
	 * @return sourceWhere
	 */
	public List<WhereCondition> getSourceWhere() {
		return sourceWhere;
	}

	/**
	 * @Description: 属性 sourceWhere 的set方法 
	 * @param sourceWhere 
	 */
	public void setSourceWhere(List<WhereCondition> sourceWhere) {
		this.sourceWhere = sourceWhere;
	}

	/**
	 * @Description: 属性 destWhere 的get方法 
	 * @return destWhere
	 */
	public List<WhereCondition> getDestWhere() {
		return destWhere;
	}

	/**
	 * @Description: 属性 destWhere 的set方法 
	 * @param destWhere 
	 */
	public void setDestWhere(List<WhereCondition> destWhere) {
		this.destWhere = destWhere;
	}
	

	/**
	 * @Description: 属性 procedures 的get方法 
	 * @return procedures
	 */
	public List<Procedures> getProcedures() {
		return procedures;
	}

	/**
	 * @Description: 属性 procedures 的set方法 
	 * @param procedures 
	 */
	public void setProcedures(List<Procedures> procedures) {
		this.procedures = procedures;
	}
	

	/**
	 * @Description: 属性 sourceGroup 的get方法 
	 * @return sourceGroup
	 */
	public MongoGroup getSourceGroup() {
		return sourceGroup;
	}


	/**
	 * @Description: 属性 sourceGroup 的set方法 
	 * @param sourceGroup 
	 */
	public void setSourceGroup(MongoGroup sourceGroup) {
		this.sourceGroup = sourceGroup;
	}

	/**
	 * @Description: 属性 aggregateList 的get方法 
	 * @return aggregateList
	 */
	public List<DBObject> getAggregateList() {
		return aggregateList;
	}

	/**
	 * @Description: 属性 aggregateList 的set方法 
	 * @param aggregateList 
	 */
	public void setAggregateList(List<DBObject> aggregateList) {
		this.aggregateList = aggregateList;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-11-28 下午8:48:04 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "ETLConfig [sourceTableSchama=" + sourceTableSchama
				+ ", sourceTableName=" + sourceTableName + ", sourceEncoding="
				+ sourceEncoding + ", destTableSchama=" + destTableSchama
				+ ", destTableName=" + destTableName + ", destEncoding="
				+ destEncoding + ", mappingList=" + mappingList
				+ ", sourceWhere=" + sourceWhere + ", destWhere=" + destWhere
				+ ", procedures=" + procedures + ", fetchSize=" + fetchSize
				+ ", batchSize=" + batchSize + ", stageCommint=" + stageCommint
				+ ", trancateTable=" + trancateTable + "]";
	}


}
