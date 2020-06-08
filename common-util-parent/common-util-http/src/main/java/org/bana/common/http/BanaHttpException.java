package org.bana.common.http;


/**
 * @ClassName: BanaHttpException
 * @Description: 运行异常
 * @author Liu Wenjie
 */
public class BanaHttpException extends RuntimeException {
	
	public static final String LOG_ERROR = "l-001";
	public static final String XML_ERROR = "X-001";

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = -2297847701398138521L;

	protected String code;
	
	/**
	 * <p>Description: </p>
	 * @author Liu Wenjie
	 * @date 2018年1月19日 上午10:37:59
	 */
	public BanaHttpException() {
		super();
	}
	
	/**
	 * <p>Description: message的消息</p>
	 * @author Liu Wenjie
	 * @date 2018年1月19日 上午10:39:11
	 * @param code
	 * @param message
	 */
	public BanaHttpException(String code,String message){
		super(message);
		this.code = code;
	}
	
	/**
	 * <p>Description: message的消息</p>
	 * @author Liu Wenjie
	 * @date 2018年1月19日 上午10:39:11
	 * @param code
	 * @param message
	 */
	public BanaHttpException(String code,String message,Throwable t){
		super(message,t);
		this.code = code;
	}
	/**
	 * @Description: 属性 code 的get方法 
	 * @return code
	 */
	public String getCode() {
		return code;
	}
}
