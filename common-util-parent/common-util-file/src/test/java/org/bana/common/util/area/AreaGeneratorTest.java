package org.bana.common.util.area;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

	
	@Test
	public void testListSteam() {
		List<String> arrayList = new ArrayList<>();
		for (int i = 0; i < 100000; i++) {
			arrayList.add(i+"");
		}
		List<String> streamList = new ArrayList<>();
		long begin = System.currentTimeMillis();
		arrayList.parallelStream().forEach(item->{
			streamList.add(item);
		});
		long end = System.currentTimeMillis();
		System.out.println(streamList.size() + "时间" + (end-begin));
		
		List<String> streamList2 = new ArrayList<>();
		List<String> returnList = Collections.synchronizedList(streamList2);
		long begin2 = System.currentTimeMillis();
		arrayList.parallelStream().forEach(item->{
			returnList.add(item);
		});
		long end2 = System.currentTimeMillis();
		System.out.println(returnList.size()+ "时间" + (end2-begin2));
		System.out.println(streamList2.size());
	}
}
