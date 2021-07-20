/**
* @Company weipu   
* @Title: TreeTest.java 
* @Package org.bana.common.util.tree 
* @author Liu Wenjie   
* @date 2015-12-2 下午3:15:52 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;


/** 
 * @ClassName: TreeTest 
 * @Description: 测试一个树形对象的基本方法
 *  
 */
public class TreeTest {
	private Tree<TestTreeData> tree;

	@Before
	public void init(){
		List<TestTreeData> treeList = new ArrayList<TestTreeData>();
//		addTreeData(treeList,1,null);
//		addTreeData(treeList,10,1);
//		addTreeData(treeList,20,1);
////		addTreeData(treeList,10000,100000);
		addTreeData2(treeList);
		long begin = System.currentTimeMillis();
		tree = TreeUtil.getTree(treeList, "id", "parentId");
		long end = System.currentTimeMillis();
		System.out.println("转化 " + tree.size() + " 个树节点共用时" + (end-begin));
	}
	
	private void addTreeData2(List<TestTreeData> treeList){
		treeList.add(new TestTreeData(1, 0));
		treeList.add(new TestTreeData(3, 1));
		treeList.add(new TestTreeData(4, 1));
//		treeList.add(new TestTreeData(2, 1));
//		treeList.add(new TestTreeData(3, 1));
//		treeList.add(new TestTreeData(4, 1));
//		treeList.add(new TestTreeData(5, 2));
//		treeList.add(new TestTreeData(6, 3));
//		treeList.add(new TestTreeData(7, 4));
//		treeList.add(new TestTreeData(8, 4));
//		treeList.add(new TestTreeData(9, 4));
//		treeList.add(new TestTreeData(10, 6));
//		treeList.add(new TestTreeData(11, 6));
//		treeList.add(new TestTreeData(12, 2));
//		treeList.add(new TestTreeData(14, 13));
	}
	
	private void addTreeData(List<TestTreeData> treeList, Integer parent, Integer child) {
		Random random = new Random();
		for (int i = 0; i < child; i++) {
			TestTreeData treeData = new TestTreeData();
			treeData.setValue("value"+random.nextInt(1000));
			treeData.setId(child+i);
			treeData.setParentId(parent + random.nextInt(parent));
			treeList.add(treeData);
		}
	}

	@Test
	public void testSize(){
		System.out.println(tree.size());
	}
	
	@Test
	public void testIterator(){
		TreeNodeList<TestTreeData> rootTreeNodes = tree.getRootTreeNodes();
		printNode(rootTreeNodes,1);
	}
	
	
	
	private void printNode(TreeNodeList<TestTreeData> treeNodeList,int level){
		for(int i = 0;i < treeNodeList.size(); i++){
			TreeNode<TestTreeData> treeNode = treeNodeList.get(i);
			System.out.print("层级"+ level + ":" + treeNode.getId() + "\t");
			printNode(treeNode.getChildrenList(),level+1);
			System.out.println();
		}
	}
}
