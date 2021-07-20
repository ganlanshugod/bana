package org.bana.common.util.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.junit.Test;

/**
 * @ClassName: TestPdf2Png
 * @Description: pdf转换为图片的测试类
 * @author liuwenjie
 */
public class TestPdf2Png {

	@Test
	public void testPdf2Png() {
		String pdfFile = "office/pdf/PDF2.pdf";

		List<String> list = pdfPathToImagePaths(pdfFile);
		for (int i = 0; i < list.size(); i++) {
			System.out.println("path==" + list.get(i));
		}
	}

	public static List<String> pdfPathToImagePaths(String pdfPath) {
		File pdfFile = new File(pdfPath);
		PDDocument pdDocument;
		try {
			pdDocument = PDDocument.load(pdfFile);
			int pageCount = pdDocument.getNumberOfPages();
			PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
			List<String> imagePathList = new ArrayList<>();
			String fileParent = pdfFile.getParent();
			String imgPath = fileParent + File.separator + UUID.randomUUID().toString() + ".png";
			File file = new File(imgPath);
			for (int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
				BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105, ImageType.RGB);
				ImageIO.write(image, "png", file);
				imagePathList.add(imgPath);
			}
			pdDocument.close();
			return imagePathList;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Test
	public void testPdf2Png2() throws IOException {
		String pdfFile = "office/pdf/PDF2.pdf";
		FileOutputStream fos = new FileOutputStream("office/pdf/pdf2png.png");
		Pdf2ImageUtil.pdf2Png(new File(pdfFile), fos);
	}

	@Test
	public void testPdf2Png3() throws IOException {
		String pdfFile = "office/pdf/PDF2.pdf";
		String disFile = "office/pdf/pdf2png2.png";
		pdfToImage(pdfFile, disFile);
	}

	// 默认图片分辨率按需求调节,该参数影响生成时间
	public static final float DEFAULT_DPI = 500;
	// 默认转换的图片格式为jpg
	// 如果生成将PDF生成一张长图片时,页数过大会抛出异常建议使用PNG格式
	public static final String DEFAULT_FORMAT = "jpg";

	public static void pdfToImage(String pdfPath, String imgPath) throws IOException {
		// 宽度
		int width = 0;
		// 保存一张图片中的RGB数据
		int[] singleImgRGB;
		int shiftHeight = 0;
		// 保存每张图片的像素值
		BufferedImage imageResult = null;
		// 利用PdfBox生成图像
		PDDocument pdDocument = PDDocument.load(new File(pdfPath));
		PDFRenderer renderer = new PDFRenderer(pdDocument);
		// 循环每个页码
		for (int i = 0, len = pdDocument.getNumberOfPages(); i < len; i++) {
			if (i == -1 || i <= len) {
				BufferedImage image = renderer.renderImageWithDPI(i, DEFAULT_DPI, ImageType.RGB);
				int imageHeight = image.getHeight();
				int imageWidth = image.getWidth();
				// 使用第一张图片宽度;
				width = imageWidth;
				// 保存每页图片的像素值
				imageResult = new BufferedImage(width, imageHeight, BufferedImage.TYPE_INT_RGB);
				// 这里有高度，可以将imageHeight*len，我这里值提取一页所以不需要
				singleImgRGB = image.getRGB(0, 0, width, imageHeight, null, 0, width);
				// 写入流中
				imageResult.setRGB(0, shiftHeight, width, imageHeight, singleImgRGB, 0, width);
			} else if (i > len) {
				continue;
			}
		}
		pdDocument.close();
		// 写图片
		ImageIO.write(imageResult, DEFAULT_FORMAT, new File(imgPath));

	}

}
