/**
 * @Company 青鸟软通   
 * @Title: RestClient.java 
 * @Package org.bana.common.util.ws 
 * @author Liu Wenjie   
 * @date 2015-1-26 上午11:22:16 
 * @version V1.0   
 */
package org.bana.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

/**
 * @ClassName: RestClient
 * @Description: 远程调用 Restful 类型的接口 客户端
 * 
 */
public class RestClient {
	private static final Logger LOG = Logger.getLogger(RestClient.class.getName());

	/**
	 * @Description: 获取一个http访问的链接
	 * @author Liu Wenjie
	 * @date 2015-1-26 上午11:25:08
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static HttpURLConnection get(String url) throws IOException {
		LOG.info("Getting from url '" + url + "'");

		URL connectionUrl = new URL(url);
		return (HttpURLConnection) connectionUrl.openConnection();

	}

	/**
	 * Helper Method to post data to the given url and with the given params
	 * 
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public static HttpURLConnection post(String url, Map<String, Object> params) throws IOException {
		LOG.info("Posting to url '" + url + "' w/ params '" + params.toString() + "'");
		
		URL connectionUrl = new URL(url);
		byte[] postDataBytes = convertParamMapToBytes(params);
		HttpURLConnection conn = (HttpURLConnection) connectionUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
//		 conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//		conn.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
//		conn.setDoOutput(true);
		OutputStream out = conn.getOutputStream();
		out.write(postDataBytes);
		out.flush();
		out.close();
		return conn;
	}

	/**
	 * Helper method to convert a map to POST bytes
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static byte[] convertParamMapToBytes(Map<String, Object> params) throws UnsupportedEncodingException {
		StringBuilder postData = new StringBuilder();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			String stringValue = String.valueOf(param.getValue());
			if (!StringUtils.isBlank(stringValue) && !"null".equalsIgnoreCase(stringValue)) {
				// postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				// postData.append('=');
				// postData.append(URLEncoder.encode(String.valueOf(param.getValue()),
				// "UTF-8"));
				if (postData.length() != 0) {
					postData.append('&');
				}
				postData.append(param.getKey());
				postData.append('=');
				postData.append(String.valueOf(param.getValue()));
			}
		}
		return postData.toString().getBytes();
	}

	/**
	 * @Description: 获取链接内容中的字符串返回结果
	 * @author Liu Wenjie
	 * @date 2015-5-9 下午3:51:23
	 * @param conn
	 * @return
	 * @throws IOException
	 */
	public static String getConnectionResponse(HttpURLConnection conn) throws IOException {
		//LOG.info("状态"+conn.getResponseCode() + ",信息" + conn.getResponseMessage());
//		conn.getOutputStream().close();
		StringBuilder responseBuilder = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String input;
		while ((input = in.readLine()) != null) {
			responseBuilder.append(input).append("\r\n");
		}
		in.close();
//		conn.disconnect();
		String response = responseBuilder.toString();
		return response;
	}

	/** 
	* @Description: 指定请求数据内容的post请求
	* @author Liu Wenjie   
	* @date 2015-5-9 下午9:03:29 
	* @param url
	* @param postData
	* @return  
	*/ 
	public static HttpURLConnection post(String url, String postData) throws IOException{
		LOG.info("Posting to url '" + url + "' w/ params '" + postData + "'");

		URL connectionUrl = new URL(url);
		byte[] postDataBytes = postData.getBytes();
		HttpURLConnection conn = (HttpURLConnection) connectionUrl.openConnection();
		conn.setRequestMethod("POST");
		conn.setUseCaches(false);
//		 conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
//		 conn.setRequestProperty("Content-Length",String.valueOf(postDataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(postDataBytes);
		conn.getOutputStream().flush();
		return conn;
	}
}
