package org.bana.common.util.office.impl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置excel的列字段
 * @author Liu Wenjie
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelColumn {
	
	/**
	 * 当前列在数据中的顺序
	 * @return
	 */
	int index() default -1;

	/**
	 * 当前列占据的宽度
	 * @return
	 */
	int colspan() default 1;
	
	
	/**
	 * 列名
	 * @return
	 */
	String name();
	
	/**
	 * 配置样式
	 * @return
	 */
	String style() default "";
	
	/**
	 * 动态列配置中对应的key值
	 * @return
	 */
	String mutiMap() default "";
	
	/**
	 * 使用数据字典匹配数据
	 * @return
	 */
	boolean useDic() default false;
	
	/**
	 * 数据字典的key值
	 * @return
	 */
	String dicType() default "";
}
