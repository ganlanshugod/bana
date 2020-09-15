/**
* @Company 全域旅游
* @Title: Async.java 
* @Package  
* @author liuwenjie   
* @date Sep 11, 2020 6:18:14 PM 
* @version V1.0   
*/
package org.bana.common.util.async;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @ClassName: Async
 * @Description: 异步处理线程的方法
 * @author liuwenjie
 */
public class Async implements Serializable {

	/**
	 * @Fields serialVersionUID :
	 */
	private static final long serialVersionUID = 4392180309790709930L;

	private List<Future<Object>> asyncFnList = new ArrayList<>();

	// 声明一个可以无限扩充的线程池，用于并发执行
	private static ExecutorService exec = Executors.newCachedThreadPool();
	
	private CompletionService<Object> completionService ;
//	private int count = 0;
	

	/**
	 * <p>
	 * Description:
	 * </p>
	 * @author liuwenjie
	 * @date Sep 11, 2020 6:38:12 PM
	 */
	public Async(AsyncFunction<Object> callable) {
		completionService = new ExecutorCompletionService<Object>(exec);
		add(callable);
	}

	public Async add(AsyncFunction<Object> callable) {
		Future<Object> submit = completionService.submit(callable);
		asyncFnList.add(submit);
//		count++;
		return this;
	}
	
	public List<Object> await() throws InterruptedException, ExecutionException {
		List<Object> result = new ArrayList<>();
		// 按照加入顺序返回执行结果
		for (Future<Object> future : asyncFnList) {
			result.add(future.get());
		}
		return result;
	}

	@FunctionalInterface
	public static interface AsyncFunction<T> extends Callable<T> {
		
	}

	public void shutDown() {
		exec.shutdown();
	}
	

}