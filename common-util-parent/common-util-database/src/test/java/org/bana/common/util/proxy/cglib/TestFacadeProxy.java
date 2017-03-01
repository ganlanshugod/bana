/**
* @Company 青鸟软通   
* @Title: TestFacadeProxy.java 
* @Package org.bana.common.util.proxy.cglib 
* @author Liu Wenjie   
* @date 2015-1-23 下午3:03:45 
* @version V1.0   
*/ 
package org.bana.common.util.proxy.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/** 
 * @ClassName: TestFacadeProxy 
 * @Description: 
 *  
 */
public class TestFacadeProxy implements  MethodInterceptor{

	/** 
	* @Fields target : 实际执行业务方法的实例对象
	*/ 
	private Object target;   
	/** 
     * 创建代理对象 
     *  
     * @param target 
     * @return 
     */  
	public <T>T getInstance(T target,Class[] classes,Object[] obj) {  
        this.target = target;  
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());  
        // 回调方法  
        enhancer.setCallback(this);
        
//        this.target.getClass().getMethod("sayHello", parameterTypes);
        // 创建代理对象 
        if(classes == null){
        	return (T)enhancer.create();
        }
        return (T)enhancer.create(classes,obj);
    }  
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-1-23 下午3:05:44 
	* @param obj
	* @param method
	* @param args
	* @param proxy
	* @return
	* @throws Throwable 
	* @see net.sf.cglib.proxy.MethodInterceptor#intercept(java.lang.Object, java.lang.reflect.Method, java.lang.Object[], net.sf.cglib.proxy.MethodProxy) 
	*/ 
	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		System.out.println("事物开始");  
        Object invokeSuper = proxy.invokeSuper(obj, args); 
        System.out.println("事物结束");
        return invokeSuper;
	}

}
