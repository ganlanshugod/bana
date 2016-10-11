/**
* @Company 青鸟软通   
* @Title: StringUtilsTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-11-29 上午11:13:55 
* @version V1.0   
*/ 
package org.bana.common.util.basic;


import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/** 
 * @ClassName: StringUtilsTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class StringUtilsTest {
	
	@Test
	public void testEquals(){
		String type = "102";
		System.out.println(type.equals(102));
	}
	
	@Test
	public void testInteger(){
		String hexString = "90000CB20301F401F401F401F407D447FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
		String binaryString = hexString2binaryString(hexString);
		String hexString2 = binaryString2hexString(binaryString);
		System.out.println(hexString);
		System.out.println(hexString2);
		System.out.println(binaryString);
		
		byte[] a = new byte[]{2,1,3};
		byte[] b = new byte[]{1,2,3};
		
		System.out.println(Arrays.equals(a, b));
		System.out.println(a.equals(b));
	}
	
	public static String binaryString2hexString(String bString)  
    {  
        if (bString == null || bString.equals("") || bString.length() % 8 != 0)  
            return null;  
        StringBuffer tmp = new StringBuffer();  
        int iTmp = 0;  
        for (int i = 0; i < bString.length(); i += 4)  
        {  
            iTmp = 0;  
            for (int j = 0; j < 4; j++)  
            {  
                iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);  
            }  
            tmp.append(Integer.toHexString(iTmp));  
        }  
        return tmp.toString();  
    }  
	
	public static String hexString2binaryString(String hexString)  
    {  
        if (hexString == null || hexString.length() % 2 != 0)  
            return null;  
        String bString = "", tmp;  
        for (int i = 0; i < hexString.length(); i++)  
        {  
            tmp = "0000"  
                    + Integer.toBinaryString(Integer.parseInt(hexString  
                            .substring(i, i + 1), 16));  
            bString += tmp.substring(tmp.length() - 4);  
        }  
        return bString;  
    }  

	@Test
	public void testBoolean(){
		String booleanStr = null;
		Assert.assertFalse(Boolean.parseBoolean(booleanStr));
	}
	
	@Test
	public void testBoolean2(){
		String booleanStr = "false";
		Assert.assertFalse(Boolean.parseBoolean(booleanStr));
	}
	
	@Test
	public void testBoolean3(){
		String booleanStr = "false2";
		Assert.assertFalse(Boolean.parseBoolean(booleanStr));
	}
	
	@Test
	public void testBoolean4(){
		String booleanStr = "true";
		Assert.assertTrue(Boolean.parseBoolean(booleanStr));
	}
	
	@Test
	public void testReplace(){
		String source = "各位家长，因为今天下午我要外出学习，所以今天自修课暂停，请及时接孩子！\u0014\u0017";
		System.out.println(source);
		System.out.println(StringUtils.replaceUxxx(source));
	}
	
	@Test
	public void testRandom(){
		String random = "";
		System.out.println(StringUtils.getRandomStr());
	}
}
