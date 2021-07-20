/**
* @Company weipu
* @Title: HttpConfig.java 
* @Package org.bana.common.http 
* @author liuwenjie   
* @date Jul 22, 2020 10:14:02 AM 
* @version V1.0   
*/ 
package org.bana.common.http;

/** 
* @ClassName: HttpConfig 
* @Description: http的配置 
* @author liuwenjie   
*/
public class HttpConfig {

	private int timeout = 10000; //单位是毫秒
	
	private int socketTimeout = 10000; //单位是毫秒

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public int getSocketTimeout() {
		return socketTimeout;
	}

	public void setSocketTimeout(int socketTimeout) {
		this.socketTimeout = socketTimeout;
	}
	
}
