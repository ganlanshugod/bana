/**
* @Company 全域旅游
* @Title: TestAsync.java 
* @Package org.bana.common.util.async 
* @author liuwenjie   
* @date Sep 11, 2020 7:15:21 PM 
* @version V1.0   
*/
package org.bana.common.util.async;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @ClassName: TestAsync
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author liuwenjie
 */
public class TestAsync {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		final long begin = System.currentTimeMillis();
		Async async = new Async(() -> {
			System.out.println("begin1:" + (System.currentTimeMillis() - begin));
			Thread.sleep(2000);
			System.out.println("end1:" + (System.currentTimeMillis() - begin));
			return "f";
		}).add(() -> {
			System.out.println("begin2:" + (System.currentTimeMillis() - begin));
			Thread.sleep(1000);
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
}
