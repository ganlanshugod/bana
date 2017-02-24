/**
* @Company 青鸟软通   
* @Title: CodeUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-9-24 下午5:16:49 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.Random;
import java.util.UUID;

/** 
 * @ClassName: CodeUtil 
 * @Description: 生成唯一编码的工具类 
 *  
 */
public class CodeUtil {
	public static final String[] codeItems = 
		{"2","3","4","5","6","7","8","9",
		 "A","B","C","D","E","F","G","H",
		 "J","K","L","M","N","P","Q","R",
		 "S","T","U","V","W","X","Y","Z"
		};
	/** 
	* @Description: 生成一个唯一的订单单号
	* @author Liu Wenjie   
	* @date 2015-9-24 下午5:19:11 
	* @return  
	*/ 
	public static String generatorNumberCode(){
		Random random = new Random();
		String time = DateUtil.getNowString("yyMMddHHmmssSSS");
		int uuidhashCode = UUID.randomUUID().toString().hashCode();
		String uniqueStr = StringUtils.leftPad(String.valueOf(uuidhashCode), 11, (char)(random.nextInt(10)+48));
		return time + uniqueStr + random.nextInt(10);
		
	}
	
	
	/** 
	* @Description: 将指定数值对应到指定进制的编码
	* @author Richard Core   
	* @date 2016-1-8 上午10:29:32 
	* @param targetNum
	* @return  
	*/ 
	public static String generatorCodeFromNumber(Long targetNum){
		if(targetNum == null){
			return null;
		}
		return generatorCodeFromNumber(targetNum ,codeItems);
	}
	/** 
	* @Description: 将指定数值对应到指定进制的编码
	* @author Richard Core   
	* @date 2016-1-8 上午10:29:32 
	* @param targetNum 需要编码的数字
	* @param targetCodeAry 编码数组
	* @return  
	*/
	public static String generatorCodeFromNumber(Long targetNum ,String... targetCodeAry){
		if(targetNum == null){
			return null;
		}
		if(targetCodeAry == null || targetCodeAry.length == 0){
			targetCodeAry = codeItems;
		}
		long divisor = targetCodeAry.length;
		int remindIndex = 0;
		if(targetNum.compareTo(divisor) >= 0){
			StringBuffer resultBuffer = new StringBuffer();
			while(targetNum.compareTo(divisor) >= 0){
				remindIndex = (int) (targetNum % divisor); //求余数
				targetNum /= divisor; //更新被除数
				resultBuffer.insert(0,targetCodeAry[remindIndex]);
			}
			resultBuffer.insert(0,targetCodeAry[Integer.valueOf(String.valueOf(targetNum))]);
			return resultBuffer.toString();
		}else{
			return targetCodeAry[Integer.valueOf(String.valueOf(targetNum))];
		}
	}
	
}
