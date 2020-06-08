package org.bana.common.http.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtil {
	
	private static ExecutorService POOL = Executors.newFixedThreadPool(100);
	
	public static ExecutorService getThreadPool(){
		return  POOL;
	}
	
}
