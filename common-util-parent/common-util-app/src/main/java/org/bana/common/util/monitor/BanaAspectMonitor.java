/**
 * @Company 青鸟软通   
 * @Title: BanaAspectMonitor.java 
 * @Package org.bana.common.util.monitor 
 * @author Liu Wenjie   
 * @date 2014-11-11 下午2:16:48 
 * @version V1.0   
 */
package org.bana.common.util.monitor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.aspectj.lang.ProceedingJoinPoint;
import org.bana.common.util.basic.MapRunable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: BanaAspectMonitor
 * @Description: 利用面向切面思想的监控器
 * 
 */
public abstract class BanaAspectMonitor {
	private static final Logger LOG = LoggerFactory
			.getLogger(BanaAspectMonitor.class);
	
	//创建一个可根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。因为是日志执行的方式，线程允许重用和排队，所以增加一个上限，防止资源消耗
	private	ExecutorService pool = Executors.newFixedThreadPool(50);

	/**
	 * @Description: around的监控参数
	 * @author Liu Wenjie
	 * @date 2014-11-11 上午11:01:31
	 * @param jp
	 * @return
	 * @throws Throwable
	 */
	public Object around(ProceedingJoinPoint jp) throws Throwable {
		LOG.info(this.getClass() + "=========AOP execute ========");
		// 获取访问参数，记录访问时间
		BanaAspectMonitorLog monitorLog = new BanaAspectMonitorLog();
		// 开始时间
		monitorLog.setBeginDate(new Date());
		// 参数
		monitorLog.setArgs(jp.getArgs());
		// 访问目标
		monitorLog.setSignature(jp.getSignature());

//		SourceLocation sourceLocation = jp.getSourceLocation();
//		System.out.println(sourceLocation);
//		StaticPart staticPart = jp.getStaticPart();
//		System.out.println(staticPart.getId() + ":" + staticPart.getKind());
//		System.out.println(jp.getTarget().getClass().toString());
//		System.out.println(jp.getThis().getClass().toString());
		LOG.info("execute class function is    " + jp.getSignature().getDeclaringTypeName() + "====" + jp.getSignature().getName());
		
		// 执行方法
		try {
			Object proceed = jp.proceed();
			// 获取返回值
			monitorLog.setResult(proceed);
			LOG.info(this.getClass() + "==============monitor excute success ===============");
			return proceed;
		} catch (Throwable e) {
			LOG.info(this.getClass() + "==============monitor find exception=================", e);
			// 获取异常信息
			monitorLog.setThrowable(e);
			throw e;
		} finally {
			//记录结束时间 
			monitorLog.setEndDate(new Date());
			LOG.info("execute time is " + (monitorLog.getEndDate().getTime() - monitorLog.getBeginDate().getTime()) + "ms");
			// 对访问日志进行记录
			saveMonitorLog(monitorLog);
		}

	}

	/**
	 * @Description: 保存监控日志信息
	 * @author Liu Wenjie
	 * @date 2014-11-11 下午2:10:43
	 * @param monitorLog
	 */
	private void saveMonitorLog(BanaAspectMonitorLog monitorLog) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("monitorLog", monitorLog);
		pool.execute(new MapRunable(map) {
			@Override
			public void run() {
				BanaAspectMonitorLog monitorLog = (BanaAspectMonitorLog) map
						.get("monitorLog");
				doSaveMonitorLog(monitorLog);
			}
		});

	}

	/** 
	* @Description: 保存日志的方法
	* @author Liu Wenjie   
	* @date 2014-11-11 下午5:41:41 
	* @param monitorLog  
	*/ 
	protected abstract void doSaveMonitorLog(BanaAspectMonitorLog monitorLog);

}
