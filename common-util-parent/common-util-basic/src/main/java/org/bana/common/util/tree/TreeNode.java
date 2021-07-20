/**
* @Company weipu   
* @Title: TreeNode.java 
* @Package org.bana.common.util.tree 
* @author Liu Wenjie   
* @date 2015-12-2 下午2:54:48 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

import java.io.Serializable;


/** 
 * @ClassName: TreeNode 
 * @Description: 树的节点对象
 *  
 */
public interface TreeNode<T> extends Serializable{

	/** 
	* @Description: 返回当前节点的id
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:27:03 
	* @return  
	*/ 
	Object getId();
	
	/** 
	* @Description: 返回当前节点的父级节点方法
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:27:25 
	* @return  
	*/ 
	Object getParentId();
	
	
	/** 
	* @Description: 获取当前节点保存的对象
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:43:18 
	* @return  
	*/ 
	T getThisData();
	
	
	/** 
	* @Description: 返回子集合的TreeNode集合
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:28:46 
	* @return  
	*/ 
	TreeNodeList<T> getChildrenList();
	
}
