/**
* @Company weipu   
* @Title: AccessLogDomain.java 
* @Package org.bana.web.log 
* @author Liu Wenjie   
* @date 2015-5-26 下午8:01:28 
* @version V1.0   
*/ 
package org.bana.core.log;

import java.io.Serializable;
import java.util.Map;

/** 
 * @ClassName: AccessLogDomain 
 * @Description: AccessDomain对象，保存记录访问记录的值
 *  
 */
public class AccessLogDomain implements Serializable{

	/** 
	* @Fields serialVersionUID :
	*/ 
	private static final long serialVersionUID = -8163740201145602188L;
	
	/** 
	* @Fields userIp : 用户ip
	*/
	private String userIp;
	/** 
	* @Fields executeUrl : 调用的url
	*/
	private String executeUrl;
	/** 
	* @Fields executeParamsJson : 调用的参数转为json
	*/
	private Map<String,String[]> paramsMap;
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
	* @Fields errorMessage : 异常信息
	*/
	private String errorMessage;
	/** 
	* @Fields clientInfo : 客户端信息
	*/
	private String clientInfo;
	/** 
	* @Fields executeMethod : 访问类型,post还是get方法等
	*/
	private String executeMethod;
	/**
	 * @Description: 属性 userIp 的get方法 
	 * @return userIp
	 */
	public String getUserIp() {
		return userIp;
	}
	/**
	 * @Description: 属性 userIp 的set方法 
	 * @param userIp 
	 */
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	/**
	 * @Description: 属性 executeUrl 的get方法 
	 * @return executeUrl
	 */
	public String getExecuteUrl() {
		return executeUrl;
	}
	/**
	 * @Description: 属性 executeUrl 的set方法 
	 * @param executeUrl 
	 */
	public void setExecuteUrl(String executeUrl) {
		this.executeUrl = executeUrl;
	}
	
	/**
	 * @Description: 属性 paramsMap 的get方法 
	 * @return paramsMap
	 */
	public Map<String, String[]> getParamsMap() {
		return paramsMap;
	}
	/**
	 * @Description: 属性 paramsMap 的set方法 
	 * @param paramsMap 
	 */
	public void setParamsMap(Map<String, String[]> paramsMap) {
		this.paramsMap = paramsMap;
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
	 * @Description: 属性 errorMessage 的get方法 
	 * @return errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @Description: 属性 errorMessage 的set方法 
	 * @param errorMessage 
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @Description: 属性 clientInfo 的get方法 
	 * @return clientInfo
	 */
	public String getClientInfo() {
		return clientInfo;
	}
	/**
	 * @Description: 属性 clientInfo 的set方法 
	 * @param clientInfo 
	 */
	public void setClientInfo(String clientInfo) {
		this.clientInfo = clientInfo;
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
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-5-26 下午9:38:15 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "AccessLogDomain [userIp=" + userIp + ", executeUrl=" + executeUrl + ", paramsMap=" + paramsMap + ", startTime=" + startTime + ", endTime=" + endTime
				+ ", startMillisecond=" + startMillisecond + ", endMillisecond=" + endMillisecond + ", duration=" + duration + ", statusCode=" + statusCode + ", errorMessage="
				+ errorMessage + ", clientInfo=" + clientInfo + ", executeMethod=" + executeMethod + "]";
	}
	
}
