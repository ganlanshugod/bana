/**
* @Company 青鸟软通   
* @Title: CodeUtilTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-9-24 下午5:31:45 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

/** 
 * @ClassName: CodeUtilTest 
 * @Description:  
 *  
 */
public class CodeUtilTest {

	@Test
	public void test2() {
		long begin = System.currentTimeMillis();
		String code = CodeUtil.generatorNumberCode();
		System.out.println(code);
	}
	@Test
	public void test() {
		ArrayList<String> arrayList = new ArrayList<String>();
		Set<String> hashSet = new HashSet<String>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			String code = CodeUtil.generatorNumberCode();
//			System.out.println(code);
			hashSet.add(code);
		}
		System.out.println(System.currentTimeMillis() - begin);
		System.out.println(hashSet.size());
//		System.out.println(arrayList);
	}
	
	@Test
	public void  testUUID() {
		ArrayList<String> arrayList = new ArrayList<String>();
		Set<String> hashSet = new HashSet<String>();
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			String code = UUID.randomUUID().toString();
//			System.out.println(code);
			if(hashSet.contains(code)){
				arrayList.add(code);
			}
			hashSet.add(code);
		}
		System.out.println(System.currentTimeMillis() - begin);
		System.out.println(hashSet.size());
		System.out.println(arrayList.toString());
	}

}
