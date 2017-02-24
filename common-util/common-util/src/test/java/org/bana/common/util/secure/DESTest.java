package org.bana.common.util.secure;

import java.util.Random;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密介绍 DES是一种对称加密算法，所谓对称加密算法即：加密和解密使用相同密钥的算法。DES加密算法出自IBM的研究，
 * 后来被美国政府正式采用，之后开始广泛流传，但是近些年使用越来越少，因为DES使用56位密钥，以现代计算能力，
 * 24小时内即可被破解。虽然如此，在某些简单应用中，我们还是可以使用DES加密算法，本文简单讲解DES的JAVA实现 。
 * 注意：DES加密和解密过程中，密钥长度都必须是8的倍数
 */
public class DESTest {
	public DESTest() {
	}
	
	public static String getPassword(){
		Random ran = new Random();
		System.out.println(ran.nextInt(10));
		String password = "";
		while(password.length() <160){
			password = password + ran.nextInt(10);
		}
		System.out.println(password.length() + "==" + password);
		return password;
	}

	// 测试
	public static void main(String args[]) {
		// 待加密内容
		String str = "测试内容";
		// 密码，长度要是8的倍数
//		String password = "9588028820109132570743325311898426347857298773549468758875018579537757772163084478873699447306034466200616411960574122434059469100235892702736860872901247123456";
//		String password = getPassword();
		String password = "5502371938126290729141505563143324947905848891019283849899444248941936042570205778918093896184338373557878761607282260195790613629135575844061278404464777165404";
		
		
		System.out.println(password.length());
		
		byte[] result = DES.encrypt(str.getBytes(), password);
		String encodeString = Base64.encodeBase64String(result);
		System.out.println("加密后：" + encodeString);
		byte[] decodeBase64 = Base64.decodeBase64(encodeString);
		
		// 直接将如上内容解密
		try {
			byte[] decryResult = DES.decrypt(decodeBase64, password);
			System.out.println("解密后：" + new String(decryResult));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}