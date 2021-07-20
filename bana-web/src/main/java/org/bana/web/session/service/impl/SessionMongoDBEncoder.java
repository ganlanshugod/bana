/**
* @Company weipu   
* @Title: SessionMongoDBEncoder.java 
* @Package org.bana.web.session.service.impl 
* @author liuwenjie   
* @date 2016-5-11 下午1:44:29 
* @version V1.0   
*/ 
package org.bana.web.session.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.mongodb.DBEncoder;
import com.mongodb.DBEncoderFactory;
import com.mongodb.DefaultDBEncoder;

/** 
 * @ClassName: SessionMongoDBEncoder 
 * @Description: 通过接口往数据库中保存session的时候，用户转化对象的session方法
 *  
 */
public class SessionMongoDBEncoder extends DefaultDBEncoder {

	/**
	* <p>Description: 覆盖防止特殊内容的方法</p> 
	* @author liuwenjie   
	* @date 2016-5-11 下午1:45:53 
	* @param name
	* @param val
	* @return 
	* @see com.mongodb.DefaultDBEncoder#putSpecial(java.lang.String, java.lang.Object) 
	*/ 
	@Override
	protected boolean putSpecial(String name, Object val) {
		//如果之前已经操作过并执行了覆盖，则使用下面的方法进行重新覆盖
		if(super.putSpecial(name, val)){
			return true;
		}else if(val instanceof Serializable){
			//如果对象是可以序列化的方法，则进行序列化操作
			putSerializable(name, (Serializable) val);
			return true;
		}
		return false;
	}

	/** 
	* @Description: 执行序列化的写入操作
	* @author liuwenjie   
	* @date 2016-5-11 下午1:49:21 
	* @param name
	* @param val  
	*/ 
	private void putSerializable(String name, Serializable val) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();  
		try {
			ObjectOutputStream out = new ObjectOutputStream(outputStream);
		    out.writeObject(val);  
		    putBinary(name, outputStream.toByteArray());
		    out.close();  
		    outputStream.close();
		} catch (IOException e) {
			throw new RuntimeException("对象"+ name + " ，类 " + val.getClass() + "序列化存储失败",e);
		}  
	}
	
	public static class SessionMongoDBEncoderFactory implements DBEncoderFactory {
        @Override
        public DBEncoder create( ){
            return new SessionMongoDBEncoder( );
        }

        @Override
        public String toString() {
            return "SessionMongoDBEncoder.SessionMongoDbEncoderFactory";
        }

    }

	/**
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date 2016-5-11 下午2:19:44 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "SessionMongoDBEncoder";
	}
	
	
}
