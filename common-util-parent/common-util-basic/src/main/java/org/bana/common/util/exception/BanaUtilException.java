/**
* @Company weipu   
* @Title: BanaUtilException.java 
* @Package org.bana.common.util.exception 
* @author Liu Wenjie   
* @date 2014-9-3 下午2:15:38 
* @version V1.0   
*/ 
package org.bana.common.util.exception;

/** 
 * @ClassName: BanaUtilException 
 * @Description: bana的commonUtil对应的异常类 
 *  
 */
public class BanaUtilException extends RuntimeException {

	private static final long serialVersionUID = 549835414253539012L;

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38  
	 */
	public BanaUtilException() {
		super();
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0 
	 */
	public BanaUtilException(String message) {
		super(message);
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0 
	 */
	public BanaUtilException(Throwable e) {
		super(e);
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0
	 * @param arg1 
	 */
	public BanaUtilException(String message, Throwable e) {
		super(message, e);
	}

}
