package org.bana.common.util.pdf;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

/** 
* @ClassName: Pdf2PngUtil 
* @Description: pdf转Png的工具类
* @author liuwenjie   
*/ 
public class Pdf2ImageUtil {
	
	/** 
	* @Description: pdf 转化为png图片
	* @author liuwenjie   
	* @date 2021年6月30日 上午11:11:33 
	* @param pdfFile
	* @param os
	* @throws IOException  
	*/ 
	public static void pdf2Png(File pdfFile, OutputStream os) throws IOException {
		BufferedImage concat = concat(convertToBufferImage(pdfFile));
		ImageIO.write(concat, "png", os);
	}

	public static BufferedImage[] convertToBufferImage(File file) throws IOException {
        PDDocument document = PDDocument.load(file);
        PDFRenderer pdfRenderer = new PDFRenderer(document);
        List<BufferedImage> bufferedImageList = new ArrayList<>();

        for (int page = 0;page<document.getNumberOfPages();page++){
            BufferedImage img = pdfRenderer.renderImageWithDPI(page, 105, ImageType.RGB);
            bufferedImageList.add(img);
        }
        document.close();
        return bufferedImageList.toArray(new BufferedImage[bufferedImageList.size()]);
    }
	
	public static BufferedImage concat(BufferedImage[] images) throws IOException {
        int heightTotal = 0;
        for(int j = 0; j < images.length; j++) {
            heightTotal += images[j].getHeight();
        }

        int heightCurr = 0;
        BufferedImage concatImage = new BufferedImage(images[0].getWidth(), heightTotal, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = concatImage.createGraphics();
        for(int j = 0; j < images.length; j++) {
            g2d.drawImage(images[j], 0, heightCurr, null);
            heightCurr += images[j].getHeight();
        }
        g2d.dispose();

        return concatImage;
	}
}
