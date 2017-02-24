/**
* @Company 青鸟软通   
* @Title: FTPClientTest.java 
* @Package org.bana.common.util.file.ftp 
* @author Liu Wenjie   
* @date 2015-4-13 上午11:21:40 
* @version V1.0   
*/ 
package org.bana.common.util.file.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.junit.Test;

/** 
 * @ClassName: FTPClientTest 
 * @Description: 测试apache的ftp上传文件功能
 *  
 */
public class FTPClientTest {
	private String host = "121.42.209.241";
	private String userName = "webftp";
	private String password = "jbinfo";
	private String dirPath = "/i3618/20150413";
	private String fileName = "othername.txt";
	
	@Test
	public void testConnection() throws IOException{
		//链接ftp
		FTPClient ftp = new FTPClient(); 
		ftp.connect(host);
		ftp.login(userName, password);
		//获取文件流
		File file = new File("/file/ftp/test.txt");
		InputStream resourceAsStream = FTPClientTest.class.getResourceAsStream("/file/ftp/test.txt");
		System.out.println("文件是否存在" + file.exists());
		ftp.setControlEncoding("UTF8");  
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);  
        boolean changeWorkingDirectory = ftp.changeWorkingDirectory(dirPath);
        System.out.println(changeWorkingDirectory);
        changeWorkingDirectory = ftp.changeWorkingDirectory("~");
        FTPFile[] listFiles = ftp.listFiles();
        System.out.println(changeWorkingDirectory);
        boolean makeDirectory = FTPClientUtil.createDirecroty(ftp,"i36182/20150415/123/");
        System.out.println(makeDirectory);
        
        ftp.storeFile(fileName, resourceAsStream);
        resourceAsStream.close();
        ftp.disconnect();
	}
	
	
}
