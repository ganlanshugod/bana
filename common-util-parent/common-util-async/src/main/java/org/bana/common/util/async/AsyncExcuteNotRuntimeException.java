/**
* @Company 全域旅游
* @Title: AsyncRuntimeException.java 
* @Package org.bana.common.util.async 
* @author liuwenjie   
* @date Sep 15, 2020 2:55:21 PM 
* @version V1.0   
*/ 
package org.bana.common.util.async;

/** 
* @ClassName: AsyncRuntimeException 
* @Description: 异步执行的异常返回结果类
* @author liuwenjie   
*/
public class AsyncExcuteNotRuntimeException extends RuntimeException{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 4758871742232129736L;

	/** 
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Sep 15, 2020 2:56:09 PM  
	*/
	public AsyncExcuteNotRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	
}
