/**
* @Company 青鸟软通   
* @Title: TreeNode.java 
* @Package org.bana.common.util.tree 
* @author Liu Wenjie   
* @date 2015-12-2 下午2:37:29 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

import org.bana.common.util.tree.impl.SimpleTreeNode;


/** 
 * @ClassName: Tree 
 * @Description: 树形集合的顶层接口
 *  
 */
public interface Tree<T> {
	
	/** 
	* @Description: 返回根组织的treeNode集合
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:05:45   
	*/ 
	TreeNodeList<T> getRootTreeNodes();
	
	/** 
	* @Description: 返回树形结构节点的个数
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:04:57 
	* @return  
	*/ 
	int size();
	
	/** 
	* @Description: 根据id获取tree节点的方法
	* @author Liu Wenjie   
	* @date 2015-12-2 下午8:25:24 
	* @param id
	* @return  
	*/ 
	TreeNode<T> findById(Object id);

}
