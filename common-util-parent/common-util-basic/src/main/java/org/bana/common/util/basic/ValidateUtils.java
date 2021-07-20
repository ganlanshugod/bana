/**
* @Company weipu   
* @Title: ValidateUtils.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-10-21 下午5:44:08 
* @version V1.0   
*/ 
package org.bana.common.util.basic;


/** 
 * @ClassName: ValidateUtils 
 * @Description: 封装各种数据的格式和有效性的验证方法
 *  
 */
public class ValidateUtils {
	
	/** 
	* @Description: 验证是否是一个合法的邮件地址（不包含汉字的）
	* @author Liu Wenjie   
	* @date 2014-10-21 下午5:46:52 
	* @return  
	*/ 
	public static boolean isEmail(String email){
		if(StringUtils.isBlank(email)){
			return false;
		}
		String regex = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		return email.matches(regex);
	}
}
