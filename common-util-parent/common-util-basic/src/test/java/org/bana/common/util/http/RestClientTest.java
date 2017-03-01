/**
* @Company 青鸟软通   
* @Title: RestClientTest.java 
* @Package org.bana.common.util.ws 
* @author Liu Wenjie   
* @date 2015-1-26 上午11:29:55 
* @version V1.0   
*/ 
package org.bana.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/** 
 * @ClassName: RestClientTest 
 * @Description: 测试restClient的使用方法
 *  
 */
public class RestClientTest {

	/**
	 * Test method for {@link org.bana.common.util.http.RestClient#post(java.lang.String, java.util.Map)}.
	 * @throws IOException 
	 */
	@Test
	public void testPost() throws IOException {
//		String url = "http://iyx.haier.net/app/a/login";
//		Map<String,Object> params = new HashMap<String,Object>();
//		params.put("username", "admin");
//		params.put("password", "111111");
//		HttpURLConnection postConn = RestClient.post(url, params);
//		int responseCode = postConn.getResponseCode();
//		
//		System.out.println(responseCode);
//		if(responseCode == 200){
//			String location = postConn.getHeaderField("Location");
//			System.out.println(location);
//			System.out.println(postConn.getContent());
//			System.out.println(postConn.getResponseMessage());
//			InputStream in = postConn.getInputStream();
//			BufferedReader breader = new BufferedReader(new InputStreamReader(in , "utf8"));
//			String str=breader.readLine(); 
//			while(str != null){ 
//				System.out.println(str); 
//				str=breader.readLine(); 
//			} 
//		}else{
//			System.out.println(postConn.getResponseMessage());
//			System.out.println(postConn.getErrorStream());
//		}
	}
	
	/** 
	* @Description: 测试get方法访问
	* @author Liu Wenjie   
	* @date 2015-1-26 上午11:32:16 
	* @throws IOException  
	*/ 
	@Test
	public void testGet() throws IOException{
//		String url = "http://10.135.1.110:7001/EAI/service/HMMS/TransAddMendianErrorToHMMS/TransAddMendianErrorToHMMS";
//		String data = "&id=8700000123&name=你好&longitude=1203873910&latitude=360712680";
		String url = "http://manager.i3618.com.cn/i3618-web/pages/i3618/login.html";
		HttpURLConnection getConn = RestClient.get(url);
		//设置发送请求
		StringBuffer bankXmlBuffer = new StringBuffer();  
		//创建URL连接，提交到银行卡鉴权，获取返回结果  
		getConn.setRequestMethod("GET");  
		getConn.setDoOutput(true);  
		getConn.setRequestProperty("User-Agent", "directclient");  
//		PrintWriter out = new PrintWriter(getConn.getOutputStream());  
//		out.println(data);  
//		out.close();
		//获取返回值
		int responseCode = getConn.getResponseCode();
		System.out.println(responseCode);
		if(responseCode == 200){
			String location = getConn.getHeaderField("Location");
			System.out.println(location);
			System.out.println(getConn.getContent());
			System.out.println(getConn.getResponseMessage());
			InputStream in = getConn.getInputStream();
			BufferedReader breader = new BufferedReader(new InputStreamReader(in , "utf8"));
			String str=breader.readLine(); 
			while(str != null){ 
				System.out.println(str); 
				str=breader.readLine(); 
			} 
		}else{
			System.out.println(getConn.getResponseMessage());
			System.out.println(getConn.getErrorStream());
		}
		
	}
	
	@Test
	public void testGet2() {
//        String content = HttpConnectionUtil.getHttpContent("http://192.168.1.61:8001/");
        String content = getHttpContent("http://manager.i3618.com.cn/i3618-web/pages/i3618/login.html");
        System.out.println("content = " + content);
    }
	
	
	public static String getHttpContent(String url) {
        return getHttpContent(url, "utf8");
    }

    public static String getHttpContent(String url, String charSet) {
        HttpURLConnection connection = null;
        String content = "";
        try {
            URL address_url = new URL(url);
            connection = (HttpURLConnection) address_url.openConnection();
//            connection.setRequestMethod("GET");
            //设置访问超时时间及读取网页流的超市时间,毫秒值
            System.setProperty("sun.net.client.defaultConnectTimeout","30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");

            //after JDK 1.5
//            connection.setConnectTimeout(10000);
//            connection.setReadTimeout(10000);
            //得到访问页面的返回值
            int response_code = connection.getResponseCode();
            if (response_code == HttpURLConnection.HTTP_OK) {
                InputStream in = connection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in,charSet);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in, charSet));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    content+=line;
                }
                return content;
            }else{
            	System.out.println("code is " + response_code);
            	System.out.println("msg" + connection.getResponseMessage());
            	return "";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(connection !=null){
                connection.disconnect();
            }
        }
        return "";
    }

}
