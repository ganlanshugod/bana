/**
 * @Company weipu   
 * @Title: BarCodeTest.java 
 * @Package org.bana.common.util.file.barcode 
 * @author Liu Wenjie   
 * @date 2015-7-24 下午1:38:41 
 * @version V1.0   
 */
package org.bana.common.util.file.barcode;

/** 
 * @ClassName: BarCodeTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;

public class BarCodeTest {
	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		String text = "http://iwechat.i3618.com.cn/wechat-web/wechat/system/organization/qyUserCollectInit?corpCode=wx8b493a07d680f469&schoolCode=7";
		int width = 300;
		int height = 300;
		// 二维码的图片格式
		
		Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
		// 内容所使用编码
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		BitMatrix bitMatrix = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, hints);
		// 生成二维码
		File outputFile = new File("d:" + File.separator + "new.gif");
		String format = "gif";
		MatrixToImageWriter.writeToFile(bitMatrix, format, outputFile);
		OutputStream stream = new FileOutputStream(new File("D:/new2.gif"));
		MatrixToImageWriter.writeToStream(bitMatrix, format, stream);
		System.out.println("end");
	}
}