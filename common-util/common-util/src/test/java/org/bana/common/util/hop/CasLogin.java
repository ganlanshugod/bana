package org.bana.common.util.hop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.security.sasl.AuthenticationException;


public class CasLogin {

private static final Logger LOGGER = Logger.getLogger(CasLogin.class.getName());
	
	public static String getServiceTicket(String username, String password, String casUrl,String serviceUrl) throws IOException {
		// get TGT
        String location = getTicketGrantingTicket(username, password,casUrl);
        
        // get SGT
        return getServiceGrantingTicket(location, serviceUrl);
		
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getServiceTicket("A0000552", "Jbinfo123", "http://10.135.7.58:8686/cas/v1/tickets", "http://10.135.13.137:7013/index.jsp"));
	}
	
	/**
	 * With the TGT location and service url this will get the SGT
	 * @param tgtLocation
	 * @param serviceUrl
	 * @return
	 * @throws IOException
	 */
	private static String getServiceGrantingTicket(String tgtLocation, String serviceUrl) throws IOException {
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("service", serviceUrl);
        params.put("method", "POST");
        
        
        HttpURLConnection conn = RestClient.post(tgtLocation, params);
        StringBuilder responseBuilder = new StringBuilder();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String input;
		while ((input = in.readLine()) != null) {
			responseBuilder.append(input);
		}
		in.close();
		
		String response = responseBuilder.toString();
		LOGGER.info("SGT -> " + response);
		 
		return response;
	}
	
	/**
	 * Gets the TGT for the given username and password
	 * @param username
	 * @param password
	 * @return
	 * @throws IOException
	 */
	private static String getTicketGrantingTicket(String username, String password,String casUrl) throws IOException {
		Map<String,Object> params = new LinkedHashMap<String,Object>();
		params.put("username", username);
		params.put("password", password);
		HttpURLConnection conn = RestClient.post(casUrl, params);
		
        if(conn.getResponseCode() == 400) {
        	throw new AuthenticationException("bad username or password");
        }
        String location = conn.getHeaderField("Location");
        LOGGER.info("TGT LOCATION -> " + location);
        return location;
	}
}
