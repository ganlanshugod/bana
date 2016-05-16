/**
 * @Company 青鸟软通   
 * @Title: FTPClientUtil.java 
 * @Package org.bana.common.util.file.ftp 
 * @author Liu Wenjie   
 * @date 2015-4-13 下午1:32:09 
 * @version V1.0   
 */
package org.bana.common.util.file.ftp;

import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName: FTPClientUtil
 * @Description: apache的ftp客户端对应的工具类
 * 
 */
public class FTPClientUtil {

	private static Logger LOG = LoggerFactory.getLogger(FTPClientUtil.class);

	public static boolean createDirecroty(FTPClient ftp, String remote) {
		try {

			boolean success = true;
			String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
			// 如果远程目录不存在，则递归创建远程服务器目录
			if (!directory.equalsIgnoreCase("/")
					&& !ftp.changeWorkingDirectory(new String(directory))) {

				int start = 0;
				int end = 0;
				if (directory.startsWith("/")) {
					start = 1;
				} else {
					start = 0;
				}
				end = directory.indexOf("/", start);
				while (true) {
					String subDirectory = new String(remote.substring(start,
							end));
					if (!ftp.changeWorkingDirectory(subDirectory)) {
						if (ftp.makeDirectory(subDirectory)) {
							ftp.changeWorkingDirectory(subDirectory);
						} else {
							LOG.error("创建目录 " + subDirectory + " 失败");
							success = false;
							return success;
						}
					}
					start = end + 1;
					end = directory.indexOf("/", start);
					// 检查所有目录是否创建完毕
					if (end <= start) {
						break;
					}
				}
			}
			return success;
		} catch (IOException e) {
			throw new BanaUtilException("ftp服务器上创建 " + remote + "的所在文件夹失败",e);
		}
	}
}
