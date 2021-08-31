package org.bana.common.util.poi;

/** 
* @ClassName: PoiCustomException 
* @Description: Poi的自定义异常类
* @author liuwenjie   
*/ 
public class BanaPoiException extends RuntimeException{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 4823974692486475092L;
	
	public BanaPoiException(String message) {
		super(message);
	}
	
	public BanaPoiException(String message,Throwable e) {
		super(message, e);
	}

}
