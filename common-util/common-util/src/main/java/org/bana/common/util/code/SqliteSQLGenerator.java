package org.bana.common.util.code;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.bana.common.util.jdbc.DbUtil;

/**
 * Update by internetroot on 2014-09-06.
 */
public class SqliteSQLGenerator {

	private static Connection conn = null;
	private static Statement sm = null;
	private static String insert = "INSERT INTO";// 插入sql
	private static String values = "VALUES";// values关键字
	private static List<String> tableList = new ArrayList<String>();// 全局存放表名列表
	private static List<String> insertList = new ArrayList<String>();// 全局存放insertsql文件的数据

	public static String generateTableDataSQL(String sql, String[] params) {
		return null;
	}

	public static String executeSelectSQLFile(String sqlString, String[] params , String destFile) throws Exception {
		List<String> listSQL = new ArrayList<String>();
		connectSQL();// 连接数据库
		
		listSQL = createSQL(sqlString);// 创建查询语句
		executeSQL(conn, sm, listSQL, tableList);// 执行sql并拼装
		createFile(destFile);// 创建文件
		return null;
	}

	/**
	 * 拼装查询语句
	 * 
	 * @return 返回select集合
	 */
	private static List<String> createSQL(String sqlString) throws Exception {
		List<String> listSQL = new ArrayList<String>();
		listSQL.add(sqlString);
		
		String rec = sqlString.toLowerCase();  
        int i = rec.indexOf("from ", 1) + 5;  
        int k = rec.indexOf(" ", i);  
        if (k == -1) {  
            k = rec.length();  
        }  
        String tableName = rec.substring(i, k);  
        tableList.add(tableName);  
        //获取所有查询语句  
		return listSQL;
	}

	/**
	 * 创建insertsql.txt并导出数据
	 * @param destFile 
	 */
	private static void createFile(String destFile) {
		File file = new File(destFile);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("创建文件名失败！！");
				e.printStackTrace();
			}
		}
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(file);
			bw = new BufferedWriter(fw);
			if (insertList.size() > 0) {
				for (int i = 0; i < insertList.size(); i++) {
					bw.append(insertList.get(i));
					bw.append("\n");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 连接数据库 创建statement对象
	 * 
	 * @param driver
	 * @param url
	 * @param UserName
	 * @param Password
	 */
	public static void connectSQL() {
		try {
			conn = DbUtil.getConnection();
			sm = conn.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 执行sql并返回插入sql
	 * 
	 * @param conn
	 * @param sm
	 * @param listSQL
	 * @throws java.sql.SQLException
	 */
	public static void executeSQL(Connection conn, Statement sm, List listSQL, List listTable) throws SQLException {
		List<String> insertSQL = new ArrayList<String>();
		ResultSet rs = null;
		try {
			rs = getColumnNameAndColumeValue(sm, listSQL, listTable, rs);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			rs.close();
			sm.close();
			conn.close();
		}
	}

	/**
	 * 获取列名和列值
	 * 
	 * @param sm
	 * @param listSQL
	 * @param rs
	 * @return
	 * @throws java.sql.SQLException
	 */
	private static ResultSet getColumnNameAndColumeValue(Statement sm, List listSQL, List ListTable, ResultSet rs) throws SQLException {
		for (int j = 0; j < listSQL.size(); j++) {
			String sql = String.valueOf(listSQL.get(j));
			rs = sm.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			while (rs.next()) {
				StringBuffer ColumnName = new StringBuffer();
				StringBuffer ColumnValue = new StringBuffer();
				for (int i = 1; i <= columnCount; i++) {
					String value = rs.getString(i);
					if (i == columnCount) {
						ColumnName.append(rsmd.getColumnName(i));
						if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null");
							} else {
								ColumnValue.append("'").append(value).append("'");
							}
						} else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i)
								|| Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null");
							} else {
								ColumnValue.append(value);
							}
						} else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null");
							} else {
								ColumnValue.append("timestamp'").append(value).append("'");
							}
						} else {
							if (value == null) {
								ColumnValue.append("null");
							} else {
								ColumnValue.append(value);
							}
						}
					} else {
						ColumnName.append(rsmd.getColumnName(i) + ",");
						if (Types.CHAR == rsmd.getColumnType(i) || Types.VARCHAR == rsmd.getColumnType(i) || Types.LONGVARCHAR == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null,");
							} else {
								ColumnValue.append("'").append(value).append("',");
							}
						} else if (Types.SMALLINT == rsmd.getColumnType(i) || Types.INTEGER == rsmd.getColumnType(i) || Types.BIGINT == rsmd.getColumnType(i) || Types.FLOAT == rsmd.getColumnType(i) || Types.DOUBLE == rsmd.getColumnType(i)
								|| Types.NUMERIC == rsmd.getColumnType(i) || Types.DECIMAL == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null,");
							} else {
								ColumnValue.append(value).append(",");
							}
						} else if (Types.DATE == rsmd.getColumnType(i) || Types.TIME == rsmd.getColumnType(i) || Types.TIMESTAMP == rsmd.getColumnType(i)) {
							if (value == null) {
								ColumnValue.append("null,");
							} else {
								ColumnValue.append("timestamp'").append(value).append("',");
							}
						} else {
							if (value == null) {
								ColumnValue.append("null,");
							} else {
								ColumnValue.append(value).append(",");
							}
						}
					}
				}
				// System.out.println(ColumnName.toString());
				// System.out.println(ColumnValue.toString());
				insertSQL(ListTable.get(j).toString(), ColumnName, ColumnValue);
			}
		}
		return rs;
	}

	/**
	 * 拼装insertsql 放到全局list里面
	 * 
	 * @param ColumnName
	 * @param ColumnValue
	 */
	private static void insertSQL(String TableName, StringBuffer ColumnName, StringBuffer ColumnValue) {
		StringBuffer insertSQL = new StringBuffer();
		insertSQL.append(insert).append(" ").append(TableName).append("(").append(ColumnName.toString()).append(")").append(values).append("(").append(ColumnValue.toString()).append(");");
		insertList.add(insertSQL.toString());
		System.out.println(insertSQL.toString());
	}

}
