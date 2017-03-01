/**
 * @Company 青鸟软通   
 * @Title: TestFacadeProxy2.java 
 * @Package org.bana.common.util.proxy.cglib 
 * @author Liu Wenjie   
 * @date 2015-1-23 下午4:04:07 
 * @version V1.0   
 */
package org.bana.common.util.proxy.cglib;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName: TestFacadeProxy2
 * @Description:
 * 
 */
public class TestFacadeProxy2 implements InvocationHandler {

	private Object target;

	public Object bind(Object target) {
		this.target = target;
		// 取得代理对象
		Object obj =  Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this); // 要绑定接口(这是一个缺陷，cglib弥补了这一缺陷)
		
		return obj;
	}

	/**
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @author Liu Wenjie
	 * @date 2015-1-23 下午4:04:35
	 * @param proxy
	 * @param method
	 * @param args
	 * @return
	 * @throws Throwable
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object,
	 *      java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object result = null;
		System.out.println("事物开始");
		// 执行方法
		result = method.invoke(target, args);
		System.out.println("事物结束");
		return result;
	}

}
