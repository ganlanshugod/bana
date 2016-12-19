/**
* @Company 艾美伴行   
* @Title: ImageUtilsTest.java 
* @Package org.bana.common.util.file.img 
* @author liuwenjie   
* @date 2016-10-27 上午10:56:09 
* @version V1.0   
*/ 
package org.bana.common.util.file.img;

import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.file.barcode.MatrixToImageWriter;
import org.bana.common.util.jdbc.DbUtil;
import org.junit.Test;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

/** 
 * @ClassName: ImageUtilsTest 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 *  
 */
public class ImageUtilsTest {

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#getSizeInfo(java.lang.String)}.
	 */
	@Test
	public void testGetSizeInfoString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#getSizeInfo(java.net.URL)}.
	 */
	@Test
	public void testGetSizeInfoURL() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#getSizeInfo(java.io.File)}.
	 */
	@Test
	public void testGetSizeInfoFile() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#getSizeInfo(java.io.InputStream)}.
	 */
	@Test
	public void testGetSizeInfoInputStream() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.lang.String, java.lang.String, int, int)}.
	 */
	@Test
	public void testResizeStringStringIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.io.InputStream, java.io.OutputStream, int, int)}.
	 */
	@Test
	public void testResizeInputStreamOutputStreamIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.io.InputStream, java.io.OutputStream, int, int, int, int)}.
	 */
	@Test
	public void testResizeInputStreamOutputStreamIntIntIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.lang.String, java.lang.String, int, int, int, int)}.
	 */
	@Test
	public void testResizeStringStringIntIntIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.io.File, java.io.File, int, int)}.
	 */
	@Test
	public void testResizeFileFileIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#resize(java.io.File, java.io.File, int, int, int, int)}.
	 */
	@Test
	public void testResizeFileFileIntIntIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#crop(java.lang.String, java.lang.String, int, int, int, int)}.
	 */
	@Test
	public void testCropStringStringIntIntIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#crop(java.io.File, java.io.File, int, int, int, int)}.
	 */
	@Test
	public void testCropFileFileIntIntIntInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#crop(java.io.InputStream, java.io.OutputStream, int, int, int, int, boolean)}.
	 */
	@Test
	public void testCropInputStreamOutputStreamIntIntIntIntBoolean() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#optimize(java.io.InputStream, java.io.OutputStream, float)}.
	 */
	@Test
	public void testOptimizeInputStreamOutputStreamFloat() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#optimize(java.lang.String, java.lang.String, float)}.
	 */
	@Test
	public void testOptimizeStringStringFloat() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#optimize(java.io.File, java.io.File, float)}.
	 */
	@Test
	public void testOptimizeFileFileFloat() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#makeRoundedCorner(java.io.File, java.io.File, int)}.
	 */
	@Test
	public void testMakeRoundedCornerFileFileInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#makeRoundedCorner(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testMakeRoundedCornerStringStringInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#makeRoundedCorner(java.io.InputStream, java.io.OutputStream, int)}.
	 */
	@Test
	public void testMakeRoundedCornerInputStreamOutputStreamInt() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#mergeImage(java.io.InputStream, java.io.InputStream, java.io.OutputStream, java.lang.String)}.
	 */
	@Test
	public void testMergeImageInputStreamInputStreamOutputStreamString() {
		fail("Not yet implemented");
	}

	private String basePath = "D:/user/testbase/img/";
	/**
	 * Test method for {@link org.bana.common.util.file.img.ImageUtils#mergeImage(java.io.File, java.io.File, java.io.File)}.
	 */
	@Test
	public void testMergeImageFileFileFile() {
		File file1 = new File(basePath+"02.png");
		File file2 = new File(basePath+"06.png");
		File destFile = new File(basePath+"09.png");
		ImageUtils.mergeImage(file2,file1, destFile);
	}
	
	/** 
	* @Description: 合并多张图片，将图片进行排列生成一张图片
	* @author liuwenjie   
	 * @throws IOException 
	 * @throws SQLException 
	* @date 2016-11-1 上午10:12:06   
	*/ 
	@Test
	public void testMergeImage() throws IOException, SQLException{
		int picW = 300;
	    int pading = 40;
	    int margin = 70;
		String basePath = "D:/user/testbase/qrcode20161121/";
		int width = 2480;
		int height = 3508;
		
		int ractW = 148;
		int ractH = 40;
		Connection connection = DbUtil.getConnection();
		String sql = "select id,qrcode,qrcode_url from test.bx_qrcode order by id limit ?,?";
		PreparedStatement pstat = connection.prepareStatement(sql);
		boolean hasNext = true;
		int pageSize = 1;
		while(hasNext){
			pstat.setInt(1, 1 + 70*pageSize);
			pstat.setInt(2, 70);
			hasNext = generatorImg(picW, pading, margin, basePath, width, height, ractW, ractH, pstat, pageSize);
			pageSize ++;
		}
		pstat.close();
		connection.close();
		
	}

	/** 
	* @Description:
	* @author liuwenjie   
	* @date 2016-11-1 下午12:19:01 
	* @param picW
	* @param pading
	* @param margin
	* @param basePath
	* @param width
	* @param height
	* @param ractW
	* @param ractH
	* @param pstat
	* @param pageSize
	* @return
	* @throws SQLException
	* @throws IOException
	* @throws FileNotFoundException
	* @throws MalformedURLException  
	*/ 
	private boolean generatorImg(int picW, int pading, int margin, String basePath, int width, int height, int ractW, int ractH, PreparedStatement pstat, int pageSize)
			throws SQLException, IOException, FileNotFoundException, MalformedURLException {
		boolean hasNext = false;
		ResultSet rs = pstat.executeQuery();
		
		File outFile = new File(basePath + pageSize +".png");
		if(!outFile.exists()){
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			outFile.createNewFile();
		}
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)combined.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,width,height);//填充整个屏幕
		OutputStream destStream = new FileOutputStream(outFile);
		int index = 0;
		while(rs.next()){
			hasNext = true;
			Long id = rs.getLong("id");
			String qrcode = rs.getString("qrcode");
			String qrcodeUrl = rs.getString("qrcode_url");
			BufferedImage sourceImg = ImageIO.read(new URL(qrcodeUrl));
			// paint both images, preserving the alpha channels
			int qrX = (index%7) * (picW+pading)+margin;
			int qrY = index/7 * (picW+pading)+margin;
			g.drawImage(sourceImg.getScaledInstance(picW, picW, Image.SCALE_SMOOTH), qrX,qrY, null);
			//画一个文字的背景
			g.setColor(Color.WHITE);
			int ractX = qrX + ((picW - ractW)/2);
			int ractY = qrY + (picW - ractH)/2;
			g.fillRect(ractX,ractY,ractW,ractH);
			g.setColor(Color.BLACK);
			g.setFont(new Font("黑体",800, 24));
			g.drawString(qrcode.substring(4), ractX+25,ractY+28);
			//画一个二维码的边框
		    g.setColor(new Color(229 , 229 , 228));     
			g.drawRect(qrX, qrY, picW, picW);  
			index ++;
		}
		ImageIO.write(combined, "PNG", destStream);
		
		rs.close();
		destStream.close();
		return hasNext;
	}
	
	@Test
	public void testGeneratorFile() throws IOException, WriterException{
		String filePath = this.getClass().getResource("/file/image/all.json").getFile();
		File file = new File(filePath);  
		System.out.println(file.getAbsolutePath());
        String content = FileUtils.readFileToString(file);  
//        System.out.println("Contents of file: " + content);  
        JSONArray jsonArray = JSONArray.fromObject(content);
        System.out.println(jsonArray.size());
        
        int picW = 300;
	    int pading = 40;
	    int margin = 70;
		String basePath = "D:/user/testbase/qrcode20161121/";
		int width = 2480;
		int height = 3508;
		
		int ractW = 60;
		int ractH = 40;
		
		for (int i = 0; i < jsonArray.size(); i+=70) {
			int pageSize = i+70 > jsonArray.size()?jsonArray.size()-i:70;
			String imageName = (i+1) + "_" + (i+pageSize);
			generatorImg2(picW, pading, margin, basePath, width, height, ractW, ractH, jsonArray,i,pageSize, imageName);
		}
	}
	
	private String baseUrl = "http://m.i3618.com.cn/other/pingjia/show.html?code=";
	
	private boolean generatorImg2(int picW, int pading, int margin, String basePath, int width, int height, int ractW, int ractH, JSONArray jsonArray,int i,int pageSize, String imageName)
			throws IOException, FileNotFoundException, MalformedURLException, WriterException {
		boolean hasNext = false;
		
		File outFile = new File(basePath + imageName +".png");
		if(!outFile.exists()){
			if(!outFile.getParentFile().exists()){
				outFile.getParentFile().mkdirs();
			}
			outFile.createNewFile();
		}
		BufferedImage combined = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = (Graphics2D)combined.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(0,0,width,height);//填充整个屏幕
		OutputStream destStream = new FileOutputStream(outFile);
		int index = 0;
		for(int j=0;j<pageSize;j++){
			JSONObject obj = jsonArray.getJSONObject(i+j);
			hasNext = true;
			Long id = obj.getLong("code");
//			String name = obj.getString("name");
			String qrcodeUrl = baseUrl+id;
			// 二维码的图片格式
			
			Map<EncodeHintType,Object> hints = new HashMap<EncodeHintType,Object>();
			// 内容所使用编码
			hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
			hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(qrcodeUrl, BarcodeFormat.QR_CODE, picW, picW, hints);
			
			BufferedImage sourceImg = MatrixToImageWriter.toBufferedImage(bitMatrix);
			// paint both images, preserving the alpha channels
			int qrX = (index%7) * (picW+pading)+margin;
			int qrY = index/7 * (picW+pading)+margin;
			g.drawImage(sourceImg.getScaledInstance(picW, picW, Image.SCALE_SMOOTH), qrX,qrY, null);
			//画一个文字的背景
			g.setColor(Color.WHITE);
			int ractX = qrX + ((picW - ractW)/2);
			int ractY = qrY + (picW - ractH)/2;
			g.fillRect(ractX,ractY,ractW,ractH);
			g.setColor(Color.BLACK);
			g.setFont(new Font("黑体",800, 24));
			g.drawString(id+"", ractX+18,ractY+28);
			//画一个二维码的边框
		    g.setColor(new Color(229 , 229 , 228));     
			g.drawRect(qrX, qrY, picW, picW);  
			index ++;
		}
		ImageIO.write(combined, "PNG", destStream);
		
		return hasNext;
	}
}
