/**
* @Company weipu   
* @Title: BanaStatusMonitor.java 
* @Package org.bana.common.util.monitor 
* @author Liu Wenjie   
* @date 2014-12-5 下午1:25:02 
* @version V1.0   
*/ 
package org.bana.common.util.monitor;

/** 
 * @ClassName: BanaStatusMonitor 
 * @Description: 监控Dubbo运行状态的接口定制
 *  
 */
public interface BanaStatusMonitor {

	/** 
	* @Description: 验证状态的方法，为了通信使用
	* @author Liu Wenjie   
	* @date 2014-12-5 下午1:42:42 
	* @return  
	*/ 
	boolean checkStatus();
}
