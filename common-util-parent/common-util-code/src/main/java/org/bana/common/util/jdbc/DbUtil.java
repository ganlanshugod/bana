package org.bana.common.util.jdbc;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSourceFactory;

public class DbUtil {
	private static DataSource dataSource;
	
	private static ThreadLocal<Connection> connLocal =	new ThreadLocal<Connection>();
	
	static {
		Properties props = new Properties();
		try {
			props.load(DbUtil.class.getClassLoader().getResourceAsStream("jdbc/dbcp.properties"));
			dataSource = BasicDataSourceFactory.createDataSource(props);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException{
		Connection conn = connLocal.get();
		if(conn == null){
			conn = dataSource.getConnection();
			connLocal.set(conn);
		}
		return conn;
	}
	
	public static void closeConnection() throws SQLException{
		Connection conn = connLocal.get();
		connLocal.set(null);
		if(conn != null){
			conn.close();
		}
	}

	/**
	 * @Description: 属性 dataSource 的get方法 
	 * @return dataSource
	 */
	public static DataSource getDataSource() {
		return dataSource;
	}
	
}
