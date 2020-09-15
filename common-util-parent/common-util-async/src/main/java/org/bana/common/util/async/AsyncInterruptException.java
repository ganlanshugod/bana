/**
* @Company 全域旅游
* @Title: AsyncInterruptException.java 
* @Package org.bana.common.util.async 
* @author liuwenjie   
* @date Sep 15, 2020 2:57:53 PM 
* @version V1.0   
*/ 
package org.bana.common.util.async;

/** 
* @ClassName: AsyncInterruptException 
* @Description: 增加一个异步异常封装类
* @author liuwenjie   
*/
public class AsyncInterruptException extends RuntimeException {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -8039644313433847432L;


	public AsyncInterruptException(String message, Throwable cause) {
		super(message, cause);
	}
}
