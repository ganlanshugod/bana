package org.bana.common.util.office.impl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Liu Wenjie
 * 指定对应的sheet对象配置
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sheet {
	
	/**
	 * @return 默认处理的sheet index值
	 */
	int index() default 0;
	
	/**
	 * @return 指定Sheet配置也的名称
	 */
	String name() default "";
	
	/**
	 * @return 样式配置对象
	 */
	String style() default "border:all;fontName:微软雅黑;";
	
	/**
	 * 是否验证表头
	 */
	boolean checkTitle() default true;
}
