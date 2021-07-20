/**
* @Company weipu   
* @Title: FileUtilTest.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2014-10-25 上午9:44:19 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.io.File;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/** 
 * @ClassName: FileUtilTest 
 * @Description: 文件生成工具的测试方法 
 *  
 */
//@Ignore
public class FileUtilTest {
	private File targetFile;
	
	@Before
	public void before(){
		targetFile = new File("D:/test/fileUtilTest.txt");
	}

	/**
	 * Test method for {@link org.bana.common.util.basic.FileUtil#generateFile(java.io.File, java.lang.String)}.
	 */
	@Test
	public void testGenerateFile() {
		String fileContent = "测试新增文件内容的问题。\n\r换行后添加的内容";
		FileUtil.generateFile(targetFile, fileContent);
	}

	/**
	 * Test method for {@link org.bana.common.util.basic.FileUtil#appendFile(java.io.File, java.lang.String)}.
	 */
	@Test
	public void testAppendFile() {
		String fileContent = "测试追加文件内容的问题。\n\r换行后添加的内容";
		FileUtil.appendFile(targetFile, fileContent);
	}
	
	@Test
	public void testUrl(){
		System.out.println(targetFile.getPath());
		System.out.println(FileUtilTest.class.getResource("/xml/test/note.xml"));
	}

}
