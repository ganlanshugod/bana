/**
* @Company 艾美伴行   
* @Title: DbUtilTest.java 
* @Package org.bana.common.util.jdbc 
* @author liuwenjie   
* @date 2016-12-14 下午8:37:27 
* @version V1.0   
*/ 
package org.bana.common.util.jdbc;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.Test;

/** 
 * @ClassName: DbUtilTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class DbUtilTest {

	@Test
	public void test() throws SQLException {
		Connection connection = DbUtil.getConnection();
		connection.getAutoCommit();
		DbUtil.closeConnection();
	}

}
