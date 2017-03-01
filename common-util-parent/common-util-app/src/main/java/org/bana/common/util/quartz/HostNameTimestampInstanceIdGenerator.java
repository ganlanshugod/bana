/**
 * @Company 青鸟软通   
 * @Title: test.java 
 * @Package org.bana.common.util.quartz 
 * @author Han Tongyang   
 * @date 2015-11-4 上午9:54:15 
 * @version V1.0   
 */
package org.bana.common.util.quartz;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.quartz.SchedulerException;
import org.quartz.spi.InstanceIdGenerator;

/**
 * 根据主机名称和时间节点生成instance id字符串，主机名和时间戳之间默认以'#'分隔
 * 如计算名为TAOPSIBNBA00165，时间为2011-01-
 * 01-12-34-123则instanceid为TAOPSIBNBA00165#2011-01-01-12-34-123+0800#123223,
 * 默认时间格式为yyyy-MM-dd-HH-mm-ss-SSSZ
 * 
 */
public class HostNameTimestampInstanceIdGenerator implements
		InstanceIdGenerator {
	private static final String DEFAULT_SEPARATOR = "#";
	private SimpleDateFormat defaultDateFormat = new SimpleDateFormat(
			"yyyy-MM-dd-HH-mm-ss-SSSZ");

	@Override
	public String generateInstanceId() throws SchedulerException {
		try {
			return InetAddress.getLocalHost().getHostName()
					+ DEFAULT_SEPARATOR
					+ defaultDateFormat.format(new Date(System
							.currentTimeMillis())) + DEFAULT_SEPARATOR
					+ UUID.randomUUID().toString().hashCode()
					+ DEFAULT_SEPARATOR;
		} catch (Exception e) {
			throw new SchedulerException("Couldn't get host name!", e);
		}
	}
}
