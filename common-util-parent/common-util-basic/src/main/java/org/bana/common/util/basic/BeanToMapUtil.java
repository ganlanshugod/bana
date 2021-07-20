/**
* @Company weipu   
* @Title: BeanTOMapUtil.java 
* @Package com.haier.common.util 
* @author Liu Wenjie   
* @date 2014-3-25 下午6:47:29 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

/** 
 * @ClassName: BeanTOMapUtil 
 * @Description: JavaBean对象与Map对象互相转化
 *  
 */
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.bana.common.util.exception.BanaUtilException;

public class BeanToMapUtil { 
   
    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public static <T>T convertMap(Class<T> type, Map<Object,Object> map) { 
        try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性 
			T obj = type.newInstance(); // 创建 JavaBean 对象 

			// 给 JavaBean 对象的属性赋值 
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
			for (int i = 0; i< propertyDescriptors.length; i++) { 
			    PropertyDescriptor descriptor = propertyDescriptors[i]; 
			    String propertyName = descriptor.getName(); 

			    if (map.containsKey(propertyName)) { 
			        // 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。 
			        Object value = map.get(propertyName); 

			        Object[] args = new Object[1]; 
			        args[0] = value; 

			        descriptor.getWriteMethod().invoke(obj, args); 
			    } 
			} 
			return obj;
		} catch (Exception e) {
			throw new BanaUtilException("Bean converMap error",e);
		} 
    } 

    /** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
    public static Map<String,Object> convertBean(Object bean) { 
        try {
			Class<? extends Object> type = bean.getClass(); 
			Map<String,Object> returnMap = new HashMap<String,Object>(); 
			BeanInfo beanInfo = Introspector.getBeanInfo(type,Object.class); 

			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
			for (int i = 0; i< propertyDescriptors.length; i++) { 
			    PropertyDescriptor descriptor = propertyDescriptors[i]; 
			    String propertyName = descriptor.getName(); 
			    if (!"class".equals(propertyName)) { 
			        Method readMethod = descriptor.getReadMethod(); 
			        Object result = readMethod.invoke(bean, new Object[0]); 
			        if (result != null) { 
			            returnMap.put(propertyName, result); 
			        } else { 
			            returnMap.put(propertyName, null); 
			        } 
			    } 
			} 
			return returnMap;
		} catch (Exception e) {
			throw new BanaUtilException("Map convertBean error",e);
		} 
    } 
    
    
}
