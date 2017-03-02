package org.bana.common.util.basic;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 两个类的同名复制（属性名相同的属性值被复制，注意如果类型不同，避免使用相同的属性名）
 * @author liuwenjie
 */
public class ClonePojoUtil {
	private static final Logger LOG = LoggerFactory.getLogger(ClonePojoUtil.class);
	
	@Target(ElementType.FIELD)
	@Retention(RetentionPolicy.RUNTIME)
	public @interface Formatter{
		String value() default "yyyy-MM-dd";
	}
	
    /** 
    * @Description: 将前一个类中的对象，按照名字相同，类型相同进行复制
    * @author Liu Wenjie   
    * @date 2013-11-13 下午3:19:30 
    * @param from
    * @param toClass
    * @return  
    */ 
    public static <T>T copyClassFromTo(Object from,Class<T> toClass){
        if(from == null || toClass == null){
            LOG.warn("参数对象有null，转化失败... ...");
            return null;
        }
        Field[] eFields = toClass.getDeclaredFields();
        
        T entity = null;
		try {
			entity = toClass.newInstance();
		} catch (Exception e) {
			LOG.error("实体转换时实例化被转化的对象失败",e);
			throw new RuntimeException("实体转换时实例化被转化的对象失败",e);
		}
        LOG.debug("要转化成的目标类定义中有 " + eFields.length + " 个属性");
        if (arrayIsEmpty(eFields)) {
            LOG.error("转化失败... ...");
            throw new IllegalArgumentException("domain和entity类中存在没有定义的类，或者没有属性的类，无转化的意义，此方法暂时不兼容此种解析");
        }
        for (Field ef : eFields) {
            String eFieldName = ef.getName();
            if(eFieldName.equalsIgnoreCase("serialVersionUID")){
                LOG.debug("序列化的属性serialVersionUID不进行赋值...执行下一步...");
                continue;
            }
            Field df;
			try {
				df = from.getClass().getDeclaredField(eFieldName);
			} catch (Exception e) {
				String info = "原始对象中不包含没有对应的属性" + eFieldName;
				try {
					df = from.getClass().getSuperclass().getDeclaredField(eFieldName);
					LOG.debug(info + "，但是原始对象的父类中包含此属性");
				} catch (Exception e1) {
					LOG.debug(info + "，原始对象的父类中也不包含没有对应的属性");
					continue;
				}
			}
			df.setAccessible(true);
            ef.setAccessible(true);
            try {
            	Object object = df.get(from);
            	if(object == null){
            		Method getMethod = findGetMethod(from, df);
            		if(getMethod != null){
            			object = getMethod.invoke(from);
            		}
            	}
            	
				Method efWriteMethod;
				try {
					PropertyDescriptor efpd = new PropertyDescriptor(ef.getName(), entity.getClass());
					efWriteMethod = efpd.getWriteMethod();
				} catch (Exception e) {
					String setMethodName = "set" + StringUtils.upcaseFirstChar(ef.getName());
					try {
						efWriteMethod = entity.getClass().getDeclaredMethod(setMethodName, ef.getType());
					}catch(Exception e1){
						setMethodName = "set" + ef.getName();
						efWriteMethod = entity.getClass().getDeclaredMethod(setMethodName, ef.getType());
					}
				}
            	if(ef.getType().equals(String.class)){
            		if(object != null){
            			if(object instanceof Date){
            				Date date = (Date)object;
            				//如果原方法是一个时间格式，通过逐渐判断格式化规则，判断一下是否包含一个formatter注解
            				Formatter meta = ef.getAnnotation(Formatter.class);
            				if(meta != null){
            					String format = meta.value(); 
            					efWriteMethod.invoke(entity, DateUtil.toString(date, format));
            				}else{
            					efWriteMethod.invoke(entity, object.toString());
            				}
            			}else{
            				efWriteMethod.invoke(entity, object.toString());
            			}
            		}
				}else if(ef.getType().equals(Date.class)){
					if(object != null){
            			if(object instanceof String){
            				String dateStr = (String)object;
            				//如果原方法是一个时间格式，通过逐渐判断格式化规则，判断一下是否包含一个formatter注解
            				Formatter meta = ef.getAnnotation(Formatter.class);
            				if(meta != null){
            					String format = meta.value();
            					efWriteMethod.invoke(entity, DateUtil.formateToDate(dateStr, format));
            				}else{
            					efWriteMethod.invoke(entity, DateUtil.formateToDate(dateStr));
            				}
            			}else{
            				efWriteMethod.invoke(entity, object);
            			}
            		}
				}else{
					efWriteMethod.invoke(entity, object);
				}
            }  catch (IllegalAccessException ex) {
                LOG.error("反射错误",ex);
            } 
            //catch(IntrospectionException e){
            //	LOG.error("获取java bean的读取和设置方法出现错误",e);
//            } 
            catch (IllegalArgumentException e) {
            	LOG.error("执行设置方法时，设置方法的参数异常",e);
			} catch (InvocationTargetException e) {
				LOG.error("执行设置方法时出现错误",e);
			} catch (NoSuchMethodException e) {
				LOG.error("获取对象的方法是出现错误",e);
			}
            
            df.setAccessible(false);
            ef.setAccessible(false);
        }
        LOG.debug("转化成功... ...");
        return entity;
    }

	private static Method findGetMethod(Object from, Field df) {
		try {
			PropertyDescriptor pd = new PropertyDescriptor(df.getName(), from.getClass());
			Method getMethod = pd.getReadMethod();
			return getMethod;
		} catch (Exception e) {
			String getMethodName = "get" + StringUtils.upcaseFirstChar(df.getName());
			try {
				Method getMethod = from.getClass().getDeclaredMethod(getMethodName, null);
				return getMethod;
			} catch (Exception e1) {
				getMethodName = "get" + df.getName();
				try {
					Method getMethod = from.getClass().getDeclaredMethod(getMethodName, null);
					return getMethod;
				} catch (Exception e2) {
					LOG.warn("没有找到" + df.getName() + "值 对象对应的get方法",e);
					return null;
				}
			}
		}
	}
    
    private static boolean arrayIsEmpty(Object[] objs) {
        return objs == null || objs.length == 0;
    }
    
    
    
}
