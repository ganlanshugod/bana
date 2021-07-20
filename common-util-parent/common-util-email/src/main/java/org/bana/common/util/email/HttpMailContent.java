/**
* @Company weipu   
* @Title: HttpMailContent.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-23 上午11:02:15 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import java.io.StringWriter;
import java.util.List;

import org.apache.commons.mail.EmailAttachment;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.bana.common.util.exception.BanaUtilException;
import org.bana.common.util.velocity.SimpleVelocityEngineFactory;

/** 
 * @ClassName: HttpMailContent 
 * @Description: 邮件发送的模板路径 
 *  
 */
public class HttpMailContent {
	private String msg; //邮件内容
	private String subject; //邮件主题
	private List<EmailAttachment> attachments; //附件
	private MailType mailType;

	public enum MailType{
		Plain,Html
	}
	
	
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:40:41  
	*/ 
	public HttpMailContent() {
		super();
	}
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:40:34 
	* @param msg 
	*/ 
	public HttpMailContent(String msg) {
		super();
		this.msg = msg;
	}
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:40:24 
	* @param msg
	* @param subject 
	*/ 
	public HttpMailContent(String msg, String subject) {
		super();
		this.msg = msg;
		this.subject = subject;
	}
	/** 
	* <p>Description: </p> 
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:40:11 
	* @param msg
	* @param subject
	* @param attachments 
	*/ 
	public HttpMailContent(String msg, String subject,
			List<EmailAttachment> attachments) {
		super();
		this.msg = msg;
		this.subject = subject;
		this.attachments = attachments;
	}

	/** 
	* @Description: 设置邮件模板，使用模板初始化邮件内容
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:45:10 
	* @param mailVelocityPath
	* @param context  
	*/ 
	public void setTemplate(String mailVelocityPath,VelocityContext context){
		Template template = SimpleVelocityEngineFactory.getTemplate(mailVelocityPath);
		StringWriter writer = new StringWriter();  
		try {
			template.merge(context, writer);
			this.msg = writer.toString();
			this.mailType = MailType.Html;
		} catch (Exception e) {
			throw new BanaUtilException("使用模板路径" + mailVelocityPath + "转换模板出错",e);
		} // 转换  
	}
	
	
	/**
	 * @Description: 属性 mailType 的get方法 
	 * @return mailType
	 */
	public MailType getMailType() {
		return mailType;
	}
	/**
	 * @Description: 属性 mailType 的set方法 
	 * @param mailType 
	 */
	public void setMailType(MailType mailType) {
		this.mailType = mailType;
	}
	/*===================getter and setter =============*/
	/**
	 * @Description: 属性 msg 的get方法 
	 * @return msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @Description: 属性 msg 的set方法 
	 * @param msg 
	 */
	public void setMsg(String msg) {
		this.msg = msg;
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
	 * @Description: 属性 attachments 的get方法 
	 * @return attachments
	 */
	public List<EmailAttachment> getAttachments() {
		return attachments;
	}
	/**
	 * @Description: 属性 attachments 的set方法 
	 * @param attachments 
	 */
	public void setAttachments(List<EmailAttachment> attachments) {
		this.attachments = attachments;
	}
	/**
	* <p>Description: </p> 
	* @author liuwenjie   
	* @date 2016-3-2 下午4:41:31 
	* @return 
	* @see java.lang.Object#toString() 
	*/ 
	@Override
	public String toString() {
		return "HttpMailContent [msg=" + msg + ", subject=" + subject + ", attachments=" + attachments + ", mailType=" + mailType + "]";
	}
	
}
