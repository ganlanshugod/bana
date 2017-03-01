/**
 * @Company 青鸟软通   
 * @Title: mailSenderTest.java 
 * @Package org.bana.common.util.email 
 * @author Liu Wenjie   
 * @date 2014-8-25 上午11:51:20 
 * @version V1.0   
 */
package org.bana.common.util.email;

import javax.mail.internet.MimeUtility;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @ClassName: mailSenderTest
 * @Description: 测试邮件的基本发送功能
 * 
 */
@Ignore
public class MailSenderTest {

	@Test
	public void testSendHtmlMail() throws Exception {
		// 不要使用SimpleEmail,会出现乱码问题
		HtmlEmail email = new HtmlEmail();
		//email.setSSLOnConnect(true);
		// 这里是发送服务器的名字
		email.setHostName("smtp.qq.com");
		// 编码集的设置
		email.setCharset("utf8");
		// 收件人的邮箱
		email.addTo("ganlanshugod@gmail.com", "小刘");
		// 发送人的邮箱
		email.setFrom("liuwenjie@jbinfo.cn", "刘文杰");
		// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
		email.setAuthentication("314830496", "victoria0");
		email.setSubject("测试Email");
		
		
		email.setHtmlMsg("<img src='http://a.hiphotos.baidu.com/super/whfpf%3D425%2C260%2C50/sign=79955cca0bfa513d51ff3f9e5b5061c9/7a899e510fb30f24aabd2515cb95d143ad4b0385.jpg' ></img> <div style='color:red'>ceshi html</div>");
		// 要发送的信息
		email.setMsg("<img src='http://a.hiphotos.baidu.com/super/whfpf%3D425%2C260%2C50/sign=79955cca0bfa513d51ff3f9e5b5061c9/7a899e510fb30f24aabd2515cb95d143ad4b0385.jpg' ></img> <div style='color:red'>ceshi html</div>");
		// 发送
		email.send();
	}

	@Test
	public void testSendAttashMail() throws Exception {
		MultiPartEmail email = new MultiPartEmail();
		// 这里是发送服务器的名字
		email.setHostName("smtp.gmail.com");
		// 编码集的设置
		email.setCharset("utf8");
		// 收件人的邮箱
		email.addTo("liuwenjie@jbinfo.cn", "小刘");
		// 发送人的邮箱
		
		email.setFrom("ganlanshugod@gmail.com", "刘文杰");
		// 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
		email.setAuthentication("ganlanshugod", "liuwenjie0812");		
		email.setSubject("图片");
		email.setMsg("这是你想要的图片!");
		
		// Create the attachment
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath("C:/Users/liuwenjie/Desktop/分栏文档.doc");// 指定附件在本地的绝对路径
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription("附件描述");// 附件描述
		// attachment.setName("测试");//附件名称
		// 如果附件是中文名会在乱码,attachment.setName(MimeUtility.encodeText("测试"));
		attachment.setName(MimeUtility.encodeText("文档名称"));
		// Create the email message

		// add the attachment
		email.attach(attachment);
		// send the email
		email.send();
	}
}
