/**
* @Company weipu   
* @Title: SimpleTree.java 
* @Package org.bana.common.util.tree.impl 
* @author Liu Wenjie   
* @date 2015-12-2 下午2:44:48 
* @version V1.0   
*/ 
package org.bana.common.util.tree.impl;

import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.tree.Tree;
import org.bana.common.util.tree.TreeNode;
import org.bana.common.util.tree.TreeNodeList;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** 
 * @ClassName: SimpleTree 
 * @Description: 一个简单的树形结构对象，此对象要求，T 对象必须要实现自己的eqauls和hashCode方法
 *  
 */
public class SimpleTree<T> implements Tree<T> {
	
	/** 
	* @Fields emptyParentId : 如果父级id为空的话，则使用此值为empty的parent值
	*/ 
	private String emptyParentId = UUID.randomUUID().toString() + "null";
	/** 
	* @Fields parentNodeMap : 保存所有id为key值得map对象，方便在添加节点的时候，快速定位，保存了一个类似指针链条一样的对象
	*/ 
	private Map<Object,SimpleTreeNode<T>> nodeMap = new HashMap<Object,SimpleTreeNode<T>>();
	
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:34:18 
	* @return 
	* @see org.bana.common.util.tree.Tree#getRootTreeNodes() 
	*/ 
	@Override
	public TreeNodeList<T> getRootTreeNodes() {
		return getEmptyRootNode().getChildrenList();
	}
	
	/** 
	* @Description: 获取一个实际不存在的空节点集合
	* @author Liu Wenjie   
	* @date 2015-12-2 下午7:21:38 
	* @return  
	*/ 
	private SimpleTreeNode<T> getEmptyRootNode() {
		SimpleTreeNode<T> emptyRootNode = nodeMap.get(emptyParentId);//所有树结点的一个不存在的唯一节点
		if(emptyRootNode == null){
			emptyRootNode = new SimpleTreeNode<T>();
			emptyRootNode.setId(emptyParentId);
			nodeMap.put(emptyParentId, emptyRootNode);
		}
		return emptyRootNode;
	}
	
	/** 
	* @Description: 返回id对应的节点数据
	* @author Liu Wenjie   
	* @date 2015-12-2 下午8:24:48 
	* @param id
	* @return  
	*/ 
	public SimpleTreeNode<T> findById(Object id){
		return nodeMap.get(id);
	}
	
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:34:18 
	* @return 
	* @see org.bana.common.util.tree.Tree#size() 
	*/ 
	@Override
	public int size() {
		return nodeMap.size() - 1;
	}

	/**
	* <p>Description: 将一个节点加入到树中</p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午4:02:10 
	* @param t
	* @param idName
	* @param parentIdName 
	*/ 
	public void pushNode(T t, String idName, String parentIdName) {
		if(t == null){
			return;
		}
		if(StringUtils.isBlank(idName,parentIdName)){
			throw new BanaUtilException("必须制定id与parentId的名字，不能使用空白名称");
		}
		try {
			//获取id属性
			PropertyDescriptor pd = new PropertyDescriptor(idName, t.getClass());
			Method getIdMethod = pd.getReadMethod();
			if(getIdMethod == null){
				throw new BanaUtilException("指定id名称 " + idName + " 没有找到get方法");
			}
			Object id = getIdMethod.invoke(t);
			if(id == null){
				throw new BanaUtilException("根据指定名称 " + idName + " 获取的值为空，不能将null id的节点添加到属性结构中");
			}
			//获取parentId属性
			PropertyDescriptor parentPd = new PropertyDescriptor(parentIdName, t.getClass());
			Method getParentIdMethod = parentPd.getReadMethod();
			Object parentId = getParentIdMethod.invoke(t);
			//使用指定的对象和属性更新当前tree对象中的属性
			SimpleTreeNode<T> childTreeNode = new SimpleTreeNode<T>();
			childTreeNode.setId(id);
			childTreeNode.setThisData(t);
			if(parentId == null){
				childTreeNode.setParentId(emptyParentId);
			}else{
				childTreeNode.setParentId(parentId);
			}
			SimpleTreeNode<T> parentTreeNode = null;
			if(parentId != null){
				parentTreeNode = new SimpleTreeNode<T>();
				parentTreeNode.setId(parentId);
				parentTreeNode.setParentId(emptyParentId);
//				parentTreeNode.getChildrenList().add(childTreeNode);
			}
			pushToKeep(parentTreeNode,childTreeNode);
		} catch (IntrospectionException e) {
			e.printStackTrace();
			throw new BanaUtilException("获取类的描述性对象时出现错误",e);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			throw new BanaUtilException("访问属性错误",e);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			throw new BanaUtilException("执行方法的参数错误",e);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			throw new BanaUtilException("指定的对象方法不匹配",e);
		}
		
	}

	/** 
	* @Description: 将父子节点保存到tree树中，方法执行的前提是，childTreeNode不为空，parentTreeNode可为空，但当不为空是，id不能为空
	* @author Liu Wenjie   
	* @date 2015-12-2 下午5:47:15 
	* @param parentTreeNode
	* @param childTreeNode  
	*/ 
	private void pushToKeep(SimpleTreeNode<T> parentTreeNode, SimpleTreeNode<T> childTreeNode) {
		//处理parent节点，
		SimpleTreeNode<T> emptyRootNode = getEmptyRootNode();
		if(parentTreeNode == null){
			//如果没有父节点，那么将子节点加入树莲的空根节点上
			childTreeNode.setParentId(emptyParentId);
			emptyRootNode.addChild(childTreeNode);
		}else{
			//如果有父节点，那么先查找一下父节点在当前树形链条中是否已经保存了，如果保存了，就不需要条件父节点了，只需要添加一下子节点
			if(emptyParentId.equals(parentTreeNode.getId())){
				parentTreeNode.addChild(childTreeNode);
				emptyRootNode.addChild(parentTreeNode);
			}else{
				SimpleTreeNode<T> parentNodeInTree = nodeMap.get(parentTreeNode.getId());
				SimpleTreeNode<T> childNodeInTree = nodeMap.get(childTreeNode.getId());
				if(parentNodeInTree == null){//原本没有父节点
					if(childNodeInTree == null){
						parentTreeNode.addChild(childTreeNode);
						emptyRootNode.addChild(parentTreeNode);
					}else{
						//当插入时不存在父级组织，但是存在自组织，只可能是子组织关在了empty的根组织上，如果不是，说明树有问题
						if(emptyParentId.equals(childNodeInTree.getParentId())){
							emptyRootNode.removeChild(childNodeInTree);
							childNodeInTree.setParentId(parentTreeNode.getId());
							childNodeInTree.setThisData(childTreeNode.getThisData());
							parentTreeNode.addChild(childNodeInTree);
							emptyRootNode.addChild(parentTreeNode);
						}else{
							throw new BanaUtilException("创建根组织时，发现树上已经包含 id 为" + childNodeInTree.getId() + " 的元素，而且parentId为" + childNodeInTree.getParentId() + ", 而当前要插入的元素id值为 " + childTreeNode.getId() + " ,parentId 为" + childTreeNode.getParentId());
						}
					}
				}else{//原本就有父节点
					if(childNodeInTree == null){
						parentNodeInTree.addChild(childTreeNode);
					}else{
						//如果树上原本的id值和给定的id是重复的，则不会继续添加对应的元素
						if(childNodeInTree.getParentId().equals(childTreeNode.getParentId())){
							//什么都不做
						}else{
							emptyRootNode.removeChild(childNodeInTree);
							childNodeInTree.setParentId(parentTreeNode.getId());
							childNodeInTree.setThisData(childTreeNode.getThisData());
							parentTreeNode.addChild(childNodeInTree);
							parentNodeInTree.addChild(childNodeInTree);
//							throw new BanaUtilException("创建根组织时，发现树上已经包含 id 为" + childNodeInTree.getId() + " 的元素，而且parentId为" + childNodeInTree.getParentId() + ", 而当前要插入的元素id值为 " + childTreeNode.getId() + " ,parentId 为" + childTreeNode.getParentId());
						}
					}
				}
				
			}
		}
		
		//当把节点的链接挂好后，把节点加入到树的集合中去
		if(parentTreeNode != null && parentTreeNode.getId() != null && !emptyParentId.equals(parentTreeNode.getId())){
			if(nodeMap.get(parentTreeNode.getId()) == null){
				nodeMap.put(parentTreeNode.getId(), parentTreeNode);
			}
		}
		//把子节点也加入到树中
		if(childTreeNode != null && childTreeNode.getId() != null && !emptyParentId.equals(childTreeNode.getId())){
			if(nodeMap.get(childTreeNode.getId()) == null){
				nodeMap.put(childTreeNode.getId(), childTreeNode);
			}
		}
	}

	@Override
	public String toString() {
		TreeNodeList<T> rootTreeNodes = this.getRootTreeNodes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < rootTreeNodes.size(); i++) {
			TreeNode<T> treeNode = rootTreeNodes.get(i);
			printNode(treeNode,0,sb);
		}
		return sb.toString();
	}

	private void printNode(TreeNode<T> treeNode, int level, StringBuffer sb){
		if(level > 0){
			for (int i = 0; i < level; i++) {
				if(i == level-1){
					sb.append("|-- ");
				}else{
					sb.append("  ");
				}
			}
		}
		sb.append(treeNode.getId() + "  :  " + treeNode.getThisData()).append("\n");
		for (int i = 0; i < treeNode.getChildrenList().size(); i++) {
			TreeNode<T> simpleDepartment2 =  treeNode.getChildrenList().get(i);
			printNode(simpleDepartment2, level+1, sb);
		}
	}
}
