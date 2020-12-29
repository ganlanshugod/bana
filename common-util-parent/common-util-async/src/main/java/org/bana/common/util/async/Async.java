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

	private CompletionService<Object> completionService;
//	private int count = 0;

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author liuwenjie
	 * @date Sep 11, 2020 6:38:12 PM
	 */
	public Async(AsyncFunction<Object> callable) {
		completionService = new ExecutorCompletionService<Object>(exec);
		add(callable);
	}

	/**
	 * <p>
	 * Description: 增加无参的构造方法
	 * </p>
	 * 
	 * @author zhangzhichao
	 * @date Dec 23, 2020 4:12:03 PM
	 */
	public Async() {
		completionService = new ExecutorCompletionService<Object>(exec);
	}

	public Async add(AsyncFunction<Object> callable) {
		Future<Object> submit = completionService.submit(callable);
		asyncFnList.add(submit);
//		count++;
		return this;
	}

	public List<Object> await() {
		List<Object> result = new ArrayList<>();
		// 按照加入顺序返回执行结果
		for (Future<Object> future : asyncFnList) {
			try {
				result.add(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
//				throw new AsyncInterruptException("异步执行出现InterruptedException==", e);
				result.add(null);
			} catch (ExecutionException e) {
				e.printStackTrace();
//				if (e.getCause() instanceof RuntimeException) {
//					throw (RuntimeException) e.getCause();
//				} else {
//					throw new AsyncExcuteNotRuntimeException("异步执行出现非runtimeException", e.getCause());
//				}
				result.add(null);
			}
		}

		return result;
	}

	public List<Object> awaitWithThrowable() {
		List<Object> result = new ArrayList<>();
		// 按照加入顺序返回执行结果
		for (Future<Object> future : asyncFnList) {
			try {
				result.add(future.get());
			} catch (InterruptedException e) {
				e.printStackTrace();
				result.add(e);
//			throw new AsyncInterruptException("异步执行出现InterruptedException==", e);
			} catch (ExecutionException e) {
				e.printStackTrace();
				if (e.getCause() instanceof RuntimeException) {
					result.add(e);
//				throw (RuntimeException) e.getCause();
				} else {
					result.add(e);
					throw new AsyncExcuteNotRuntimeException("异步执行出现非runtimeException", e.getCause());
				}
			}
		}
		return result;
	}

	@FunctionalInterface
	public static interface AsyncFunction<T> extends Callable<T> {

	}

	public static void shutDown() {
		exec.shutdown();
	}

}
