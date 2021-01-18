/**
* @Company 全域旅游
* @Title: AsyncTestInfo.java 
* @Package org.bana.common.util.async 
* @author liuwenjie   
* @date 2021年1月18日 上午11:36:24 
* @version V1.0   
*/ 
package org.bana.common.util.async;

import java.util.concurrent.ThreadPoolExecutor;

import org.junit.Test;

/** 
* @ClassName: AsyncTestInfo 
* @Description: 异步测试信息的方法
* @author liuwenjie   
*/
public class AsyncTestInfo {

	@Test
	public void testPoolInfo() throws InterruptedException {
		Async async = new Async();
		for (int i = 0; i < 1000; i++) {
			final int abc = i;
			async.add(()->{
				Thread.sleep(100);
				return "success";
			});
		}
		
		Async async2 = new Async();
		for (int i = 0; i < 1000; i++) {
			final int abc = i;
			async2.add(()->{
				Thread.sleep(200);
				return "success";
			});
		}
		
		int index = 0;
		while(true && index < 10) {
			index ++;
			ThreadPoolExecutor exec = (ThreadPoolExecutor)Async.getExec();
			System.out.print("ActiveCount: " + exec.getActiveCount());
			System.out.print("\tPoolSize: " + exec.getPoolSize());
			System.out.print("\tQueneSize:" + exec.getQueue().size());
			System.out.print("\tremainingCapacity" + exec.getQueue().remainingCapacity());
			System.out.print("\tLargestPoolSize: " + exec.getLargestPoolSize());
			System.out.print("\tCompletedTaskCount: " + exec.getCompletedTaskCount());
			System.out.print("\tCorePoolSize: " + exec.getCorePoolSize());
			System.out.print("\tMaximumPoolSiz: " + exec.getMaximumPoolSize());
			System.out.print("\tTaskCount: " + exec.getTaskCount());
			System.out.println();
			Thread.sleep(1000);
		}
		async.await();
		async2.await();
	}
}
