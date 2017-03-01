/**
* @Company 青鸟软通   
* @Title: TestTreeData.java 
* @Package org.bana.common.util.tree 
* @author Liu Wenjie   
* @date 2015-12-2 下午3:19:29 
* @version V1.0   
*/ 
package org.bana.common.util.tree;

/** 
 * @ClassName: TestTreeData 
 * @Description: 
 *  
 */
public class TestTreeData {

	private Integer id;
	private Integer parentId;
	private String value;
	
	public TestTreeData(){
	}

	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午7:34:44 
	* @param id
	* @param parentId 
	*/ 
	public TestTreeData(Integer id, Integer parentId) {
		super();
		this.id = id;
		this.parentId = parentId;
	}



	/**
	 * @Description: 属性 id 的get方法 
	 * @return id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @Description: 属性 id 的set方法 
	 * @param id 
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * @Description: 属性 parentId 的get方法 
	 * @return parentId
	 */
	public Integer getParentId() {
		return parentId;
	}
	/**
	 * @Description: 属性 parentId 的set方法 
	 * @param parentId 
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	/**
	 * @Description: 属性 value 的get方法 
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @Description: 属性 value 的set方法 
	 * @param value 
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2015-12-2 下午3:20:19 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "TestTreeData [id=" + id + ", parentId=" + parentId + ", value=" + value + "]";
	}
	
}
