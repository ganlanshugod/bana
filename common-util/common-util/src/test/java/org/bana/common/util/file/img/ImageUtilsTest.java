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

import java.io.File;

import org.junit.Test;

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

}
