/**
* @Company weipu   
* @Title: MongoDBTransactionFacadeCglib.java 
* @Package org.bana.mongodb 
* @author Liu Wenjie   
* @date 2015-1-23 下午1:37:28 
* @version V1.0   
*/
package org.bana.common.util.mongodb;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DB;
import com.mongodb.DBApiLayer;
import com.mongodb.DBCollection;

/**
 * @ClassName: MongoDBTransactionFacadeCglib
 * @Description: MongoDB的代理
 * 
 */
public class MongoDBTransactionFacadeCglib implements MethodInterceptor {

    private static final Logger LOG = LoggerFactory.getLogger(MongoDBTransactionFacadeCglib.class);
    /**
     * @Fields target : 实际执行业务方法的实例对象
     */
    private Object target;

    public static final String METHOD_REGEX = "^(insert|save|update|remove).*";

    /**
     * 创建代理对象
     * 
     * @param target
     * @param colName
     * @param db
     * @return
     */
    public DBCollection getInstance(DBCollection target, DB db, String colName) {
        this.target = target;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        // 回调方法
        enhancer.setCallback(this);
        // 设置参数
        Class[] classes = new Class[2];
        classes[0] = DBApiLayer.class;
        classes[1] = String.class;

        Object[] objs = new Object[2];
        objs[0] = db;
        objs[1] = colName;
        // 创建代理对象
        return (DBCollection) enhancer.create(classes, objs);
    }

    /**
     * <p>
     * Description:
     * </p>
     * 
     * @author Liu Wenjie
     * @date 2015-1-23 下午1:53:26
     * @param obj
     * @param method
     * @param args
     * @param proxy
     * @return
     * @throws Throwable
     * @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object,
     *      java.lang.reflect.Method, java.lang.Object[],
     *      net.sf.cglib.proxy.MethodProxy)
     */
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (method.getName().matches(METHOD_REGEX)) {
            LOG.debug("事物开始" + method.getName());
        }
        Object invokeSuper = proxy.invokeSuper(obj, args);
        if (method.getName().matches(METHOD_REGEX)) {
            LOG.debug("事物结束" + method.getName());
        }
        return invokeSuper;
    }

}
