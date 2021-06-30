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
	
	public static List<String> pdfPathToImagePaths(String pdfPath){
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
            for(int pageIndex = 0; pageIndex < pageCount; pageIndex++) {
                BufferedImage image = pdfRenderer.renderImageWithDPI(pageIndex, 105,ImageType.RGB);
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
	
	
}
