/**
* @Company 青鸟软通   
* @Title: StringUtilsTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-11-29 上午11:13:55 
* @version V1.0   
*/ 
package org.bana.common.util.basic;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.junit.Assert;
import org.junit.Test;

/** 
 * @ClassName: StringUtilsTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class StringUtilsTest {
	
	@Test
	public void testChineseLength(){
		System.out.println(StringUtils.chineselength("伴行a618电影优惠券测试"));
	}
	
	@Test
	public void testArray(){
		Random ran = new Random();
		int index = 0;
		String[] buffer = new String[16];
		String[] temp1 = new String[]{"1","2","3","4","5","6","7","8","9"};
		System.arraycopy(temp1, 0, buffer, index, 9);
		String[] temp2 = new String[]{"a","b","c","d","e","f","0"};
		System.arraycopy(temp2, 0, buffer, 9, temp2.length);
		System.out.println(Arrays.toString(buffer));
	}
	
	@Test
	public void testString(){
		String doBuffer = doBuffer(new byte[]{64, 7, 0, 0, 8, 4, 0, 8, 4, -116, 57, 80, 44, -96, -114, 13},16);
		System.out.println(doBuffer);
		String doBuffer2 = doBuffer(new byte[]{4, -116, 57, 80, 44, -96, -114, 13, 64, 7, 0, 0, 8, 4, 0, 8},16);
		System.out.println(doBuffer2);
		System.out.println(getTruthID(doBuffer));
		System.out.println(getTruthID(doBuffer2));
		//0743455116
		//2c50398c
	}
	
	private String getTruthID(String str){
		str = StringUtils.trimAll(str).substring(2,10);
        String result="";
        List<String> spid=new ArrayList<String>();
        for (int i=0;i<str.length();i++){
            if(i%2==0){
                spid.add(str.substring(i,i+2));
            }
        }
        for (int i=spid.size()-1;i>-1;i--){
            result+=spid.get(i);
        }
//        int buwei=10-result.length();


        if(result==null||result.length()<1){
            throw new RuntimeException("字符串不合法");
        }

        BigInteger sum=new BigInteger(result,16);

        result=String.valueOf(sum);
        int buwei=10-result.length();
        for (int i=0;i<buwei;i++){
            result="0"+result;
        }
        return  result;
    }
	
	private String doBuffer(byte[] buffer, int bufferLength) {

        String bufferString = "";

        for (int i = 0; i < bufferLength; i++) {

            String hexChar = Integer.toHexString(buffer[i] & 0xFF);
            if (hexChar.length() == 1) {
                hexChar = "0" + hexChar;
            }

            if (i % 16 == 0) {

                if (bufferString != "") {
                    bufferString = "";
                }
            }

            bufferString += hexChar.toUpperCase() + " ";
        }

        if (bufferString != "") {
            return bufferString;
        }
        return "";

    }
	
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
		System.out.println(StringUtils.isBlank(""));
	}
	
	@Test
	public void testBase64() throws UnsupportedEncodingException{
		System.out.println(new String(Base64.encodeBase64("11113".getBytes("utf-8")),"utf-8"));
	}
	
	@Test
	public void testJoin(){
		List<String> list = Arrays.asList("a","b","c");
		System.out.println(StringUtils.join(list,","));
	}
}
