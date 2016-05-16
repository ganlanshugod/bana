/**
* @Company 青鸟软通   
* @Title: HttpMailSender.java 
* @Package org.bana.common.email.htmlimpl 
* @author Liu Wenjie   
* @date 2014-9-3 下午12:35:56 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.bana.common.util.basic.StringUtils;
import org.bana.common.util.basic.URLUtil;
import org.bana.common.util.basic.ValidateUtils;
import org.bana.common.util.email.HttpMailContent.MailType;
import org.bana.common.util.exception.BanaUtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * @ClassName: HttpMailSender 
 * @Description: html 
 *  
 */
public class HttpMailSender extends HtmlEmail {
	private static final Logger LOG =  LoggerFactory.getLogger(URLUtil.class);
	/** 发件人信息配置的路径   */
	private String propertyConfigPath;
	/** 发送邮件的属性配置文件*/
	private PropertiesConfiguration propertyConfig;
	
	/** 
	* @Fields dafaultPropertiesFileEncoding : 
	*/ 
	private static String defaultPropertiesFileEncoding = "utf8";
	
	//邮件中的几个固定属性
	private enum EmailProperties{
		发件人邮箱("from.email"),
		发件人用户名("from.user_name"),
		发件人别名("from.user_sign_name"),
		发件人密码("from.password"),
		发件人服务器地址("from.host_name"),
		
		邮件编码格式("mail.charset");
		
		private String key;
		private EmailProperties(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
	}
	
	/** 
	* @Description: 发送使用HttpMailContent构建的邮件对象
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:58:32 
	* @param mailContent
	* @return  
	*/ 
	public String send(HttpMailContent mailContent){
		// 如果发送的邮件内容是通过模板生成的，那就通过邮件模板生成邮件内容，如果不是，则通过主动设置进行发送邮件
		this.initMailContext(mailContent);
		return this.send();
	}
	
	
	
	/** 
	* @Description: 可以使用属性文件初始化配置的邮件发送方法
	* @author Liu Wenjie   
	* @date 2014-10-23 下午12:58:28 
	* @param mailContent  
	*/ 
	private void initMailContext(HttpMailContent mailContent) {
		if(mailContent == null){
			LOG.error("设置的准备发送邮件的参数 mailContent 为  null");
			return;
		}
		try {
			//邮件内容
			if(!StringUtils.isBlank(mailContent.getMsg())){
//				if(MailType.Html.equals(mailContent.getMailType())){
//					super.setHtmlMsg(mailContent.getMsg());
//				}else{
//					super.setMsg(mailContent.getMsg());
//				}
				super.setHtmlMsg(mailContent.getMsg());
			}
			//邮件主题
			if(!StringUtils.isBlank(mailContent.getSubject())){
				super.setSubject(mailContent.getSubject());
			}
			//邮件附件
			List<EmailAttachment> attachments = mailContent.getAttachments();
			if(attachments != null && !attachments.isEmpty()){
				for (EmailAttachment emailAttachment : attachments) {
					super.attach(emailAttachment);
				}
			}
			
		} catch (EmailException e) {
			throw new BanaUtilException("init mailContext error!!!!",e);
		}
	}



	/**
	* <p>Description: 邮件发送的方法 </p> 
	* @author Liu Wenjie   
	* @date 2014-9-3 下午2:10:47 
	* @return 
	* @see org.apache.commons.mail.Email#send() 
	*/ 
	public String send(){
		try {
			this.initProperty();
			return super.send();
		} catch (EmailException e) {
			throw new BanaUtilException("send Email error!!!",e);
		}
	}
	
	/** 
	* @Description: 根据现有参数初始化对应的属性，包括发件人
	* @author Liu Wenjie   
	 * @throws EmailException 
	* @date 2014-9-3 下午2:11:45   
	*/ 
	private void initProperty() throws EmailException {
		if(propertyConfig == null){
			return;
		}
		//如果配置是通过property文件进行设置的，并且没有手动设置这些属性，那么就通过property中的属性进行设置，
		//准备发送邮件的属性 ,发件人邮箱属性,服务器地址、邮件格式
		//编码格式
		String mailCharset = propertyConfig.getString(EmailProperties.邮件编码格式.getKey());
		if(!StringUtils.isEmpty(mailCharset) && StringUtils.isEmpty(super.charset)){
			super.setCharset(mailCharset);
		}
		//服务器地址
		String postName = propertyConfig.getString(EmailProperties.发件人服务器地址.getKey());
		if(!StringUtils.isBlank(postName)&&StringUtils.isEmpty(super.getHostName())){
			super.setHostName(postName);
		}
		//邮件地址和别名,认证信息
		String email = propertyConfig.getString(EmailProperties.发件人邮箱.getKey());
		if(!ValidateUtils.isEmail(email)){
			LOG.error("email " + email + "is not validate !!!!!!!");
		}else if(super.getFromAddress() == null){
			//别名
			String otherName = propertyConfig.getString(EmailProperties.发件人别名.getKey());
			if(StringUtils.isBlank(otherName)){
				super.setFrom(email);
			}else{
				super.setFrom(email, otherName);
			}
			//认证信息
			String userName = propertyConfig.getString(EmailProperties.发件人用户名.getKey());
			if(StringUtils.isBlank(userName)){
				userName = email.split("@")[0];
			}
			String password = propertyConfig.getString(EmailProperties.发件人密码.getKey());
			super.setAuthentication(userName, password);
		}
		
	}
	
	/**
	* @Description: 设置邮件对应的信息属性,通过配置文件设置的属性，不会覆盖通过方法设置的参数属性
	* @author Liu Wenjie   
	* @date 2014-9-3 下午2:07:23
	 */
	public void setPropertyConfigPath(String propertyConfigPath){
		this.propertyConfigPath = propertyConfigPath;
		//如果发件人的配置文件路存在，则去加载此配置文件
		if(StringUtils.isNotBlank(this.propertyConfigPath)){
			//loading配置文件，设置对应的属性
			try {
				propertyConfig = new PropertiesConfiguration();
				propertyConfig.setEncoding(defaultPropertiesFileEncoding);
				propertyConfig.load(propertyConfigPath);
			} catch (ConfigurationException e) {
				throw new BanaUtilException("loading config error: " + propertyConfigPath,e);
			}  
		}
	}



	/**
	 * @Description: 属性 defaultPropertiesFileEncoding 的set方法 
	 * @param defaultPropertiesFileEncoding 
	 */
	public static void setDefaultPropertiesFileEncoding(
			String defaultPropertiesFileEncoding) {
		HttpMailSender.defaultPropertiesFileEncoding = defaultPropertiesFileEncoding;
	}
	

}
