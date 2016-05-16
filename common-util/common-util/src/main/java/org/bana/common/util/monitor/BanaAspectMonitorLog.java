/**
* @Company 青鸟软通   
* @Title: BanaMonitorLog.java 
* @Package com.haier.isales.monitor.service.impl 
* @author Liu Wenjie   
* @date 2014-11-11 下午1:45:49 
* @version V1.0   
*/ 
package org.bana.common.util.monitor;

import java.util.Date;

import org.aspectj.lang.Signature;

/** 
 * @ClassName: BanaAspectMonitorLog 
 * @Description: 监控日志需要记录的信息
 *  
 */
public class BanaAspectMonitorLog {
	/** 
	* @Fields args : 调用方法时的参数集合
	*/ 
	private Object[] args;
	
	/** 
	* @Fields signature : 目标方法的名片信息 
	*/ 
	private Signature signature;
	
	/** 
	* @Fields beginDate : 方法的开始时间
	*/ 
	private Date beginDate;
	
	/** 
	* @Fields endDate : 方法结束时间
	*/ 
	private Date endDate;
	
	/** 
	* @Fields throwable : 方法产生的异常信息
	*/ 
	private Throwable throwable;
	
	/** 
	* @Fields result : 方法执行后的返回值
	*/ 
	private Object result;
	
	
	
	/*============getter and setter =============*/

	/**
	 * @Description: 属性 args 的get方法 
	 * @return args
	 */
	public Object[] getArgs() {
		return args;
	}

	/**
	 * @Description: 属性 args 的set方法 
	 * @param args 
	 */
	public void setArgs(Object[] args) {
		this.args = args;
	}

	/**
	 * @Description: 属性 signature 的get方法 
	 * @return signature
	 */
	public Signature getSignature() {
		return signature;
	}

	/**
	 * @Description: 属性 signature 的set方法 
	 * @param signature 
	 */
	public void setSignature(Signature signature) {
		this.signature = signature;
	}

	/**
	 * @Description: 属性 beginDate 的get方法 
	 * @return beginDate
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * @Description: 属性 beginDate 的set方法 
	 * @param beginDate 
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * @Description: 属性 endDate 的get方法 
	 * @return endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @Description: 属性 endDate 的set方法 
	 * @param endDate 
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @Description: 属性 throwable 的get方法 
	 * @return throwable
	 */
	public Throwable getThrowable() {
		return throwable;
	}

	/**
	 * @Description: 属性 throwable 的set方法 
	 * @param throwable 
	 */
	public void setThrowable(Throwable throwable) {
		this.throwable = throwable;
	}

	/**
	 * @Description: 属性 result 的get方法 
	 * @return result
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * @Description: 属性 result 的set方法 
	 * @param result 
	 */
	public void setResult(Object result) {
		this.result = result;
	}
	
	
}
