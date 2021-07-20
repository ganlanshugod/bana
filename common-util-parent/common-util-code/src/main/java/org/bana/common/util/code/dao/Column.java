/**
* @Company weipu   
* @Title: TableAttribute.java 
* @Package org.bana.common.util.code.dao 
* @author Liu Wenjie   
* @date 2014-10-30 下午5:04:04 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.code.dao.jpa.ColumnCover;

/** 
 * @ClassName: TableAttribute 
 * @Description: 保存用户指定的表结构信息的方法
 *  
 */
public class Column {
	
	/** 
	* @Fields columnName : 列名
	*/ 
	private String columnName;
	/** 
	* @Fields dataType : 数据类型，没有长度
	*/ 
	private String dataType;
	/** 
	* @Fields columnType : 列类型，包含长度
	*/ 
	private String columnType;
	/** 
	* @Fields columnKey : 列的特殊判断（主键、非空、唯一等判断）
	*/ 
	private String columnKey;
	/** 
	* @Fields extra : 列的扩展内容，比如自动增长表示等
	*/ 
	private String extra;
	/** 
	* @Fields nullAble : 是否可空
	*/ 
	private boolean nullAble;
	/** 
	* @Fields columnLength : 列在对应类型的长度
	*/ 
	private int columnLength;
	
	/** 
	* @Fields columnComment : 列的注释信息
	*/ 
	private String columnComment;
	
	public static final String COLUMN_PRI_KEY = "PRI";
	
	/**
	 * @Fields columnDefault : 默认值
	 */
	public String columnDefault;
	
	
	private ColumnHandler columnHandler;
	
	
	/** 
	* @Description: 生成hashCode时使用的类型
	* @author Liu Wenjie   
	* @date 2014-10-31 下午3:35:42 
	* @return  
	*/ 
	public String getHashCodeString(){
		String javaType = getJavaType();
		String javaName = getJavaName();
		String[] basicType = new String[]{"int","long","byte","shot"};
		if(Arrays.asList(basicType).contains(javaType)){
			return "result = prime * result + (int) ("+javaName+" ^ (" + javaName + ">>> 32));";
		}else if("double".equals(javaType)){
			return "long temp = Double.doubleToLongBits("+javaName+");" +
				   "result = prime * result + (int) (temp ^ (temp >>> 32));";
		}else if("float".equals(javaType)){
			return "result = prime * result + Float.floatToIntBits("+javaName+");";
		}else if("boolean".equals(javaType)){
			return "result = prime * result + (" + javaName + " ? 1231 : 1237)";
		}else{
			return "result = prime * result" + 
				"+ ((" + javaName + " == null) ? 0 : " + javaName + ".hashCode());";
		}
	}
	
	/** 
	* @Description: 判断是否是主键列
	* @author Liu Wenjie   
	* @date 2014-11-2 下午12:15:36 
	* @return  
	*/ 
	public boolean isPriColumn(){
		return COLUMN_PRI_KEY.equalsIgnoreCase(this.columnKey);
	}
	
	/** 
	* @Description: 获取Equals 方法的字符串
	* @author Liu Wenjie   
	* @date 2014-10-31 下午4:02:48 
	* @return  
	*/ 
	public String getEquelsString(){
		String[] basicType = new String[]{"int","long","byte","shot","float","double","boolean"};
		String javaType = getJavaType();
		String javaName = getJavaName();
		
		if(Arrays.asList(basicType).contains(javaType)){
			return "if (" + javaName + " != other." + javaName + "){return false;}";
		}else{
			return "if (" + javaName + " == null) {" +
			"if (other." + javaName + " != null){ return false;} " +
			"}else if (!" + javaName + ".equals(other." + javaName + ")){" +
			" return false;}";
		}
		
	}
	/** 
	* @Description: 获取当前列对应的where条件语句
	* @author Liu Wenjie   
	* @date 2014-11-2 下午9:07:42 
	* @return  
	*/ 
	public String getConditionString(boolean hasPkDomain,String pkColumnName){
		String javaType = this.getJavaType();
		this.getDataType();
		StringBuffer sb = new StringBuffer();
		sb.append("<if test=\"entity." + (hasPkDomain&&isPriColumn()?pkColumnName + "!=null and entity."+pkColumnName+".":"") + this.getJavaName() + "!=null\">");
		if("String".equalsIgnoreCase(javaType)){
			sb.append("and ").append(this.getColumnName()).append(" like CONCAT('%',#{entity.").append((hasPkDomain&&isPriColumn()?pkColumnName+".":"") + this.getJavaName()).append("},'%')");
		}else if("java.util.Date".equalsIgnoreCase(javaType)){
			//Date类型的暂不进行模糊查询
			return "<!-- " + javaType + " " + this.columnName + " has been ignored!!! -->";
		}else{
			sb.append("and ").append(this.getColumnName()).append("=#{entity.").append((hasPkDomain&&isPriColumn()?pkColumnName+".":"") +this.getJavaName()).append("}");
		}
		sb.append("</if>");
		return sb.toString();
	}
	
	/** 
	* @Description: 获取相等的参数判断
	* @author liuwenjie   
	* @date Feb 26, 2016 5:02:34 PM 
	* @param hasPkDomain
	* @param pkColumnName
	* @return  
	*/ 
	public String getEqualsConditionString(boolean hasPkDomain,String pkColumnName){
		String javaType = this.getJavaType();
		this.getDataType();
		StringBuffer sb = new StringBuffer();
		sb.append("<if test=\"entity." + (hasPkDomain&&isPriColumn()?pkColumnName + "!=null and entity."+pkColumnName+".":"") + this.getJavaName() + "!=null\">");
		if("String".equalsIgnoreCase(javaType)){
			sb.append("and ").append(this.getColumnName()).append("=#{entity.").append((hasPkDomain&&isPriColumn()?pkColumnName+".":"") + this.getJavaName()).append("}");
		}else if("java.util.Date".equalsIgnoreCase(javaType)){
			//Date类型的暂不进行模糊查询
			return "<!-- " + javaType + " " + this.columnName + " has been ignored!!! -->";
		}else{
			sb.append("and ").append(this.getColumnName()).append("=#{entity.").append((hasPkDomain&&isPriColumn()?pkColumnName+".":"") +this.getJavaName()).append("}");
		}
		sb.append("</if>");
		return sb.toString();
	}
	
	
	/*============业务方法============*/
	/** 
	* @Description: 获取对应的Java类型
	* @author Liu Wenjie   
	* @date 2014-10-30 下午7:17:47 
	* @return  
	*/ 
	public String getJavaType(){
		ColumnCover coverType = this.getCoverType();
		if(coverType != null) {
			return coverType.getCoverJavaClass();
		}
		return ColumnType.getInstanceByString(dataType).getJavaType(getColumnLength());
	}
	
	public ColumnCover getCoverType() {
		if(this.getColumnHandler() != null) {
			return this.getColumnHandler().handleCover(this.getDataType());
		}
		return null;
	}
	
	public boolean hasCoverType() {
		return this.getCoverType() != null;
	}
	
	
	/** 
	* @Description: 获取对应的jdbcType类型
	* @author Liu Wenjie   
	* @date 2014-11-14 上午11:23:03 
	* @return  
	*/ 
	public String getJdbcType(){
		return ColumnType.getInstanceByString(dataType).getJdbcType();
	}
	
	/** 
	* @Description: 根据表名获取对应的实体类名称
	* @author Liu Wenjie   
	* @date 2014-10-31 上午11:26:20 
	* @return  
	*/ 
	public String getJavaName(){
		Pattern p = Pattern.compile("_\\w");
		Matcher matcher = p.matcher(this.columnName.toLowerCase());
		StringBuffer sb = new StringBuffer();
		while(matcher.find()){
			String group = matcher.group();
			matcher.appendReplacement(sb, group.substring(1).toUpperCase());
		}
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	/** 
	* @Description: 获取列的类型的长度
	* @author Liu Wenjie   
	* @date 2014-10-30 下午7:41:41 
	* @param columnType
	* @return  
	*/ 
	public int getColumnLength(){
		if(this.columnLength != 0){
			return this.columnLength;
		}
		if(StringUtils.isBlank(this.columnType)){
			return 0;
		}
		Pattern pattern = Pattern.compile("\\(\\w+\\)");
		Matcher matcher = pattern.matcher(this.columnType);
		if(matcher.find()){
			String group = matcher.group();
			return Integer.valueOf(group.substring(1, group.length() - 1));
		}
		return 0;
	}
	
	
	/** 
	* @Description: 获取java的Get方法
	* @author Liu Wenjie   
	* @date 2014-10-31 下午3:11:31 
	* @return  
	*/ 
	public String findFieldGetMethod(){
		String fieldName = getJavaName();
    	return "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }
	
	/** 
	* @Description: 获取java的set方法名字
	* @author Liu Wenjie   
	* @date 2014-10-31 下午3:12:36 
	* @return  
	*/ 
	public String findFieldSetMethod(){
		String fieldName = getJavaName();
    	return "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
    }
	
	
	/*================getter and setter ====================*/
	/**
	 * @Description: 属性 columnName 的get方法 
	 * @return columnName
	 */
	public String getColumnName() {
		return columnName;
	}
	/**
	 * @Description: 属性 columnName 的set方法 
	 * @param columnName 
	 */
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	/**
	 * @Description: 属性 dataType 的get方法 
	 * @return dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @Description: 属性 dataType 的set方法 
	 * @param dataType 
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @Description: 属性 columnType 的get方法 
	 * @return columnType
	 */
	public String getColumnType() {
		return columnType;
	}
	/**
	 * @Description: 属性 columnType 的set方法 
	 * @param columnType 
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	/**
	 * @Description: 属性 columnKey 的get方法 
	 * @return columnKey
	 */
	public String getColumnKey() {
		return columnKey;
	}
	/**
	 * @Description: 属性 columnKey 的set方法 
	 * @param columnKey 
	 */
	public void setColumnKey(String columnKey) {
		this.columnKey = columnKey;
	}
	/**
	 * @Description: 属性 extra 的get方法 
	 * @return extra
	 */
	public String getExtra() {
		return extra;
	}
	/**
	 * @Description: 属性 extra 的set方法 
	 * @param extra 
	 */
	public void setExtra(String extra) {
		this.extra = extra;
	}
	/**
	 * @Description: 属性 nullAble 的get方法 
	 * @return nullAble
	 */
	public boolean isNullAble() {
		return nullAble;
	}
	/**
	 * @Description: 属性 nullAble 的set方法 
	 * @param nullAble 
	 */
	public void setNullAble(boolean nullAble) {
		this.nullAble = nullAble;
	}
	/**
	 * @Description: 属性 columnLength 的set方法 
	 * @param columnLength 
	 */
	public void setColumnLength(int columnLength) {
		this.columnLength = columnLength;
	}

	/**
	 * @Description: 属性 columnComment 的get方法 
	 * @return columnComment
	 */
	public String getColumnComment() {
		return columnComment;
	}

	/**
	 * @Description: 属性 columnComment 的set方法 
	 * @param columnComment 
	 */
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
	}

	public String getColumnDefault() {
		return columnDefault;
	}

	public void setColumnDefault(String columnDefault) {
		this.columnDefault = columnDefault;
	}

	public ColumnHandler getColumnHandler() {
		return columnHandler;
	}

	public void setColumnHandler(ColumnHandler columnHandler) {
		this.columnHandler = columnHandler;
	}
	
}
