/**
 * @Company 青鸟软通   
 * @Title: Cn2Spell.java 
 * @Package com.jbinfo.i3618.web.util 
 * @author Han Tongyang   
 * @date 2015-5-20 下午3:06:51 
 * @version V1.0   
 */
package org.bana.common.util.basic;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @ClassName: Cn2Spell
 * @Description: 汉字转字母工具类
 * 
 */
public class Cn2Spell {
	
	//正则表达式，表示字母和数字
	private static final String SPECIAL_REGEX="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 

	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) {
		String pinyinName = "";
		//判断是否为空，为空则返回空值
		if(StringUtils.isBlank(chines)){
			return pinyinName;
		}
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			String nameStr = String.valueOf(nameChar[i]);
			if(StringUtils.isBlank(regexpStr(nameStr))){
				continue;
			}
			if (nameChar[i] > 128) {
				try {
					String hanyupinyin[] = PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat);
					if(hanyupinyin != null && hanyupinyin.length > 0){
						pinyinName += hanyupinyin[0].charAt(0);
					}else{
						pinyinName += "XXXX";
					}
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					pinyinName += "XXXX";
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}

	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * 
	 * @param chines
	 *            汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) {
		String pinyinName = "";
		//判断是否为空，为空则返回空值
		if(StringUtils.isBlank(chines)){
			return pinyinName;
		}
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			String nameStr = String.valueOf(nameChar[i]);
			if(StringUtils.isBlank(regexpStr(nameStr))){
				continue;
			}
			if (nameChar[i] > 128) {
				try {
					pinyinName += PinyinHelper.toHanyuPinyinStringArray(
							nameChar[i], defaultFormat)[0];
				} catch (BadHanyuPinyinOutputFormatCombination e) {
					e.printStackTrace();
				}
			} else {
				pinyinName += nameChar[i];
			}
		}
		return pinyinName;
	}
	
	/** 
	* @Description: 验证是否是数字或字母
	* @author Han Tongyang   
	* @date 2015-11-24 下午1:52:32 
	* @param str
	* @return  
	*/ 
	private static String regexpStr(String str){
		Pattern p = Pattern.compile(SPECIAL_REGEX); 
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public static void main(String[] args) {
		long thisTime = new Date().getTime();
		System.out.println("begin..." + thisTime);
		System.out.println(converterToSpell("Y10级张小驰家01a"));
		System.out.println("end..." + ((new Date().getTime()) - thisTime));
		System.out.println(converterToFirstSpell("张 悦？"));
	}
}
