/**
* @Company weipu
* @Title: AreaUrlConfig.java 
* @Package org.bana.common.util.area 
* @author liuwenjie   
* @date Jun 15, 2020 2:10:15 PM 
* @version V1.0   
*/ 
package org.bana.common.util.area;

import org.bana.common.util.exception.BanaUtilException;

/** 
* @ClassName: AreaUrlConfig 
* @Description: 
* @author liuwenjie   
*/
public class AreaUrlConfig {
	private static String baseUrl = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/";
	private int level;
	private String parentCode;

	public static AreaUrlConfig parse(String url) {
		if (url.indexOf(baseUrl) == -1) {
			throw new BanaUtilException("不合法的爬取链接地址，不能正确解析：" + url);
		}
		String[] split = url.split(baseUrl);
		String infoUrl = split[split.length - 1];
		String[] split2 = infoUrl.split("/");
		AreaUrlConfig config = new AreaUrlConfig();
		config.level = split2.length;
		if (config.level == 1) {
			config.parentCode = "0";
		} else {
			config.parentCode = split2[split2.length - 1].split("\\.")[0];
		}
		return config;
	}
	
	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}


	public static void main(String[] args) {
		String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/13/04/33/130433203.html";
//		String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2019/13.html";
		AreaUrlConfig parse = AreaUrlConfig.parse(url);
		System.out.println(parse.level);
		System.out.println(parse.parentCode);
	}
}
