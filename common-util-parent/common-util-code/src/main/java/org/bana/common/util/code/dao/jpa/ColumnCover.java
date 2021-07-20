/**
* @Company weipu
* @Title: ColumnCover.java 
* @Package org.bana.common.util.code.dao.jpa 
* @author liuwenjie   
* @date Sep 22, 2020 12:47:18 PM 
* @version V1.0   
*/ 
package org.bana.common.util.code.dao.jpa;

/** 
* @ClassName: ColumnCover 
* @Description: JPA列的Cover类
* @author liuwenjie   
*/
public class ColumnCover {
	
	/** 
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date Sep 22, 2020 1:03:40 PM 
	* @param coverClass
	* @param coverJdbcTypeName
	* @param coverJavaClass 
	*/ 
	public ColumnCover(String coverClass, String coverJdbcTypeName, String coverJavaClass) {
		super();
		this.coverClass = coverClass;
		this.coverJdbcTypeName = coverJdbcTypeName;
		this.coverJavaClass = coverJavaClass;
	}
	private String coverClass;
	private String coverJdbcTypeName;
	private String coverJavaClass;
	
	
	
	
	public String getCoverJavaClass() {
		return coverJavaClass;
	}
	public void setCoverJavaClass(String coverJavaClass) {
		this.coverJavaClass = coverJavaClass;
	}
	public String getCoverClass() {
		return coverClass;
	}
	public void setCoverClass(String coverClass) {
		this.coverClass = coverClass;
	}
	public String getCoverJdbcTypeName() {
		return coverJdbcTypeName;
	}
	public void setCoverJdbcTypeName(String coverJdbcTypeName) {
		this.coverJdbcTypeName = coverJdbcTypeName;
	}
	@Override
	public String toString() {
		return "ColumnCover [coverClass=" + coverClass + ", coverJdbcTypeName=" + coverJdbcTypeName
				+ ", coverJavaClass=" + coverJavaClass + "]";
	}
	
}
