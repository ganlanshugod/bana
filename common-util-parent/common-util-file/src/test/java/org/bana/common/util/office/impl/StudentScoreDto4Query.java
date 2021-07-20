/**
* @Company weipu   
* @Title: StudentScoreDto4Query.java 
* @Package com.jbinfo.i3618.message.dto 
* @author Liu Wenjie   
* @date 2015-11-19 上午10:50:44 
* @version V1.0   
*/ 
package org.bana.common.util.office.impl;

import java.io.Serializable;

import org.bana.common.util.basic.ClonePojoUtil.Formatter;

/** 
 * @ClassName: StudentScoreDto4Query 
 * @Description: 学生积分的查询结果对象，对应的是查询结果
 *  
 */
public class StudentScoreDto4Query implements Serializable{

	/** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = -1593344919645028932L;
	
	/** 
	* @Fields id : 
	*/
	private Long id;
	/** 
	* @Fields uploadId : 上传记录的id
	*/
	private Long uploadId;
	/** 
	* @Fields subject : 科目
	*/
	private String subject;
	/** 
	* @Fields score : 学生成绩，
	*/
	private String score;
	/** 
	* @Fields createTime : 创建时间
	*/
	@Formatter("yyyy-MM-dd HH:mm:ss")
	private String createTime;
	/** 
	* @Fields createId : 创建人
	*/
	private Long createId;
	/** 
	* @Fields studentId : 学生id
	*/
	private Long studentId;
	/** 
	* @Fields studentName : 学生名称
	*/
	private String studentName;
	/** 
	* @Fields classOrgId : 学生班级id
	*/
	private Integer classOrgId;
	/** 
	* @Fields classOrgName : 学生班级名称
	*/
	private String classOrgName;
	/** 
	* @Fields comment : 老师给学生的评语
	*/
	private String comment;
	/** 
	* @Fields examinationTime : 考试时间
	*/
	@Formatter("yyyy-MM-dd")
	private String examinationTime;
	/** 
	* @Fields subjectId : 科目ID
	*/
	private String subjectId;
	/** 
	* @Fields title : 成绩单名称
	*/
	private String title;
	/** 
	* @Fields createName : 创建人
	*/
	private String createName;
	/**
	 * @Description: 属性 id 的get方法 
	 * @return id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @Description: 属性 id 的set方法 
	 * @param id 
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @Description: 属性 uploadId 的get方法 
	 * @return uploadId
	 */
	public Long getUploadId() {
		return uploadId;
	}
	/**
	 * @Description: 属性 uploadId 的set方法 
	 * @param uploadId 
	 */
	public void setUploadId(Long uploadId) {
		this.uploadId = uploadId;
	}
	/**
	 * @Description: 属性 subject 的get方法 
	 * @return subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @Description: 属性 subject 的set方法 
	 * @param subject 
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @Description: 属性 score 的get方法 
	 * @return score
	 */
	public String getScore() {
		return score;
	}
	/**
	 * @Description: 属性 score 的set方法 
	 * @param score 
	 */
	public void setScore(String score) {
		this.score = score;
	}
	/**
	 * @Description: 属性 createTime 的get方法 
	 * @return createTime
	 */
	public String getCreateTime() {
		return createTime;
	}
	/**
	 * @Description: 属性 createTime 的set方法 
	 * @param createTime 
	 */
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	/**
	 * @Description: 属性 createId 的get方法 
	 * @return createId
	 */
	public Long getCreateId() {
		return createId;
	}
	/**
	 * @Description: 属性 createId 的set方法 
	 * @param createId 
	 */
	public void setCreateId(Long createId) {
		this.createId = createId;
	}
	/**
	 * @Description: 属性 studentId 的get方法 
	 * @return studentId
	 */
	public Long getStudentId() {
		return studentId;
	}
	/**
	 * @Description: 属性 studentId 的set方法 
	 * @param studentId 
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	/**
	 * @Description: 属性 studentName 的get方法 
	 * @return studentName
	 */
	public String getStudentName() {
		return studentName;
	}
	/**
	 * @Description: 属性 studentName 的set方法 
	 * @param studentName 
	 */
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	/**
	 * @Description: 属性 classOrgId 的get方法 
	 * @return classOrgId
	 */
	public Integer getClassOrgId() {
		return classOrgId;
	}
	/**
	 * @Description: 属性 classOrgId 的set方法 
	 * @param classOrgId 
	 */
	public void setClassOrgId(Integer classOrgId) {
		this.classOrgId = classOrgId;
	}
	/**
	 * @Description: 属性 classOrgName 的get方法 
	 * @return classOrgName
	 */
	public String getClassOrgName() {
		return classOrgName;
	}
	/**
	 * @Description: 属性 classOrgName 的set方法 
	 * @param classOrgName 
	 */
	public void setClassOrgName(String classOrgName) {
		this.classOrgName = classOrgName;
	}
	/**
	 * @Description: 属性 comment 的get方法 
	 * @return comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @Description: 属性 comment 的set方法 
	 * @param comment 
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @Description: 属性 examinationTime 的get方法 
	 * @return examinationTime
	 */
	public String getExaminationTime() {
		return examinationTime;
	}
	/**
	 * @Description: 属性 examinationTime 的set方法 
	 * @param examinationTime 
	 */
	public void setExaminationTime(String examinationTime) {
		this.examinationTime = examinationTime;
	}
	/**
	 * @Description: 属性 subjectId 的get方法 
	 * @return subjectId
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * @Description: 属性 subjectId 的set方法 
	 * @param subjectId 
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * @Description: 属性 title 的get方法 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @Description: 属性 title 的set方法 
	 * @param title 
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @Description: 属性 createName 的get方法 
	 * @return createName
	 */
	public String getCreateName() {
		return createName;
	}
	/**
	 * @Description: 属性 createName 的set方法 
	 * @param createName 
	 */
	public void setCreateName(String createName) {
		this.createName = createName;
	}
	
	
}
