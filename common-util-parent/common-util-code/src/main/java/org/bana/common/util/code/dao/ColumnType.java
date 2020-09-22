package org.bana.common.util.code.dao;

import org.bana.common.util.exception.BanaUtilException;

/**
 * @ClassName: ColumnToJava
 * @Description: 列类型与Java类型的对照关系
 * 
 */
public enum ColumnType {
	BIGINT(new String[] { "Long" }, new int[] { 0 }, "BIGINT"),
	INT(new String[] { "Integer" }, new int[] { 0 }, "INTEGER"),
	TINYINT(new String[] { "Integer" }, new int[] { 0 }, "INTEGER"),
	VARCHAR(new String[] { "String" }, new int[] { 0 }, "VARCHAR"),
	CHAR(new String[] { "String" }, new int[] { 0 }, "CHAR"),
	TIMESTAMP(new String[] { "java.util.Date" }, new int[] { 0 }, "TIMESTAMP"),
	DATETIME(new String[] { "java.util.Date" }, new int[] { 0 }, ""),
	DATE(new String[] { "java.util.Date" }, new int[] { 0 }, ""), TEXT(new String[] { "String" }, new int[] { 0 }, ""),
	BIT(new String[] { "Boolean" }, new int[] { 0 }, "BIT"),
	DECIMAL(new String[] { "java.math.BigDecimal" }, new int[] { 0 }, "DECIMAL"),
	LONGTEXT(new String[] { "String" }, new int[] { 0 }, ""),
	DOUBLE(new String[] { "Double" }, new int[] { 0 }, "DOUBLE"),
	JSON(new String[] { "String" }, new int[] { 0 }, "");
	private String[] javaTypes;
	private int[] columnLengths;
	private String jdbcType;

	private ColumnType(String[] javaTypes, int[] columnLengths, String jdbcType) {
		this.javaTypes = javaTypes;
		this.columnLengths = columnLengths;
		this.jdbcType = jdbcType;
	}

	public static ColumnType getInstanceByString(String name) {
		try {
			return ColumnType.valueOf(name.toUpperCase());
		} catch (Exception e) {
			throw new BanaUtilException("no avaliable columnToJava for name " + name, e);
		}
	}

	public String getJavaType(int columnLength) {
		for (int i = 0; i < this.columnLengths.length; i++) {
			int length = this.columnLengths[i];
			if (length >= columnLength) {
				return this.javaTypes[i];
			}
		}
		return this.javaTypes[this.javaTypes.length - 1];
	}

	/**
	 * @Description: 获取jdbcType
	 * @author Liu Wenjie
	 * @date 2014-11-14 上午11:21:43
	 * @return
	 */
	public String getJdbcType() {
		return this.jdbcType;
	}

}