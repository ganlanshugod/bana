/**
 * @Company weipu   
 * @Title: BanaFTPClient.java 
 * @Package org.bana.common.util.file.ftp 
 * @author Liu Wenjie   
 * @date 2015-4-13 下午1:42:18 
 * @version V1.0   
 */
package org.bana.common.util.file.ftp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: BanaFTPClient
 * @Description: 基于apache FTPClient实现的上传下载文件到ftp服务器的方法
 * 
 */
public class BanaFTPClient {

	private static Logger LOG = LoggerFactory.getLogger(BanaFTPClient.class);
	private FTPConnectionFactory ftpConnectionFactory;

	/**
	 * @Description: 保存文件到ftp服务器上
	 * @author Liu Wenjie
	 * @date 2015-4-13 下午1:45:37
	 * @param relativeFileName
	 *            带文件路径的文件 名
	 * @param resourceAsStream
	 *            文件的输入流
	 * @return
	 */
	public boolean storeFile(String relativeFileName, InputStream resourceAsStream) {
		if (StringUtils.isBlank(relativeFileName) || relativeFileName.endsWith("/")) {
			throw new BanaUtilException("relativeFileName " + relativeFileName + " is not avild name");
		}
		try {
			FTPClient connection = ftpConnectionFactory.getConnection();
			boolean makeDirectory = FTPClientUtil.createDirecroty(connection, relativeFileName);
			LOG.info("创建ftp远程文件夹 " + (makeDirectory ? "成功" : " 失败 "));
			// 获取文件夹与文件名的分割位置
			int splitPosition = relativeFileName.lastIndexOf("/");
			// boolean changeWorkingDirectory =
			// connection.changeWorkingDirectory(relativeFileName.substring(0,
			// splitPosition));
			// LOG.info("切换路径到文件夹 " + relativeFileName.substring(0,
			// splitPosition) + (changeWorkingDirectory ? "成功" : " 失败 "));
			String fileName = relativeFileName.substring(splitPosition + 1);
			boolean result = connection.storeFile(fileName, resourceAsStream);
			LOG.info("保存ftp远程文件 " + fileName + (result ? "成功" : " 失败 "));
			return result;
		} catch (IOException e) {
			throw new BanaUtilException(e);
		} finally{
			ftpConnectionFactory.disConnect();
		}
	}

	/**
	* @Description: 使用outputstream 流 下载 ftp服务器上的文件
	* @author Liu Wenjie   
	* @date 2015-4-13 下午3:58:23 
	* @param remoteFilePath
	* @param outputStream 保存下载后文件的输出流
	* @return
	 */
	public boolean loadFile(String remoteFilePath, OutputStream outputStream) {
		try {
			FTPClient connection = ftpConnectionFactory.getConnection();
			boolean result = connection.retrieveFile(remoteFilePath, outputStream);
			LOG.info("从ftp服务器下载文件 " + remoteFilePath  + (result ? "成功" : " 失败 "));
			return result;
		} catch (IOException e) {
			LOG.error("从ftp服务器下载文件 " + remoteFilePath + "失败！", e);
			throw new BanaUtilException("从ftp服务器下载文件 " + remoteFilePath + "失败！" ,e);
		} finally{
			ftpConnectionFactory.disConnect();
		}
	}

	/* =================getter and setter ================ */

	/**
	 * @Description: 属性 ftpConnectionFactory 的set方法
	 * @param ftpConnectionFactory
	 */
	public void setFtpConnectionFactory(FTPConnectionFactory ftpConnectionFactory) {
		this.ftpConnectionFactory = ftpConnectionFactory;
	}

}
