/**
* @Company 青鸟软通   
* @Title: BanaDubboRestMonitor.java 
* @Package org.bana.common.util.monitor.impl 
* @author Liu Wenjie   
* @date 2014-12-5 下午1:45:12 
* @version V1.0   
*/ 
package org.bana.common.util.monitor.impl;

import org.bana.common.util.monitor.BanaStatusMonitor;

/** 
 * @ClassName: BanaDubboRestMonitor 
 * @Description: 发布的dubbo状态的监控机制
 *  
 */
public class BanaDubboRestMonitor implements BanaStatusMonitor{

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-12-5 下午1:45:46 
	* @return 
	* @see org.bana.common.util.monitor.BanaStatusMonitor#checkStatus() 
	*/ 
	@Override
	public boolean checkStatus() {
		return true;
	}

}
