/**
 * @Company 青鸟软通   
 * @Title: MD5Util.java 
 * @Package com.haier.hrnet.util 
 * @author Liu Wenjie   
 * @date 2013-5-24 上午9:57:21 
 * @version V1.0   
 */
package org.bana.common.util.basic;

/**
 * @ClassName: MD5Util
 * @Description: 密码加密工具
 * 
 */
public class MD5Util {
	
	public static String getMD5UseKey(String sourceStr){
		char[] hexDigits = { '0', '1', '4', '3', '2', '5', 'a', '7', '8', '6',
				'9', 'b', 'c', 'd', 'e', 'f' };
		return getMD5(sourceStr, hexDigits);
	}
	
	public static String getMD5(String sourceStr){
		char[] hexDigits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		return getMD5(sourceStr, hexDigits);
	}

	public static String getMD5(String sourceStr,char[] hexDigits) {

		byte[] source = sourceStr.getBytes();
		String s = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			s = new String(str);
			return s;
		} catch (Exception e) {
			return null;
		}

	}
	

}
