/**
* @Company 青鸟软通   
* @Title: SimpleTreeNode.java 
* @Package org.bana.common.util.tree.impl 
* @author Liu Wenjie   
* @date 2015-12-2 下午2:56:45 
* @version V1.0   
*/ 
package org.bana.common.util.tree.impl;

import org.bana.common.util.tree.TreeNode;
import org.bana.common.util.tree.TreeNodeList;


/** 
 * @ClassName: SimpleTreeNode 
 * @Description: 一个简单的树形结构节点
 *  
 */
public class SimpleTreeNode<T> implements TreeNode<T> {

	/** 
	* @Fields id : id值
	*/ 
	private Object id;
	/** 
	* @Fields parentId : 父级id值
	*/ 
	private Object parentId;
	
	/** 
	* @Fields data : 当前值字段
	*/ 
	private T thisData;
	
	/** 
	* @Fields childrenList : 子集合
	*/ 
	private TreeNodeList<T> childrenList = new TreeNodeList<T>();
	
	
	/** 
	* @Description: 当前节点增加一个子元素
	* @author Liu Wenjie   
	* @date 2015-12-2 下午6:29:06 
	* @param child  
	*/ 
	public void addChild(SimpleTreeNode<T> child){
		childrenList.add(child);
	}
	
	/** 
	* @Description: 从当前元素上删除指定的子元素
	* @author Liu Wenjie   
	* @date 2015-12-2 下午6:45:00 
	* @param child  
	*/ 
	public void removeChild(SimpleTreeNode<T> child){
		childrenList.remove(child);
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:34:32 
	* @return 
	* @see org.bana.common.util.tree.TreeNode#getId() 
	*/ 
	@Override
	public Object getId() {
		return this.id;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:34:32 
	* @return 
	* @see org.bana.common.util.tree.TreeNode#getParentId() 
	*/ 
	@Override
	public Object getParentId() {
		return this.parentId;
	}
	
	/**
	 * @Description: 属性 id 的set方法 
	 * @param id 
	 */
	public void setId(Object id) {
		this.id = id;
	}

	/**
	 * @Description: 属性 parentId 的set方法 
	 * @param parentId 
	 */
	public void setParentId(Object parentId) {
		this.parentId = parentId;
	}

	/**
	 * @Description: 属性 thisData 的get方法 
	 * @return thisData
	 */
	public T getThisData() {
		return thisData;
	}

	/**
	 * @Description: 属性 thisData 的set方法 
	 * @param thisData 
	 */
	public void setThisData(T thisData) {
		this.thisData = thisData;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:34:32 
	* @return 
	* @see org.bana.common.util.tree.TreeNode#getChildrenList() 
	*/ 
	@Override
	public TreeNodeList<T> getChildrenList() {
		return childrenList;
	}

	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午7:28:20 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "SimpleTreeNode [thisData=" + thisData + "]";
	}

}
