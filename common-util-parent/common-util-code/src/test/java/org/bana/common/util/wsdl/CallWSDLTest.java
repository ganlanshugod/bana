/**
* @Company 青鸟软通   
* @Title: CallWSDLTest.java 
* @Package org.bana.common.util.wsdl 
* @author Liu Wenjie   
* @date 2015-1-16 上午11:25:43 
* @version V1.0   
*/ 
package org.bana.common.util.wsdl;

import java.util.Arrays;

import org.apache.cxf.endpoint.Client;
import org.junit.Ignore;
import org.junit.Test;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;     

/** 
 * @ClassName: CallWSDLTest 
 * @Description: 测试直接访问wsdl的方法
 *  
 */
public class CallWSDLTest {
	
	@Test
	@Ignore
	public void testCallUseCXF(){
		String xmlInput = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
				"<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:web=\"http://webservice.framwork.haier.com\">"+
				"  <soapenv:Header/>" + 
				"   <soapenv:Body>" + 
				"      <web:getSystemInfoBycn soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">" + 
				"         <cn xsi:type=\"xsd:string\">00594748</cn>" + 
				"      </web:getSystemInfoBycn>" + 
				"   </soapenv:Body>" + 
				"</soapenv:Envelope>";       
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		String wsUrl = "http://10.135.17.69:7001/web/services/Getsysteminfo?wsdl";
		String method = "getSystemInfoBycn";
		Client client = dcf.createClient(wsUrl);
		Object[] res = null;
		try {
			res = client.invoke(method, xmlInput);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(Arrays.toString(res));
		System.exit(0);
	}
}
