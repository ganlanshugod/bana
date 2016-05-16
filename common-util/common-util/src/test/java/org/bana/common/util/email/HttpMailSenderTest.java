/**
* @Company ������ͨ   
* @Title: HttpMailSenderTest.java 
* @Package org.bana.common.util.email 
* @author Liu Wenjie   
* @date 2014-10-23 ����1:07:15 
* @version V1.0   
*/ 
package org.bana.common.util.email;

import org.apache.commons.mail.EmailException;
import org.apache.velocity.VelocityContext;
import org.junit.Before;
import org.junit.Ignore;
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

}
