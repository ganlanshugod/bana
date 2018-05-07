package org.bana.common.util.basic;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

/**
 * @ClassName: IdCardUtil
 * @Description: 校验身份证是否合法
 * @author Sun Chao
 */
public class IdCardUtil {
	/** 
	* Description: 校验身份证是否合法
	* @author Sun Chao   
	* @date 2018年1月30日 下午6:04:10 
	* @param idNo
	* @return  
	*/ 
	public static boolean isValid(String idNo) {
		return isIdNoPattern(idNo) && isValidProvinceId(idNo.substring(0, 2)) && isValidDate(idNo.substring(6, 14))
				&& checkIdNoLastNum(idNo);
	}

	// 省(直辖市)码表
	private static String provinceCode[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34",
			"35", "36", "37", "41", "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64",
			"65", "71", "81", "82", "91" };

	/**
	 * Description: 二代身份证正则表达式
	 * 
	 * @author Sun Chao
	 * @date 2018年1月30日 下午6:01:35
	 * @param idNo
	 * @return
	 */
	private static boolean isIdNoPattern(String idNo) {
		return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$",
				idNo);
	}

	/**
	 * Description: 检查身份证的省份信息是否正确
	 * 
	 * @author Sun Chao
	 * @date 2018年1月30日 下午6:01:50
	 * @param provinceId
	 * @return
	 */
	public static boolean isValidProvinceId(String provinceId) {
		for (String id : provinceCode) {
			if (id.equals(provinceId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Description: 判断日期是否有效
	 * 
	 * @author Sun Chao
	 * @date 2018年1月30日 下午6:02:02
	 * @param inDate
	 * @return
	 */
	public static boolean isValidDate(String inDate) {
		if (inDate == null) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		if (inDate.trim().length() != dateFormat.toPattern().length()) {
			return false;
		}
		dateFormat.setLenient(false);// 执行严格的日期匹配
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

	// 身份证前17位每位加权因子
	private static int[] power = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	// 身份证第18位校检码
	private static String[] refNumber = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };

	/** 
	* Description: 计算身份证的第十八位校验码
	* @author Sun Chao   
	* @date 2018年1月30日 下午6:02:48 
	* @param cardIdArray
	* @return  
	*/ 
	public static String sumPower(int[] cardIdArray) {
		int result = 0;
		for (int i = 0; i < power.length; i++) {
			result += power[i] * cardIdArray[i];
		}
		return refNumber[(result % 11)];
	}

	/** 
	* Description: 校验身份证第18位是否正确(只适合18位身份证)
	* @author Sun Chao   
	* @date 2018年1月30日 下午6:03:01 
	* @param idNo
	* @return  
	*/ 
	public static boolean checkIdNoLastNum(String idNo) {
		if (idNo.length() != 18) {
			return false;
		}
		char[] tmp = idNo.toCharArray();
		int[] cardidArray = new int[tmp.length - 1];
		int i = 0;
		for (i = 0; i < tmp.length - 1; i++) {
			cardidArray[i] = Integer.parseInt(tmp[i] + "");
		}
		String checkCode = sumPower(cardidArray);
		String lastNum = tmp[tmp.length - 1] + "";
		if (lastNum.equals("x")) {
			lastNum = lastNum.toUpperCase();
		}
		if (!checkCode.equals(lastNum)) {
			return false;
		}
		return true;
	}
}
