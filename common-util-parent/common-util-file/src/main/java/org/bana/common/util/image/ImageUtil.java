package org.bana.common.util.image;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.bana.common.util.exception.BanaUtilException;

/** 
* @ClassName: ImageUtil 
* @Description: 图片压缩工具类 提供的方法中可以设定生成的缩略图片的大小尺寸等
*  
*/ 
public class ImageUtil {
	/** 
	* @Description: 图片文件读取
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:28:43 
	* @param srcImgPath
	* @return  
	*/ 
	private static BufferedImage inputImage(String srcImgPath) {
		BufferedImage srcImage = null;
		try {
			FileInputStream in = new FileInputStream(srcImgPath);
			srcImage = javax.imageio.ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("读取图片文件出错！" + e.getMessage());
			throw new BanaUtilException(e.getMessage());
		}
		return srcImage;
	}
	
	/** 
	* @Description: 图片文件读取
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:28:43 
	* @param srcImgPath
	* @return  
	*/ 
	private static BufferedImage inputImage(InputStream in) {
		BufferedImage srcImage = null;
		try {
			srcImage = javax.imageio.ImageIO.read(in);
		} catch (IOException e) {
			System.out.println("读取图片文件出错！" + e.getMessage());
			throw new BanaUtilException(e.getMessage());
		}
		return srcImage;
	}

	/** 
	* @Description: 将图片按照指定的图片尺寸压缩
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:28:53 
	* @param srcImgPath :源图片路径
	* @param new_w :压缩后的图片宽
	* @param new_h :压缩后的图片高
	*/ 
	public static BufferedImage compressImage(String srcImgPath, int new_w, int new_h) {
		BufferedImage src = inputImage(srcImgPath);
		return disposeImage(src, new_w, new_h);
	}
	
	/** 
	* @Description: TODO (这里用一句话描述这个类的作用)
	* @author Han Tongyang   
	* @date 2016-7-13 下午8:30:00 
	* @param in :源图片流
	* @param new_w :压缩后的图片宽
	* @param new_h :压缩后的图片高
	* @return  
	*/ 
	public static BufferedImage compressImage(InputStream in, int new_w, int new_h){
		BufferedImage src = inputImage(in);
		return disposeImage(src, new_w, new_h);
	}

	/** 
	* @Description: 指定长或者宽的最大值来压缩图片
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:29:34 
	* @param srcImgPath :源图片路径
	* @param maxLength  :长或者宽的最大值
	*/ 
	public static BufferedImage compressImage(String srcImgPath, int maxLength) {
		// 得到图片
		BufferedImage src = inputImage(srcImgPath);
		if (null != src) {
			int old_w = src.getWidth();
			// 得到源图宽
			int old_h = src.getHeight();
			// 得到源图长
			int new_w = 0;
			// 新图的宽
			int new_h = 0;
			// 新图的长
			// 根据图片尺寸压缩比得到新图的尺寸
			if (old_w > old_h) {
				// 图片要缩放的比例
				new_w = maxLength;
				new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
			} else {
				new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
				new_h = maxLength;
			}
			return disposeImage(src, new_w, new_h);
		}else{
			return null;
		}
	}
	
	/** 
	* @Description: 指定长或者宽的最大值来压缩图片
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:29:34 
	* @param srcImgPath :源图片路径
	* @param maxLength  :长或者宽的最大值
	*/ 
	public static BufferedImage compressImage(InputStream in, int maxLength) {
		// 得到图片
		BufferedImage src = inputImage(in);
		if (null != src) {
			int old_w = src.getWidth();
			// 得到源图宽
			int old_h = src.getHeight();
			// 得到源图长
			int new_w = 0;
			// 新图的宽
			int new_h = 0;
			// 新图的长
			// 根据图片尺寸压缩比得到新图的尺寸
			if (old_w > old_h) {
				// 图片要缩放的比例
				new_w = maxLength;
				new_h = (int) Math.round(old_h * ((float) maxLength / old_w));
			} else {
				new_w = (int) Math.round(old_w * ((float) maxLength / old_h));
				new_h = maxLength;
			}
			return disposeImage(src, new_w, new_h);
		}else{
			return null;
		}
	}

	/** 
	* @Description: 处理图片
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:30:03 
	* @param src
	* @param new_w
	* @param new_h  
	*/ 
	private synchronized static BufferedImage disposeImage(BufferedImage src, int new_w, int new_h) {
		// 得到图片
		int old_w = src.getWidth();
		// 得到源图宽
		int old_h = src.getHeight();
		// 得到源图长
		BufferedImage newImg = null;
		// 判断输入图片的类型
		switch (src.getType()) {
		case 13:
			// png,gifnewImg = new BufferedImage(new_w, new_h,
			// BufferedImage.TYPE_4BYTE_ABGR);
			break;
		default:
			newImg = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_RGB);
			break;
		}
		Graphics2D g = newImg.createGraphics();
		// 从原图上取颜色绘制新图
		g.drawImage(src, 0, 0, old_w, old_h, null);
		g.dispose();
		// 根据图片尺寸压缩比得到新图的尺寸
		newImg.getGraphics().drawImage(
				src.getScaledInstance(new_w, new_h, Image.SCALE_SMOOTH), 0, 0,
				null);
		return newImg;
		// 调用方法输出图片文件
		//OutImage(outImgPath, newImg);
	}

	/** 
	* @Description: 将图片文件输出到指定的路径，并可设定压缩质量
	* @author Han Tongyang   
	* @date 2016-7-13 下午7:30:13 
	* @param outImgPath
	* @param newImg  
	*/ 
	@SuppressWarnings("unused")
	@Deprecated
	private static void outImage(String outImgPath, BufferedImage newImg) {
		// 判断输出的文件夹路径是否存在，不存在则创建
		File file = new File(outImgPath);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}// 输出到文件流
		try {
			ImageIO.write(newImg,
					outImgPath.substring(outImgPath.lastIndexOf(".") + 1),
					new File(outImgPath));
		} catch (FileNotFoundException e) {
			throw new BanaUtilException(e.getMessage());
		} catch (IOException e) {
			throw new BanaUtilException(e.getMessage());
		}
	}
}