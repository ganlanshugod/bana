/**
* @Company 青鸟软通   
* @Title: ThrowableUtil.java 
* @Package org.bana.common.util.exception 
* @author Liu Wenjie   
* @date 2014-11-11 下午8:21:21 
* @version V1.0   
*/ 
package org.bana.common.util.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/** 
 * @ClassName: ThrowableUtil 
 * @Description: 异常信息的处理工具类
 *  
 */
public class ThrowableUtil {
	
	/** 
	* @Description:  获取系统的堆栈信息
	* @author Liu Wenjie   
	* @date 2014-11-11 下午8:24:04 
	* @param e
	* @return  
	*/ 
	public static String getStackTrace(Throwable e){
		ByteArrayOutputStream buf = new ByteArrayOutputStream();
		e.printStackTrace(new PrintWriter(buf, true));
		return buf.toString();
	}
	
}
