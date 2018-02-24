package org.bana.common.util.area;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

public class AreaGeneratorTest {
	String filePath = "C:\\Users\\liuwenjie\\Desktop\\临时文件\\居家养老平台\\65.json";
	String sqlFilePath = "C:\\Users\\liuwenjie\\Desktop\\临时文件\\居家养老平台\\65.sql";

	@Test
	public void testGeneratorSqlFile() {
		String parentUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/65.html";
//		parentUrl ="http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2016/65/6501.html";
		
		AreaGenerator.generatorJsonFile(parentUrl,filePath);
	}
	
	@Test
	public void testGeneratorSql() throws IOException{
		AreaGenerator.generatorSqlFromJsonFile("65", sqlFilePath, filePath);
	}

}
