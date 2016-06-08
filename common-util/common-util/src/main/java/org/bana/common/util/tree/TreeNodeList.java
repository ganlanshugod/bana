/**
* @Company 青鸟软通   
* @Title: TreeNodeList.java 
* @Package org.bana.common.util.tree 
* @author Liu Wenjie   
* @date 2015-12-2 下午3:10:20 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


/** 
 * @ClassName: TreeNodeList 
 * @Description: 树形节点的集合
 *  
 */
public class TreeNodeList<T> implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -8841287820482599880L;
	/** 
	* @Fields nodeList : 保存节点的集合方法
	*/ 
	private List<TreeNode<T>> nodeList = new ArrayList<TreeNode<T>>();

	/** 
	* @Description:返回节点集合的大小
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:22:48 
	* @return  
	*/ 
	public int size() {
		return nodeList.size();
	}
	
	/** 
	* @Description: 返回是否为空的判断
	* @author Liu Wenjie   
	* @date 2015-12-2 下午7:46:36 
	* @return  
	*/ 
	public boolean isEmpty(){
		return size() == 0;
	}

	/** 
	* @Description: 返回指定序号的tree节点
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:23:01 
	* @param i
	* @return  
	*/ 
	public TreeNode<T> get(int i) {
		return nodeList.get(i);
	}
	
	/** 
	* @Description: 增加一个元素
	* @author Liu Wenjie   
	* @date 2015-12-2 下午5:43:22 
	* @param t
	* @return  
	*/ 
	public void add(TreeNode<T> t){
		if(t != null){
			this.nodeList.add(t);
		}
	}
	
	/** 
	* @Description: 删除一个子元素
	* @author Liu Wenjie   
	* @date 2015-12-2 下午6:47:10 
	* @param t  
	*/ 
	public void remove(TreeNode<T> t){
		if(t != null){
			this.nodeList.remove(t);
		}
	}

}
