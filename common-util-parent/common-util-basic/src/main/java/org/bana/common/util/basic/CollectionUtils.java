/**
* @Company weipu   
* @Title: CollectionUtils.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-11-29 下午5:33:48 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** 
 * @ClassName: CollectionUtils 
 * @Description: 集合的静态方法
 *  
 */
public class CollectionUtils extends org.apache.commons.collections.CollectionUtils {

	/** 
	* @Description: 返回一个集合中重复的元素
	* @author Liu Wenjie   
	* @date 2015-11-29 下午5:35:56 
	* @param targetList
	* @return  
	*/ 
	public static <T> List<T> findDuplicateList(List<T> targetList){
		if(targetList == null || targetList.isEmpty()){
			return null;
		}
		Set<T> result = new HashSet<T>();
		Set<T> temp = new HashSet<T>();
		for (T t : targetList) {
			if(temp.contains(t)){
				result.add(t);
			}
			temp.add(t);
		}
		return Arrays.asList((T[])result.toArray());
	}
	
	/** 
	* @Description: 把一个long类型的集合转化为Int的集合
	* @author liuwenjie   
	* @date 2016-8-23 下午8:06:28 
	* @param targetList
	* @return  
	*/ 
	public static List<Integer> parseLongList2IntegerList(List<Long> targetList){
		if(targetList == null){
			return null;
		}
		List<Integer> interList = new ArrayList<Integer>();
		for (Long value : targetList) {
			interList.add(value.intValue());
		}
		return interList;
	}
}
