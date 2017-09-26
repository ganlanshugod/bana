/**
 * @Company 青鸟软通   
 * @Title: FTPConnectionFactory.java 
 * @Package org.bana.common.util.file.ftp 
 * @author Liu Wenjie   
 * @date 2015-4-13 下午1:49:55 
 * @version V1.0   
 */
package org.bana.common.util.file.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: FTPConnectionFactory
 * @Description: 获取ftp链接的工厂类
 * 
 */
public class FTPConnectionFactory {

	private static Logger LOG = LoggerFactory.getLogger(FTPConnectionFactory.class);

	private String host = "118.190.61.243";
	private String userName = "webftp";
	private String password = "jbinfo";
	private String basePath = "~";
	private String encoding = "UTF8";
	private int FileType = FTPClient.BINARY_FILE_TYPE;

	/**
	 * 保存当前线程ftp连接的集合
	 */
	private ThreadLocal<FTPClient> ftpCollection = new ThreadLocal<FTPClient>();

	/**
	 * @Description: 返回FTPClient 的工厂类方法
	 * @author Liu Wenjie
	 * @date 2015-4-13 下午1:51:24
	 * @return
	 */
	public FTPClient getConnection() {
		try {
			FTPClient ftpClient = ftpCollection.get();
			if (ftpClient == null) {
				ftpClient = new FTPClient();
				ftpClient.connect(host);
				ftpClient.login(userName, password);
				ftpClient.setControlEncoding(encoding);
				ftpClient.setFileType(FileType);
				ftpCollection.set(ftpClient);
			}
			ftpClient.changeWorkingDirectory(basePath);
			return ftpClient;
		} catch (IOException e) {
			LOG.error("连接ftp服务器失败", e);
			throw new BanaUtilException("连接ftp服务器失败", e);
		}
	}
	
	/**
	* @Description: 管理链接工厂中，当前的链接
	* @author Liu Wenjie   
	* @date 2015-4-13 下午2:20:00 
	* @return
	 */
	public boolean disConnect(){
		FTPClient ftpClient = ftpCollection.get();
		ftpCollection.remove();
		if(ftpClient != null){
			try {
				ftpClient.disconnect();
				return true;
			} catch (IOException e) {
				LOG.error("关闭ftp链接异常", e);
				return false;
			}
		}
		return true;
	}

	/* ================getter and setter =============== */

	/**
	 * @Description: 属性 host 的get方法
	 * @return host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @Description: 属性 host 的set方法
	 * @param host
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @Description: 属性 userName 的get方法
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @Description: 属性 userName 的set方法
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @Description: 属性 password 的get方法
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @Description: 属性 password 的set方法
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @Description: 属性 basePath 的get方法
	 * @return basePath
	 */
	public String getBasePath() {
		return basePath;
	}

	/**
	 * @Description: 属性 basePath 的set方法
	 * @param basePath
	 */
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	/**
	 * @Description: 属性 encoding 的get方法 
	 * @return encoding
	 */
	public String getEncoding() {
		return encoding;
	}

	/**
	 * @Description: 属性 encoding 的set方法 
	 * @param encoding 
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @Description: 属性 fileType 的get方法 
	 * @return fileType
	 */
	public int getFileType() {
		return FileType;
	}

	/**
	 * @Description: 属性 fileType 的set方法 
	 * @param fileType 
	 */
	public void setFileType(int fileType) {
		FileType = fileType;
	}
	
}
