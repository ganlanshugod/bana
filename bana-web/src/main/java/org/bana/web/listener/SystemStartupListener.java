package org.bana.web.listener;

import javax.servlet.ServletContextEvent;


/** 
* @ClassName: SystemStartupListener 
* @Description: 系统启动监听器
*  
*/ 
public interface SystemStartupListener {
	/** 
	* @Description: 启动监听方法
	* @author Liu Wenjie   
	* @date 2014-12-3 下午2:10:41 
	* @param contextEvent  
	*/ 
	public void onStartup(ServletContextEvent contextEvent);
}
