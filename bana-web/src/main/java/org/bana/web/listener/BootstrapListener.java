package org.bana.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/** 
* @ClassName: BootstrapListener 
* @Description: 
*/ 
public class BootstrapListener implements ServletContextListener {
	private static final String CONFIG_KEY = "startupListeners";
	private static final String SPERATOR = ",";

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-5-21 下午5:42:33 
	* @param sce 
	* @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent) 
	*/ 
	public void contextInitialized(ServletContextEvent contextEvent) {
		String startupListeners = contextEvent.getServletContext().getInitParameter(CONFIG_KEY);
		if(startupListeners == null || startupListeners.isEmpty()){
			return;
		}
		String[] listeners = startupListeners.split(SPERATOR);
		for(String listenerClass : listeners){
			try {
				@SuppressWarnings("unchecked")
				Class<SystemStartupListener> clazz = (Class<SystemStartupListener>) Class.forName(listenerClass);
				clazz.newInstance().onStartup(contextEvent);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
	}
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-5-21 下午5:42:33 
	* @param sce 
	* @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent) 
	*/ 
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

	
}