/**
* @Company 青鸟软通   
* @Title: StatusExposingServletResponse2.java 
* @Package org.bana.web.http 
* @author Liu Wenjie   
* @date 2015-5-26 下午8:12:26 
* @version V1.0   
*/ 
package org.bana.web.http;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

/** 
 * @ClassName: StatusExposingServletResponse2 
 * @Description: 重载一个httpResponse，为了方便获取statuscode
 *  
 */
public class StatusExposingServletResponse extends HttpServletResponseWrapper {

    private int httpStatus;
    private String errorMessage;

    public StatusExposingServletResponse(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void sendError(int sc) throws IOException {
        httpStatus = sc;
        super.sendError(sc);
    }

    @Override
    public void sendError(int sc, String msg) throws IOException {
        httpStatus = sc;
        errorMessage = msg;
        super.sendError(sc, msg);
    }


    @Override
    public void setStatus(int sc) {
        httpStatus = sc;
        super.setStatus(sc);
    }

    public int getStatus() {
        return this.httpStatus;
    }
    
    public String getMessage(){
    	return this.errorMessage;
    }

}
