/**
* @Company ������ͨ   
* @Title: HttpMailSenderTest.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-23 ����1:07:15 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.bana.common.util.basic.DateUtil;
import org.junit.Before;
import org.junit.Test;

/** 
 * @ClassName: HttpMailSenderTest 
 * @Description: ����MailSend����
 *  
 */
//@Ignore
public class HttpMailSenderTest {
	private HttpMailSender mailSender;
	private String mailVelocityPath = "/email/mailTemplate.vm";
	@Before
	public void before(){
		mailSender = new HttpMailSender();
		mailSender.setPropertyConfigPath("email/emailConfig.properties");
	}

	/**
	 * Test method for {@link org.bana.common.util.email.HttpMailSender#send(org.bana.common.util.email.HttpMailContent)}.
	 * @throws EmailException 
	 */
	@Test
	public void testSendHttpMailContent() throws EmailException {
		HttpMailContent mailContent = new HttpMailContent();
		mailContent.setSubject("����HttpMailSend ����ʼ�");
		
		VelocityContext context = new VelocityContext();
		context.put("name", "�����û�������");
		mailContent.setTemplate(mailVelocityPath, context);
		
		mailSender.addTo("liuwenjiegod@126.com");
		System.out.println(mailSender.send(mailContent));
	}
	
	@Test
	public void testSendHttpMailContent2() throws EmailException{
		List<String> emailList = new ArrayList<String>();
		emailList.add("liuwenjie@i3618.com.cn");
		emailList.add("hantongyang@i3618.com.cn");
		HttpMailContent mailContent = new HttpMailContent();
		StringBuffer subject = new StringBuffer();
		String time = DateUtil.toString(new Date(),"yyyy-MM-dd HH:mm:ss");
		subject.append("消息发送到微信端时失败-消息id为").append(22).append("-")
	    .append(time);
		mailContent.setSubject(subject.toString());
		mailContent.setMsg("备注内容");
		for (String email : emailList) {
			mailSender.addTo(email);
		}
		mailSender.send(mailContent);
	}
	

}
