package org.bana.common.http;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.bana.common.http.log.HttpLogDomain;
import org.bana.common.http.log.HttpLogger;
import org.bana.common.http.log.HttpLoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName: HttpHelper
 * @Description: 重新封装HttpHelper方法
 * @author Liu Wenjie
 */
public class HttpHelper {

	public static final String HTTP_GET = "get";
	public static final String HTTP_POST = "post";
	
	private static HttpLogger LOG;
	
	private HttpConfig config;
	
	/** 
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Jul 22, 2020 10:16:58 AM  
	*/ 
	public HttpHelper() {
		config = new HttpConfig();
	}

	/** 
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Jul 22, 2020 10:16:49 AM 
	* @param config 
	*/ 
	public HttpHelper(HttpConfig config) {
		this.config = config;
	}

	public static HttpLogger getLOG() {
		if(LOG == null) {
			LOG = HttpLoggerFactory.getHttpLogger();
		}
		return LOG;
	}
	
	public JSONObject httpParamPost(String url,Map<String,String> data) {
		HttpLogDomain domain = getLOG().getHttpLogDomain();
		// 构建请求
		HttpPost httpPost = new HttpPost(url);
		
		// 处理参数信心为json格式
		String params = null;
		if(data != null) {
			params = JSON.toJSONString(data);
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> param : data.entrySet()) {
				BasicNameValuePair basicNameValuePair = new BasicNameValuePair(param.getKey(), param.getValue());
				list.add(basicNameValuePair);
			}
			UrlEncodedFormEntity formEntity;
			try {
				formEntity = new UrlEncodedFormEntity(list, "UTF-8");
				// 第一步：通过setEntity 将我们的entity对象传递过去
				httpPost.setEntity(formEntity);
				httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
			} catch (UnsupportedEncodingException e) {
				getLOG().logException(e);
				throw new BanaHttpException("500",e.getMessage(),e);
			}
		}
		getLOG().logBegin(url, params, HTTP_POST);
		
		return doHttp(domain, httpPost);
	}
	public JSONObject httpPost(String url, Object data, Map<String,String> headerData) {
		HttpLogDomain domain = getLOG().getHttpLogDomain();
		// 构建请求
		HttpPost httpPost = new HttpPost(url);
		
		// 处理参数信心为json格式
		String params = null;
		if(data != null) { // 使用json请求方式
			params = JSON.toJSONString(data);
			StringEntity requestEntity = new StringEntity(params, "utf-8");
			httpPost.setEntity(requestEntity);
			httpPost.addHeader("Content-Type", "application/json");
			if(headerData != null) {
				for (Map.Entry<String, String> oneHead : headerData.entrySet()) {
					httpPost.addHeader(oneHead.getKey(), oneHead.getValue());
				}
			}
		}
		// 记录开始信息内容
		getLOG().logBegin(url, params, HTTP_POST);
		
		return doHttp(domain, httpPost);
	}
	public JSONObject httpPost(String url, Object data) {
		return httpPost(url, data, null);
	}

	private JSONObject doHttp(HttpLogDomain domain, HttpPost httpPost) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(config.getSocketTimeout()).setConnectTimeout(config.getTimeout()).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost,new BasicHttpContext());
			StringResponseHandler handler = new StringResponseHandler();
			String result = handler.handleResponse(response);
			//LOG 内容
			domain.setResult(result);
			return JSON.parseObject(result);
		} catch (IOException e) {
			getLOG().logException(e);
			throw new BanaHttpException("500",e.getMessage(),e);
		} finally {
			getLOG().logEnd();
			try {
				if(response != null){
					domain.setStatusCode(String.valueOf(response.getStatusLine().getStatusCode()));
					response.close();
	//				domain.setHttpMessage(String.valueOf(response.getStatusLine()));
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			getLOG().saveLog();
		}
	}
	
	
	public JSONObject httpGet(String url) {
		// 记录开始信息内容
		getLOG().logBegin(url, null, HTTP_POST);
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(config.getSocketTimeout()).setConnectTimeout(config.getTimeout()).build();
		httpGet.setConfig(requestConfig);
		try {
			response = httpClient.execute(httpGet, new BasicHttpContext());
			StringResponseHandler handler = new StringResponseHandler();
			String result = handler.handleResponse(response);
			getLOG().getHttpLogDomain().setResult(result);
			return JSON.parseObject(result);
		} catch (IOException e) {
			getLOG().logException(e);
			throw new BanaHttpException("500",e.getMessage(),e);
		} finally {
			getLOG().logEnd();
			if (response != null) {
				getLOG().getHttpLogDomain().setStatusCode(String.valueOf(response.getStatusLine().getStatusCode()));
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			getLOG().saveLog();
		}
	}
	
	
	public JSONObject uploadMedia(String url, File file){
		// 记录开始信息内容
		getLOG().logBegin(url, null, HTTP_GET);
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(config.getSocketTimeout())
				.setConnectTimeout(config.getTimeout()).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder
				.create()
				.addPart(
						"media",
						new FileBody(file,
								ContentType.APPLICATION_OCTET_STREAM, file
										.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());
			StringResponseHandler handler = new StringResponseHandler();
			String result = handler.handleResponse(response);
			getLOG().getHttpLogDomain().setResult(result);
			return JSON.parseObject(result);
		} catch (IOException e) {
			getLOG().logException(e);
			throw new BanaHttpException("500",e.getMessage(),e);
		} finally {
			getLOG().logEnd();
			if (response != null) {
				getLOG().getHttpLogDomain().setStatusCode(String.valueOf(response.getStatusLine().getStatusCode()));
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			getLOG().saveLog();
		}
	}

	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}
	
}
