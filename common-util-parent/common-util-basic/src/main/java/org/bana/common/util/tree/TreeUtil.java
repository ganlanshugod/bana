/**
* @Company weipu   
* @Title: TreeUtil.java 
* @Package org.bana.common.util.basic 
* @author Liu Wenjie   
* @date 2015-12-2 上午11:14:21 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

import java.util.List;

import org.bana.common.util.tree.impl.SimpleTree;

/** 
 * @ClassName: TreeUtil 
 * @Description: 构建树型结构和遍历的工具类
 *  
 */
public class TreeUtil {
	
	/** 
	* @Description: 根据list集合生成一个可以进行树形结构遍历的对象
	* @author Liu Wenjie   
	* @date 2015-12-2 下午2:40:17 
	* @param treeList
	* @return  
	*/ 
	public static <T> Tree<T> getTree(List<T> treeList,String idName,String parentIdName){
		if(treeList == null){
			return null;
		}
		//判断idName和parentIdName的属性值
		SimpleTree<T> tree = new SimpleTree<T>();
		for (T t : treeList) {
			tree.pushNode(t,idName,parentIdName);
		}
		return tree;
	}
	
}
