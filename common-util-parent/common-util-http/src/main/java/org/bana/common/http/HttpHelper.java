package org.bana.common.http;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
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
	public static final String HTTP_PUT = "put";
	public static final String HTTP_DELETE = "delete";
	
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
		
		return doHttp(domain, httpPost, null);
	}
	public JSONObject httpPost(String url, Object data, Map<String,String> headerData,boolean returnHeader) {
		return httpPost(url, data, headerData, null,returnHeader);
	}
	public JSONObject httpPost(String url, Object data) {
		return httpPost(url, data, false);
	}
	public JSONObject httpPost(String url, Object data,boolean returnHeader) {
		return httpPost(url, data, null,returnHeader);
	}
	
	private JSONObject doHttp(HttpLogDomain domain, HttpPost httpPost, Function<Object, String> extendHandlerFunc) {
		return doHttp(domain, httpPost, extendHandlerFunc,false);
	}
	
	private JSONObject doHttp(HttpLogDomain domain, HttpRequestBase httpPost, Function<Object, String> extendHandlerFunc, boolean includeHeader) {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(config.getSocketTimeout()).setConnectTimeout(config.getTimeout()).build();
		httpPost.setConfig(requestConfig);
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost,new BasicHttpContext());
			StringResponseHandler handler = new StringResponseHandler();
			String result = handler.handleResponse(response);
			JSONObject parseObject = JSON.parseObject(result);
			//LOG 内容
			if(includeHeader) {
				Header[] allHeaders = response.getAllHeaders();
				parseObject.put("$_headers", allHeaders);
			}
			domain.setResult(result);
			return parseObject;
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
				if(extendHandlerFunc!=null) {
					String paramExtendStr = extendHandlerFunc.apply(domain.getParamData());
					String resultExtendStr = extendHandlerFunc.apply(domain.getResult());
					domain.setParamExtend(paramExtendStr);
					domain.setResultExtend(resultExtendStr);
				}
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			getLOG().saveLog();
		}
	}
	
	// http Get 对应的方法
	
	public JSONObject httpGet(String url) {
		return httpGet(url,false);
	}
	
	public JSONObject httpGet(String url, Map<String,String> headerData) {
		return httpGet(url,headerData,false);
	}
	
	public JSONObject httpGet(String url, boolean includeHeaders) {
		// 记录开始信息内容
		return httpNoBody(url, HTTP_GET, null, includeHeaders);
	}
	
	public JSONObject httpGet(String url, Map<String,String> headerData,boolean includeHeaders) {
		// 记录开始信息内容
		return httpNoBody(url, HTTP_GET, headerData, includeHeaders);
	}
	
	// httpDelete 对应的方法
	
	public JSONObject httpDelete(String url) {
		return httpDelete(url,false);
	}
	
	public JSONObject httpDelete(String url,Map<String,String> headerData) {
		return httpDelete(url,headerData,false);
	}
	
	public JSONObject httpDelete(String url, boolean includeHeaders) {
		// 记录开始信息内容
		return httpNoBody(url, HTTP_DELETE, null, includeHeaders);
	}
	
	public JSONObject httpDelete(String url, Map<String,String> headerData, boolean includeHeaders) {
		// 记录开始信息内容
		return httpNoBody(url, HTTP_DELETE, headerData, includeHeaders);
	}
	
	private JSONObject httpNoBody(String url, String httpMethod, Map<String,String> headerData, boolean includeHeaders) {
		// 记录开始信息内容
		getLOG().logBegin(url, null, httpMethod);
		HttpRequestBase httpBase = null;
		if(HTTP_DELETE.equals(httpMethod)){
			httpBase = new HttpDelete(url);
		}else {
			httpBase = new HttpGet(url);
		}
		if(headerData != null) {
			for (Map.Entry<String, String> oneHead : headerData.entrySet()) {
				httpBase.addHeader(oneHead.getKey(), oneHead.getValue());
			}
		}
//				HttpDelete httpDelete = new HttpDelete(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
				.setSocketTimeout(config.getSocketTimeout()).setConnectTimeout(config.getTimeout()).build();
		httpBase.setConfig(requestConfig);
		try {
			response = httpClient.execute(httpBase, new BasicHttpContext());
			StringResponseHandler handler = new StringResponseHandler();
			String result = handler.handleResponse(response);
			getLOG().getHttpLogDomain().setResult(result);
			JSONObject parseObject = JSON.parseObject(result);
			if(includeHeaders) {
				Header[] allHeaders = response.getAllHeaders();
				// 设置返回值的headers
				parseObject.put("$_headers", allHeaders);
			}
			
			return parseObject;
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
	
	/** 
	* @Description: 增加处理函数
	* @author zhangzhichao   
	* @date Sep 11, 2020 2:09:57 PM 
	* @param url
	* @param data
	* @param headerData
	* @param extendHandlerFunc
	* @return  
	*/ 
	public JSONObject httpPost(String url, Object data, Map<String,String> headerData, Function<Object, String> extendHandlerFunc,boolean returnHeader) {
		HttpLogDomain domain = getLOG().getHttpLogDomain();
		// 构建请求
		HttpPost httpPost = new HttpPost(url);
		
		// 处理参数信心为json格式
		String params = null;
		
		if(headerData != null) {
			for (Map.Entry<String, String> oneHead : headerData.entrySet()) {
				httpPost.addHeader(oneHead.getKey(), oneHead.getValue());
			}
		}
		if(data != null) { // 使用json请求方式
			params = JSON.toJSONString(data);
			StringEntity requestEntity = new StringEntity(params, "utf-8");
			httpPost.setEntity(requestEntity);
			httpPost.addHeader("Content-Type", "application/json");
		}
		// 记录开始信息内容
		getLOG().logBegin(url, params, HTTP_POST);
		
		return doHttp(domain, httpPost, extendHandlerFunc,returnHeader);
	}
	
	
	public JSONObject httpPut(String url, Object data, Map<String,String> headerData,boolean returnHeader) {
		return httpPut(url, data, headerData, null,returnHeader);
	}
	public JSONObject httpPut(String url, Object data) {
		return httpPut(url, data, false);
	}
	public JSONObject httpPut(String url, Object data,boolean returnHeader) {
		return httpPut(url, data, null,returnHeader);
	}
	
	public JSONObject httpPut(String url, Object data, Map<String,String> headerData, Function<Object, String> extendHandlerFunc,boolean returnHeader) {
		HttpLogDomain domain = getLOG().getHttpLogDomain();
		// 构建请求
		HttpPut httpPut = new HttpPut(url);
		
		// 处理参数信心为json格式
		String params = null;
		
		if(headerData != null) {
			for (Map.Entry<String, String> oneHead : headerData.entrySet()) {
				httpPut.addHeader(oneHead.getKey(), oneHead.getValue());
			}
		}
		if(data != null) { // 使用json请求方式
			params = JSON.toJSONString(data);
			StringEntity requestEntity = new StringEntity(params, "utf-8");
			httpPut.setEntity(requestEntity);
			httpPut.addHeader("Content-Type", "application/json");
		}
		// 记录开始信息内容
		getLOG().logBegin(url, params, HTTP_PUT);
		
		return doHttp(domain, httpPut, extendHandlerFunc,returnHeader);
	}
	

	public HttpConfig getConfig() {
		return config;
	}

	public void setConfig(HttpConfig config) {
		this.config = config;
	}
	
}
