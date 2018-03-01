package org.bana.common.util.office.impl.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface TitleRow {

	/**
	 * 设置默认的titleIndex起始位置
	 * @return
	 */
	int titleIndex() default -1;
	
	/**
	 * 标题行样式
	 * @return
	 */
	String style() default "fontWeight:bold;";
	
	/**
	 * 表格上是否有动态列
	 */
	boolean mutiTitle() default true;
	
	/**
	 * 序号的列名称
	 */
	String indexName() default "";
	
	/**
	 * 序号的列的顺序值
	 * @return
	 */
	int indexSort() default 0;
}
