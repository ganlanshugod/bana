/**
* @Company 全域旅游
* @Title: TestAsync.java 
* @Package org.bana.common.util.async 
* @author liuwenjie   
* @date Sep 11, 2020 7:15:21 PM 
* @version V1.0   
*/
package org.bana.common.util.async;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Test;

/**
 * @ClassName: TestAsync
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liuwenjie
 */
public class TestAsync {
	
	@Test
	public void nullCast() {
		
		Date date = (Date)testResult();
		if(date instanceof Date) {
			System.out.println(123);
		}
		System.out.println((Date)null);
	}
	
	private Object testResult() {
		return null;
	}

	@Test
	public void testAsync() {
		long begin = System.currentTimeMillis();
		Async async = new Async(() -> {
			System.out.println("begin1:" + (System.currentTimeMillis() - begin));
			Thread.sleep(2000);
			System.out.println("end1:" + (System.currentTimeMillis() - begin));
			return "f";
		}).add(() -> {
			System.out.println("begin2:" + (System.currentTimeMillis() - begin));
			Thread.sleep(1000);
			if(1==1) {
				throw new RuntimeException("123");
			}
			System.out.println("end2:" + (System.currentTimeMillis() - begin));
			return "s";
		}).add(()->{
			return null;
		});
		System.out.println("main2:" + (System.currentTimeMillis() - begin));
		List<Object> await = async.await();
		System.out.println("main3:" + (System.currentTimeMillis() - begin));
		System.out.println(await);
	}
	
	@Test
	public void testListSteam() {
		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 100000 ; i++) {
			list.add(i);
		}
		long begin = System.currentTimeMillis();
		list.stream().forEach(i->{
			System.out.print(i+" ");
		});
		System.out.println();
		System.out.println("steam 执行了："+ (System.currentTimeMillis() - begin));
		begin = System.currentTimeMillis();
		List<Integer> list2 = new ArrayList<>();
		List<Integer> synchronizedList = Collections.synchronizedList(list2);
		list.parallelStream().forEach(i->{
			System.out.print(i+" ");
			synchronizedList.add(i);
		});
		System.out.println();
		System.out.println(list2.size());
		
		System.out.println("parallelStream 执行了："+ (System.currentTimeMillis() - begin));
	}
}
