package org.bana.common.http;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.util.EntityUtils;

/**
 * @ClassName: StringResponseHandler
 * @Description: 
 * @author Liu Wenjie
 */
public class StringResponseHandler extends BasicResponseHandler {

	private String encoding = "utf-8";
	
	/**
	 * <p>Description: </p>
	 * @author Liu Wenjie
	 * @date 2018年5月15日 下午1:53:50
	 */
	public StringResponseHandler() {
		this.encoding = "utf-8";
	}
	
	public StringResponseHandler(String encoding) {
		this.encoding = encoding;
	}


	/**
	 * <p>Description: </p>
	 * @author Liu Wenjie
	 * @date 2018年5月15日 下午1:53:21
	 * @param entity
	 * @return
	 * @throws IOException
	 * @see org.apache.http.impl.client.BasicResponseHandler#handleEntity(org.apache.http.HttpEntity)
	 */
	@Override
	public String handleEntity(HttpEntity entity) throws IOException {
		return EntityUtils.toString(entity,encoding);
	}
}
