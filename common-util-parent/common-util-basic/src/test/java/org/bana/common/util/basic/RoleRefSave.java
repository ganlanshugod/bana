/**
* @Company weipu   
* @Title: RoleRefSave.java 
* @Package org.bana.system.role.dto 
* @author YangShuangshuang   
* @date 2016-7-20 上午9:55:23 
* @version V1.0   
*/ 
package org.bana.common.util.basic;

import java.io.Serializable;
import java.util.List;

/** 
 * @ClassName: RoleRefSave 
 * @Description: 角色关联类
 *  
 */
public class RoleRefSave implements Serializable {

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1221890999717747644L;
	/** 
	* @Fields roleCode : 
	*/
	private String roleCode;
	/** 
	* @Fields users : 
	*/
	private List<Long> users;
	/** 
	* @Fields status : 
	*/
	private List<Integer> orgs;
	
	
	
	
	/**
	 * @Description: 属性 roleCode 的get方法 
	 * @return roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}
	/**
	 * @Description: 属性 roleCode 的set方法 
	 * @param roleCode 
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public List<Long> getUsers() {
		return users;
	}
	public void setUsers(List<Long> users) {
		this.users = users;
	}
	public List<Integer> getOrgs() {
		return orgs;
	}
	public void setOrgs(List<Integer> orgs) {
		this.orgs = orgs;
	}
	/**
	* Description: 
	* @author liuwenjie   
	* @date 2016-9-29 下午1:29:20 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "RoleRefSave [roleCode=" + roleCode + ", users=" + users + ", orgs=" + orgs + "]";
	}
	
}
