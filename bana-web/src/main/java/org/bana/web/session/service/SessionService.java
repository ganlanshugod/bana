/**
 * @Company weipu   
 * @Title: SessionService.java 
 * @Package com.jbinfo.i3618.system.session.service 
 * @author Yang Shuangshuang   
 * @date 2015-4-11 下午1:40:13 
 * @version V1.0   
 */
package org.bana.web.session.service;

import java.util.Map;

/**
 * @ClassName: SessionService
 * @Description: 对session进行操作的service接口
 */
public interface SessionService {
	/**
	 * 根据sessionID获取session信息
	 * 
	 * @param id
	 * @return
	 */
	public Map<Object, Object> getSession(String sessionId);

	/**
	 * 更新session信息
	 * 
	 * @param id
	 * @param session
	 */
	public void updateSession(String id, Map<Object, Object> session);

	/**
	 * 删除session信息
	 * 
	 * @param id
	 */
	public void removeSession(String id);

	/**
	 * 新增session信息
	 * 
	 * @param id
	 * @param session
	 */
	public void addSession(String id, Map<Object, Object> session);
}
