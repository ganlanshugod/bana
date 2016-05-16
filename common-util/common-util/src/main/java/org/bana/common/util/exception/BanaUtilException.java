/**
* @Company 青鸟软通   
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
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0 
	 */
	public BanaUtilException(String arg0) {
		super(arg0);
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0 
	 */
	public BanaUtilException(Throwable arg0) {
		super(arg0);
	}

	/** 
	 * <p>Description: </p> 
	 * @author Liu Wenjie   
	 * @date 2014-9-3 下午2:15:38 
	 * @param arg0
	 * @param arg1 
	 */
	public BanaUtilException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
