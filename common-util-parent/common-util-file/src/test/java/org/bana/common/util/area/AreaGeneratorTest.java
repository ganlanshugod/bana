package org.bana.common.util.area;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class AreaGeneratorTest {
	String filePath = "area/37.json";
	String sqlFilePath = "area/37.sql";

	@Test
	public void testGeneratorSqlFile() {
		long begin = System.currentTimeMillis();
		String parentUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/37.html";
//		parentUrl ="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/65/6501.html";
		AreaGenerator.generatorJsonFile(parentUrl,filePath);
		long end = System.currentTimeMillis();
		System.out.println("执行共花费了"+(end-begin) +"ms");
	}
	
	@Test
	public void testGeneratorSql() throws IOException{
		long begin = System.currentTimeMillis();
		AreaGenerator.generatorSqlFromJsonFile("37", sqlFilePath, filePath);
		long end = System.currentTimeMillis();
		System.out.println("执行共花费了"+(end-begin) +"ms");
	}

}
