/**
* @Company 青鸟软通   
* @Title: Snippet.java 
* @Package org.bana.wechat.qy.common.log 
* @author Liu Wenjie   
* @date 2015-5-27 下午4:22:27 
* @version V1.0   
*/ 
package org.bana.common.http.log;

import java.io.Serializable;

/** 
* @ClassName: Snippet 
* @Description: 日志记录对应的domain对象
*  
*/ 
public class HttpLogDomain implements Serializable{
	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 8913553794367177420L;
	
	private String url;
	/** 
	* @Fields paramData : 调用的参数转为json
	*/
	private String paramData;
	/** 
	* @Fields startTime : 开始时间
	*/
	private java.util.Date startTime;
	/** 
	* @Fields endTime : 结束时间
	*/
	private java.util.Date endTime;
	/** 
	* @Fields startMillisecond : 开始时间毫秒数
	*/
	private Long startMillisecond;
	/** 
	* @Fields endMillisecond : 结束时间毫秒数
	*/
	private Long endMillisecond;
	/** 
	* @Fields duration : 时长
	*/
	private Long duration;
	/** 
	* @Fields statusCode : http返回值编码
	*/
	private String statusCode;
	/** 
	* @Fields httpMessage : 异常信息
	*/
	private String httpMessage;
	/** 
	* @Fields wechatResult : 客户端信息
	*/
	private String result;
	/** 
	* @Fields executeMethod : 访问类型,post还是get方法等
	*/
	private String executeMethod;
	
	/** 
	* @Fields exceptionClass : 
	*/ 
	private String exceptionClass;
	
	/** 
	* @Fields exceptionMessage :
	*/ 
	private String exceptionMessage;
	
	/** 
	* @Fields paramExtend : 参数扩展信息 
	*/ 
	private String paramExtend;
	/** 
	* @Fields resultExtend : 返回结果扩展信息 
	*/ 
	private String resultExtend;
	
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	/**
	 * @Description: 属性 paramData 的get方法 
	 * @return paramData
	 */
	public String getParamData() {
		return paramData;
	}
	/**
	 * @Description: 属性 paramData 的set方法 
	 * @param paramData 
	 */
	public void setParamData(String paramData) {
		this.paramData = paramData;
	}
	/**
	 * @Description: 属性 startTime 的get方法 
	 * @return startTime
	 */
	public java.util.Date getStartTime() {
		return startTime;
	}
	/**
	 * @Description: 属性 startTime 的set方法 
	 * @param startTime 
	 */
	public void setStartTime(java.util.Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * @Description: 属性 endTime 的get方法 
	 * @return endTime
	 */
	public java.util.Date getEndTime() {
		return endTime;
	}
	/**
	 * @Description: 属性 endTime 的set方法 
	 * @param endTime 
	 */
	public void setEndTime(java.util.Date endTime) {
		this.endTime = endTime;
	}
	/**
	 * @Description: 属性 startMillisecond 的get方法 
	 * @return startMillisecond
	 */
	public Long getStartMillisecond() {
		return startMillisecond;
	}
	/**
	 * @Description: 属性 startMillisecond 的set方法 
	 * @param startMillisecond 
	 */
	public void setStartMillisecond(Long startMillisecond) {
		this.startMillisecond = startMillisecond;
	}
	/**
	 * @Description: 属性 endMillisecond 的get方法 
	 * @return endMillisecond
	 */
	public Long getEndMillisecond() {
		return endMillisecond;
	}
	/**
	 * @Description: 属性 endMillisecond 的set方法 
	 * @param endMillisecond 
	 */
	public void setEndMillisecond(Long endMillisecond) {
		this.endMillisecond = endMillisecond;
	}
	/**
	 * @Description: 属性 duration 的get方法 
	 * @return duration
	 */
	public Long getDuration() {
		return duration;
	}
	/**
	 * @Description: 属性 duration 的set方法 
	 * @param duration 
	 */
	public void setDuration(Long duration) {
		this.duration = duration;
	}
	/**
	 * @Description: 属性 statusCode 的get方法 
	 * @return statusCode
	 */
	public String getStatusCode() {
		return statusCode;
	}
	/**
	 * @Description: 属性 statusCode 的set方法 
	 * @param statusCode 
	 */
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	/**
	 * @Description: 属性 httpMessage 的get方法 
	 * @return httpMessage
	 */
	public String getHttpMessage() {
		return httpMessage;
	}
	/**
	 * @Description: 属性 httpMessage 的set方法 
	 * @param httpMessage 
	 */
	public void setHttpMessage(String httpMessage) {
		this.httpMessage = httpMessage;
	}
	/**
	 * @Description: 属性 executeMethod 的get方法 
	 * @return executeMethod
	 */
	public String getExecuteMethod() {
		return executeMethod;
	}
	/**
	 * @Description: 属性 executeMethod 的set方法 
	 * @param executeMethod 
	 */
	public void setExecuteMethod(String executeMethod) {
		this.executeMethod = executeMethod;
	}
	/**
	 * @Description: 属性 exceptionClass 的get方法 
	 * @return exceptionClass
	 */
	public String getExceptionClass() {
		return exceptionClass;
	}
	/**
	 * @Description: 属性 exceptionClass 的set方法 
	 * @param exceptionClass 
	 */
	public void setExceptionClass(String exceptionClass) {
		this.exceptionClass = exceptionClass;
	}
	/**
	 * @Description: 属性 exceptionMessage 的get方法 
	 * @return exceptionMessage
	 */
	public String getExceptionMessage() {
		return exceptionMessage;
	}
	/**
	 * @Description: 属性 exceptionMessage 的set方法 
	 * @param exceptionMessage 
	 */
	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}
	public String getParamExtend() {
		return paramExtend;
	}
	public void setParamExtend(String paramExtend) {
		this.paramExtend = paramExtend;
	}
	public String getResultExtend() {
		return resultExtend;
	}
	public void setResultExtend(String resultExtend) {
		this.resultExtend = resultExtend;
	}
	@Override
	public String toString() {
		return "HttpLogDomain [url=" + url + ", paramData=" + paramData + ", startTime=" + startTime + ", endTime="
				+ endTime + ", startMillisecond=" + startMillisecond + ", endMillisecond=" + endMillisecond
				+ ", duration=" + duration + ", statusCode=" + statusCode + ", httpMessage=" + httpMessage + ", result="
				+ result + ", executeMethod=" + executeMethod + ", exceptionClass=" + exceptionClass
				+ ", exceptionMessage=" + exceptionMessage + ", paramExtend=" + paramExtend + ", resultExtend="
				+ resultExtend + "]";
	}
	
	
}

